package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.java_type.JavaType;
import sword.java.class_analyzer.ref.FieldReference;

public class FieldEntry extends AbstractMemberEntry {

    private FieldReference mReference;

    protected FieldEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        final boolean parentResult = super.resolve(pool);

        if (parentResult) {
            JavaType fieldType = JavaType.getFromSignature(mVariableEntry.getType());
            mReference = mClassEntry.getReference().addField(mVariableEntry.getName(), fieldType);
        }

        return parentResult;
    }

    @Override
    public FieldReference getReference() {
        return mReference;
    }

    @Override
    public String toString() {
        return mReference.getQualifiedName() + ' ' + mReference.getTypeSignature();
    }
}
