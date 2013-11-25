package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class IntEntry extends ConstantPoolEntry {

    private int mValue;

    IntEntry(InputStream inStream) throws IOException, FileError {
        mValue = Utils.getBigEndian4Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        return true;
    }

}
