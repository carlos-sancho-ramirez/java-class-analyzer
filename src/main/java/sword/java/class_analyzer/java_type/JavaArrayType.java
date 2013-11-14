package sword.java.class_analyzer.java_type;

public class JavaArrayType extends JavaType {

    private final JavaType mArrayType;

    JavaArrayType(JavaType arrayType) {
        mArrayType = arrayType;
    }

    @Override
    public String signature() {
        return "[" + mArrayType.signature();
    }
}
