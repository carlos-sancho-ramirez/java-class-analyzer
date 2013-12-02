package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.java_type.JavaMethod;
import sword.java.class_analyzer.java_type.JavaType;

public class ArrayClassReference extends ClassReference {

    private ClassReference mElementReference;

    ArrayClassReference(ClassReference elementReference) {
        super(elementReference.getJavaParentReference(), elementReference.getSimpleName() + "[]");
        mElementReference = elementReference;
    }

    @Override
    public FieldReference addField(String qualifiedName, JavaType fieldType) {
        throw new UnsupportedOperationException("Arrays cannot add extra fields");
    }

    @Override
    public MethodReference addMethod(String qualifiedName, JavaMethod methodType) {
        throw new UnsupportedOperationException("Arrays cannot add extra methods");
    }

    @Override
    public File getFile(File classPath) {
        return mElementReference.getFile(classPath);
    }

    @Override
    public Set<ClassReference> setOfClasses() {
        final Set<ClassReference> set = new HashSet<ClassReference>();
        set.add(this);
        set.addAll(mElementReference.setOfClasses());
        return set;
    }

    public ClassReference getOriginElementReference() {
        final ArrayClassReference array = mElementReference.tryCastToArray();

        if (array != null) {
            return array.getOriginElementReference();
        }
        else {
            return mElementReference;
        }
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
