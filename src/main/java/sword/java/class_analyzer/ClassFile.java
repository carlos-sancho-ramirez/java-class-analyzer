package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public class ClassFile {

    private static final byte FILE_SIGNATURE[] = {
        (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE
    };

    public final JavaVersion majorVersion;
    public final int minorVersion;
    public final ConstantPool pool;
    public final ModifierMask accessMask;

    public final ClassReferenceEntry thisClassReference;
    public final ClassReferenceEntry superClassReference;

    public final InterfaceTable interfaceTable;

    public ClassFile(InputStream inStream) throws IOException, FileError {

        // We must check first that this is a class file
        byte signatureBuffer[] = new byte[FILE_SIGNATURE.length];
        inStream.read(signatureBuffer);
        if (!Arrays.equals(signatureBuffer, FILE_SIGNATURE)) {
            throw new FileError(Kind.NO_CLASS_FILE);
        }

        minorVersion = Utils.getBigEndian2Int(inStream);
        majorVersion = JavaVersion.get(Utils.getBigEndian2Int(inStream));

        pool = new ConstantPool(inStream);

        accessMask = new ModifierMask(Utils.getBigEndian2Int(inStream));
        final int thisIndex = Utils.getBigEndian2Int(inStream);
        final int superIndex = Utils.getBigEndian2Int(inStream);

        thisClassReference = pool.get(thisIndex, ClassReferenceEntry.class);
        superClassReference = pool.get(superIndex, ClassReferenceEntry.class);

        interfaceTable = new InterfaceTable(inStream, pool);
    }

    @Override
    public String toString() {
        String output = "Class file for version " + majorVersion + "\n";

        output = output + "Class declaration: " + accessMask.getModifiersString()
                + " class " + thisClassReference + " extends " + superClassReference;

        ClassReferenceEntry interfaces[] = interfaceTable.interfaces;
        if (interfaces.length != 0) {
            output = output + " implements";
            for (int i=0; i<interfaces.length; i++) {
                output = output + ' ' + interfaces[i];
                if (i < interfaces.length - 1) {
                    output = output + ',';
                }
            }
        }

        return output;
    }
}
