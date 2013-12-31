package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
import sword.java.class_analyzer.pool.ConstantPool;

public class MethodTable {

    public final MethodInfo methods[];

    public MethodTable(InputStream inStream, ConstantPool pool, ExtendedTypeFactory factory) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);
        methods = new MethodInfo[count];

        for (int counter=0; counter<count; counter++) {
            methods[counter] = new MethodInfo(inStream, pool, factory);
        }
    }

    @Override
    public String toString() {
        String output = "";
        for (int counter = 0; counter<methods.length; counter++) {
            final boolean last = counter == methods.length - 1;
            output = output + methods[counter].toString() + (last? "" : '\n');
        }

        return output;
    }
}
