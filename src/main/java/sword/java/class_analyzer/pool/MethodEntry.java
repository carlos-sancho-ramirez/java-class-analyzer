package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;

public class MethodEntry extends AbstractMemberEntry {

    protected MethodEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }

    @Override
    public String toString() {
        return getName() + ' ' + getType();
    }
}
