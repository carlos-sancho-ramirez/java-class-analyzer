package sword.java.class_analyzer.java_type;

public class PrimitiveType extends JavaType {

    private final String mSignature;

    PrimitiveType(String signature) {
        mSignature = signature;
    }

    @Override
    public String signature() {
        return mSignature;
    }
}
