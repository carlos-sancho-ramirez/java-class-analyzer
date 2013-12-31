package sword.java.class_analyzer.ref;

import java.io.File;

import sword.java.class_analyzer.independent_type.JavaArrayType;
import sword.java.class_analyzer.independent_type.PrimitiveType;

public class PrimitiveArrayClassReference extends ClassReference {

    private final JavaArrayType mJavaType;

    PrimitiveArrayClassReference(RootReference rootReference, PrimitiveType javaType) {
        super(rootReference, javaType.getArrayType().getJavaRepresentation());
        mJavaType = javaType.getArrayType();
    }

    @Override
    public File getFile(File classPath) {
        return null;
    }
}
