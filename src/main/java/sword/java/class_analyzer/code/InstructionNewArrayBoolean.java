package sword.java.class_analyzer.code;

public class InstructionNewArrayBoolean extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x04;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayBoolean(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayBoolean() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "boolean";
    }
}
