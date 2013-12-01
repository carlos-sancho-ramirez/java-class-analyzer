package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionSipush extends AbstractInstruction {

    private final int mValue;

    public InstructionSipush(byte code[], int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, pool, interpreter);
        mValue = getSignedBigEndian2Int(code, index + 1);
    }

    @Override
    public String disassemble() {
        return "sipush\t" + mValue;
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte) mValue;
        code[index + 2] = (byte) (mValue >> 8);
    }
}
