package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class ConstantPool {

    public final int poolEntryCount;
    private final List<ConstantPoolEntry> entries = new ArrayList<ConstantPoolEntry>();

    public ConstantPool(InputStream inStream) throws IOException, FileError {
        poolEntryCount = Utils.getBigEndian2Int(inStream);

        for (int counter = 0; counter < poolEntryCount; counter++) {
            entries.add(ConstantPoolEntry.get(inStream));
        }
    }
}
