package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.attributes.AttributeTable;
import sword.java.class_analyzer.attributes.CodeAttribute;
import sword.java.class_analyzer.attributes.ExceptionsAttribute;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class MethodInfo {

    public final ModifierMask accessMask;
    public final TextEntry name;
    public final SignatureResolver type;
    public final AttributeTable attributes;

    public MethodInfo(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int accessMaskValue = Utils.getBigEndian2Int(inStream);
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final int typeIndex = Utils.getBigEndian2Int(inStream);

        accessMask = new ModifierMask(accessMaskValue);
        name = pool.get(nameIndex, TextEntry.class);
        type = new SignatureResolver(pool.get(typeIndex, TextEntry.class));
        attributes = new AttributeTable(inStream, pool);
    }

    @Override
    public String toString() {
        String result = accessMask.getModifiersString() + ' ' + type.returnTypeToString() +
                ' ' + name + '(' + type + ')';
        ExceptionsAttribute exceptions = attributes.getExceptions();
        if (exceptions != null) {
            result = result + " throws " + exceptions.exceptionListToString();
        }

        result = result + attributes.genericAttributesToString();

        CodeAttribute code = attributes.getCode();
        if (code != null) {
            result = result + ' ' + code.instructionBundle;
        }
        return result;
    }
}
