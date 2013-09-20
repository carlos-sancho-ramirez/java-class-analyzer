package sword.java.class_analyzer.code;

public class InstructionIconst_m1 extends AbstractInstruction {

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x02;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    protected InstructionIconst_m1(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionIconst_m1() {
        super();
    }

    @Override
    public String disassemble() {
        return "iconst_m1";
    }
}
