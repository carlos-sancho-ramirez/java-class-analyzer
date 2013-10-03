package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;

public class InstructionIf_icmpeq extends AbstractConditionalInstruction {

    protected static final AbstractByteCodeInterpreter interpreter = new AbstractByteCodeInterpreter() {

        private static final int expectedOpcode = 0x9F;

        @Override
        public boolean matches(byte[] code, int index) {
            return matches(code, index, expectedOpcode);
        }
    };

    protected InstructionIf_icmpeq(byte[] code, int index) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, interpreter);
    }

    @Override
    public String disassemble() {
        return "if_icmpeq\t" + getOffsetString();
    }
}