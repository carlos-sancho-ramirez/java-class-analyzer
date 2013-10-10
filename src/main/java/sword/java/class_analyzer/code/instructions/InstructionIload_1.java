package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionIload_1 extends AbstractInstruction {

    public InstructionIload_1(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "iload_1";
    }
}
