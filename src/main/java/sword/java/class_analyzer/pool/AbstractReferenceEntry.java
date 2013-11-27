package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.JavaTypeFactory;

public abstract class AbstractReferenceEntry extends ConstantPoolEntry {

    private final int mTextEntryIndex;
    protected TextEntry mTextEntry;

    public AbstractReferenceEntry(InputStream inStream) throws IOException, FileError {
        mTextEntryIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError {
        mTextEntry = pool.get(mTextEntryIndex, TextEntry.class);

        final boolean resolved = mTextEntry.mResolved;
        if (resolved) {
            mResolved = true;
        }

        return resolved;
    }

    @Override
    public String toString() {
        return mTextEntry.toString();
    }
}
