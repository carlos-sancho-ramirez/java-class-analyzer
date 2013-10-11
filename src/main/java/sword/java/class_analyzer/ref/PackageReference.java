package sword.java.class_analyzer.ref;

public class PackageReference extends JavaReference {

    private final PackageReference mParent;

    public PackageReference(PackageReference javaPackage, String name) {
        super(name);

        final int parentCharsCount = mName.length() - name.length() - 1;
        if (parentCharsCount > 0) {
            mParent = new PackageReference(javaPackage, name.substring(0,parentCharsCount));
        }
        else {
            mParent = javaPackage;
        }
    }

    @Override
    public PackageReference getJavaParentReference() {
        return mParent;
    }
}
