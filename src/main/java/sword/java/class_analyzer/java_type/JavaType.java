package sword.java.class_analyzer.java_type;

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

    /**
     * Returns the JavaType instance matching the given signature.
     * @param signature JNI style signature.
     * @return The instance matching the signature or null if the signature is not valid.
     */
    public static JavaType getFromSignature(String signature) {
        if (signature == null) {
            return null;
        }

        for(JavaType instance : INSTANCES) {
            if (instance.signature().equals(signature)) {
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
            final JavaType type = new JavaClassType(signature);
            if (type != null) {
                INSTANCES.add(type);
            }
            return type;
        }

        return null;
    }

    public abstract String signature();

    @Override
    public int hashCode() {
        return signature().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof JavaType &&
                signature().equals(((JavaType) object).signature());
    }
}
