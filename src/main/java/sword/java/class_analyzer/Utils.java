package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;

public final class Utils {

    private Utils() {}

    public static int getBigEndian2Int(InputStream inStream) throws IOException, FileError {
        int value1 = inStream.read();
        int value2 = inStream.read();

        if (value1 < 0 || value2 < 0 ) {
            throw new FileError(Kind.UNEXPECTED_END_OF_FILE);
        }

        return (value1 << 8) + value2;
    }
}
