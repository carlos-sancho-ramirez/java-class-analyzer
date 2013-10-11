package sword.java.class_analyzer.type;

import java.util.HashSet;
import java.util.Set;

public abstract class JavaType {

    private static final Set<JavaType> INSTANCES = new HashSet<JavaType>();

    static {
        if (
                !INSTANCES.add(new PrimitiveType("V")) ||
                !INSTANCES.add(new PrimitiveType("Z")) ||
                !INSTANCES.add(new PrimitiveType("B")) ||
                !INSTANCES.add(new PrimitiveType("C")) ||
                !INSTANCES.add(new PrimitiveType("S")) ||
                !INSTANCES.add(new PrimitiveType("I")) ||
                !INSTANCES.add(new PrimitiveType("J")) ||
                !INSTANCES.add(new PrimitiveType("F")) ||
                !INSTANCES.add(new PrimitiveType("D"))
            ) {
            throw new IllegalArgumentException();
        }
    }

    public static JavaType getFromSignature(String signature) {
        for(JavaType instance : INSTANCES) {
            if (instance.matchesSignature(signature)) {
                return instance;
            }
        }

        if (signature.startsWith("[")) {
            final JavaType arrayType = getFromSignature(signature.substring(1));
            if (arrayType == null) {
                return null;
            }

            final JavaType newArray = new JavaArrayType(arrayType);
            INSTANCES.add(newArray);
            return newArray;
        }

        if (JavaClassType.checkValidSignature(signature)) {
            return new JavaClassType(signature);
        }

        return null;
    }

    public abstract String signature();
    public abstract boolean matchesSignature(String signature);
}
