package sword.java.class_analyzer.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.code.InstructionBlock.DisassemblerOptions;
import sword.java.class_analyzer.interf.KnownReferencesProvider;
import sword.java.class_analyzer.pool.AbstractMethodEntry;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;

/**
 * Collection of InstructionBlocks make a self contained algorithm.
 *
 * In this context a method contain a first block that will be always executed
 * first. The rest of blocks are not public for other methods and will only be
 * executed by another block within this method.
 */
public class MethodCode implements KnownReferencesProvider {

    private static class BlockHolder {
        public int index;
        public InstructionBlock block;

        public BlockHolder(int index, InstructionBlock block) {
            this.index = index;
            this.block = block;
        }
    }

    private final List<BlockHolder> mHolders = new ArrayList<BlockHolder>();
    private String mInvalidReason;

    /**
     * Set to true if there is code passed that not correspond to any block.
     */
    public final boolean hasUnreferencedCode;

    private int findFirstIndexNoMatchingBlock(Set<Integer> indexes) {

        int targetIndex = -1;

        for (Integer index : indexes) {
            BlockHolder found = null;
            for (BlockHolder holder : mHolders) {
                if (index == holder.index) {
                    found = holder;
                    break;
                }
            }

            if (found == null) {
                targetIndex = index;
                break;
            }
        }

        return targetIndex;
    }

    private BlockHolder findBlockForIndex(int index) {
        for (BlockHolder holder : mHolders) {
            if (index >= holder.index && index < holder.block.byteCodeSize()) {
                return holder;
            }
        }

        return null;
    }

    private int findExpectedEndingIndex(int index, Set<Integer> indexes, int codeLength) {
        int firstIndex = -1;

        for(Integer startingIndex : indexes) {
            if (index < startingIndex && (firstIndex < 0 || startingIndex < firstIndex)) {
                firstIndex = startingIndex;
            }
        }

        if (firstIndex < 0) {
            firstIndex = codeLength;
        }

        return firstIndex;
    }

    private int findNewBlockSuitableArrayPosition(int index, List<BlockHolder> holders) {
        for(int i=0; i<holders.size(); i++) {
            if (holders.get(i).index > index) return i;
        }

        return holders.size();
    }

    private void ensureHoldersAreSorteredByIndex(List<BlockHolder> holders) {
        int index = -1;
        for(BlockHolder holder : holders) {
            if (index >= holder.index) {
                throw new IllegalStateException();
            }

            index = holder.index;
        }
    }

    public MethodCode(byte code[], ConstantPool pool, final Set<Integer> indexes) throws IOException, FileError {
        final int codeLength = code.length;
        while (indexes.size() > mHolders.size()) {

            final int targetIndex = findFirstIndexNoMatchingBlock(indexes);
            final BlockHolder blockToSplit = findBlockForIndex(targetIndex);

            final InstructionBlock newBlock;
            if (blockToSplit != null) {
                assert(blockToSplit.index < targetIndex);

                newBlock = blockToSplit.block.split(targetIndex - blockToSplit.index);
                final BlockHolder newHolder = new BlockHolder(targetIndex, newBlock);
                mHolders.add(mHolders.indexOf(blockToSplit) + 1, newHolder);
            }
            else {
                final int endingIndex = findExpectedEndingIndex(targetIndex, indexes, codeLength);
                newBlock = new InstructionBlock(code, targetIndex, endingIndex, pool);
                final BlockHolder newHolder = new BlockHolder(targetIndex, newBlock);
                mHolders.add(findNewBlockSuitableArrayPosition(targetIndex, mHolders), newHolder);
            }

            ensureHoldersAreSorteredByIndex(mHolders);

            indexes.addAll(newBlock.getKnownBlockPositions(targetIndex));
        }

        for (int i=0; i<mHolders.size(); i++) {
            final InstructionBlock block = mHolders.get(i).block;
            if (!block.isValid()) {
                mInvalidReason = block.getInvalidReason() + " at block" + i;
                break;
            }
        }

        // Checks if there is unreferenced code
        int checkingIndex = 0;
        boolean indexUpdated = true;
        while (checkingIndex < codeLength && indexUpdated) {
            indexUpdated = false;
            for (BlockHolder holder : mHolders) {
                if (holder.index == checkingIndex) {
                    checkingIndex += holder.block.byteCodeSize();
                    indexUpdated = true;
                }
            }
        }

        hasUnreferencedCode = checkingIndex < codeLength;
    }

