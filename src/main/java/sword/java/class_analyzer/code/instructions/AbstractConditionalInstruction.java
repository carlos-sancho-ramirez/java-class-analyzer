package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public abstract class AbstractConditionalInstruction extends AbstractInstruction {

    private final int mOffset;

    @Override
    public boolean canBranch() {
        return true;
    }

    @Override
    public Set<Integer> knownBranches(int index) {
        HashSet<Integer> set = new HashSet<Integer>(1);
        set.add(index + mOffset);
        return set;
    }

    protected AbstractConditionalInstruction(byte code[], int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {

        super(code, index, pool, interpreter);
        mOffset = getUnsignedBigEndian2Int(code, index + 1);
    }

    public final int getOffset() {
        return mOffset;
    }

    protected String getOffsetString() {
        return ((mOffset > 0)? "+" : "") + mOffset;
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        fillBigEndian2Int(code, index + 1, mOffset);
    }
}
