package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.java_type.JavaType;
import sword.java.class_analyzer.ref.MethodReference;

public class MethodEntry extends AbstractMemberEntry {

    private MethodReference mReference;

    protected MethodEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool) throws FileError {
        final boolean parentResult = super.resolve(pool);

        if (parentResult) {
            JavaType methodType = JavaType.getFromSignature(mVariableEntry.getType());
            mReference = mClassEntry.getReference().addMethod(mVariableEntry.getName(), methodType);
        }

        return parentResult;
    }

    @Override
    public MethodReference getReference() {
        return mReference;
    }

    @Override
    public String toString() {
        return mReference.getQualifiedName() + ' ' + mReference.getTypeSignature();
    }
}
