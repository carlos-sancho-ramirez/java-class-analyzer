package sword.java.class_analyzer.code;


public class InvalidInstructionException extends InvalidByteCodeException {

    private static final long serialVersionUID = 4792032580720285668L;

    /**
     * Number of bytes of raw data that must be shown when an invalid bytecode is found.
     */
    private static final int MAX_DISPLAYED_INVALID_BYTECODE = 4;

    private static String extractBytes(byte code[], int index) {
        final int maxInvalidCode = MAX_DISPLAYED_INVALID_BYTECODE;
        String invalidByteCode = "";

        if (maxInvalidCode > 0) {
            int counter = 1;
            invalidByteCode = Integer.toHexString((code[index]) & 0xFF);

            while (counter < maxInvalidCode && (index + counter) < code.length) {
                invalidByteCode = invalidByteCode + ' ' + Integer.toHexString((code[index + counter]) & 0xFF);
                counter++;
            }
        }

        return invalidByteCode;
    }

    public InvalidInstructionException(byte code[], int index) {
        super("Invalid instruction found " + extractBytes(code, index));
    }
}
