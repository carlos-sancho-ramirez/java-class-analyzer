package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public abstract class MemberReference extends JavaReference {

    private final ClassReference mClass;

    MemberReference(ClassReference classReference, String name) {
        super(name);
        mClass = classReference;
    }

    @Override
    public ClassReference getJavaParentReference() {
        return mClass;
    }

    public abstract String getTypeSignature();

    @Override
    public File getFile(File classPath) {
        return mClass.getFile(classPath);
    }

    @Override
    public Set<ClassReference> setOfClasses() {
        final Set<ClassReference> set = new HashSet<ClassReference>(1);
        set.add(mClass);
        return set;
    }
}
