package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public abstract class AbstractMemberEntry extends ConstantPoolEntry {

    private final int mClassReferenceIndex;
    private final int mVarReferenceIndex;

    private ClassReferenceEntry mClass;
    private VariableEntry mVariable;

    protected AbstractMemberEntry(InputStream inStream) throws IOException, FileError {
        mClassReferenceIndex = Utils.getBigEndian2Int(inStream);
        mVarReferenceIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        mClass = pool.get(mClassReferenceIndex, ClassReferenceEntry.class);
        mVariable = pool.get(mVarReferenceIndex, VariableEntry.class);

        boolean resolved = mClass.mResolved && mVariable.mResolved;
        if (resolved) {
            mResolved = true;
        }

        return resolved;
    }

    public String getName() {
        return mClass.toString() + '.' + mVariable.getName();
    }

    public String getType() {
        return mVariable.getType();
    }
}
