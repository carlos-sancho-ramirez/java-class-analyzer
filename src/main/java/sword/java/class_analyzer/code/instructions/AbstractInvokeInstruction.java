package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.AbstractMethodEntry;
import sword.java.class_analyzer.pool.ConstantPool;

public abstract class AbstractInvokeInstruction<T extends AbstractMethodEntry> extends AbstractConstantPoolReferenceInstruction {

    protected final T mMethod;

    protected AbstractInvokeInstruction(byte[] code, int index, ConstantPool pool,
            Class<T> entryClass, ByteCodeInterpreter interpreter) throws
            IllegalArgumentException, IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mMethod = pool.get(mPoolIndex, entryClass);
    }

    @Override
    public Set<AbstractMethodEntry> getKnownInvokedMethods() {
        Set<AbstractMethodEntry> methods = new HashSet<AbstractMethodEntry>(1);
        methods.add(mMethod);
        return methods;
    }

    protected String methodToString() {
        return mMethod.toString();
    }
}
