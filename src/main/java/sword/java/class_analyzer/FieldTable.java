package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.java_type.JavaTypeFactory;
import sword.java.class_analyzer.pool.ConstantPool;

public class FieldTable {

    public final FieldInfo fields[];

    public FieldTable(InputStream inStream, ConstantPool pool, JavaTypeFactory factory) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);
        fields = new FieldInfo[count];

        for (int counter=0; counter<count; counter++) {
            fields[counter] = new FieldInfo(inStream, pool, factory);
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int counter = 0; counter<fields.length; counter++) {
            final boolean last = counter == fields.length - 1;
            output = output + fields[counter].toString() + (last? "" : '\n');
        }

        return output;
    }
}
