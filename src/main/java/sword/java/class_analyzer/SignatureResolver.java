package sword.java.class_analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sword.java.class_analyzer.pool.TextEntry;

public class SignatureResolver {

    private final TextEntry mText;

    private static final Map<Character, String> primitiveTypes =
            new HashMap<Character, String>();

    static {
        primitiveTypes.put('B', "byte");
        primitiveTypes.put('C', "char");
        primitiveTypes.put('D', "double");
        primitiveTypes.put('F', "float");
        primitiveTypes.put('I', "int");
        primitiveTypes.put('J', "long");
        primitiveTypes.put('S', "short");
        primitiveTypes.put('Z', "boolean");
        primitiveTypes.put('V', "void");
    }

    public SignatureResolver(TextEntry text) {
        mText = text;
    }

    public static class ResolvedType {
        public String javaType;
        public String signature = "";
    }

    public static List<ResolvedType> resolveTypeList(final String text) throws IllegalArgumentException {
        final int textLength = text.length();
        int index = 0;
        final List<ResolvedType> returnList = new ArrayList<ResolvedType>();

        while (index < textLength) {
            ResolvedType result = new ResolvedType();
            char currentChar = text.charAt(index++);
            int arrayDepth = 0;
            while (currentChar == '[') {
                if (index >= textLength) {
                    throw new IllegalArgumentException("Found character [ at the end of the signature");
                }

                arrayDepth++;
                result.signature = result.signature + '[';
                currentChar = text.charAt(index++);
            }

            result.javaType = primitiveTypes.get(currentChar);

            if (result.javaType != null) {
                result.signature = result.signature + currentChar;
            }
            else if (currentChar == 'L'){
                final int semicolonPosition = text.indexOf(';', index);
                if (semicolonPosition < 0) {
                    throw new IllegalArgumentException("Class reference found in the signature with no ; at the end");
                }

                result.signature = result.signature + text.substring(index - 1, semicolonPosition + 1);
                result.javaType = text.substring(index, semicolonPosition).replace('/', '.');
                index = semicolonPosition + 1;
            }
            else {
                throw new IllegalArgumentException("Wrong character on signature: " + currentChar);
            }

            for (int i=0; i<arrayDepth; i++) {
                result.javaType = result.javaType + "[]";
            }

            returnList.add(result);
        }

        return returnList;
    }

    public static class ResolvedMethod {
        public List<ResolvedType> parameters;
        public String parametersSignature;
        public ResolvedType returnType;
    }

    public static boolean hasMethodSignature(String signature) {
        final int closingParameters = signature.indexOf(')');
        return !(signature.charAt(0) != '(' || closingParameters < 0 || closingParameters == signature.length() - 1);
    }

    public static ResolvedMethod resolveMethod(String signature) throws IllegalArgumentException {
        final int closingParameters = signature.indexOf(')');

        if (!hasMethodSignature(signature)) {
            throw new IllegalArgumentException("Worng method signature");
        }

        ResolvedMethod returned = new ResolvedMethod();
        returned.parametersSignature = signature.substring(1, closingParameters);
        returned.parameters = resolveTypeList(returned.parametersSignature);

        final List<ResolvedType> returnedTypes = resolveTypeList(signature.substring(closingParameters + 1));
        if (returnedTypes.size() != 1) {
            throw new IllegalArgumentException("Returning multiple types is not allowed");
        }
        returned.returnType = returnedTypes.get(0);

        return returned;
    }

    /**
     * Returns the list of types in the signature with comma separation.
     *
     * In case of being a method signature, the resulting list will only reflect
     * the parameters for the method, but not the returning type.
     */
    public String typeListToString() {
        final String signature = mText.toString();

        final List<ResolvedType> types;
        if (hasMethodSignature(signature)) {
            types = resolveMethod(signature).parameters;
        }
        else {
            types = resolveTypeList(signature);
        }

        String result = "";

        final int typeCount = types.size();
        for (int i=0; i<typeCount; i++) {
            final String type = types.get(i).javaType;
            final boolean lastType = i == typeCount -1;
            result = result + (lastType? type : (type + ", "));
        }

        return result;
    }

    /**
     * Returns the java-like returning type in case this is a method signature
     * or an empty string in any other case.
     */
    public String returnTypeToString() {
        return resolveMethod(mText.toString()).returnType.javaType;
    }

    @Override
    public String toString() {
        return typeListToString();
    }
}
