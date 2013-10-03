package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.ConstantPoolEntry;

public class InstructionLdc_w extends AbstractInstruction {

    private final ConstantPoolEntry mPoolEntry;

    protected static final ByteCodeInterpreter interpreter = new ByteCodeInterpreter() {

        @Override
        public boolean matches(byte[] code, int index) {
            final int opcode = (code[index]) & 0xFF;
            return opcode == 0x13;
        }

        @Override
        public int instructionSize(byte[] code, int index) {
            return matches(code, index)? 3 : 0;
        }
    };

    protected InstructionLdc_w(byte[] code, int index, ConstantPool pool) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);

        final int poolIndex = getUnsignedBigEndian2Int(code, index + 1);
        mPoolEntry = pool.get(poolIndex, ConstantPoolEntry.class);
    }

    @Override
    public int byteCodeSize() {
        return 3;
    }

    @Override
    public String disassemble() {
        return "ldc_w\t" + mPoolEntry;
    }
}
