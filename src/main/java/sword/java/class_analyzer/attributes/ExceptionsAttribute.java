package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class ExceptionsAttribute extends AbstractAttribute {

    static final String attrType = "Exceptions";

    public final ClassReferenceEntry exceptions[];

    ExceptionsAttribute(InputStream inStream, ConstantPool pool)
            throws IOException, FileError {

        final int attrInfoSize = Utils.getBigEndian4Int(inStream);
        final int exceptionCount = Utils.getBigEndian2Int(inStream);
        final int expectedInfoLength = (exceptionCount + 1) * 2;

        final int extraBytes = attrInfoSize - expectedInfoLength;
        if (extraBytes < 0) {
            throw new FileError(Kind.TOO_SMALL_ATTRIBUTE, attrType, attrInfoSize,
                    expectedInfoLength);
        }

        exceptions = new ClassReferenceEntry[exceptionCount];
        for (int i=0; i<exceptionCount; i++) {
            final int exceptionIndex = Utils.getBigEndian2Int(inStream);
            exceptions[i] = pool.get(exceptionIndex, ClassReferenceEntry.class);
        }

        Utils.skipBytes(inStream, extraBytes);
    }

    public String exceptionListToString() {
        String result = "";

        final int exceptionCount = exceptions.length;
        for (int i=0; i<exceptionCount; i++) {
            final String exception = exceptions[i].toString();
            final boolean lastException = i == exceptionCount -1;
            result = result + (lastException? exception : (exception + ", "));
        }

        return result;
    }
}
