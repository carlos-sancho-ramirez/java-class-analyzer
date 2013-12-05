package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
import sword.java.class_analyzer.ref.MemberReference;

public abstract class AbstractMemberEntry extends ConstantPoolEntry {

    private final int mClassReferenceIndex;
    private final int mVarReferenceIndex;

    ClassReferenceEntry mClassEntry;
    VariableEntry mVariableEntry;

    AbstractMemberEntry(InputStream inStream) throws IOException, FileError {
        mClassReferenceIndex = Utils.getBigEndian2Int(inStream);
        mVarReferenceIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
        mClassEntry = pool.get(mClassReferenceIndex, ClassReferenceEntry.class);
        mVariableEntry = pool.get(mVarReferenceIndex, VariableEntry.class);

        boolean resolved = mClassEntry.mResolved && mVariableEntry.mResolved;
        if (resolved) {
            mResolved = true;
        }

        return resolved;
    }

    abstract MemberReference getReference();

    public String getName() {
        return getReference().getQualifiedName();
    }

    public String getType() {
        return mVariableEntry.getType();
    }

    @Override
    public String toString() {
        final MemberReference reference = getReference();
        return reference.getQualifiedName() + ' '
                + reference.getTypeSignature();
    }
}
