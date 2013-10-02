package sword.java.class_analyzer.code;

public class InstructionIload_2 extends AbstractInstruction {

    static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x1C;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    public InstructionIload_2() {
        super();
    }

    protected InstructionIload_2(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    @Override
    public String disassemble() {
        return "iload_2";
    }
}
