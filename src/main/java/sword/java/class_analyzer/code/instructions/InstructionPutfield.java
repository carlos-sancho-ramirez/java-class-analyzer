package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionPutfield extends AbstractFieldReferenceInstruction {

    public InstructionPutfield(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "putfield\t" + mField.getName();
    }
}
