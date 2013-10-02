package sword.java.class_analyzer.code;

public class InstructionAthrow extends AbstractInstruction {

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xBF;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    public InstructionAthrow() {
        super();
    }

    protected InstructionAthrow(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    @Override
    public boolean alwaysJump() {
        return true;
    }

    @Override
    public String disassemble() {
        return "athrow";
    }
}
