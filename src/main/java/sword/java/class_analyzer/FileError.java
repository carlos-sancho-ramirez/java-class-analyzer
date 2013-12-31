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
        public static final int INVALID_POOL_TYPE_MATCH = 6;

        public static final int INVALID_POOL_INDEX = 7;
        public static final int POOL_INCONSISTENCE = 8;

        public static final int INVALID_MEMBER_SIGNATURE = 9;

        /**
         * Triggered in case an attribute declared has a length smaller that the expected due to it content.
         */
        public static final int TOO_SMALL_ATTRIBUTE = 10;

        public static final int ATTRIBUTE_NOT_UNIQUE = 11;
    }

    private static final Map<Integer,String> messageMap = new HashMap<Integer, String>();
    static {
        messageMap.put(Kind.NO_CLASS_FILE, "The file introduced is not a class file");
        messageMap.put(Kind.UNEXPECTED_END_OF_FILE, "Unexpected end of file has been reached");
        messageMap.put(Kind.FILE_NOT_FOUND, "Unable to find the specified file");
        messageMap.put(Kind.UNABLE_TO_READ, "Unable te extract data from the file");
        messageMap.put(Kind.INVALID_POOL_TYPE, "Found an invalid type within the constant pool. Type found is {0}");
        messageMap.put(Kind.INVALID_POOL_TYPE_MATCH, "Found a non-matching type within the constant pool for index {0}");
        messageMap.put(Kind.INVALID_POOL_INDEX, "Provided index {0} but it is out of the table. Table contains only {1} entries");
        messageMap.put(Kind.POOL_INCONSISTENCE, "Pool is inconsistent");
        messageMap.put(Kind.INVALID_MEMBER_SIGNATURE, "Invalid signature in the constant pool. A valid {0} signature was expected but {1} was found");
        messageMap.put(Kind.TOO_SMALL_ATTRIBUTE, "Attribute {0} has length {1} but at least {2} is expected");
        messageMap.put(Kind.ATTRIBUTE_NOT_UNIQUE, "Attribute {0} is present more than once in the same table, which is forbidden");
    }

    protected int mKind;

    public FileError(int kind, Object... strings) {
        super(MessageFormat.format(messageMap.get(kind), strings));
        mKind = kind;
    }
}
