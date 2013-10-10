package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.code.TwoBytesSimpleByteCodeInterpreter;
import sword.java.class_analyzer.pool.ConstantPool;

public abstract class AbstractInstructionNewArray extends AbstractInstruction {

    protected AbstractInstructionNewArray(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, pool, interpreter);

        if (interpreter == null || !(interpreter instanceof TwoBytesSimpleByteCodeInterpreter)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte)((TwoBytesSimpleByteCodeInterpreter)mInterpreter).expectedSecondOpcode();
    }

    @Override
    public String disassemble() {
        return "newarray ";
    }
}
