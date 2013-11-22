package sword.java.class_analyzer.ref;

import sword.java.class_analyzer.java_type.JavaMethod;

public class MethodReference extends MemberReference {

    private final JavaMethod mType;

    MethodReference(ClassReference classReference, String name,
            JavaMethod methodType) {
        super(classReference, name);

        if (methodType == null) {
            throw new IllegalArgumentException();
        }

        mType = methodType;
    }

    @Override
    public String getTypeSignature() {
        return mType.signature();
    }
}
