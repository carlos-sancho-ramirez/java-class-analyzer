package sword.java.class_analyzer.code;

public class InstructionNewArrayShort extends AbstractInstructionNewArray {

    static final ByteCodeInterpreter interpreter = new AbstractInstructionNewArray.Interpreter() {

        private static final byte expectedType = 0x09;

        @Override
        public boolean matches(byte[] code, int index) {
            return super.matches(code, index, expectedType);
        }
    };

    protected InstructionNewArrayShort(byte[] code, int index) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, interpreter);
    }

    InstructionNewArrayShort() {
        super();
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "short";
    }
}
