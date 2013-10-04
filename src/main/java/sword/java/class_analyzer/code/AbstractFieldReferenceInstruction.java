package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;

public abstract class AbstractFieldReferenceInstruction extends AbstractInstruction {

    protected final FieldEntry mField;

    protected static abstract class AbstractByteCodeInterpreter implements ByteCodeInterpreter {

        protected boolean matches(byte[] code, int index, int expectedType) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == expectedType;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    protected AbstractFieldReferenceInstruction(byte[] code, int index,
            ConstantPool pool, ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException, FileError {
        super(code, index, interpreter);

        final int poolIndex = getUnsignedBigEndian2Int(code, index + 1);
        mField = pool.get(poolIndex, FieldEntry.class);
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public Set<FieldEntry> getKnownReferencedFields() {
        Set<FieldEntry> fields = new HashSet<FieldEntry>(1);
        fields.add(mField);
        return fields;
    }
}
