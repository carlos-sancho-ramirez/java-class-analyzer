package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.JavaTypeFactory;

public class FloatEntry extends ConstantPoolEntry {

    private int mValue;

    FloatEntry(InputStream inStream) throws IOException, FileError {
        // This float is codified in IEEE 754 single-precision float.
        // TODO: Transforms this in into float if valuable
        mValue = Utils.getBigEndian4Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError {
        return true;
    }
}
