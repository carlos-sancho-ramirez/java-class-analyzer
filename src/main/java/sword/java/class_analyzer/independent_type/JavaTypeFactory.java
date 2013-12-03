package sword.java.class_analyzer.independent_type;

import java.util.HashSet;
import java.util.Set;

public class JavaTypeFactory {

    /**
     * Set for primitives and arrays of primitives to be reused.
     */
    protected static final Set<JavaType> INDEPENDENT_INSTANCES = new HashSet<JavaType>();

    static {
        if (!INDEPENDENT_INSTANCES.add(new PrimitiveType("V", "void"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("Z", "boolean"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("B", "byte"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("C", "char"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("S", "short"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("I", "int"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("J", "long"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("F", "float"))
                || !INDEPENDENT_INSTANCES.add(new PrimitiveType("D", "double"))) {
            throw new IllegalArgumentException();
        }
    }

    public static JavaType getIndependentTypeFromSignature(String signature) {
        if (signature == null || signature.equals("")) {
            return null;
        }

        for (JavaType javaType : INDEPENDENT_INSTANCES) {
            if (javaType.signature().equals(signature)) {
                return javaType;
            }
        }

        if (signature.startsWith("[")) {
            final JavaType element = getIndependentTypeFromSignature(signature.substring(1));
            if (element != null) {
                final JavaArrayType result = new JavaArrayType(element);
                INDEPENDENT_INSTANCES.add(result);
                return result;
            }
        }

        return null;
    }
}
