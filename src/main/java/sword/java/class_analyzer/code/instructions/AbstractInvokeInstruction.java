package sword.java.class_analyzer.code.instructions;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.MethodEntry;

public abstract class AbstractInvokeInstruction extends AbstractConstantPoolReferenceInstruction {

    protected final MethodEntry mMethod;

    protected AbstractInvokeInstruction(byte[] code, int index, ConstantPool pool,
            ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, interpreter);
        mMethod = pool.get(mPoolIndex, MethodEntry.class);
    }

    @Override
    public Set<MethodEntry> getKnownInvokedMethods() {
        Set<MethodEntry> methods = new HashSet<MethodEntry>(1);
        methods.add(mMethod);
        return methods;
    }

    protected String methodToString() {
        return mMethod.toString();
    }
}
