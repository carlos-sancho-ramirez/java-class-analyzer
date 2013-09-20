package sword.java.class_analyzer.code;

public class InstructionBastore extends AbstractInstruction {

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final byte opcode = code[index];
            return opcode == 0x54;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 1 : 0;
        }
    };

    protected InstructionBastore(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionBastore() {
        super();
    }

    @Override
    public String disassemble() {
        return "bastore";
    }
}
