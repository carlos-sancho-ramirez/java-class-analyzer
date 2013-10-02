package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractConditionalInstruction extends AbstractInstruction {

    protected static abstract class AbstractByteCodeInterpreter implements ByteCodeInterpreter {

        public boolean matches(byte[] code, int index, int expectedType) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == expectedType;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

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

    protected AbstractConditionalInstruction(byte code[], int index,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {

        super(code, index, interpreter);
        mOffset = getUnsignedBigEndian2Int(code, index + 1);
    }

    public final int getOffset() {
        return mOffset;
    }

    protected String getOffsetString() {
        return ((mOffset > 0)? "+" : "") + mOffset;
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }
}
