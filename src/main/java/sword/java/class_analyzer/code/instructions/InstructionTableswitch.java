package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

/**
 * Opcode: 0xAA
 * After the first opcode there are from 0 to 3 padding bytes to ensure the table is aligned to 4 within the java method.
 * After that 3 big-endian 32-bits integers: default, low, high
 * After that there is (high - low + 1) 32-bits entries, they are offsets to
 * somewhere within the method. The current offset for the first opcode of this
 * instruction must be added to get the real position where to jump.
 *
 * If index retrieved from the stack is lower than low or higher than high this
 * instruction uses the default value as the offset to use.
 *
 * Check this URL for more info about the tableswitch opcode:
 * http://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html#jvms-6.5.tableswitch
 */
public class InstructionTableswitch extends AbstractInstruction {

    private final int mDefault;
    private final int mLow;
    private final int mHigh;
    private final int mOffsets[];

    public InstructionTableswitch(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);

        final int alignedBase = (index + 4) & 0xFFFFFFFC;
        mDefault = getBigEndian4Int(code, alignedBase);
        mLow = getBigEndian4Int(code, alignedBase + 4);
        mHigh = getBigEndian4Int(code, alignedBase + 8);

        final int entries = mHigh - mLow + 1;
        mOffsets = new int[entries];

        int offPos = 0;
        int key = mLow;
        int codeIndex = alignedBase + 12;

        do {
            mOffsets[offPos] = getBigEndian4Int(code, codeIndex);

            ++key;
            ++offPos;
            codeIndex += 4;
        } while (key <= mHigh);
    }

    @Override
    public String disassemble() {
        String result = "tableswitch {";

        final int entries = mOffsets.length;
        for (int i=0; i<entries; i++) {
            final int offset = mOffsets[i];
            result = result + "\n\t\t" + (mLow + i) + " -> " + ((offset > 0)? "+" : "") + offset;
        }
        result = result + "\n\t\tdefault -> " + ((mDefault > 0)? "+" : "") + mDefault + "\n\t}";

        return result;
    }

    @Override
    public int byteCodeSize(int index) {
        final int padding = 3 - (index % 4);
        return 1 + padding + (3 + mOffsets.length) * 4;
    }

    @Override
    public boolean alwaysJump() {
        return true;
    }

}
