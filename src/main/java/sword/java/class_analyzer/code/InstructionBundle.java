package sword.java.class_analyzer.code;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

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
    private boolean valid;
    private List<InstructionHolder> holders = new ArrayList<InstructionHolder>();

    public InstructionBundle(InputStream inStream, int codeLength) throws IOException, FileError {
        final byte code[] = new byte[codeLength];
        Utils.fillBuffer(inStream, code);

        int counter = 0;
        valid = true;
        while (counter < codeLength) {
            AbstractInstruction instruction = Instances.match(code, counter);

            if (instruction != null) {
                InstructionHolder holder = new InstructionHolder(counter, instruction);
                holders.add(holder);
                counter += instruction.size();
            }
            else {
                valid = false;
                break;
            }
        }
    }

    @Override
    public String toString() {

        String result = "";
        if (!valid) {
            result = result + "WARNING: Instruction bundle not valid.\n";
        }

        result = result + "{\n";
        for (InstructionHolder holder : holders) {
            result = result + "  " + holder.index + '\t' + holder.instruction.disassemble() + '\n';
        }

        result = result + "}\n";

        return result;
    }
}
