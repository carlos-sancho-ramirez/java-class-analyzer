package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;

public class InstructionIinc extends AbstractInstruction {

    private final int mLocalVarIndex;
    private final int mIncrement;

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0x84;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    protected InstructionIinc(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);

        mLocalVarIndex = (code[index + 1]) & 0xFF;
        mIncrement = code[index + 2];
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public String disassemble() {
        return "iinc\t" + mLocalVarIndex + ", " + mIncrement;
    }
}
