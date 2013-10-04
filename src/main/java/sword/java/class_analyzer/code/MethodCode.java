package sword.java.class_analyzer.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;
import sword.java.class_analyzer.pool.MethodEntry;

/**
 * Collection of InstructionBlocks make a self contained algorithm.
 *
 * In this context a method contain a first block that will be always executed
 * first. The rest of blocks are not public for other methods and will only be
 * executed by another block within this method.
 */
public class MethodCode {

    private static class BlockHolder {
        public int index;
        public InstructionBlock block;

        public BlockHolder(int index, InstructionBlock block) {
            this.index = index;
            this.block = block;
        }
    }

    private List<BlockHolder> mHolders = new ArrayList<BlockHolder>();
    private String mInvalidReason;

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

    public MethodCode(InputStream inStream, int codeLength, ConstantPool pool) throws IOException, FileError {
        final byte code[] = new byte[codeLength];
        Utils.fillBuffer(inStream, code);

        Set<Integer> indexes = new HashSet<Integer>();
        indexes.add(0);

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

        Set<MethodEntry> methods = getKnownInvokedMethods();
        if (methods.size() > 0) {
            result = result + "depends on methods:\n";
            for(MethodEntry method : methods) {
                result = result + "  " + method.getName() + '\n';
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
            result = result + holder.block.disassemble(holder.index);
        }

        result = result + "}\n";

        return result;
    }

    public Set<MethodEntry> getKnownInvokedMethods() {
        Set<MethodEntry> methods = new HashSet<MethodEntry>();
        for (BlockHolder holder : mHolders) {
            Set<MethodEntry> insMethods = holder.block.getKnownInvokedMethods();
            if (insMethods.size() > 0) {
                methods.addAll(insMethods);
            }
        }

        return methods;
    }

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

    public boolean isValid() {
        return mInvalidReason == null;
    }
}
