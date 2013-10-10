package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public abstract class AbstractConstantPoolReferenceInstruction extends AbstractInstruction {

    protected final int mPoolIndex;

    protected AbstractConstantPoolReferenceInstruction(byte[] code, int index,
            ConstantPool pool, ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolIndex = getUnsignedBigEndian2Int(code, index + 1);
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        fillBigEndian2Int(code, index + 1, mPoolIndex);
    }
}
