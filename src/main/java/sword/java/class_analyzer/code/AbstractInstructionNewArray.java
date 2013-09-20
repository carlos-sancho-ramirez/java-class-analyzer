package sword.java.class_analyzer.code;

public abstract class AbstractInstructionNewArray extends AbstractInstruction {

    protected static abstract class Interpreter implements ByteCodeInterpreter {

        public boolean matches(byte[] code, int index, byte expectedType) {
            if (index > code.length - 2) {
                return false;
            }

            final byte opcode = code[index];
            final byte atype = code[index + 1];

            return opcode == (byte)0xBC && atype == expectedType;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 2 : 0;
        }
    };

    protected AbstractInstructionNewArray(byte[] code, int index,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, interpreter);
    }

    protected AbstractInstructionNewArray() {
        super();
    }

    @Override
    public int byteCodeSize() {
        return 2;
    }

    @Override
    public String disassemble() {
        return "newarray ";
    }
}
