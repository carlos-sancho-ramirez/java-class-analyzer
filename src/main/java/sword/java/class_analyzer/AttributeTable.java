package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.pool.ConstantPool;

public class AttributeTable {

    public final AttributeInfo attrs[];

    public AttributeTable(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);
        attrs = new AttributeInfo[count];

        for (int counter=0; counter<count; counter++) {
            attrs[counter] = new AttributeInfo(inStream, pool);
        }
    }

    public int size() {
        return attrs.length;
    }

    @Override
    public String toString() {
        String output = "";
        for (int counter = 0; counter<attrs.length; counter++) {
            final boolean last = counter == attrs.length - 1;
            output = output + attrs[counter].toString() + (last? "" : '\n');
        }

        return output;
    }
}
