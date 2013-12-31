package sword.java.class_analyzer.code;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError;
import sword.java.class_analyzer.code.instructions.*;
import sword.java.class_analyzer.pool.ConstantPool;

public class InstructionFactory {

    private static final Set<ByteCodeInterpreter> interpreters = new HashSet<ByteCodeInterpreter>();

    static {
        if (
                !interpreters.add(new SimpleByteCodeInterpreter(0x01, 1, InstructionAconst_null.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x02, 1, InstructionIconst_m1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x03, 1, InstructionIconst_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x04, 1, InstructionIconst_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x05, 1, InstructionIconst_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x06, 1, InstructionIconst_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x07, 1, InstructionIconst_4.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x08, 1, InstructionIconst_5.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x09, 1, InstructionLconst_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0A, 1, InstructionLconst_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0B, 1, InstructionFconst_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0C, 1, InstructionFconst_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0D, 1, InstructionFconst_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0E, 1, InstructionDconst_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x0F, 1, InstructionDconst_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x10, 2, InstructionBipush.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x11, 3, InstructionSipush.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x12, 2, InstructionLdc.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x13, 3, InstructionLdc_w.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x14, 3, InstructionLdc2_w.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x15, 2, InstructionIload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x16, 2, InstructionLload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x17, 2, InstructionFload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x18, 2, InstructionDload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x19, 2, InstructionAload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1A, 1, InstructionIload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1B, 1, InstructionIload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1C, 1, InstructionIload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1D, 1, InstructionIload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1E, 1, InstructionLload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x1F, 1, InstructionLload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x20, 1, InstructionLload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x21, 1, InstructionLload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x22, 1, InstructionFload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x23, 1, InstructionFload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x24, 1, InstructionFload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x25, 1, InstructionFload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x26, 1, InstructionDload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x27, 1, InstructionDload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x28, 1, InstructionDload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x29, 1, InstructionDload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2A, 1, InstructionAload_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2B, 1, InstructionAload_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2C, 1, InstructionAload_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2D, 1, InstructionAload_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2E, 1, InstructionIaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x2F, 1, InstructionLaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x30, 1, InstructionFaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x31, 1, InstructionDaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x32, 1, InstructionAaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x33, 1, InstructionBaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x34, 1, InstructionCaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x35, 1, InstructionSaload.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x36, 2, InstructionIstore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x37, 2, InstructionLstore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x38, 2, InstructionFstore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x39, 2, InstructionDstore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3A, 2, InstructionAstore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3B, 1, InstructionIstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3C, 1, InstructionIstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3D, 1, InstructionIstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3E, 1, InstructionIstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x3F, 1, InstructionLstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x40, 1, InstructionLstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x41, 1, InstructionLstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x42, 1, InstructionLstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x43, 1, InstructionFstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x44, 1, InstructionFstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x45, 1, InstructionFstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x46, 1, InstructionFstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x47, 1, InstructionDstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x48, 1, InstructionDstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x49, 1, InstructionDstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4A, 1, InstructionDstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4B, 1, InstructionAstore_0.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4C, 1, InstructionAstore_1.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4D, 1, InstructionAstore_2.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4E, 1, InstructionAstore_3.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x4F, 1, InstructionIastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x50, 1, InstructionLastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x51, 1, InstructionFastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x52, 1, InstructionDastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x53, 1, InstructionAastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x54, 1, InstructionBastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x55, 1, InstructionCastore.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x56, 1, InstructionSastore.class)) ||
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
                !interpreters.add(new SimpleByteCodeInterpreter(0x85, 1, InstructionI2l.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x86, 1, InstructionI2f.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x87, 1, InstructionI2d.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x88, 1, InstructionL2i.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x89, 1, InstructionL2f.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8A, 1, InstructionL2d.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8B, 1, InstructionF2i.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8C, 1, InstructionF2l.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8D, 1, InstructionF2d.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8E, 1, InstructionD2i.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x8F, 1, InstructionD2l.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x90, 1, InstructionD2f.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x91, 1, InstructionI2b.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x92, 1, InstructionI2c.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x93, 1, InstructionI2s.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x94, 1, InstructionLcmp.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x95, 1, InstructionFcmpl.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x96, 1, InstructionFcmpg.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x97, 1, InstructionDcmpl.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0x98, 1, InstructionDcmpg.class)) ||
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
                !interpreters.add(new SimpleByteCodeInterpreter(0xAC, 1, InstructionIreturn.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xAD, 1, InstructionLreturn.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xAE, 1, InstructionFreturn.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xAF, 1, InstructionDreturn.class)) ||
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
                !interpreters.add(new SimpleByteCodeInterpreter(0xC0, 3, InstructionCheckcast.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xC1, 3, InstructionInstanceof.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xC6, 3, InstructionIfnull.class)) ||
                !interpreters.add(new SimpleByteCodeInterpreter(0xC7, 3, InstructionIfnonnull.class))
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
