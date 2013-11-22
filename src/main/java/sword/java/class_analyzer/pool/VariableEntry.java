package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class VariableEntry extends ConstantPoolEntry {

    private final int mNameReferenceIndex;
    private final int mTypeReferenceIndex;

    private TextEntry mNameEntry;
    private TextEntry mTypeEntry;

    public VariableEntry(InputStream inStream) throws IOException, FileError {
        mNameReferenceIndex = Utils.getBigEndian2Int(inStream);
        mTypeReferenceIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        mNameEntry = pool.get(mNameReferenceIndex, TextEntry.class);
        mTypeEntry = pool.get(mTypeReferenceIndex, TextEntry.class);

        boolean resolved = mNameEntry.mResolved && mTypeEntry.mResolved;
        if (resolved) {
            mResolved = true;
        }

        return resolved;
    }

    public String getName() {
        return mNameEntry.toString();
    }

    public String getType() {
        return mTypeEntry.toString();
    }

    @Override
    public String toString() {
        return mNameEntry.toString() + ' ' + mTypeEntry.toString();
    }
}
