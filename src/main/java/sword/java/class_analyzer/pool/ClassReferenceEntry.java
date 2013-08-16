package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;

public class ClassReferenceEntry extends AbstractReferenceEntry {

    public ClassReferenceEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }
}
