package sword.java.class_analyzer.code;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.pool.ConstantPool;

public class Instances {

    public static final InstructionArraylength ARRAYLENGTH = new InstructionArraylength();
    public static final InstructionBastore BASTORE = new InstructionBastore();
    public static final InstructionDup DUP = new InstructionDup();
    public static final InstructionPop POP = new InstructionPop();
    public static final InstructionReturn RETURN = new InstructionReturn();
    public static final InstructionAthrow ATHROW = new InstructionAthrow();

    public static final InstructionAaload AALOAD = new InstructionAaload();
    public static final InstructionAastore AASTORE = new InstructionAastore();

    public static final InstructionAload_0 ALOAD_0 = new InstructionAload_0();
    public static final InstructionAload_1 ALOAD_1 = new InstructionAload_1();
    public static final InstructionAload_2 ALOAD_2 = new InstructionAload_2();
    public static final InstructionAload_3 ALOAD_3 = new InstructionAload_3();

    public static final InstructionAstore_0 ASTORE_0 = new InstructionAstore_0();
    public static final InstructionAstore_1 ASTORE_1 = new InstructionAstore_1();
    public static final InstructionAstore_2 ASTORE_2 = new InstructionAstore_2();
    public static final InstructionAstore_3 ASTORE_3 = new InstructionAstore_3();

    public static final InstructionIconst_0 ICONST_0 = new InstructionIconst_0();
    public static final InstructionIconst_1 ICONST_1 = new InstructionIconst_1();
    public static final InstructionIconst_2 ICONST_2 = new InstructionIconst_2();
    public static final InstructionIconst_3 ICONST_3 = new InstructionIconst_3();
    public static final InstructionIconst_4 ICONST_4 = new InstructionIconst_4();
    public static final InstructionIconst_5 ICONST_5 = new InstructionIconst_5();
    public static final InstructionIconst_m1 ICONST_M1 = new InstructionIconst_m1();

    public static final InstructionIadd IADD = new InstructionIadd();
    public static final InstructionIsub ISUB = new InstructionIsub();
    public static final InstructionImul IMUL = new InstructionImul();
    public static final InstructionIdiv IDIV = new InstructionIdiv();
    public static final InstructionIrem IREM = new InstructionIrem();

    public static final InstructionIand IAND = new InstructionIand();
    public static final InstructionIor IOR = new InstructionIor();
    public static final InstructionIneg INEG = new InstructionIneg();
    public static final InstructionIxor IXOR = new InstructionIxor();

    public static final InstructionIshl ISHL = new InstructionIshl();
    public static final InstructionIshr ISHR = new InstructionIshr();
    public static final InstructionIushr IUSHR = new InstructionIushr();

    public static final InstructionIload_0 ILOAD_0 = new InstructionIload_0();
    public static final InstructionIload_1 ILOAD_1 = new InstructionIload_1();
    public static final InstructionIload_2 ILOAD_2 = new InstructionIload_2();
    public static final InstructionIload_3 ILOAD_3 = new InstructionIload_3();

    public static final InstructionIstore_0 ISTORE_0 = new InstructionIstore_0();
    public static final InstructionIstore_1 ISTORE_1 = new InstructionIstore_1();
    public static final InstructionIstore_2 ISTORE_2 = new InstructionIstore_2();
    public static final InstructionIstore_3 ISTORE_3 = new InstructionIstore_3();

    public static final InstructionNewArrayBoolean NEW_ARRAY_BOOLEAN = new InstructionNewArrayBoolean();
    public static final InstructionNewArrayByte NEW_ARRAY_BYTE = new InstructionNewArrayByte();
    public static final InstructionNewArrayChar NEW_ARRAY_CHAR = new InstructionNewArrayChar();
    public static final InstructionNewArrayDouble NEW_ARRAY_DOUBLE = new InstructionNewArrayDouble();
    public static final InstructionNewArrayFloat NEW_ARRAY_FLOAT = new InstructionNewArrayFloat();
    public static final InstructionNewArrayInt NEW_ARRAY_INT = new InstructionNewArrayInt();
    public static final InstructionNewArrayLong NEW_ARRAY_LONG = new InstructionNewArrayLong();
    public static final InstructionNewArrayShort NEW_ARRAY_SHORT = new InstructionNewArrayShort();

