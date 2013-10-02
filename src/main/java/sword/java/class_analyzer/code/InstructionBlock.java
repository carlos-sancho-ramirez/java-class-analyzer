package sword.java.class_analyzer.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;

/**
 * Class representing a sortered list of instructions to execute.
 *
 * A block is understood in this context as group of instructions that are
 * executed always together and in the same order, being only the first one the
 * only one public on the block for other blocks to jump into. Having a block it
 * is ensured that none of the instructions in the middle, or the last, will be
 * executed without executing the previous one first.
 */
public class InstructionBlock {

    private static class InstructionHolder {
        public int index;
        public AbstractInstruction instruction;

        public InstructionHolder(int index, AbstractInstruction instruction) {
            this.index = index;
            this.instruction = instruction;
        }
    }

    private boolean mValid;
    private final List<InstructionHolder> mHolders;
    private String mInvalidReason;

    private transient final Set<Integer> mKnownBlockOffsets;
    private transient int mByteCodeSize;

    public InstructionBlock(byte code[], int startIndex, int endIndex, ConstantPool pool) throws IOException, FileError {
        mHolders = new ArrayList<InstructionHolder>();
        mKnownBlockOffsets = new HashSet<Integer>();

        int counter = startIndex;
        endIndex = Math.min(code.length, endIndex);

        mValid = true;
        try {
            while (counter < endIndex) {
                final int blockRelativeIndex = counter - startIndex;
                AbstractInstruction instruction = Instances.match(code, counter, pool);
                InstructionHolder holder = new InstructionHolder(blockRelativeIndex, instruction);
                mHolders.add(holder);

                if (instruction.canBranch()) {
                    Set<Integer> positions = instruction.knownBranches(blockRelativeIndex);
                    for(Integer position : positions) {
                        if (position >= blockRelativeIndex) {
                            endIndex = Math.min(endIndex, position + startIndex);
                        }
                    }

                    mKnownBlockOffsets.addAll(positions);
                }

                counter += instruction.byteCodeSize();

                if (instruction.alwaysJump()) {
                    break;
                }
            }
        } catch(InvalidByteCodeException e) {
            mValid = false;
            mInvalidReason = e.getInvalidReason();
        }

        mByteCodeSize = extractByteCodeSize(mHolders);
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

    private int extractByteCodeSize(List<InstructionHolder> holders) {
        int result = 0;
        for(InstructionHolder holder : holders) {
            result += holder.instruction.byteCodeSize();
        }

        return result;
    }

    private InstructionBlock(List<InstructionHolder> list, String invalidReason) {
        mHolders = list;

        mValid = invalidReason != null;
        mInvalidReason = invalidReason;

        mKnownBlockOffsets = extractKnownBlockOffsets(list);
        mByteCodeSize = extractByteCodeSize(list);
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

        List<InstructionHolder> subList = mHolders.subList(arraySplitPosition, originalArraySize);
        mHolders.removeAll(subList);

        for (InstructionHolder holder : subList) {
            holder.index -= index;
        }
        mKnownBlockOffsets.clear();
        mKnownBlockOffsets.addAll(extractKnownBlockOffsets(mHolders));
        mByteCodeSize = extractByteCodeSize(mHolders);

        return new InstructionBlock(subList, mInvalidReason);
    }

    public Set<Integer> getKnownBlockPositions(int index) {
        Set<Integer> result = new HashSet<Integer>(mKnownBlockOffsets.size());
        for (Integer offset : mKnownBlockOffsets) {
            result.add(offset + index);
        }

        return result;
    }

    public int byteCodeSize() {
        return mByteCodeSize;
    }

    public String disassemble(int offset) {
        String result = "";

        for (InstructionHolder holder : mHolders) {
            result = result + "  " + (holder.index + offset) + '\t' + holder.instruction.disassemble() + '\n';
        }

        return result;
    }

    @Override
    public String toString() {

        String result = "";
        if (!mValid) {
            result = result + "WARNING: Instruction bundle not valid. " + mInvalidReason + '\n';
        }

        return result + disassemble(0);
    }

    public boolean isValid() {
        return mValid;
    }

    public String getInvalidReason() {
        return mInvalidReason;
    }
}
