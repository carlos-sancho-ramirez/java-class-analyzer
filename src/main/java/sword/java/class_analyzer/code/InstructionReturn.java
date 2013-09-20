package sword.java.class_analyzer.code;

public class InstructionReturn extends AbstractInstruction {

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xB1;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    protected InstructionReturn(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionReturn() {
        super();
    }

    @Override
    public String disassemble() {
        return "return";
    }
}
