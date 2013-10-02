package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionNew extends AbstractInstruction {

    private final ClassReferenceEntry mClassReference;

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xBB;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    protected InstructionNew(byte[] code, int index, ConstantPool pool) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);

        final int poolIndex = getUnsignedBigEndian2Int(code, index + 1);
        mClassReference = pool.get(poolIndex, ClassReferenceEntry.class);
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public String disassemble() {
        return "new\t" + mClassReference.toString();
    }
}
