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
    }

    public SignatureResolver(TextEntry text) {
        mText = text;
    }

    public static class ResolvedType {
        public String javaType;
        public String signature = "";
    }

    public static List<ResolvedType> resolve(final String text) throws IllegalArgumentException {
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

    @Override
    public String toString() {
        List<ResolvedType> types = resolve(mText.toString());
        String result = "";

        final int typeCount = types.size();
        for (int i=0; i<typeCount; i++) {
            final String type = types.get(i).javaType;
            final boolean lastType = i == typeCount -1;
            result = result + (lastType? type : (type + ", "));
        }

        return result;
    }
}
