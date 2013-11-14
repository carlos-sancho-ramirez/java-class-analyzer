package sword.java.class_analyzer.ref;

public class ClassReference extends JavaReference {

    private final PackageReference mPackage;

    public ClassReference(PackageReference javaPackage, String name) {
        super(name);

        final int parentCharsCount = name.length() - mName.length() - 1;
        if (parentCharsCount > 0) {
            mPackage = new PackageReference(javaPackage, name.substring(0,parentCharsCount));
        }
        else {
            mPackage = javaPackage;
        }
    }

    public ClassReference(String qualifiedName) {
        this(null, qualifiedName);
    }

    @Override
    public PackageReference getJavaParentReference() {
        return mPackage;
    }
}
