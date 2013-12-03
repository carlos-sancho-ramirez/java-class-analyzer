package sword.java.class_analyzer.java_type;

import sword.java.class_analyzer.independent_type.JavaArrayType;
import sword.java.class_analyzer.independent_type.JavaType;
import sword.java.class_analyzer.ref.RootReference;

public class JavaTypeFactory extends sword.java.class_analyzer.independent_type.JavaTypeFactory {

    private RootReference mRootReference;

    public JavaTypeFactory(RootReference rootReference) {
        mRootReference = rootReference;
    }

    public RootReference getRootReference() {
        return mRootReference;
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
        if (returningType == null || returningType instanceof JavaTypeList) {
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

        final JavaType independent = getIndependentTypeFromSignature(signature);
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
