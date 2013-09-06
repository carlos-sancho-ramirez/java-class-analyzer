package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public abstract class AbstractAttribute {

    public static AbstractAttribute get(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final TextEntry name = pool.get(nameIndex, TextEntry.class);

        final AbstractAttribute result;
        if (ExceptionsAttribute.attrType.equals(name.text)) {
            result = new ExceptionsAttribute(inStream, pool);
        }
        else {
            result = new GenericAttribute(inStream, name, pool);
        }

        return result;
    }
}
