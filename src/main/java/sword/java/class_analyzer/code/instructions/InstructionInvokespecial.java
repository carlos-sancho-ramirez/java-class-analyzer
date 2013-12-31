package sword.java.class_analyzer.code.instructions;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.ByteCodeInterpreter;
import sword.java.class_analyzer.code.IncompleteInstructionException;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.InstanceMethodEntry;

public class InstructionInvokespecial extends AbstractInvokeInstruction<InstanceMethodEntry> {

    public InstructionInvokespecial(byte[] code, int index, ConstantPool pool, ByteCodeInterpreter interpreter) throws IllegalArgumentException,
            IncompleteInstructionException, FileError {
        super(code, index, pool, InstanceMethodEntry.class, interpreter);
    }

    @Override
    public String disassemble() {
        return "invokespecial\t" + methodToString();
    }
}
