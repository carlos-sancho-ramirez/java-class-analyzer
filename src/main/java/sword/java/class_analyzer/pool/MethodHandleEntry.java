package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;

public class MethodHandleEntry extends ConstantPoolEntry {

	// TODO: Implement this class properly

    public MethodHandleEntry(InputStream inStream) throws IOException, FileError {
    	Utils.skipBytes(inStream, 3);
    }

    @Override
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
        return true;
    }
}
