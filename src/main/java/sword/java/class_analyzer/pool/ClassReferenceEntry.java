package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.java_type.JavaArrayType;
import sword.java.class_analyzer.java_type.JavaClassType;
import sword.java.class_analyzer.java_type.JavaType;
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
            String text = mTextEntry.getText();

            if (text.startsWith("[")) {
                final JavaType javaType = factory.getFromSignature(text);
                if (javaType != null) {
                    int depth = 0;
                    JavaType elementType = javaType;
                    while (elementType instanceof JavaArrayType) {
                        final JavaArrayType array = (JavaArrayType) elementType;
                        elementType = array.getElementType();
                        depth++;
                    }

                    if (elementType instanceof JavaClassType) {
                        final JavaClassType classType = (JavaClassType) elementType;
                        mReference = classType.getReference();

                        while (depth-- > 0) {
                            mReference = mReference.getJavaParentReference().addArrayClass(mReference);
                        }
                    }
                }
            }

            if (mReference == null) {
                mReference = factory.getRootReference().addClass(
                        mTextEntry.getText().replace('/', '.'));
            }
        }

        return parentResult;
    }

    public ClassReference getReference() {
        return mReference;
    }
}
