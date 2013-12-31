package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionAload extends AbstractOneByteIndexedInstruction {

    public InstructionAload(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "aload " + mLocalVariableIndex;
    }
}
