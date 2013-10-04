package sword.java.class_analyzer.code;

public class InstructionAreturn extends AbstractInstruction {

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xB0;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    protected InstructionAreturn(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionAreturn() {
        super();
    }

    @Override
    public boolean alwaysJump() {
        return true;
    }

    @Override
    public String disassemble() {
        return "areturn";
    }
}
