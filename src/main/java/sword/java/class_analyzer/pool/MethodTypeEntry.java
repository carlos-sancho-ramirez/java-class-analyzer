package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;

public class MethodTypeEntry extends ConstantPoolEntry {

    private final int mDescriptionIndex;

    private TextEntry mDescription;

    public MethodTypeEntry(InputStream inStream) throws IOException, FileError {
        mDescriptionIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
    	mDescription = pool.get(mDescriptionIndex, TextEntry.class);
    	mResolved = mDescription.mResolved;
    	return mResolved;
    }

    @Override
    public String toString() {
        return mDescription.toString();
    }
}
