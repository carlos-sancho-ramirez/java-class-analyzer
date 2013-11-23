package sword.java.class_analyzer.ref;

import java.io.File;

public class RootReference extends PackageReference {

    private static final RootReference INSTANCE = new RootReference();

    private RootReference() {
        super(null, "");
    }

    public static RootReference getInstance() {
        return INSTANCE;
    }

    public String getQualifiedName() {
        return getSimpleName();
    }

    public boolean isRootReference() {
        return true;
    }

    public RootReference getJavaParentReference() {
        return this;
    }

    @Override
    public File getFile(File classPath) {
        return classPath;
    }
}
