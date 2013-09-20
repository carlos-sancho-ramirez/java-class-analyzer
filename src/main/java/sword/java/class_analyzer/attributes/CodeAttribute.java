package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.code.InstructionBundle;
import sword.java.class_analyzer.pool.ConstantPool;

public class CodeAttribute extends AbstractAttribute {

    static final String attrType = "Code";

    public final int maxStackEntries;
    public final int maxLocalVariables;

    public final InstructionBundle instructionBundle;
    public final CodeException exceptions[];
    public final AttributeTable attributes;

    CodeAttribute(InputStream inStream, ConstantPool pool)
            throws IOException, FileError {

        super(inStream);

        maxStackEntries = Utils.getBigEndian2Int(inStream);
        maxLocalVariables = Utils.getBigEndian2Int(inStream);

        final int codeLength = Utils.getBigEndian4Int(inStream);
        instructionBundle = new InstructionBundle(inStream, codeLength, pool);

        final int codeExceptionCount = Utils.getBigEndian2Int(inStream);
        exceptions = new CodeException[codeExceptionCount];

        attributes = new AttributeTable(inStream, pool);

        final int expectedInfoSize = 2 + 2 + 4 + codeLength + 2 + codeExceptionCount * 8 + attributes.fileSize();
        final int extraBytes = attrInfoSize - expectedInfoSize;
        if (extraBytes < 0) {
            throw new FileError(Kind.TOO_SMALL_ATTRIBUTE, attrType, attrInfoSize,
                    expectedInfoSize);
        }

        Utils.skipBytes(inStream, extraBytes);
    }
}
