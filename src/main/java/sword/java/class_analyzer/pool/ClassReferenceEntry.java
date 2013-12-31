package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.independent_type.JavaArrayType;
import sword.java.class_analyzer.independent_type.JavaType;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
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
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
        final boolean parentResult = super.resolve(pool, factory);

        if (parentResult) {
            String text = mTextEntry.getText();

            String representation = null;
            if (JavaArrayType.isJavaArraySignature(text)) {
                final JavaType javaType = factory.getFromSignature(text);
                if (javaType != null) {
                    representation = javaType.getJavaRepresentation();
                }
            }

            if (representation == null) {
                representation = text.replace('/', '.');
            }

            mReference = factory.getRootReference().addClass(representation);
        }

        return parentResult;
    }

    public ClassReference getReference() {
        return mReference;
    }
}
