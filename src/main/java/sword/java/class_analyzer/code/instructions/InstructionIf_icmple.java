package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionIf_icmple extends AbstractConditionalInstruction {

    public InstructionIf_icmple(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "if_icmple\t" + getOffsetString();
    }
}
