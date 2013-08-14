package sword.java.class_analyzer;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class FileError extends Exception {

    private static final long serialVersionUID = 3825566187380839846L;

    public static final class Kind {
        public static final int NO_CLASS_FILE = 1;
        public static final int UNEXPECTED_END_OF_FILE = 2;
        public static final int FILE_NOT_FOUND = 3;

        /**
         * Triggered when after reading the stream several times it is always
         * returning with nothing new.
         */
        public static final int UNABLE_TO_READ = 4;

        public static final int INVALID_POOL_TYPE = 5;
    }

    private static final Map<Integer,String> messageMap = new HashMap<Integer, String>();
    static {
        messageMap.put(Kind.NO_CLASS_FILE, "The file introduced is not a class file");
        messageMap.put(Kind.UNEXPECTED_END_OF_FILE, "Unexpected end of file has been reached");
        messageMap.put(Kind.FILE_NOT_FOUND, "Unable to find the specified file");
        messageMap.put(Kind.UNABLE_TO_READ, "Unable te extract data from the file");
        messageMap.put(Kind.INVALID_POOL_TYPE, "Found an invalid type within the constant pool. Type found is {0}");
    }

    protected int mKind;

    public FileError(int kind, Object... strings) {
        super(MessageFormat.format(messageMap.get(kind), strings));
        mKind = kind;
    }
}
