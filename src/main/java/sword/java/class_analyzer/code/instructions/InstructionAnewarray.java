package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionAnewarray extends AbstractConstantPoolReferenceInstruction {

    private final ClassReferenceEntry mClassReference;

    public InstructionAnewarray(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mClassReference = pool.get(mPoolIndex, ClassReferenceEntry.class);
    }

    @Override
    public String disassemble() {
        return "anewarray\t" + mClassReference.toString();
    }
}
