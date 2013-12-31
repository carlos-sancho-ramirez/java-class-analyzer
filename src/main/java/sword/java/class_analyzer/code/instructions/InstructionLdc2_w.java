package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.ConstantPoolEntry;
import sword.java.class_analyzer.pool.DoubleEntry;
import sword.java.class_analyzer.pool.LongEntry;

public class InstructionLdc2_w extends AbstractConstantPoolReferenceInstruction {

    private final ConstantPoolEntry mPoolEntry;

    public InstructionLdc2_w(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolEntry = pool.get(mPoolIndex, ConstantPoolEntry.class);

        if (!(mPoolEntry instanceof LongEntry) && !(mPoolEntry instanceof DoubleEntry)) {
            throw new FileError(FileError.Kind.INVALID_POOL_TYPE_MATCH, mPoolIndex);
        }
    }

    @Override
    public String disassemble() {
        return "ldc2_w\t" + mPoolEntry;
    }
}
