package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;


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
