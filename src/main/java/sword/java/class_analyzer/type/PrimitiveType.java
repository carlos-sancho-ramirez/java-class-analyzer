package sword.java.class_analyzer.type;

public class PrimitiveType extends JavaType {

    private final String mSignature;

    public PrimitiveType(String signature) {
        mSignature = signature;
    }

    @Override
    public String signature() {
        return mSignature;
    }

    @Override
    public int hashCode() {
        return mSignature.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof PrimitiveType &&
                mSignature.equals(((PrimitiveType) object).signature());
    }

    @Override
    public boolean matchesSignature(String signature) {
        return mSignature.equals(signature);
    }
}
