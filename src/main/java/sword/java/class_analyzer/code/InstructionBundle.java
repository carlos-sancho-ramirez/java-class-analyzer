package sword.java.class_analyzer.code;

import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.Utils;

public class InstructionBundle {

    private final byte code[];

    public InstructionBundle(InputStream inStream, int codeLength) throws IOException, FileError {
        code = new byte[codeLength];
        Utils.fillBuffer(inStream, code);
    }

    @Override
    public String toString() {
        return "{ /* code */ }";
    }
}
