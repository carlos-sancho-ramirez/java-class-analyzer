package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream {

    private final InputStream mWrapped;
    private int mReadCounter;

    public InputStreamWrapper(InputStream stream) {
        mWrapped = stream;
    }

    @Override
    public int read() throws IOException {
        int value = mWrapped.read();

        if (value >= 0) {
            mReadCounter++;
        }

        return value;
    }

    public int readBytes() {
        return mReadCounter;
    }
}
