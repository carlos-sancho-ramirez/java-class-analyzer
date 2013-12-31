package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;

public class InterfaceMethodEntry extends AbstractMethodEntry {

    InterfaceMethodEntry(InputStream inStream) throws IOException, FileError {
        super(inStream);
    }
}
