package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class DoubleEntry extends ConstantPoolEntry {

    private long mValue;

    DoubleEntry(InputStream inStream) throws IOException, FileError {
        // This double is codified in IEEE 754
        // TODO: Transforms this in into double if valuable
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
