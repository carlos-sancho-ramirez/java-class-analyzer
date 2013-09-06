package sword.java.class_analyzer.code;

public class InstructionIconst_0 extends AbstractInstruction {

    public static boolean matches(byte code[], int index) {
        final byte opcode = code[index];
        return opcode == 0x03;
    }

    @Override
    public String disassemble() throws InvalidInstruction {
        return "iconst_0";
    }
}
