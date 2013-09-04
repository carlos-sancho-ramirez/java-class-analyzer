package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class InterfaceTable {

    public final ClassReferenceEntry interfaces[];

    public InterfaceTable(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);
        interfaces = new ClassReferenceEntry[count];

        for (int counter = 0; counter<count; counter++) {
            final int index = Utils.getBigEndian2Int(inStream);
            interfaces[counter] = pool.get(index, ClassReferenceEntry.class);
        }
    }
}
