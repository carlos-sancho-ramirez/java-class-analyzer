package sword.java.class_analyzer.independent_type;


public class JavaArrayType extends JavaType {

    private static final String ARRAY_REPRESENTATION = "[]";
    private static final String ARRAY_SIGNATURE = "[";

    /**
     * Return true if this string matches a java class array signature.
     */
    public static boolean isJavaArraySignature(String signature) {
        return getElementSignature(signature) != null;
    }

    /**
     * Returns the array element signature from the array class signature provided.
     * In case the representation is not valid, null will be returned.
     */
    public static String getElementSignature(String signature) {
        return (signature != null && signature.startsWith(ARRAY_SIGNATURE))?
                signature.substring(ARRAY_SIGNATURE.length()) : null;
    }

    public static String getArraySignature(String elementSignature) {
        return ARRAY_SIGNATURE + elementSignature;
    }

    /**
     * Return true if this string matches a java class array representation.
     */
    public static boolean isJavaArrayRepresentation(String representation) {
        return getElementJavaRepresentation(representation) != null;
    }

    /**
     * Returns the array element representation from the array class representation provided.
     * In case the representation is not valid, null will be returned.
     */
    public static String getElementJavaRepresentation(String representation) {
        return (representation != null && representation.endsWith(ARRAY_REPRESENTATION))?
                representation.substring(0, representation.length() - ARRAY_REPRESENTATION.length()) :
                null;
    }

    public static String getArrayRepresentation(String elementRepresentation) {
        return elementRepresentation + ARRAY_REPRESENTATION;
    }

    private final JavaType mArrayType;

    public JavaArrayType(JavaType arrayType) {
        mArrayType = arrayType;
    }

    @Override
    public String signature() {
        return ARRAY_SIGNATURE + mArrayType.signature();
    }

    @Override
    public String getJavaRepresentation() {
        return mArrayType.getJavaRepresentation() + ARRAY_REPRESENTATION;
    }

    public JavaType getElementType() {
        return mArrayType;
    }
}
