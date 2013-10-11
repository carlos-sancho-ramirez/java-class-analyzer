package sword.java.class_analyzer.ref;

import sword.java.class_analyzer.type.JavaType;

public class MethodReference extends MemberReference {

    private final JavaType mParameters[];
    private final JavaType mReturningType;

    public MethodReference(ClassReference classReference, String name, JavaType returningType, JavaType... parameters) {
        super(classReference, name);

        if (parameters == null || returningType == null) {
            throw new IllegalArgumentException();
        }

        for(JavaType parameter : parameters) {
            if (parameter == null) {
                throw new IllegalArgumentException();
            }
        }

        mParameters = parameters;
        mReturningType = returningType;
    }

    @Override
    public String getTypeSignature() {
        String result = "(";
        for (JavaType parameter : mParameters) {
            result = result + parameter.signature();
        }

        return result + ")" + mReturningType.signature();
    }
}
