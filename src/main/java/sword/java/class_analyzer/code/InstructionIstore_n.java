package sword.java.class_analyzer.code;

public class InstructionIstore_n extends AbstractInstruction {

    private final int mLocalVariableIndex;

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x36;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 2 : 0;
        }
    };

    protected InstructionIstore_n(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);

        mLocalVariableIndex = (code[index + 1]) & 0xFF;
    }

    @Override
    public String disassemble() {
        return "istore " + mLocalVariableIndex;
    }
}
