package sword.java.class_analyzer.java_type;

import sword.java.class_analyzer.ref.ClassReference;

public class JavaClassType extends JavaType {

    private final ClassReference mClass;

    public static boolean checkValidSignature(String signature) {
        return signature != null && signature.startsWith("L") &&
                signature.endsWith(";") && signature.length() > 2 &&
                !signature.contains(".");
    }

    JavaClassType(String signature) {
        mClass = new ClassReference(signature.substring(1, signature.length() - 1).replace('/', '.'));

        if (mClass == null) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String signature() {
        return "L" + mClass.getQualifiedName().replace('.', '/') + ";";
    }
}
