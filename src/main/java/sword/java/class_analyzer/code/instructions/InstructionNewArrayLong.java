package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionNewArrayLong extends AbstractInstructionNewArray {

    public InstructionNewArrayLong(byte[] code, int index,
            ConstantPool pool, ByteCodeInterpreter interpreter)
            throws IllegalArgumentException, IncompleteInstructionException {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return super.disassemble() + "long";
    }
}
