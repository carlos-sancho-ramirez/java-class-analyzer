package sword.java.class_analyzer.ref;

public abstract class MemberReference extends JavaReference {

    private final ClassReference mClass;

    public MemberReference(ClassReference classReference, String name) {
        super(name);
        final int parentCharsCount = mName.length() - name.length() - 1;
        if (parentCharsCount > 0) {
            mClass = new ClassReference(null, name.substring(0,parentCharsCount));
        }
        else {
            mClass = classReference;
        }
    }

    @Override
    public ClassReference getJavaParentReference() {
        return mClass;
    }

    public abstract String getTypeSignature();
}
