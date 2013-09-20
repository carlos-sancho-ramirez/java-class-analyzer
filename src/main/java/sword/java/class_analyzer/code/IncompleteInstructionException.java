package sword.java.class_analyzer.code;

public class IncompleteInstructionException extends InvalidByteCodeException {

    private static final long serialVersionUID = 1375399078661515741L;

    protected IncompleteInstructionException() {
        super("Length for last instruction is shorter than expected");
    }
}
