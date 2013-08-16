package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class TextEntry extends ConstantPoolEntry {

    public final String text;

    public TextEntry(InputStream inStream) throws IOException, FileError {
        final int bytes = Utils.getBigEndian2Int(inStream);
        final byte buffer[] = new byte[bytes];
        Utils.fillBuffer(inStream, buffer);
        text = new String(buffer);

        mResolved = true;
    }

    @Override
    boolean resolve(ConstantPool pool) {
        return true;
    }
}
