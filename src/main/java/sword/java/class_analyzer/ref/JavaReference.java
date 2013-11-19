package sword.java.class_analyzer.ref;

import java.util.Set;

import sword.java.class_analyzer.java_type.JavaType;

public abstract class JavaReference {

    protected final String mName;

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

    /** Generalized implementation to avoid having repeated code for all adders */
    interface AddNodeLambda<T extends JavaReference, P extends JavaReference> {
        public T addIt(P instance, String first, String rest,
                JavaType returningType, JavaType... paramTypes);

        public T createIt(P instance, String name, JavaType returningType,
                JavaType... paramTypes);
    }

    @SuppressWarnings("unchecked")
    <T extends JavaReference, P extends JavaReference> T addNode(
            String qualifiedName, Set<T> instances,
            AddNodeLambda<T, P> lambdas, JavaType returningType,
            JavaType... paramTypes) {
        assertValidName(qualifiedName);

        final int firstDot = qualifiedName.indexOf('.');
        final T result;
        if (firstDot > 0) {
            final String firstName = qualifiedName.substring(0, firstDot);
            final String restOfName = qualifiedName.substring(firstDot + 1);
            result = lambdas.addIt((P) this, firstName, restOfName,
                    returningType, paramTypes);
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
                result = lambdas.createIt((P) this, qualifiedName,
                        returningType, paramTypes);
            }

            // This position will never be reached with instances == null
            // because is expected that lambdas.createIt will throw an exception
            instances.add(result);
        }

        return result;
    }
}
