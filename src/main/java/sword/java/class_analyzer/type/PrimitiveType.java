package sword.java.class_analyzer.type;

public class PrimitiveType extends JavaType {

    private final String mSignature;

    PrimitiveType(String signature) {
        mSignature = signature;
    }

    @Override
    public String signature() {
        return mSignature;
    }

    @Override
    public boolean matchesSignature(String signature) {
        return mSignature.equals(signature);
    }
}
