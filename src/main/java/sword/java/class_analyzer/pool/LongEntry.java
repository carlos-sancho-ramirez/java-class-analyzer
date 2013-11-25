package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class LongEntry extends ConstantPoolEntry {

    private long mValue;

    LongEntry(InputStream inStream) throws IOException, FileError {
        mValue = Utils.getBigEndian8Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        return true;
    }

    @Override
    int size() {
        return 2;
    }
}
