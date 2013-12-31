package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;

public final class Utils {

    private static final int TRIES = 10;

    private Utils() {}

    public static int getBigEndian2Int(InputStream inStream) throws IOException, FileError {
        int value1 = inStream.read();
        int value2 = inStream.read();

        if (value1 < 0 || value2 < 0 ) {
            throw new FileError(Kind.UNEXPECTED_END_OF_FILE);
        }

        return (value1 << 8) + value2;
    }

    public static int getBigEndian4Int(InputStream inStream) throws IOException, FileError {
        int value1 = inStream.read();
        int value2 = inStream.read();
        int value3 = inStream.read();
        int value4 = inStream.read();

        if (value1 < 0 || value2 < 0 || value3 < 0 || value4 < 0) {
            throw new FileError(Kind.UNEXPECTED_END_OF_FILE);
        }

        return (((((value1 << 8) + value2) << 8) + value3) << 8) + value4;
    }

    public static long getBigEndian8Int(InputStream inStream) throws IOException, FileError {
        long value1 = inStream.read();
        long value2 = inStream.read();
        long value3 = inStream.read();
        long value4 = inStream.read();
        long value5 = inStream.read();
        int value6 = inStream.read();
        int value7 = inStream.read();
        int value8 = inStream.read();

        if (value1 < 0 || value2 < 0 || value3 < 0 || value4 < 0 ||
                value5 < 0 || value6 < 0 || value7 < 0 || value8 < 0) {
            throw new FileError(Kind.UNEXPECTED_END_OF_FILE);
        }

        return ((((((((((((value1 << 8) + value2) << 8) + value3) << 8) +
                value4) << 8) + value5) << 8 + value6) << 8) + value7) << 8) + value8;
    }

    public static void fillBuffer(InputStream inStream, final byte[] buffer) throws IOException, FileError {
        final int bytes = buffer.length;
        int index = 0;
        int triesCount = TRIES;
        while (index < bytes) {
            int readNow = inStream.read(buffer, index, bytes - index);
            if (readNow < 0) {
                throw new FileError(Kind.UNEXPECTED_END_OF_FILE);
            }
            else if (readNow == 0) {
                if ((--triesCount) == 0) {
                    throw new FileError(Kind.UNABLE_TO_READ);
                }
            }

            index += readNow;
        }
    }

    public static void skipBytes(InputStream inStream, long byteCount) throws IOException, FileError {
        long remaining = byteCount;
        int triesCount = TRIES;

        while (remaining > 0) {
            long skippedNow = inStream.skip(remaining);
            if (skippedNow <= 0) {
                if ((--triesCount) == 0) {
                    throw new FileError(Kind.UNABLE_TO_READ);
                }
            }
            else {
                remaining -= skippedNow;
            }
        }
    }
}
