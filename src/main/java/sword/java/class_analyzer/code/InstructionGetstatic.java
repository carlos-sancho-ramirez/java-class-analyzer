package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;

public class InstructionGetstatic extends AbstractInstruction {

    private final FieldEntry mField;

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0xB2;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    protected InstructionGetstatic(byte[] code, int index, ConstantPool pool) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);

        final int poolIndex = getUnsignedBigEndian2Int(code, index + 1);
        mField = pool.get(poolIndex, FieldEntry.class);
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public String disassemble() {
        return "getstatic\t" + mField.getName();
    }
}
