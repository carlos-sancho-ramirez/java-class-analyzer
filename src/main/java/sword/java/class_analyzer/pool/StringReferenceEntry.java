package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;

public class StringReferenceEntry extends AbstractReferenceEntry {

    public StringReferenceEntry(InputStream inStream) throws IOException,
            FileError {
        super(inStream);
    }

    @Override
    public String toString() {
        return "\"" + super.toString() + "\"";
    }
}
