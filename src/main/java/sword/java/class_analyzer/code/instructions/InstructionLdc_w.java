package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.ConstantPoolEntry;

public class InstructionLdc_w extends AbstractConstantPoolReferenceInstruction {

    private final ConstantPoolEntry mPoolEntry;

    public InstructionLdc_w(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolEntry = pool.get(mPoolIndex, ConstantPoolEntry.class);
    }

    @Override
    public String disassemble() {
        return "ldc_w\t" + mPoolEntry;
    }
}
