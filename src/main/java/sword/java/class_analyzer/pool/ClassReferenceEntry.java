package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.java_type.JavaTypeFactory;
import sword.java.class_analyzer.ref.ClassReference;

public class ClassReferenceEntry extends AbstractReferenceEntry {

    private ClassReference mReference;

    public ClassReferenceEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }

    @Override
    public String toString() {
        return mReference.getQualifiedName();
    }

    @Override
    boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError {
        final boolean parentResult = super.resolve(pool, factory);

        if (parentResult) {
            mReference = factory.getRootReference().addClass(
                    mTextEntry.getText().replace('/', '.'));
        }

        return parentResult;
    }

    public ClassReference getReference() {
        return mReference;
    }
}
