package sword.java.class_analyzer.java_type;

import java.util.HashSet;
import java.util.Set;

public abstract class JavaType {

    /**
     * Set for primitive, arrays and class references.
     * Type lists and method signatures are not included.
     */
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
        if (signature == null || signature.equals("")) {
            return null;
        }

        if (signature.startsWith("(")) {
            final int closingIndex = signature.lastIndexOf(')');
            if (closingIndex <= 0 || closingIndex >= signature.length() - 1) {
                return null;
            }

            final String parametersSignature = signature.substring(1,closingIndex);
            final JavaType parameters;
            if (parametersSignature.length() != 0) {
                parameters = getFromSignature(parametersSignature);
                if (parameters == null) {
                    return null;
                }
            }
            else {
                parameters = new JavaTypeList();
            }

            final JavaType returningType = getFromSignature(signature.substring(closingIndex + 1));
            if (returningType == null || returningType.isTypeList()) {
                return null;
            }

            return new JavaMethod(parameters, returningType);
        }

        for(JavaType instance : INSTANCES) {
            if (instance.signature().equals(signature)) {
                return instance;
            }
        }

        int arrayDepth = 0;
        while (signature.startsWith("[")) {
            ++arrayDepth;
            signature = signature.substring(1);
        }

        for(JavaType instance : INSTANCES) {
            if (instance.signature().equals(signature)) {
                JavaType javaType = instance;
                while (arrayDepth-- > 0) {
                    javaType = new JavaArrayType(instance);
                }

                INSTANCES.add(javaType);
                return javaType;
            }
        }

        for(JavaType instance : INSTANCES) {
            if (signature.startsWith(instance.signature())) {
                final JavaType rest = getFromSignature(signature.substring(instance.signature().length()));

                if (rest == null) {
                    return null;
                }

                JavaType first = instance;
                while (arrayDepth-- > 0) {
                    first = new JavaArrayType(first);
                }

                return new JavaTypeList(first, rest);
            }
        }

        final int firstSemiColon = signature.indexOf(';');
        final boolean validClassReference = firstSemiColon > 0;
        final String firstSignature = (validClassReference &&
                firstSemiColon < signature.length() - 1) ?
                        signature.substring(0,firstSemiColon + 1) : signature;

        if (validClassReference && JavaClassType.checkValidSignature(firstSignature)) {
            final JavaType firstType = new JavaClassType(firstSignature);

            if (signature.length() > firstSignature.length()) {
                final JavaType rest = getFromSignature(signature.substring(firstSemiColon + 1));
                if (rest == null) {
                    return null;
                }

                if (firstType != null) {
                    INSTANCES.add(firstType);
                }

                return new JavaTypeList(firstType, rest);
            }

            INSTANCES.add(firstType);
            return firstType;
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

    boolean isTypeList() {
        return false;
    }
}
