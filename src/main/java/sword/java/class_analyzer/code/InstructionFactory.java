package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.instructions.AbstractInstruction;
import sword.java.class_analyzer.code.instructions.InstructionAaload;
import sword.java.class_analyzer.code.instructions.InstructionAastore;
import sword.java.class_analyzer.code.instructions.InstructionAload_0;
import sword.java.class_analyzer.code.instructions.InstructionAload_1;
import sword.java.class_analyzer.code.instructions.InstructionAload_2;
import sword.java.class_analyzer.code.instructions.InstructionAload_3;
import sword.java.class_analyzer.code.instructions.InstructionAload_n;
import sword.java.class_analyzer.code.instructions.InstructionAnewarray;
import sword.java.class_analyzer.code.instructions.InstructionAreturn;
import sword.java.class_analyzer.code.instructions.InstructionArraylength;
import sword.java.class_analyzer.code.instructions.InstructionAstore_0;
import sword.java.class_analyzer.code.instructions.InstructionAstore_1;
import sword.java.class_analyzer.code.instructions.InstructionAstore_2;
import sword.java.class_analyzer.code.instructions.InstructionAstore_3;
import sword.java.class_analyzer.code.instructions.InstructionAstore_n;
import sword.java.class_analyzer.code.instructions.InstructionAthrow;
import sword.java.class_analyzer.code.instructions.InstructionBastore;
import sword.java.class_analyzer.code.instructions.InstructionBipush;
import sword.java.class_analyzer.code.instructions.InstructionCheckcast;
import sword.java.class_analyzer.code.instructions.InstructionDup;
import sword.java.class_analyzer.code.instructions.InstructionGetfield;
import sword.java.class_analyzer.code.instructions.InstructionGetstatic;
import sword.java.class_analyzer.code.instructions.InstructionGoto;
import sword.java.class_analyzer.code.instructions.InstructionIadd;
import sword.java.class_analyzer.code.instructions.InstructionIand;
import sword.java.class_analyzer.code.instructions.InstructionIconst_0;
import sword.java.class_analyzer.code.instructions.InstructionIconst_1;
import sword.java.class_analyzer.code.instructions.InstructionIconst_2;
import sword.java.class_analyzer.code.instructions.InstructionIconst_3;
import sword.java.class_analyzer.code.instructions.InstructionIconst_4;
import sword.java.class_analyzer.code.instructions.InstructionIconst_5;
import sword.java.class_analyzer.code.instructions.InstructionIconst_m1;
import sword.java.class_analyzer.code.instructions.InstructionIdiv;
import sword.java.class_analyzer.code.instructions.InstructionIf_acmpeq;
import sword.java.class_analyzer.code.instructions.InstructionIf_acmpne;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmpeq;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmpge;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmpgt;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmple;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmplt;
import sword.java.class_analyzer.code.instructions.InstructionIf_icmpne;
import sword.java.class_analyzer.code.instructions.InstructionIfeq;
import sword.java.class_analyzer.code.instructions.InstructionIfge;
import sword.java.class_analyzer.code.instructions.InstructionIfgt;
import sword.java.class_analyzer.code.instructions.InstructionIfle;
import sword.java.class_analyzer.code.instructions.InstructionIflt;
import sword.java.class_analyzer.code.instructions.InstructionIfne;
import sword.java.class_analyzer.code.instructions.InstructionIinc;
import sword.java.class_analyzer.code.instructions.InstructionIload_0;
import sword.java.class_analyzer.code.instructions.InstructionIload_1;
import sword.java.class_analyzer.code.instructions.InstructionIload_2;
import sword.java.class_analyzer.code.instructions.InstructionIload_3;
import sword.java.class_analyzer.code.instructions.InstructionIload_n;
import sword.java.class_analyzer.code.instructions.InstructionImul;
import sword.java.class_analyzer.code.instructions.InstructionIneg;
import sword.java.class_analyzer.code.instructions.InstructionInvokeinterface;
import sword.java.class_analyzer.code.instructions.InstructionInvokespecial;
import sword.java.class_analyzer.code.instructions.InstructionInvokestatic;
import sword.java.class_analyzer.code.instructions.InstructionInvokevirtual;
import sword.java.class_analyzer.code.instructions.InstructionIor;
import sword.java.class_analyzer.code.instructions.InstructionIrem;
import sword.java.class_analyzer.code.instructions.InstructionIshl;
import sword.java.class_analyzer.code.instructions.InstructionIshr;
import sword.java.class_analyzer.code.instructions.InstructionIstore_0;
import sword.java.class_analyzer.code.instructions.InstructionIstore_1;
import sword.java.class_analyzer.code.instructions.InstructionIstore_2;
import sword.java.class_analyzer.code.instructions.InstructionIstore_3;
import sword.java.class_analyzer.code.instructions.InstructionIstore_n;
import sword.java.class_analyzer.code.instructions.InstructionIsub;
import sword.java.class_analyzer.code.instructions.InstructionIushr;
import sword.java.class_analyzer.code.instructions.InstructionIxor;
import sword.java.class_analyzer.code.instructions.InstructionLdc;
import sword.java.class_analyzer.code.instructions.InstructionLdc_w;
import sword.java.class_analyzer.code.instructions.InstructionNew;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayBoolean;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayByte;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayChar;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayDouble;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayFloat;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayInt;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayLong;
import sword.java.class_analyzer.code.instructions.InstructionNewArrayShort;
import sword.java.class_analyzer.code.instructions.InstructionPop;
import sword.java.class_analyzer.code.instructions.InstructionPutfield;
import sword.java.class_analyzer.code.instructions.InstructionPutstatic;
import sword.java.class_analyzer.code.instructions.InstructionReturn;
import sword.java.class_analyzer.code.instructions.InstructionTableswitch;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionFactory {

    private static final Set<ByteCodeInterpreter> interpreters = new HashSet<ByteCodeInterpreter>();

    static {
        if (
                !interpreters.add(new SimpleByteCodeInterpreter(0x02, 1, InstructionIconst_m1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x03, 1, InstructionIconst_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x04, 1, InstructionIconst_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x05, 1, InstructionIconst_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x06, 1, InstructionIconst_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x07, 1, InstructionIconst_4.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x08, 1, InstructionIconst_5.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x10, 2, InstructionBipush.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x12, 2, InstructionLdc.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x13, 3, InstructionLdc_w.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x15, 2, InstructionIload_n.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x19, 2, InstructionAload_n.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1A, 1, InstructionIload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1B, 1, InstructionIload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1C, 1, InstructionIload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1D, 1, InstructionIload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2A, 1, InstructionAload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2B, 1, InstructionAload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2C, 1, InstructionAload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2D, 1, InstructionAload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x32, 1, InstructionAaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x36, 2, InstructionIstore_n.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3A, 2, InstructionAstore_n.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3B, 1, InstructionIstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3C, 1, InstructionIstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3D, 1, InstructionIstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3E, 1, InstructionIstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4B, 1, InstructionAstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4C, 1, InstructionAstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4D, 1, InstructionAstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4E, 1, InstructionAstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x53, 1, InstructionAastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x54, 1, InstructionBastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x57, 1, InstructionPop.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x59, 1, InstructionDup.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x60, 1, InstructionIadd.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x64, 1, InstructionIsub.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x68, 1, InstructionImul.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x6C, 1, InstructionIdiv.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x70, 1, InstructionIrem.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x74, 1, InstructionIneg.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x78, 1, InstructionIshl.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x7A, 1, InstructionIshr.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x7C, 1, InstructionIushr.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x7E, 1, InstructionIand.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x80, 1, InstructionIor.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x82, 1, InstructionIxor.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x84, 3, InstructionIinc.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x99, 3, InstructionIfeq.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9A, 3, InstructionIfne.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9B, 3, InstructionIflt.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9C, 3, InstructionIfge.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9D, 3, InstructionIfgt.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9E, 3, InstructionIfle.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x9F, 3, InstructionIf_icmpeq.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA0, 3, InstructionIf_icmpne.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA1, 3, InstructionIf_icmplt.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA2, 3, InstructionIf_icmpge.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA3, 3, InstructionIf_icmpgt.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA4, 3, InstructionIf_icmple.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA5, 3, InstructionIf_acmpeq.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA6, 3, InstructionIf_acmpne.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xA7, 3, InstructionGoto.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xAA, 1, InstructionTableswitch.class) {

                    @Override
                    public int expectedByteCodeSize(int index) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public int instructionSize(byte[] code, int index) {
                        if (matches(code, index)) {
                            final int alignedIndex = (index + 4) & 0xFFFFFFFC;
                            final int low = AbstractInstruction.getBigEndian4Int(code, alignedIndex + 4);
                            final int high = AbstractInstruction.getBigEndian4Int(code, alignedIndex + 8);
                            final int entries = high - low + 1;
                            final int endIndex = alignedIndex + (3 + entries) * 4;
                            return endIndex - index;
                        }
                        else {
                            return 0;
                        }
                    }

                }) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB0, 1, InstructionAreturn.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB1, 1, InstructionReturn.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB2, 3, InstructionGetstatic.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB3, 3, InstructionPutstatic.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB4, 3, InstructionGetfield.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB5, 3, InstructionPutfield.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB6, 3, InstructionInvokevirtual.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB7, 3, InstructionInvokespecial.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB8, 3, InstructionInvokestatic.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xB9, 5, InstructionInvokeinterface.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xBB, 3, InstructionNew.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x04, 2, InstructionNewArrayBoolean.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x05, 2, InstructionNewArrayChar.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x06, 2, InstructionNewArrayDouble.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x07, 2, InstructionNewArrayFloat.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x08, 2, InstructionNewArrayByte.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x09, 2, InstructionNewArrayShort.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x0A, 2, InstructionNewArrayInt.class)) ||
                !interpreters.add(new TwoBytesSimpleByteCodeInterpreter(0xBC, 0x0B, 2, InstructionNewArrayLong.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xBD, 3, InstructionAnewarray.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xBE, 1, InstructionArraylength.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xBF, 1, InstructionAthrow.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xC0, 3, InstructionCheckcast.class))
                ) {
            throw new IllegalStateException("One of the interpreters is equal to some other");
        }
    }

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

        for (ByteCodeInterpreter interpreter : interpreters) {
            if (interpreter.matches(code, index)) {
                try {
                    return interpreter.newInstruction(code, index, pool);
                }
                catch (InstantiationException e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Unable to instance instruction with opcode " + code[index]);
                }
            }
        }

        throw new InvalidInstructionException(code, index);
    }
}
