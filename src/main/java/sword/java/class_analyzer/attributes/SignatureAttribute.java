package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.TextEntry;

public class SignatureAttribute extends AbstractAttribute {

    static final String attrType = "Signature";

    public final TextEntry signature;

    SignatureAttribute(InputStream inStream, ConstantPool pool)
            throws IOException, FileError {

        super(inStream);
        final int signatureIndex = Utils.getBigEndian2Int(inStream);
        signature = pool.get(signatureIndex, TextEntry.class);
        Utils.skipBytes(inStream, attrInfoSize - 2);
    }

    public String signatureToString() {
    	return signature.toString();
    }
}
