package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public abstract class AbstractAttribute {

    protected final int attrInfoSize;

    AbstractAttribute(InputStream inStream) throws IOException, FileError {
        attrInfoSize = Utils.getBigEndian4Int(inStream);
    }

    public static AbstractAttribute get(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final TextEntry name = pool.get(nameIndex, TextEntry.class);

        final AbstractAttribute result;
        if (CodeAttribute.attrType.equals(name.text)) {
            result = new CodeAttribute(inStream, pool);
        }
        else if (ExceptionsAttribute.attrType.equals(name.text)) {
            result = new ExceptionsAttribute(inStream, pool);
        }
        else if (SignatureAttribute.attrType.equals(name.text)) {
            result = new SignatureAttribute(inStream, pool);
        }
        else {
            result = new GenericAttribute(inStream, name, pool);
        }

        return result;
    }

    /**
     * Returns the amount of bytes this attribute is taking on the file.
     * This method relies on the byte count provided for each generic attribute.
     * The space used by the counter and the name is also included in the size returned.
     */
    public int fileSize() {
        return 2 + 4 + attrInfoSize;
    }
}
