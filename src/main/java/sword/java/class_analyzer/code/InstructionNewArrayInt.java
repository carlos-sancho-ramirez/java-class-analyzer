package sword.java.class_analyzer.code;

public class InstructionNewArrayInt extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x0A;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayInt(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayInt() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "int";
    }
}
