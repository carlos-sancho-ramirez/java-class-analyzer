package sword.java.class_analyzer.attributes;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class CodeException {

    public final int start;
    public final int end;
    public final int handler;
    public final int catchType;

    CodeException(InputStream inStream) throws IOException, FileError {
        start = Utils.getBigEndian2Int(inStream);
        end = Utils.getBigEndian2Int(inStream);
        handler = Utils.getBigEndian2Int(inStream);
        catchType = Utils.getBigEndian2Int(inStream);
    }
}
