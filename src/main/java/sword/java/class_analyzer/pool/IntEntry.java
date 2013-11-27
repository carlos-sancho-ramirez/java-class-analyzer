package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.JavaTypeFactory;

public class IntEntry extends ConstantPoolEntry {

    private int mValue;

    IntEntry(InputStream inStream) throws IOException, FileError {
        mValue = Utils.getBigEndian4Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError {
        return true;
    }

}
