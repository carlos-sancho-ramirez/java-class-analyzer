package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.Set;

public class ArrayClassReference extends ClassReference {

    public static final String ARRAY_IDENTIFIER = "[]";

    private ClassReference mElementReference;

    ArrayClassReference(ClassReference elementReference) {
        super(elementReference.getJavaParentReference(), elementReference.getSimpleName() + ARRAY_IDENTIFIER);
        mElementReference = elementReference;
    }

    @Override
    public File getFile(File classPath) {
        return mElementReference.getFile(classPath);
    }

    @Override
    public Set<ClassReference> setOfClasses() {
        final Set<ClassReference> set = mElementReference.setOfClasses();
        set.add(this);
        return set;
    }

    /*
    public ClassReference getOriginElementReference() {
        final ArrayClassReference array = mElementReference.tryCastToArray();

        if (array != null) {
            return array.getOriginElementReference();
        }
        else {
            return mElementReference;
        }
    }
    */

    @Override
    public ArrayClassReference tryCastToArray() {
        return this;
    }

    /**
     * Returns a dot separated full qualified name included in this reference.
     */
    @Override
    public String getQualifiedName() {
        return mElementReference.getQualifiedName() + "[]";
    }
}
