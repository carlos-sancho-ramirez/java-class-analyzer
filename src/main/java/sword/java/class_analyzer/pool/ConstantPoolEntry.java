package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.java_type.JavaTypeFactory;

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

        case Types.INT:
            return new IntEntry(inStream);

        case Types.FLOAT:
            return new FloatEntry(inStream);

        case Types.LONG:
            return new LongEntry(inStream);

        case Types.DOUBLE:
            return new DoubleEntry(inStream);

        case Types.CLASS:
            return new ClassReferenceEntry(inStream);

        case Types.STRING:
            return new StringReferenceEntry(inStream);

        case Types.FIELD:
            return new FieldEntry(inStream);

        case Types.METHOD:
            return new InstanceMethodEntry(inStream);

        case Types.INTERFACE_METHOD:
            return new InterfaceMethodEntry(inStream);

        case Types.NAME_TYPE_PAIR:
            return new VariableEntry(inStream);

        default:
            throw new FileError(Kind.INVALID_POOL_TYPE, "" + entryType);
        }
    }

    boolean mResolved;

    /**
     * Uses the pool loaded to resolve all data by extracting by linking the
     * data within the pool.
     *
     * @return whether this reference is actually resolved, this can be false if
     * some dependencies are not yet resolved.
     */
    abstract boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError;

    /**
     * Returns the number of blocks that this entry takes within the constant pool.
     *
     * Only long and double is expected to return here 2. All others will return 1
     */
    int size() {
        return 1;
    }
}
