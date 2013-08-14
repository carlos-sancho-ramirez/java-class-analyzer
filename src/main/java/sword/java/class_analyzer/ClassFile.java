package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import sword.java.class_analyzer.FileError.Kind;

public class ClassFile {

    private static final byte FILE_SIGNATURE[] = {
        (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE
    };

    public final JavaVersion majorVersion;
    public final int minorVersion;

    public ClassFile(InputStream inStream) throws IOException, FileError {

        // We must check first that this is a class file
        byte signatureBuffer[] = new byte[FILE_SIGNATURE.length];
        inStream.read(signatureBuffer);
        if (!Arrays.equals(signatureBuffer, FILE_SIGNATURE)) {
            throw new FileError(Kind.NO_CLASS_FILE);
        }

        minorVersion = Utils.getBigEndian2Int(inStream);
        majorVersion = JavaVersion.get(Utils.getBigEndian2Int(inStream));
    }

    @Override
    public String toString() {
        return "Class file for version " + majorVersion;
    }
}
