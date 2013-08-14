package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;

public abstract class ConstantPoolEntry {

    private static final class Types {
        public static final int TEXT = 1;
        public static final int INT = 3;
        public static final int FLOAT = 4;
        public static final int LONG = 5;
        public static final int DOUBLE = 6;
        public static final int CLASS = 7;
        public static final int STRING = 8;
        public static final int FIELD = 9;
        public static final int METHOD = 10;
        public static final int INTERFACE_METHOD = 11;
        public static final int NAME_TYPE_PAIR = 12;
    }

    public static ConstantPoolEntry get(InputStream inStream) throws IOException, FileError {
        final int entryType = inStream.read();

        switch (entryType) {
        case Types.TEXT:
            return new TextEntry(inStream);
        default:
            throw new FileError(Kind.INVALID_POOL_TYPE, "" + entryType);
        }
    }
}
