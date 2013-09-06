package sword.java.class_analyzer.code;

public abstract class AbstractInstructionNewArray extends AbstractInstruction {

    /**
     * Called for each NewArray instruction to check if it matches.
     * @param code Code to be checked in byte code
     * @param index Index within the code array.
     * @param atype byte code expected to be found in the second byte. Each type has a different value.
     * @return Whether the code, in the specified index position, actually
     * includes a valid instruction matching this.
     */
    protected static boolean matches(byte code[], int index, byte expectedType) {
        if (index > code.length - 2) {
            return false;
        }

        final byte opcode = code[index];
        final byte atype = code[index + 1];

        return opcode == (byte)0xBC && atype == expectedType;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String disassemble() throws InvalidInstruction {
        return "newarray ";
    }
}
