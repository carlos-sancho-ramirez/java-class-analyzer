package sword.java.class_analyzer.java_type;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.ref.RootReference;

public class JavaTypeFactory {

    private RootReference mRootReference;

    /**
     * Set for primitives and arrays of primitives to be reused.
     */
    private static final Set<JavaType> INDEPENDENT_INSTANCES = new HashSet<JavaType>();

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

    public JavaTypeFactory(RootReference rootReference) {
        mRootReference = rootReference;
    }

    public RootReference getRootReference() {
        return mRootReference;
    }

    private JavaType getIndependent(String signature) {
        if (signature == null || signature.equals("")) {
            return null;
        }

        for (JavaType javaType : INDEPENDENT_INSTANCES) {
            if (javaType.signature().equals(signature)) {
                return javaType;
            }
        }

        if (signature.startsWith("[")) {
            final JavaType element = getIndependent(signature.substring(1));
            if (element != null) {
                final JavaArrayType result = new JavaArrayType(element);
                INDEPENDENT_INSTANCES.add(result);
                return result;
            }
        }

        return null;
    }

    /**
     * Returns a JavaMethod instance by extracting it from the signature.
     *
     * All class references found in the signature will be added to the
     * RootReference of this factory.
     * This method will return null if the signature is invalid.
     */
    public JavaMethod getMethodFromSignature(String signature) {

        if (signature == null || signature.equals("") || !signature.startsWith("(")) {
            return null;
        }

        final int closingIndex = signature.lastIndexOf(')');
        if (closingIndex <= 0 || closingIndex >= signature.length() - 1) {
            return null;
        }

        final String parametersSignature = signature.substring(1, closingIndex);
        final JavaType parameters;
        if (parametersSignature.length() != 0) {
            parameters = getFromSignature(parametersSignature);
            if (parameters == null) {
                return null;
            }
        } else {
            parameters = new JavaTypeList();
        }

        final JavaType returningType = getFromSignature(signature
                .substring(closingIndex + 1));
        if (returningType == null || returningType.isTypeList()) {
            return null;
        }

        return new JavaMethod(parameters, returningType);
    }

    /**
     * Returns the JavaType instance matching the given signature.
     *
     * @param signature java binary style signature.
     * @return The instance matching the signature or null if the signature is not valid.
     */
    public JavaType getFromSignature(String signature) {
        if (signature == null || signature.equals("")) {
            return null;
        }

        final JavaType independent = getIndependent(signature);
        if (independent != null) {
            return independent;
        }

        final JavaType method = getMethodFromSignature(signature);

        int arrayDepth = 0;
        while (signature.startsWith("[")) {
            ++arrayDepth;
            signature = signature.substring(1);
        }

        for (JavaType instance : INDEPENDENT_INSTANCES) {
            if (signature.startsWith(instance.signature())) {
                final JavaType rest = getFromSignature(signature
                        .substring(instance.signature().length()));

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
        final String firstSignature = (validClassReference && firstSemiColon < signature
                .length() - 1) ? signature.substring(0, firstSemiColon + 1)
                : signature;

        if (validClassReference
                && JavaClassType.checkValidSignature(firstSignature)) {
            final JavaType firstType = new JavaClassType(mRootReference, firstSignature);

            if (signature.length() > firstSignature.length()) {
                final JavaType rest = getFromSignature(signature
                        .substring(firstSemiColon + 1));
                if (rest == null) {
                    return null;
                }

                return new JavaTypeList(firstType, rest);
            }

            return firstType;
        }

        return null;
    }
}