    /**
     * Returns an instance that matches the byte code represented in the array
     * in the given index.
     *
     * In order to save memory, this method not always creates an instance for
     * each call and instances previously returned can be reused if they are suitable.
     *
     * @param code Array of bytes containing the byte code to be extracted.
     * @param index Pointer for the byte to be read within the array.
     * @param pool ConstantPool used to retrieve constants and linked them to
     * the new generated instruction instances.
     * @return An instance matching the bytecode. Never null
     * @throws InvalidByteCodeException in case a problem is detected in the bytecode analyzed and this method cannot complete the task.
     * @throws FileError In case an error is found in the class file.
     */
    public static AbstractInstruction match(byte code[], int index, ConstantPool pool) throws InvalidByteCodeException, FileError {

        if (InstructionArraylength.interpreter.matches(code, index)) {
            return ARRAYLENGTH;
        }
        else if (InstructionBastore.interpreter.matches(code, index)) {
            return BASTORE;
        }
        else if (InstructionBipush.interpreter.matches(code, index)) {
            return new InstructionBipush(code, index);
        }
        else if (InstructionDup.interpreter.matches(code, index)) {
            return DUP;
        }
        else if (InstructionPop.interpreter.matches(code, index)) {
            return POP;
        }
        else if (InstructionReturn.interpreter.matches(code, index)) {
            return RETURN;
        }
        else if (InstructionAthrow.interpreter.matches(code, index)) {
            return ATHROW;
        }

        else if (InstructionGoto.interpreter.matches(code, index)) {
            return new InstructionGoto(code, index);
        }

        else if (InstructionAaload.interpreter.matches(code, index)) {
            return AALOAD;
        }
        else if (InstructionAastore.interpreter.matches(code, index)) {
            return AASTORE;
        }

        else if (InstructionGetstatic.interpreter.matches(code, index)) {
            return new InstructionGetstatic(code, index, pool);
        }
        else if (InstructionPutstatic.interpreter.matches(code, index)) {
            return new InstructionPutstatic(code, index, pool);
        }
        else if (InstructionGetfield.interpreter.matches(code, index)) {
            return new InstructionGetfield(code, index, pool);
        }
        else if (InstructionPutfield.interpreter.matches(code, index)) {
            return new InstructionPutfield(code, index, pool);
        }

        else if (InstructionIfeq.interpreter.matches(code, index)) {
            return new InstructionIfeq(code, index);
        }
        else if (InstructionIfne.interpreter.matches(code, index)) {
            return new InstructionIfne(code, index);
        }
        else if (InstructionIflt.interpreter.matches(code, index)) {
            return new InstructionIflt(code, index);
        }
        else if (InstructionIfge.interpreter.matches(code, index)) {
            return new InstructionIfge(code, index);
        }
        else if (InstructionIfgt.interpreter.matches(code, index)) {
            return new InstructionIfgt(code, index);
        }
        else if (InstructionIfle.interpreter.matches(code, index)) {
            return new InstructionIfle(code, index);
        }

        else if (InstructionIf_icmpeq.interpreter.matches(code, index)) {
            return new InstructionIf_icmpeq(code, index);
        }
        else if (InstructionIf_icmpne.interpreter.matches(code, index)) {
            return new InstructionIf_icmpne(code, index);
        }
        else if (InstructionIf_icmplt.interpreter.matches(code, index)) {
            return new InstructionIf_icmplt(code, index);
        }
        else if (InstructionIf_icmpge.interpreter.matches(code, index)) {
            return new InstructionIf_icmpge(code, index);
        }
        else if (InstructionIf_icmpgt.interpreter.matches(code, index)) {
            return new InstructionIf_icmpgt(code, index);
        }
        else if (InstructionIf_icmple.interpreter.matches(code, index)) {
            return new InstructionIf_icmple(code, index);
        }

        else if (InstructionIf_acmpeq.interpreter.matches(code, index)) {
            return new InstructionIf_acmpeq(code, index);
        }
        else if (InstructionIf_acmpne.interpreter.matches(code, index)) {
            return new InstructionIf_acmpne(code, index);
        }

        else if (InstructionNew.interpreter.matches(code, index)) {
            return new InstructionNew(code, index, pool);
        }
        else if (InstructionAnewarray.interpreter.matches(code, index)) {
            return new InstructionAnewarray(code, index, pool);
        }

        else if (InstructionInvokevirtual.interpreter.matches(code, index)) {
            return new InstructionInvokevirtual(code, index, pool);
        }
        else if (InstructionInvokespecial.interpreter.matches(code, index)) {
            return new InstructionInvokespecial(code, index, pool);
        }
        else if (InstructionInvokestatic.interpreter.matches(code, index)) {
            return new InstructionInvokestatic(code, index, pool);
        }

        else if (InstructionAload_0.interpreter.matches(code, index)) {
            return ALOAD_0;
        }
        else if (InstructionAload_1.interpreter.matches(code, index)) {
            return ALOAD_1;
        }
        else if (InstructionAload_2.interpreter.matches(code, index)) {
            return ALOAD_2;
        }
        else if (InstructionAload_3.interpreter.matches(code, index)) {
            return ALOAD_3;
        }

        else if (InstructionAstore_0.interpreter.matches(code, index)) {
            return ASTORE_0;
        }
        else if (InstructionAstore_1.interpreter.matches(code, index)) {
            return ASTORE_1;
        }
        else if (InstructionAstore_2.interpreter.matches(code, index)) {
            return ASTORE_2;
        }
        else if (InstructionAstore_3.interpreter.matches(code, index)) {
            return ASTORE_3;
        }

        else if (InstructionIconst_0.interpreter.matches(code, index)) {
            return ICONST_0;
        }
        else if (InstructionIconst_1.interpreter.matches(code, index)) {
            return ICONST_1;
        }
        else if (InstructionIconst_2.interpreter.matches(code, index)) {
            return ICONST_2;
        }
        else if (InstructionIconst_3.interpreter.matches(code, index)) {
            return ICONST_3;
        }
        else if (InstructionIconst_4.interpreter.matches(code, index)) {
            return ICONST_4;
        }
        else if (InstructionIconst_5.interpreter.matches(code, index)) {
            return ICONST_5;
        }
        else if (InstructionIconst_m1.interpreter.matches(code, index)) {
            return ICONST_M1;
        }

        else if (InstructionIadd.interpreter.matches(code, index)) {
            return IADD;
        }
        else if (InstructionIsub.interpreter.matches(code, index)) {
            return ISUB;
        }
        else if (InstructionImul.interpreter.matches(code, index)) {
            return IMUL;
        }
        else if (InstructionIdiv.interpreter.matches(code, index)) {
            return IDIV;
        }
        else if (InstructionIrem.interpreter.matches(code, index)) {
            return IREM;
        }

        else if (InstructionIand.interpreter.matches(code, index)) {
            return IADD;
        }
        else if (InstructionIor.interpreter.matches(code, index)) {
            return IOR;
        }
        else if (InstructionIneg.interpreter.matches(code, index)) {
            return INEG;
        }
        else if (InstructionIxor.interpreter.matches(code, index)) {
            return IXOR;
        }

        else if (InstructionIshl.interpreter.matches(code, index)) {
            return ISHL;
        }
        else if (InstructionIshr.interpreter.matches(code, index)) {
            return ISHR;
        }
        else if (InstructionIushr.interpreter.matches(code, index)) {
            return IUSHR;
        }

        else if (InstructionIinc.interpreter.matches(code, index)) {
            return new InstructionIinc(code, index);
        }

        else if (InstructionIload_0.interpreter.matches(code, index)) {
            return ILOAD_0;
        }
        else if (InstructionIload_1.interpreter.matches(code, index)) {
            return ILOAD_1;
        }
        else if (InstructionIload_2.interpreter.matches(code, index)) {
            return ILOAD_2;
        }
        else if (InstructionIload_3.interpreter.matches(code, index)) {
            return ILOAD_3;
        }
        else if (InstructionIload_n.interpreter.matches(code, index)) {
            return new InstructionIload_n(code, index);
        }

        else if (InstructionIstore_0.interpreter.matches(code, index)) {
            return ISTORE_0;
        }
        else if (InstructionIstore_1.interpreter.matches(code, index)) {
            return ISTORE_1;
        }
        else if (InstructionIstore_2.interpreter.matches(code, index)) {
            return ISTORE_2;
        }
        else if (InstructionIstore_3.interpreter.matches(code, index)) {
            return ISTORE_3;
        }
        else if (InstructionIstore_n.interpreter.matches(code, index)) {
            return new InstructionIstore_n(code, index);
        }

        else if (InstructionNewArrayBoolean.interpreter.matches(code, index)) {
            return NEW_ARRAY_BOOLEAN;
        }
        else if (InstructionNewArrayByte.interpreter.matches(code, index)) {
            return NEW_ARRAY_BYTE;
        }
        else if (InstructionNewArrayChar.interpreter.matches(code, index)) {
            return NEW_ARRAY_CHAR;
        }
        else if (InstructionNewArrayDouble.interpreter.matches(code, index)) {
            return NEW_ARRAY_DOUBLE;
        }
        else if (InstructionNewArrayFloat.interpreter.matches(code, index)) {
            return NEW_ARRAY_FLOAT;
        }
        else if (InstructionNewArrayInt.interpreter.matches(code, index)) {
            return NEW_ARRAY_INT;
        }
        else if (InstructionNewArrayLong.interpreter.matches(code, index)) {
            return NEW_ARRAY_LONG;
        }
        else if (InstructionNewArrayShort.interpreter.matches(code, index)) {
            return NEW_ARRAY_SHORT;
        }

        else if (InstructionLdc.interpreter.matches(code, index)) {
            return new InstructionLdc(code, index, pool);
        }
        else if (InstructionLdc_w.interpreter.matches(code, index)) {
            return new InstructionLdc_w(code, index, pool);
        }
        else if (InstructionCheckcast.interpreter.matches(code, index)) {
            return new InstructionCheckcast(code, index, pool);
        }

        throw new InvalidInstructionException(code, index);
    }
}
