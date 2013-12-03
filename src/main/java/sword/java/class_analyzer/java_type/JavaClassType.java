package sword.java.class_analyzer.java_type;

import sword.java.class_analyzer.independent_type.JavaType;
import sword.java.class_analyzer.ref.RootReference;
import sword.java.class_analyzer.ref.SimpleClassReference;

public class JavaClassType extends JavaType {

    private final SimpleClassReference mClass;

    public static boolean checkValidSignature(String signature) {
        return signature != null && signature.startsWith("L")
                && signature.endsWith(";") && signature.length() > 2
                && !signature.contains(".");
    }

    JavaClassType(RootReference rootReference, String signature) {
        mClass = rootReference.addSimpleClass(
                signature.substring(1, signature.length() - 1)
                        .replace('/', '.'));

        if (mClass == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String signature() {
        return "L" + mClass.getQualifiedName().replace('.', '/') + ";";
    }

    @Override
    public String getJavaRepresentation() {
        return mClass.getQualifiedName();
    }

    public SimpleClassReference getReference() {
        return mClass;
    }
}
