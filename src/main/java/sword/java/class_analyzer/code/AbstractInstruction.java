package sword.java.class_analyzer.code;

public abstract class AbstractInstruction {

    public abstract String disassemble();

    public int size() {
        return 1;
    }
}
