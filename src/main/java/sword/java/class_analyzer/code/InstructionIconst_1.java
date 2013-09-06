package sword.java.class_analyzer.code;

public class InstructionIconst_1 extends AbstractInstruction {

    public static boolean matches(byte code[], int index) {
        final byte opcode = code[index];
        return opcode == 0x04;
    }

    @Override
    public String disassemble() throws InvalidInstruction {
        return "iconst_1";
    }
}
