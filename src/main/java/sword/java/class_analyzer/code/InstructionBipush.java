package sword.java.class_analyzer.code;

public class InstructionBipush extends AbstractInstruction {

    private final int mValue;

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x10;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 2 : 0;
        }
    };

    protected InstructionBipush(byte code[], int index) throws IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
        mValue = code[index + 1];
    }

    @Override
    public String disassemble() {
        return "bipush\t" + mValue;
    }

    @Override
    public int byteCodeSize() {
        return 2;
    }
}
