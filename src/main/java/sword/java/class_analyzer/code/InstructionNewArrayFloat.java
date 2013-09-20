package sword.java.class_analyzer.code;

public class InstructionNewArrayFloat extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x07;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayFloat(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayFloat() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "double";
    }
}
