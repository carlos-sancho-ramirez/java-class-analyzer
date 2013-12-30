package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionInstanceof extends AbstractConstantPoolReferenceInstruction {

    private final ClassReferenceEntry mPoolEntry;

    public InstructionInstanceof(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolEntry = pool.get(mPoolIndex, ClassReferenceEntry.class);
    }

    @Override
    public Set<ClassReferenceEntry> getKnownReflectionClassReferences() {
        Set<ClassReferenceEntry> references = new HashSet<ClassReferenceEntry>(1);
        references.add(mPoolEntry);
        return references;
    }

    @Override
    public String disassemble() {
        return "instanceof\t" + mPoolEntry;
    }
}
