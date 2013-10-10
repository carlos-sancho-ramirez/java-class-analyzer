package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionGoto extends AbstractInstruction {

    private final int mOffset;

    public InstructionGoto(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mOffset = getSignedBigEndian2Int(code, index + 1);
    }

    private String getOffsetString() {
        return ((mOffset > 0)? "+" : "") + mOffset;
    }

    @Override
    public boolean alwaysJump() {
        return true;
    }

    @Override
    public Set<Integer> knownBranches(int index) {
        HashSet<Integer> set = new HashSet<Integer>(1);
        set.add(index + mOffset);
        return set;
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        fillBigEndian2Int(code, index + 1, mOffset);
    }

    @Override
    public String disassemble() {
        return "goto\t" + getOffsetString();
    }
}
