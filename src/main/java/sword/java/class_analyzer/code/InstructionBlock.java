package sword.java.class_analyzer.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.instructions.AbstractInstruction;
import sword.java.class_analyzer.interf.KnownReferencesProvider;
import sword.java.class_analyzer.pool.AbstractMethodEntry;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;

/**
 * Class representing a sortered list of instructions to execute.
 *
 * A block is understood in this context as group of instructions that are
 * executed always together and in the same order, being only the first one the
 * only one public on the block for other blocks to jump into. Having a block it
 * is ensured that none of the instructions in the middle, or the last, will be
 * executed without executing the previous one first.
 */
public class InstructionBlock implements KnownReferencesProvider {

    public static final class DisassemblerOptions {
       public static final int SHOW_INDEX = 1;
       public static final int SHOW_OPCODE = 2;
    }

    private static class InstructionHolder {
        public int index;
        public AbstractInstruction instruction;

        public InstructionHolder(int index, AbstractInstruction instruction) {
            this.index = index;
            this.instruction = instruction;
        }
    }

    private final List<InstructionHolder> mHolders;
    private String mInvalidReason;

    private transient final Set<Integer> mKnownBlockOffsets;
    private transient int mByteCodeSize;

    public InstructionBlock(byte code[], int startIndex, int endIndex, ConstantPool pool) throws IOException, FileError {
        mHolders = new ArrayList<InstructionHolder>();
        mKnownBlockOffsets = new HashSet<Integer>();

        int counter = startIndex;
        endIndex = Math.min(code.length, endIndex);

        try {
            while (counter < endIndex) {
                final int blockRelativeIndex = counter - startIndex;
                AbstractInstruction instruction = InstructionFactory.match(code, counter, pool);
                InstructionHolder holder = new InstructionHolder(blockRelativeIndex, instruction);
                mHolders.add(holder);

                Set<Integer> positions = instruction.knownBranches(blockRelativeIndex);

                if (!positions.isEmpty()) {
                    for(Integer position : positions) {
                        if (position >= blockRelativeIndex) {
                            endIndex = Math.min(endIndex, position + startIndex);
                        }
                    }

                    mKnownBlockOffsets.addAll(positions);
                }

                counter += instruction.byteCodeSize(counter);

                if (instruction.alwaysJump()) {
                    break;
                }
            }
        } catch(InvalidByteCodeException e) {
            mInvalidReason = e.getInvalidReason();
        }

        mByteCodeSize = extractByteCodeSize(startIndex, mHolders);
    }

    private Set<Integer> extractKnownBlockOffsets(List<InstructionHolder> holders) {

        Set<Integer> result = new HashSet<Integer>();

        for (InstructionHolder holder : holders) {
            final AbstractInstruction instruction = holder.instruction;
            final int index = holder.index;

            if (instruction.canBranch()) {
                Set<Integer> positions = instruction.knownBranches(index);
                result.addAll(positions);
            }
        }

        return result;
    }

    private int extractByteCodeSize(int index, List<InstructionHolder> holders) {

        final int holderCount = holders.size();
        int result = 0;

        for(int i=0; i<holderCount; i++) {
            final InstructionHolder holder = holders.get(i);
            result += holder.instruction.byteCodeSize(index + result);
        }

        return result;
    }

    private InstructionBlock(int index, List<InstructionHolder> list, String invalidReason) {
        mHolders = list;

        mInvalidReason = invalidReason;

        mKnownBlockOffsets = extractKnownBlockOffsets(list);
        mByteCodeSize = extractByteCodeSize(index, list);
    }

    /**
     * Returns a new block containing all instructions from that index to the
     * end of the block. This method also modifies the current block removing
     * all instructions transfered to the new block.
     * @param index Index relative to the beginning of this block where the block should be cut.
     * @throws IllegalArgumentException if the provided index is not valid or it
     * is impossible to cut the block at that exact position.
     */
    public InstructionBlock split(int index) throws IllegalArgumentException {
        int arraySplitPosition = 0;

        final int originalArraySize = mHolders.size();
        for (int i=0; i<originalArraySize; i++) {
            InstructionHolder holder = mHolders.get(i);
            if (holder.index == index) {
                arraySplitPosition = i;

                break;
            }
        }

        if (arraySplitPosition <= 0) {
            throw new IllegalArgumentException();
        }

        final int subListSize = originalArraySize - arraySplitPosition;
        List<InstructionHolder> subList = new ArrayList<InstructionHolder>(subListSize);
        for (int i=arraySplitPosition; i<originalArraySize; i++) {
            subList.add(mHolders.get(i));
        }

        mHolders.removeAll(subList);

        for (InstructionHolder holder : subList) {
            holder.index -= index;
        }
        mKnownBlockOffsets.clear();
        mKnownBlockOffsets.addAll(extractKnownBlockOffsets(mHolders));
        mByteCodeSize = extractByteCodeSize(index, mHolders);

        // This is assuming the only reason for a block to be invalid is that
        // the last instruction connot be resolved. If this is not true the next
        // piece of code may fail.
        final String invalidReason = mInvalidReason;
        mInvalidReason = null;

        return new InstructionBlock(index, subList, invalidReason);
    }

