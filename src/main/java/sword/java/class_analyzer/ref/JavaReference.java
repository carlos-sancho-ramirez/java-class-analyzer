package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.Set;

import sword.java.class_analyzer.independent_type.JavaType;

public abstract class JavaReference {

    final String mName;

    JavaReference(String name) {
        mName = name;
    }

    public String getSimpleName() {
        return mName;
    }

    void assertValidName(String name) {
        if (name.startsWith(".") || name.endsWith(".")) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns a dot separated full qualified name included in this reference.
     */
    public String getQualifiedName() {
        final JavaReference parent = getJavaParentReference();
        if (parent.isRootReference()) {
            return mName;
        } else {
            return parent.getQualifiedName() + '.' + mName;
        }
    }

    protected abstract JavaReference getJavaParentReference();

    public boolean isRootReference() {
        return false;
    }

    /**
     * Returns the root reference for this reference
     */
    public final RootReference getRootReference() {
        return isRootReference()? (RootReference) this : getJavaParentReference().getRootReference();
    }

    /** Generalized implementation to avoid having repeated code for all adders */
    interface AddNodeLambda<T extends JavaReference, P extends JavaReference, JT extends JavaType> {
        public T addIt(P instance, String first, String rest, JT javaType);

        public T createIt(P instance, String name, JT javaType);
    }

    @SuppressWarnings("unchecked")
    <T extends JavaReference, P extends JavaReference, JT extends JavaType> T addNode(
            String qualifiedName, Set<T> instances,
            AddNodeLambda<T, P, JT> lambdas, JT javaType) {
        assertValidName(qualifiedName);

        final int firstDot = qualifiedName.indexOf('.');
        final T result;
        if (firstDot > 0) {
            final String firstName = qualifiedName.substring(0, firstDot);
            final String restOfName = qualifiedName.substring(firstDot + 1);
            result = lambdas.addIt((P) this, firstName, restOfName, javaType);
        } else {
            T found = null;
            if (instances != null) {
                for (T instance : instances) {
                    if (instance.getSimpleName().equals(qualifiedName)) {
                        found = instance;
                        break;
                    }
                }
            }

            if (found != null) {
                result = found;
            } else {
                result = lambdas.createIt((P) this, qualifiedName, javaType);
            }

            // This position will never be reached with instances == null
            // because is expected that lambdas.createIt will throw an exception
            instances.add(result);
        }

        return result;
    }

    /**
     * Returns the file where the class should be within the classpath.
     * Arrays will return the file for the class on its element or null element
     * is a primitive type.
     *
     * @param classPath Folder where to look for files.
     */
    public abstract File getFile(File classPath);

    @Override
    public final int hashCode() {
        return getQualifiedName().hashCode();
    }

    @Override
    public final boolean equals(Object object) {
        return object != null && object instanceof JavaReference &&
                getQualifiedName().equals(((JavaReference) object).getQualifiedName());
    }

    public abstract Set<ClassReference> setOfClasses();
}
