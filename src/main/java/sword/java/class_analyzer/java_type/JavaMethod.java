package sword.java.class_analyzer.java_type;

public class JavaMethod extends JavaType {

    private final JavaTypeList mParameters;
    private final JavaType mReturning;

    JavaMethod(JavaType parameters, JavaType returning) {
        if (!parameters.isTypeList()) {
            mParameters = new JavaTypeList(parameters);
        }
        else {
            mParameters = (JavaTypeList) parameters;
        }

        mReturning = returning;
    }

    @Override
    public String signature() {
        return "(" + mParameters + ')' + mReturning;
    }
}
