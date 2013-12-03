package sword.java.class_analyzer.independent_type;


public class JavaArrayType extends JavaType {

    private final JavaType mArrayType;

    public JavaArrayType(JavaType arrayType) {
        mArrayType = arrayType;
    }

    @Override
    public String signature() {
        return "[" + mArrayType.signature();
    }

    @Override
    public String getJavaRepresentation() {
        return mArrayType.getJavaRepresentation() + "[]";
    }

    public JavaType getElementType() {
        return mArrayType;
    }
}
