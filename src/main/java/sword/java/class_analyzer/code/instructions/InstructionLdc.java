package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.ConstantPoolEntry;

public class InstructionLdc extends AbstractInstruction {

    private final ConstantPoolEntry mPoolEntry;
    private final int mPoolIndex;

    public InstructionLdc(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolIndex = (code[index + 1]) & 0xFF;
        mPoolEntry = pool.get(mPoolIndex, ConstantPoolEntry.class);
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        fillBigEndian2Int(code, index + 1, mPoolIndex);
    }

    @Override
    public String disassemble() {
        return "ldc\t" + mPoolEntry;
    }
}
