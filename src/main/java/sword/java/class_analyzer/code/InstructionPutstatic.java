package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionPutstatic extends AbstractFieldReferenceInstruction {

    protected static final ByteCodeInterpreter interpreter = new AbstractByteCodeInterpreter() {

        private static final int expectedType = 0xB3;

        @Override
        public boolean matches(byte[] code, int index) {
            return matches(code, index, expectedType);
        }
    };

    protected InstructionPutstatic(byte[] code, int index, ConstantPool pool) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "putstatic\t" + mField.getName();
    }
}
