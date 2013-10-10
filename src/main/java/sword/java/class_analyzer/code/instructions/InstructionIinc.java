package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionIinc extends AbstractInstruction {

    private final int mLocalVariableIndex;
    private final int mIncrement;

    public InstructionIinc(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);

        mLocalVariableIndex = (code[index + 1]) & 0xFF;
        mIncrement = code[index + 2];
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte) mLocalVariableIndex;
        code[index + 2] = (byte) mIncrement;
    }

    @Override
    public String disassemble() {
        return "iinc\t" + mLocalVariableIndex + ", " + mIncrement;
    }
}
