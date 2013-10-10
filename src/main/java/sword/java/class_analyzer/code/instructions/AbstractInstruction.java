package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;
import sword.java.class_analyzer.pool.MethodEntry;


public abstract class AbstractInstruction {

    protected final ByteCodeInterpreter mInterpreter;

    public abstract String disassemble();

    protected AbstractInstruction(byte code[], int index, ConstantPool pool, ByteCodeInterpreter interpreter)
            throws IllegalArgumentException, IncompleteInstructionException {

        final int byteCodeSize = interpreter.instructionSize(code, index);
        mInterpreter = interpreter;

        if (byteCodeSize == 0) {
            throw new IllegalArgumentException();
        }

        if (code.length - index < byteCodeSize) {
            throw new IncompleteInstructionException();
        }
    }

    public final int byteCodeSize() {
        return mInterpreter.expectedByteCodeSize();
    }

    /**
     * Whether this instruction has the ability to jump to different parts of
     * the code or continue executing part of the code instead of continue.
     *
     * This will return true only if after executing this instruction the
     * program counter can have more than one possible value depending on
     * a condition.
     *
     * Examples:
     *   - aload does not branch, so next instruction will be executed
     *     afterwards. This will return false
     *   - ifeq will return true because the program counter can be pointing to
     *     the next instruction or to another position in the code.
     *   - return and goto will return false because even if they are jumping we
     *     know the next instruction never will be executed. So, only one point
     *     of execution is possible.
     */
    public boolean canBranch() {
        return false;
    }

    /**
     * Whether this instruction jumps always to another part of the code, not
     * allowing executing the next instruction.
     */
    public boolean alwaysJump() {
        return false;
    }

    /**
     * Returns a set of known possible places where this instructions can jump
     * if possible and if it can be extracted by the instruction itself.
     * This must return an empty set if this instruction returns false on
     * calling canBranch method.
     *
     * @param index Index for this instruction. Needed to convert relative
     * pointers to absolute ones.
     */
    public Set<Integer> knownBranches(int index) {
        return new HashSet<Integer>(0);
    }

    /**
     * Returns a set of methods this instruction can call. In case no method can
     * be called an empty set will be returned instead.
     */
    public Set<MethodEntry> getKnownInvokedMethods() {
        return new HashSet<MethodEntry>(0);
    }

    public Set<FieldEntry> getKnownReferencedFields() {
        return new HashSet<FieldEntry>(0);
    }

    /**
     * Sets the byte code this instruction is representing into the buffer provided.
     *
     * This operation will overwrite any data from the index position and only
     * the number of bytes retrieved by byteCodeSize method.
     *
     * @param code buffer to be fill with the opcode data
     * @param index position to start writing on the buffer.
     */
    public final void retrieveByteCode(final byte code[], int index) throws IllegalArgumentException {
        if (index < 0 || index + byteCodeSize() > code.length) {
            throw new IllegalArgumentException();
        }

        fillByteCode(code, index);
    }

    protected void fillByteCode(byte code[], int index) {
        code[index] = (byte)mInterpreter.expectedFirstOpcode();
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

    protected static void fillBigEndian2Int(byte code[], int index, int value) {
        code[index] = (byte) (value >> 8);
        code[index + 1] = (byte) (value);
    }
}