    private static byte[] fillCodeBuffer(InputStream inStream, int codeLength) throws IOException, FileError {
        final byte code[] = new byte[codeLength];
        Utils.fillBuffer(inStream, code);
        return code;
    }

    public MethodCode(InputStream inStream, int codeLength, ConstantPool pool) throws IOException, FileError {
        this(fillCodeBuffer(inStream, codeLength), pool, new HashSet<Integer>(Arrays.asList(0)));
    }

    @Override
    public String toString() {

        String result = "";

        Set<FieldEntry> fields = getKnownReferencedFields();
        if (fields.size() > 0) {
            result = result + "depends on fields:\n";
            for(FieldEntry field : fields) {
                result = result + "  " + field.getName() + '\n';
            }
        }

        Set<AbstractMethodEntry> methods = getKnownInvokedMethods();
        if (methods.size() > 0) {
            result = result + "depends on methods:\n";
            for(AbstractMethodEntry method : methods) {
                result = result + "  " + method.toString() + '\n';
            }
        }

        Set<ClassReferenceEntry> classes = getKnownReflectionClassReferences();
        if (classes.size() > 0) {
            result = result + "depends on classes:\n";
            for(ClassReferenceEntry cls : classes) {
                result = result + "  " + cls.getReference().getQualifiedName() + '\n';
            }
        }

        if (!isValid()) {
            result = result + "WARNING: At least one instruction block is not valid. " + mInvalidReason + '\n';
        }

        result = result + "{\n";
        for (int i=0; i<mHolders.size(); i++) {
            final BlockHolder holder = mHolders.get(i);
            if (i != 0) {
                result = result + "\n Block" + i + ":\n";
            }
            final int options = DisassemblerOptions.SHOW_INDEX | DisassemblerOptions.SHOW_OPCODE;
            result = result + holder.block.disassemble(holder.index, options);
        }

        result = result + "}\n";

        return result;
    }

    @Override
    public Set<AbstractMethodEntry> getKnownInvokedMethods() {
        Set<AbstractMethodEntry> methods = new HashSet<AbstractMethodEntry>();
        for (BlockHolder holder : mHolders) {
            Set<AbstractMethodEntry> insMethods = holder.block.getKnownInvokedMethods();
            if (insMethods.size() > 0) {
                methods.addAll(insMethods);
            }
        }

        return methods;
    }

    @Override
    public Set<FieldEntry> getKnownReferencedFields() {
        Set<FieldEntry> fields = new HashSet<FieldEntry>();
        for (BlockHolder holder : mHolders) {
            Set<FieldEntry> insFields = holder.block.getKnownReferencedFields();
            if (insFields.size() > 0) {
                fields.addAll(insFields);
            }
        }

        return fields;
    }

    @Override
    public Set<ClassReferenceEntry> getKnownReflectionClassReferences() {
        Set<ClassReferenceEntry> classes = new HashSet<ClassReferenceEntry>();
        for (BlockHolder holder : mHolders) {
            Set<ClassReferenceEntry> insClasses = holder.block.getKnownReflectionClassReferences();
            if (insClasses.size() > 0) {
                classes.addAll(insClasses);
            }
        }

        return classes;
    }

    public boolean isValid() {
        return mInvalidReason == null;
    }

    public String getInvalidReason() {
        return mInvalidReason;
    }
}
