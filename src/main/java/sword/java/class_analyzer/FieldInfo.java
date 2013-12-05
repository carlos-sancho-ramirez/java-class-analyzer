package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.attributes.AttributeTable;
import sword.java.class_analyzer.independent_type.JavaType;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class FieldInfo {

    public final ModifierMask accessMask;
    public final TextEntry name;
    public final JavaType type;
    public final AttributeTable attributes;

    public FieldInfo(InputStream inStream, ConstantPool pool, ExtendedTypeFactory factory) throws IOException, FileError {
        final int accessMaskValue = Utils.getBigEndian2Int(inStream);
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final int typeIndex = Utils.getBigEndian2Int(inStream);

        accessMask = new MemberModifierMask(accessMaskValue);
        name = pool.get(nameIndex, TextEntry.class);
        final String signature = pool.get(typeIndex, TextEntry.class).text;
        type = factory.getFromSignature(signature);
        if (type == null) {
            throw new FileError(Kind.INVALID_MEMBER_SIGNATURE, "field", signature);
        }

        attributes = new AttributeTable(inStream, pool);
    }

    @Override
    public String toString() {
        return accessMask.getModifiersString() + ' ' + type.getJavaRepresentation() + ' ' + name;
    }
}
