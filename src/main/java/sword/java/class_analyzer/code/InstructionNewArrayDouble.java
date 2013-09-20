package sword.java.class_analyzer.code;

public class InstructionNewArrayDouble extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x06;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayDouble(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayDouble() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "float";
    }
}
