package sword.java.class_analyzer.type;

import sword.java.class_analyzer.ref.ClassReference;

public class JavaClassType extends JavaType {

    private final ClassReference mClass;

    public static boolean checkValidSignature(String signature) {
        return signature.startsWith("L") && signature.endsWith(";");
    }

    public JavaClassType(String signature) {
        if (!checkValidSignature(signature)) {
            throw new IllegalArgumentException();
        }

        mClass = new ClassReference(signature.substring(1, signature.length() - 1).replace('/', '.'));
    }

    @Override
    public String signature() {
        return "L" + mClass.getQualifiedName().replace('.', '/') + ";";
    }

    @Override
    public boolean matchesSignature(String signature) {
        return signature().equals(signature);
    }

}
