package sword.java.class_analyzer.code;

public class InstructionAload_0 extends AbstractInstruction {

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x2A;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    public InstructionAload_0() {
        super();
    }

    protected InstructionAload_0(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    @Override
    public String disassemble() {
        return "aload_0";
    }
}
