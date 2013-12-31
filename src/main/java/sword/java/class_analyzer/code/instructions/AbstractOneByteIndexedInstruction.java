package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public abstract class AbstractOneByteIndexedInstruction extends AbstractInstruction {

    final int mLocalVariableIndex;

    public AbstractOneByteIndexedInstruction(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, pool, interpreter);

        mLocalVariableIndex = (code[index + 1]) & 0xFF;
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte) mLocalVariableIndex;
    }
}
