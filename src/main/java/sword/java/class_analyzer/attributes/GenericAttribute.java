package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class GenericAttribute extends AbstractAttribute {

    public final TextEntry name;
    public final byte info[];

    public GenericAttribute(InputStream inStream, TextEntry name, ConstantPool pool) throws IOException, FileError {
        this.name = name;
        final int attrInfoSize = Utils.getBigEndian4Int(inStream);

        info = new byte[attrInfoSize];
        Utils.fillBuffer(inStream, info);
    }

    @Override
    public String toString() {
        return name.toString() + " has " + info.length + " bytes of info";
    }
}
