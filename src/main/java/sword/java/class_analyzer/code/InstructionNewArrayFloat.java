package sword.java.class_analyzer.code;

public class InstructionNewArrayFloat extends AbstractInstructionNewArray {

    private static final byte expectedType = 0x07;

    public static boolean matches(byte code[], int index) {
        return AbstractInstructionNewArray.matches(code, index, expectedType);
    }

    @Override
    public String disassemble() throws InvalidInstruction {
        return super.disassemble() + "double";
    }
}