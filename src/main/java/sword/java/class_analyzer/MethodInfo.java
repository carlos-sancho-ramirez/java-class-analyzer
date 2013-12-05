package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.attributes.AttributeTable;
import sword.java.class_analyzer.attributes.CodeAttribute;
import sword.java.class_analyzer.attributes.ExceptionsAttribute;
import sword.java.class_analyzer.code.MethodCode;
import sword.java.class_analyzer.java_type.JavaMethod;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class MethodInfo {

    public final ModifierMask accessMask;
    public final TextEntry name;
    public final JavaMethod type;
    public final AttributeTable attributes;

    public MethodInfo(InputStream inStream, ConstantPool pool, ExtendedTypeFactory factory) throws IOException, FileError {
        final int accessMaskValue = Utils.getBigEndian2Int(inStream);
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final int typeIndex = Utils.getBigEndian2Int(inStream);

        accessMask = new MemberModifierMask(accessMaskValue);
        name = pool.get(nameIndex, TextEntry.class);

        final String signature = pool.get(typeIndex, TextEntry.class).text;
        type = factory.getMethodFromSignature(signature);
        if (type == null) {
            throw new FileError(Kind.INVALID_MEMBER_SIGNATURE, "method", signature);
        }

        attributes = new AttributeTable(inStream, pool);
    }

    @Override
    public String toString() {
        String result = accessMask.getModifiersString() + ' ' +
                type.getReturningType().getJavaRepresentation() + ' ' + name +
                '(' + type.getParameterTypeList().getJavaRepresentation() + ')';

        ExceptionsAttribute exceptions = attributes.getExceptions();
        if (exceptions != null) {
            result = result + " throws " + exceptions.exceptionListToString();
        }

        result = result + attributes.genericAttributesToString();

        CodeAttribute code = attributes.getCode();
        if (code != null) {
            result = result + '\n' + code.methodCode;
        }
        return result;
    }

    MethodCode getMethodCode() {
        return attributes != null ? attributes.getMethodCode() : null;
    }
}
