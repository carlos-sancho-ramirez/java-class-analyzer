package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;

public abstract class AbstractFieldReferenceInstruction extends AbstractConstantPoolReferenceInstruction {

    protected final FieldEntry mField;

    protected AbstractFieldReferenceInstruction(byte[] code, int index,
            ConstantPool pool, ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mField = pool.get(mPoolIndex, FieldEntry.class);
    }

    @Override
    public Set<FieldEntry> getKnownReferencedFields() {
        Set<FieldEntry> fields = new HashSet<FieldEntry>(1);
        fields.add(mField);
        return fields;
    }
}
