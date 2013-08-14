package sword.java.class_analyzer;

import java.util.HashMap;
import java.util.Map;

public class FileError extends Exception {

    private static final long serialVersionUID = 3825566187380839846L;

    public static final class Kind {
        public static final int NO_CLASS_FILE = 1;
        public static final int UNEXPECTED_END_OF_FILE = 2;
        public static final int FILE_NOT_FOUND = 3;
    }

    private static final Map<Integer,String> messageMap = new HashMap<Integer, String>();
    static {
        messageMap.put(Kind.NO_CLASS_FILE, "The file introduced is not a class file");
        messageMap.put(Kind.UNEXPECTED_END_OF_FILE, "Unexpected end of file has been reached");
        messageMap.put(Kind.FILE_NOT_FOUND, "Unable to find the specified file");
    }

    protected int mKind;

    public FileError(int kind) {
        super(messageMap.get(kind));
        mKind = kind;
    }
}
