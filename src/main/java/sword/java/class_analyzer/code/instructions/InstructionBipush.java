package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionBipush extends AbstractInstruction {

    private final int mValue;

    public InstructionBipush(byte code[], int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, pool, interpreter);
        mValue = code[index + 1];
    }

    @Override
    public String disassemble() {
        return "bipush\t" + mValue;
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte) mValue;
    }
}
