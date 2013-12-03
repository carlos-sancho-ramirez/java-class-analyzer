package sword.java.class_analyzer.independent_type;

public abstract class JavaType {

    /**
     * Returns the binary representation of this JavaType
     *
     * Examples:
     *   - int primary type -> I
     *   - boolean primary type -> Z
     *   - array of double -> [D
     *   - matrix of int (int[][]) -> [[I
     *   - reference to java.util.List -> Ljava/util/List;
     *   - Method signature when first parameter is int, and second is String,
     *     and returns nothing (void) -> (ILjava/lang/String;)V
     */
    public abstract String signature();

    /**
     * Returns the java style representation of this JavaType
     *
     * Examples:
     *   - int primary type -> int
     *   - boolean primary type -> boolean
     *   - array of double -> double[]
     *   - matrix of int (int[][]) -> int[][]
     *   - reference to java.util.List -> java.util.List
     */
    public abstract String getJavaRepresentation();

    @Override
    public int hashCode() {
        return signature().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object != null && object instanceof JavaType
                && signature().equals(((JavaType) object).signature());
    }

    public JavaArrayType getArrayType() {
        return (JavaArrayType) JavaTypeFactory.getIndependentTypeFromSignature("[" + signature());
    }

    @Override
    public String toString() {
        return signature();
    }
}
