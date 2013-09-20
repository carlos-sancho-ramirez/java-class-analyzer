package sword.java.class_analyzer.code;

public class InstructionNewArrayByte extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x08;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayByte(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayByte() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "byte";
    }
}
