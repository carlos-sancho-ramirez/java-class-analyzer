package sword.java.class_analyzer.ref;

public abstract class JavaReference {

    protected final String mName;

    protected JavaReference(String name) {
        if (name.startsWith(".") || name.endsWith(".")) {
            throw new IllegalArgumentException();
        }

        final int lastDot = name.lastIndexOf('.');
        if (lastDot > 0) {
            mName = name.substring(lastDot + 1);
        }
        else {
            mName = name;
        }
    }

    public String getSimpleName() {
        return mName;
    }

    /**
     * Returns a dot separated full qualified name included in this reference.
     */
    public String getQualifiedName() {
        final JavaReference parent = getJavaParentReference();
        if (parent != null) {
            return parent.getQualifiedName() + '.' + mName;
        }
        else {
            return mName;
        }
    }

    protected abstract JavaReference getJavaParentReference();
}
