package sword.java.class_analyzer.pool;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;

/**
 * Instances of this class should never be referenced as they are just there to
 * cover extra indexes for longs and doubles.
 */
public class UnusedEntry extends ConstantPoolEntry {

    @Override
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
        return true;
    }
}
