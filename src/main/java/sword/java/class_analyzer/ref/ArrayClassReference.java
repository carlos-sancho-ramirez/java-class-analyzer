package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.Set;

import sword.java.class_analyzer.independent_type.JavaArrayType;

public class ArrayClassReference extends ClassReference {

    private ClassReference mElementReference;

    ArrayClassReference(ClassReference elementReference) {
        super(elementReference.getJavaParentReference(),
                JavaArrayType.getArrayRepresentation(elementReference.getSimpleName()));
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
