package sword.java.class_analyzer.code;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import sword.java.class_analyzer.code.instructions.AbstractInstruction;
import sword.java.class_analyzer.pool.ConstantPool;

public class SimpleByteCodeInterpreter implements ByteCodeInterpreter {

    private final int mFirstOpcode;
    private final int mSize;
    private final Constructor<? extends AbstractInstruction> mConstructor;

    public SimpleByteCodeInterpreter(int firstOpcode,
            int byteCodeSize, Class<? extends AbstractInstruction> instructionClass) throws IllegalArgumentException {
        mFirstOpcode = firstOpcode;
        mSize = byteCodeSize;

        if (byteCodeSize < 1) {
            throw new IllegalArgumentException();
        }

        try {
            mConstructor = instructionClass.getConstructor(byte[].class, int.class,
                    ConstantPool.class, ByteCodeInterpreter.class);
        }
        catch (SecurityException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean matches(byte[] code, int index) {
        if (index < 0 || index >= code.length) return false;

        final int opcode = (code[index]) & 0xFF;
        return opcode == expectedFirstOpcode();
    }

    @Override
    public int instructionSize(byte[] code, int index) {
        return matches(code, index)? expectedByteCodeSize(index) : 0;
    }

    @Override
    public int expectedByteCodeSize(int index) {
        return mSize;
    }

    @Override
    public int expectedFirstOpcode() {
        return mFirstOpcode;
    }

    @Override
    public AbstractInstruction newInstruction(byte[] code, int index,
            ConstantPool pool) throws InstantiationException {
        try {
            return mConstructor.newInstance(code, index, pool, this);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        throw new InstantiationException();
    }

    @Override
    public int hashCode() {
        return mFirstOpcode;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SimpleByteCodeInterpreter && object != null) {
            final SimpleByteCodeInterpreter other = (SimpleByteCodeInterpreter) object;
            return expectedFirstOpcode() == other.expectedFirstOpcode();
        }

        return false;
    }
}
