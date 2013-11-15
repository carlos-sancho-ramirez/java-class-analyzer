package sword.java.class_analyzer.code;

import sword.java.class_analyzer.code.instructions.AbstractInstruction;
import sword.java.class_analyzer.pool.ConstantPool;

public interface ByteCodeInterpreter {

    /**
     * This method returns the expected opcode to be found in code for this
     * instruction. Note that it is possible more than one opcode is present
     * to fully define a instruction, in this case this method only returns the
     * first one.
     *
     * @return The integer representation for the opcode in a interval from 0 to 255
     */
    public int expectedFirstOpcode();

    /**
     * Returns true only if the byte code found in the given index matches the
     * instruction representing this matcher.
     * @param code byte array containing byte code
     * @param index Index for the byte array to point to the next instruction to read.
     * @return Whether the byte code matches this instruction.
     */
    public boolean matches(byte code[], int index);

    /**
     * This returns the amount of byte it is expected this instruction will take.
     * This includes any opcode, offset, index for the constant pool or local table, etc.
     */
    public int expectedByteCodeSize(int index);

    /**
     * Returns the amount of bytes the instruction takes or zero if the instruction does not match.
     * @param code byte array containing byte code
     * @param index Index for the byte array to point to the next instruction to read.
     * @return the amount of bytes the instruction takes in the byte code or zero if the instruction does not match.
     */
    public int instructionSize(byte code[], int index);

    /**
     * Creates a new instance for the instruction found at that point of the code
     * @param code byte array containing byte code
     * @param index Index for the byte array to point to the next instruction to read.
     * @param pool Constant pool used to retrieve some data from it, not always needed.
     * @return A new instance for the instruction
     * @throws InstantiationException if the instruction cannot be instanced for any reason.
     */
    public AbstractInstruction newInstruction(byte code[], int index, ConstantPool pool) throws InstantiationException;
}
