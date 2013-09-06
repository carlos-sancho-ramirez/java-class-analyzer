package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;

public class AttributeTable {

    public final List<GenericAttribute> genericAttributes = new ArrayList<GenericAttribute>();

    private ExceptionsAttribute mExceptions;

    public AttributeTable(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);

        for (int i=0; i<count; i++) {
            final AbstractAttribute attr = AbstractAttribute.get(inStream, pool);

            if (attr instanceof ExceptionsAttribute) {
                mExceptions = (ExceptionsAttribute) attr;
            }
            else {
                genericAttributes.add( (GenericAttribute) attr);
            }
        }
    }

    public String genericAttributesToString() {
        String output = "";
        final int attrCount = genericAttributes.size();
        for (int counter = 0; counter<attrCount; counter++) {
            final boolean last = counter == attrCount - 1;
            output = output + " @ " + genericAttributes.get(counter).toString() + (last? "" : '\n');
        }

        return output;
    }

    /**
     * Returns the exceptions attributes or null if not defined.
     */
    public ExceptionsAttribute getExceptions() {
        return mExceptions;
    }
}
