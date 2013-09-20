package sword.java.class_analyzer.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionBundle {

    private static class InstructionHolder {
        public int index;
        public AbstractInstruction instruction;

        public InstructionHolder(int index, AbstractInstruction instruction) {
            this.index = index;
            this.instruction = instruction;
        }
    }

    /**
     * Set to true if this bundle includes valid checked code.
     */
    private boolean mValid;
    private List<InstructionHolder> mHolders = new ArrayList<InstructionHolder>();
    private String mInvalidReason;

    public InstructionBundle(InputStream inStream, int codeLength, ConstantPool pool) throws IOException, FileError {
        final byte code[] = new byte[codeLength];
        Utils.fillBuffer(inStream, code);

        int counter = 0;
        mValid = true;
        try {
            while (counter < codeLength) {
                AbstractInstruction instruction = Instances.match(code, counter, pool);
                InstructionHolder holder = new InstructionHolder(counter, instruction);
                mHolders.add(holder);
                counter += instruction.byteCodeSize();
            }
        } catch(InvalidByteCodeException e) {
            mValid = false;
            mInvalidReason = e.getInvalidReason();
        }
    }

    @Override
    public String toString() {

        String result = "";
        if (!mValid) {
            result = result + "WARNING: Instruction bundle not valid. " + mInvalidReason + '\n';
        }

        result = result + "{\n";
        for (InstructionHolder holder : mHolders) {
            result = result + "  " + holder.index + '\t' + holder.instruction.disassemble() + '\n';
        }

        result = result + "}\n";

        return result;
    }
}
