package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionInvokestatic extends AbstractInvokeInstruction {

    protected static final ByteCodeInterpreter interpreter = new AbstractByteCodeInterpreter() {

        private static final int expectedType = 0xB8;

        @Override
        public boolean matches(byte[] code, int index) {
            return matches(code, index, expectedType);
        }
    };

    protected InstructionInvokestatic(byte[] code, int index, ConstantPool pool) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
    }

    @Override
    public String disassemble() {
        return "invokestatic\t" + mMethod.getName();
    }
}
