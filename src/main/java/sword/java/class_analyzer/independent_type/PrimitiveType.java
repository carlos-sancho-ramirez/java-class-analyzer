package sword.java.class_analyzer.independent_type;

public class PrimitiveType extends JavaType {

    private final String mSignature;
    private final String mJavaRepresentation;

    PrimitiveType(String signature, String javaRepresentation) {
        mSignature = signature;
        mJavaRepresentation = javaRepresentation;
    }

    @Override
    public String signature() {
        return mSignature;
    }

    @Override
    public String getJavaRepresentation() {
        return mJavaRepresentation;
    }
}
