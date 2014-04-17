package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.code.MethodCode;
import sword.java.class_analyzer.pool.ConstantPool;

public class AttributeTable {

    public final List<AbstractAttribute> allAttributes;
    public final List<GenericAttribute> genericAttributes = new ArrayList<GenericAttribute>();

    private CodeAttribute mCode;
    private ExceptionsAttribute mExceptions;
    private SignatureAttribute mSignature;

    public AttributeTable(InputStream inStream, ConstantPool pool) throws IOException, FileError {
        final int count = Utils.getBigEndian2Int(inStream);
        allAttributes = new ArrayList<AbstractAttribute>(count);

        for (int i=0; i<count; i++) {
            final AbstractAttribute attr = AbstractAttribute.get(inStream, pool);
            allAttributes.add(attr);

            if (attr instanceof CodeAttribute) {
                if (mCode != null) {
                    throw new FileError(Kind.ATTRIBUTE_NOT_UNIQUE, CodeAttribute.attrType);
                }

                mCode = (CodeAttribute) attr;
            }
            else if (attr instanceof ExceptionsAttribute) {
                if (mExceptions != null) {
                    throw new FileError(Kind.ATTRIBUTE_NOT_UNIQUE, ExceptionsAttribute.attrType);
                }

                mExceptions = (ExceptionsAttribute) attr;
            }
            else if (attr instanceof SignatureAttribute) {
                if (mSignature != null) {
                    throw new FileError(Kind.ATTRIBUTE_NOT_UNIQUE, SignatureAttribute.attrType);
                }

                mSignature = (SignatureAttribute) attr;
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
     * Returns the code attribute or null if not defined.
     */
    public CodeAttribute getCode() {
        return mCode;
    }

    /**
     * Returns the exceptions attributes or null if not defined.
     */
    public ExceptionsAttribute getExceptions() {
        return mExceptions;
    }

    public SignatureAttribute getSignature() {
    	return mSignature;
    }

    /**
     * Returns the amount of bytes this table is currently consuming.
     * In this count it is included each of the attributes and the 2-byte
     * counter places just before the table.
     */
    public int fileSize() {
        int sizeCount = 2;
        for (AbstractAttribute attribute : allAttributes) {
            sizeCount += attribute.fileSize();
        }

        return sizeCount;
    }

    public MethodCode getMethodCode() {
        return mCode != null ? mCode.getMethodCode() : null;
    }
}
