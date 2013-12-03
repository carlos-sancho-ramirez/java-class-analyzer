package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SimpleClassReference extends ClassReference {

    public static final String FILE_EXTENSION = ".class";

    SimpleClassReference(PackageReference javaPackage, String name) {
        super(javaPackage, name);
    }

    @Override
    public File getFile(File classPath) {
        return new File(getJavaParentReference().getFile(classPath), mName + FILE_EXTENSION);
    }

    @Override
    public Set<ClassReference> setOfClasses() {
        final Set<ClassReference> set = new HashSet<ClassReference>(1);
        set.add(this);
        return set;
    }
}
