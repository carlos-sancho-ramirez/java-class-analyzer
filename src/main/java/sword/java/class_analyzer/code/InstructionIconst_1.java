package sword.java.class_analyzer.code;

public class InstructionIconst_1 extends AbstractInstruction {

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x04;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    protected InstructionIconst_1(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionIconst_1() {
        super();
    }

    @Override
    public String disassemble() {
        return "iconst_1";
    }
}