    public Set<Integer> getKnownBlockPositions(int index) {
        Set<Integer> result = new HashSet<Integer>(mKnownBlockOffsets.size());
        for (Integer offset : mKnownBlockOffsets) {
            result.add(offset + index);
        }

        return result;
    }

    @Override
    public Set<AbstractMethodEntry> getKnownInvokedMethods() {
        Set<AbstractMethodEntry> methods = new HashSet<AbstractMethodEntry>();
        for (InstructionHolder holder : mHolders) {
            Set<AbstractMethodEntry> insMethods = holder.instruction.getKnownInvokedMethods();
            if (insMethods.size() > 0) {
                methods.addAll(insMethods);
            }
        }

        return methods;
    }

    @Override
    public Set<FieldEntry> getKnownReferencedFields() {
        Set<FieldEntry> fields = new HashSet<FieldEntry>();
        for (InstructionHolder holder : mHolders) {
            Set<FieldEntry> insFields = holder.instruction.getKnownReferencedFields();
            if (insFields.size() > 0) {
                fields.addAll(insFields);
            }
        }

        return fields;
    }

    @Override
    public Set<ClassReferenceEntry> getKnownReflectionClassReferences() {
        Set<ClassReferenceEntry> classes = new HashSet<ClassReferenceEntry>();
        for (InstructionHolder holder : mHolders) {
            Set<ClassReferenceEntry> insClasses = holder.instruction.getKnownReflectionClassReferences();
            if (insClasses.size() > 0) {
                classes.addAll(insClasses);
            }
        }

        return classes;
    }

    public int byteCodeSize() {
        return mByteCodeSize;
    }

    private static final int OPCODE_MIN_SPACE = 4;

    public String disassemble(int offset, int options) {
        String result = "";

        final boolean showIndex = (options & DisassemblerOptions.SHOW_INDEX) != 0;
        final boolean showOpcodes = (options & DisassemblerOptions.SHOW_INDEX) != 0;

        for (InstructionHolder holder : mHolders) {
            result = result + "  ";

            if (showIndex) {
                result = result + (holder.index + offset) + '\t';
            }

            if (showOpcodes) {
                try {
                    final AbstractInstruction instruction = holder.instruction;
                    final int validOpcodesToShow = instruction.byteCodeSize(holder.index + offset);

                    final byte opcodes[] = new byte[validOpcodesToShow];
                    // retrieveByteCode can throw IllegalArgumentException
                    // when the size of the instruction depends on the index.
                    // This is because holder.index + offset != 0. Used above to
                    // retrieve the size for the buffer.
                    // TODO: Provide a solution for this
                    instruction.retrieveByteCode(opcodes, 0);

                    for (int i= 0; i<OPCODE_MIN_SPACE; i++) {
                        if (i < validOpcodesToShow) {
                            //result = result + ' ' + Integer.toHexString((opcodes[i]) & 0xFF);
                            result = result + String.format("%02X", (opcodes[i]) & 0xFF) + ' ';
                        }
                        else {
                            result = result + "   ";
                        }
                    }
                }
                catch (IllegalArgumentException e) {
                    result = result + "<invalid>   ";
                }
            }

            result = result + holder.instruction.disassemble() + '\n';
        }

        return result;
    }

    @Override
    public String toString() {

        String result = "";
        if (!isValid()) {
            result = result + "WARNING: Instruction block not valid. " + mInvalidReason + '\n';
        }

        return result + disassemble(0, 0);
    }

    public boolean isValid() {
        return mInvalidReason == null;
    }

    public String getInvalidReason() {
        return mInvalidReason;
    }
}
