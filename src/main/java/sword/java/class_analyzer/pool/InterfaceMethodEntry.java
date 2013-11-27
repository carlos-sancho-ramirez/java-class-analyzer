package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.java_type.JavaMethod;
import sword.java.class_analyzer.java_type.JavaTypeFactory;
import sword.java.class_analyzer.ref.MethodReference;

public class InterfaceMethodEntry extends AbstractMemberEntry {

    private MethodReference mReference;

    InterfaceMethodEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, JavaTypeFactory factory) throws FileError {
        final boolean parentResult = super.resolve(pool, factory);

        if (parentResult) {
            JavaMethod methodType = factory.getMethodFromSignature(mVariableEntry
                    .getType());

            if (methodType == null) {
                throw new FileError(Kind.INVALID_MEMBER_SIGNATURE, "method", mVariableEntry.getType());
            }

            mReference = mClassEntry.getReference().addMethod(
                        mVariableEntry.getName(), methodType);
        }

        return parentResult;
    }

    @Override
    public MethodReference getReference() {
        return mReference;
    }
}
