package sword.java.class_analyzer.code;

/**
 * Thrown in case a instruction founds itself invalid, maybe because it does not
 * have the expected structure or opcodes.
 */
public class InvalidInstruction extends RuntimeException {
    private static final long serialVersionUID = 3582068281027126798L;
}
