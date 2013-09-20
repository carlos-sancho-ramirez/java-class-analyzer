package sword.java.class_analyzer.code;

public interface ByteCodeInterpreter {

    /**
     * Returns true only if the byte code found in the given index matches the
     * instruction representing this matcher.
     * @param code byte array containing byte code
     * @param index Index for the byte array to point to the next instruction to read.
     * @return Whether the byte code matches this instruction.
     */
    public boolean matches(byte code[], int index);

    /**
     * Returns the amount of bytes the instruction takes or zero if the instruction does not match.
     * @param code byte array containing byte code
     * @param index Index for the byte array to point to the next instruction to read.
     * @return the amount of bytes the instruction takes in the byte code or zero if the instruction does not match.
     */
    public int instructionSize(byte code[], int index);
}
