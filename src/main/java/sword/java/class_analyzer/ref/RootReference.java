package sword.java.class_analyzer.ref;

import java.io.File;

public class RootReference extends PackageReference {

    public RootReference() {
        super(null, "");
    }

    @Override
    public String getQualifiedName() {
        return getSimpleName();
    }

    @Override
    public boolean isRootReference() {
        return true;
    }

    @Override
    public RootReference getJavaParentReference() {
        return this;
    }

    @Override
    public File getFile(File classPath) {
        return classPath;
    }
}
