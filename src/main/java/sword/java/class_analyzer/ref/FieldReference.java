package sword.java.class_analyzer.ref;

import sword.java.class_analyzer.java_type.JavaType;

public class FieldReference extends MemberReference {

    private final JavaType mType;

    public FieldReference(ClassReference classReference, String name, JavaType javaType) {
        super(classReference, name);

        if (javaType == null) {
            throw new IllegalArgumentException();
        }

        mType = javaType;
    }

    @Override
    public String getTypeSignature() {
        return mType.signature();
    }
}
