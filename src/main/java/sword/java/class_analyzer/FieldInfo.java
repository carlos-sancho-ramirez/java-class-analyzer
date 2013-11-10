package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.attributes.AttributeTable;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class FieldInfo {

    public final ModifierMask accessMask;
    public final TextEntry name;
    public final SignatureResolver type;
    public final AttributeTable attributes;

    public FieldInfo(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int accessMaskValue = Utils.getBigEndian2Int(inStream);
        final int nameIndex = Utils.getBigEndian2Int(inStream);
        final int typeIndex = Utils.getBigEndian2Int(inStream);

        accessMask = new MemberModifierMask(accessMaskValue);
        name = pool.get(nameIndex, TextEntry.class);
        type = new SignatureResolver(pool.get(typeIndex, TextEntry.class));
        attributes = new AttributeTable(inStream, pool);
    }

    @Override
    public String toString() {
        return accessMask.getModifiersString() + ' ' + type + ' ' + name;
    }
}
