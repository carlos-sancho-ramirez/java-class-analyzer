package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class AttributeInfo {

    public final TextEntry name;
    public final byte info[];

    public AttributeInfo(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final int attrInfoSize = Utils.getBigEndian4Int(inStream);

        name = pool.get(nameIndex, TextEntry.class);
        info = new byte[attrInfoSize];
        Utils.fillBuffer(inStream, info);
    }

    @Override
    public String toString() {
        return name.toString() + " has " + info.length + " bytes of info";
    }
}
