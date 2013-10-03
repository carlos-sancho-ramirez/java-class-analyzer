package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;

public class InstructionGoto extends AbstractInstruction {

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xA7;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    private final int mOffset;

    protected InstructionGoto(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);

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
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public String disassemble() {
        return "goto\t" + getOffsetString();
    }
}
