package sword.java.class_analyzer.pool;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;

public class InvokeDynamicEntry extends ConstantPoolEntry {

	private final int mNameAndTypeIndex;

	private VariableEntry mVariable;

    public InvokeDynamicEntry(InputStream inStream) throws IOException, FileError {
    	// TODO: Handling properly the index given for the bootstrap method attribute index
    	Utils.getBigEndian2Int(inStream);
    	mNameAndTypeIndex = Utils.getBigEndian2Int(inStream);
    }

    @Override
    boolean resolve(ConstantPool pool, ExtendedTypeFactory factory) throws FileError {
        mVariable = pool.get(mNameAndTypeIndex, VariableEntry.class);
        mResolved = mVariable.mResolved;
        return mResolved;
    }

    @Override
    public String toString() {
        return mVariable.toString();
    }
}
