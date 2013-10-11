package sword.java.class_analyzer.type;

public class JavaArrayType extends JavaType {

    private final JavaType mArrayType;

    public JavaArrayType(JavaType arrayType) {
        if (arrayType == null) {
            throw new IllegalArgumentException();
        }

        mArrayType = arrayType;
    }

    @Override
    public boolean matchesSignature(String signature) {
        return signature.startsWith("[") && mArrayType.matchesSignature(signature.substring(1));
    }

    @Override
    public String signature() {
        return "[" + mArrayType.signature();
    }
}
