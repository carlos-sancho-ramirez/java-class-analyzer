package sword.java.class_analyzer.code;

public abstract class InvalidByteCodeException extends Exception {

    private static final long serialVersionUID = -1121270295931182133L;

    protected final String mInvalidReason;

    protected InvalidByteCodeException(String invalidReason) {
        mInvalidReason = invalidReason;
    }

    public final String getInvalidReason() {
        return mInvalidReason;
    }
}
