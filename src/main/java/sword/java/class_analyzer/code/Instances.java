package sword.java.class_analyzer.code;

public class Instances {

    public static final InstructionIconst_0 ICONST_0 = new InstructionIconst_0();
    public static final InstructionIconst_1 ICONST_1 = new InstructionIconst_1();
    public static final InstructionIconst_2 ICONST_2 = new InstructionIconst_2();
    public static final InstructionIconst_3 ICONST_3 = new InstructionIconst_3();
    public static final InstructionIconst_4 ICONST_4 = new InstructionIconst_4();
    public static final InstructionIconst_5 ICONST_5 = new InstructionIconst_5();
    public static final InstructionIconst_m1 ICONST_M1 = new InstructionIconst_m1();

    public static final InstructionNewArrayBoolean NEW_ARRAY_BOOLEAN = new InstructionNewArrayBoolean();
    public static final InstructionNewArrayByte NEW_ARRAY_BYTE = new InstructionNewArrayByte();
    public static final InstructionNewArrayChar NEW_ARRAY_CHAR = new InstructionNewArrayChar();
    public static final InstructionNewArrayDouble NEW_ARRAY_DOUBLE = new InstructionNewArrayDouble();
    public static final InstructionNewArrayFloat NEW_ARRAY_FLOAT = new InstructionNewArrayFloat();
    public static final InstructionNewArrayInt NEW_ARRAY_INT = new InstructionNewArrayInt();
    public static final InstructionNewArrayLong NEW_ARRAY_LONG = new InstructionNewArrayLong();
    public static final InstructionNewArrayShort NEW_ARRAY_SHORT = new InstructionNewArrayShort();

    public static AbstractInstruction match(byte code[], int index) {

        if (InstructionIconst_0.matches(code, index)) {
            return ICONST_0;
        }
        else if (InstructionIconst_1.matches(code, index)) {
            return ICONST_1;
        }
        else if (InstructionIconst_2.matches(code, index)) {
            return ICONST_2;
        }
        else if (InstructionIconst_3.matches(code, index)) {
            return ICONST_3;
        }
        else if (InstructionIconst_4.matches(code, index)) {
            return ICONST_4;
        }
        else if (InstructionIconst_5.matches(code, index)) {
            return ICONST_5;
        }
        else if (InstructionIconst_m1.matches(code, index)) {
            return ICONST_M1;
        }

        else if (InstructionNewArrayBoolean.matches(code, index)) {
            return NEW_ARRAY_BOOLEAN;
        }
        else if (InstructionNewArrayByte.matches(code, index)) {
            return NEW_ARRAY_BYTE;
        }
        else if (InstructionNewArrayChar.matches(code, index)) {
            return NEW_ARRAY_CHAR;
        }
        else if (InstructionNewArrayDouble.matches(code, index)) {
            return NEW_ARRAY_DOUBLE;
        }
        else if (InstructionNewArrayFloat.matches(code, index)) {
            return NEW_ARRAY_FLOAT;
        }
        else if (InstructionNewArrayInt.matches(code, index)) {
            return NEW_ARRAY_INT;
        }
        else if (InstructionNewArrayLong.matches(code, index)) {
            return NEW_ARRAY_LONG;
        }
        else if (InstructionNewArrayShort.matches(code, index)) {
            return NEW_ARRAY_SHORT;
        }

        return null;
    }
}
