package sword.java.class_analyzer.code;

import sword.java.class_analyzer.code.instructions.AbstractInstruction;


public class TwoBytesSimpleByteCodeInterpreter extends SimpleByteCodeInterpreter {

    private final int mSecondOpcode;

    public TwoBytesSimpleByteCodeInterpreter(int firstOpcode, int secondOpcode, int byteCodeSize,
            Class<? extends AbstractInstruction> instructionClass) throws IllegalArgumentException {
        super(firstOpcode, byteCodeSize, instructionClass);
        mSecondOpcode = secondOpcode;

        if (byteCodeSize < 2) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean matches(byte[] code, int index) {
        if (index < 0 || index + 1 >= code.length) return false;

        final int second = (code[index + 1]) & 0xFF;
        return second == mSecondOpcode && super.matches(code, index);
    }

    public int expectedSecondOpcode() {
        return mSecondOpcode;
    }

    @Override
    public int hashCode() {
        return (super.hashCode() << 8) + mSecondOpcode;
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object) && object instanceof TwoBytesSimpleByteCodeInterpreter && object != null) {
            final TwoBytesSimpleByteCodeInterpreter other = (TwoBytesSimpleByteCodeInterpreter) object;
            return mSecondOpcode == other.mSecondOpcode;
        }

        return false;
    }
}
