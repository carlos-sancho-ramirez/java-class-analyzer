package sword.java.class_analyzer.code;


public abstract class AbstractInstruction {

    public abstract String disassemble();

    protected AbstractInstruction(byte code[], int index, ByteCodeInterpreter interpreter)
            throws IllegalArgumentException, IncompleteInstructionException {

        final int byteCodeSize = interpreter.instructionSize(code, index);

        if (byteCodeSize == 0) {
            throw new IllegalArgumentException();
        }

        if (code.length - index < byteCodeSize) {
            throw new IncompleteInstructionException();
        }
    }

    protected AbstractInstruction() { }

    public int byteCodeSize() {
        return 1;
    }

    protected static int getUnsignedBigEndian2Int(byte code[], int index) {
        final int value1 = (code[index]) & 0xFF;
        final int value2 = (code[index + 1]) & 0xFF;

        return (value1 << 8) + value2;
    }

    protected static int getSignedBigEndian2Int(byte code[], int index) {
        final int value1 = code[index];
        final int value2 = (code[index + 1]) & 0xFF;

        return (value1 << 8) + value2;
    }
}
