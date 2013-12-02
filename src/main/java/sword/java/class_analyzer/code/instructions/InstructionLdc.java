package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.ConstantPoolEntry;

public class InstructionLdc extends AbstractInstruction {

    private final ConstantPoolEntry mPoolEntry;
    private final int mPoolIndex;

    public InstructionLdc(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mPoolIndex = (code[index + 1]) & 0xFF;
        mPoolEntry = pool.get(mPoolIndex, ConstantPoolEntry.class);
    }

    @Override
    public Set<ClassReferenceEntry> getKnownReflectionClassReferences() {
        if (mPoolEntry instanceof ClassReferenceEntry) {
            ClassReferenceEntry entry = (ClassReferenceEntry) mPoolEntry;
            Set<ClassReferenceEntry> references = new HashSet<ClassReferenceEntry>(1);
            references.add(entry);
            return references;
        }

        return new HashSet<ClassReferenceEntry>(0);
    }

    @Override
    protected void fillByteCode(byte code[], int index) {
        super.fillByteCode(code, index);
        code[index + 1] = (byte) mPoolIndex;
    }

    @Override
    public String disassemble() {
        return "ldc\t" + mPoolEntry;
    }
}
