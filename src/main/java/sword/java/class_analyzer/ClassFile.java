package sword.java.class_analyzer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.code.MethodCode;
import sword.java.class_analyzer.independent_type.JavaType;
import sword.java.class_analyzer.java_type.ExtendedTypeFactory;
import sword.java.class_analyzer.java_type.JavaClassType;
import sword.java.class_analyzer.pool.AbstractMethodEntry;
import sword.java.class_analyzer.pool.ClassReferenceEntry;
import sword.java.class_analyzer.pool.ConstantPool;
import sword.java.class_analyzer.pool.FieldEntry;
import sword.java.class_analyzer.ref.ClassReference;
import sword.java.class_analyzer.ref.RootReference;

public class ClassFile {

    private static final byte FILE_SIGNATURE[] = {
        (byte)0xCA, (byte)0xFE, (byte)0xBA, (byte)0xBE
    };

    public final JavaVersion majorVersion;
    public final int minorVersion;
    public final ConstantPool pool;
    public final ModifierMask accessMask;

    public final ClassReferenceEntry thisClassReference;
    public final ClassReferenceEntry superClassReference;

    public final InterfaceTable interfaceTable;
    public final FieldTable fieldTable;
    public final MethodTable methodTable;

    public ClassFile(InputStream inStream, RootReference rootReference) throws IOException, FileError {

        // We must check first that this is a class file
        byte signatureBuffer[] = new byte[FILE_SIGNATURE.length];
        inStream.read(signatureBuffer);
        if (!Arrays.equals(signatureBuffer, FILE_SIGNATURE)) {
            throw new FileError(Kind.NO_CLASS_FILE);
        }

        minorVersion = Utils.getBigEndian2Int(inStream);
        majorVersion = JavaVersion.get(Utils.getBigEndian2Int(inStream));

        final ExtendedTypeFactory factory = new ExtendedTypeFactory(rootReference);
        pool = new ConstantPool(inStream, factory);

        accessMask = new ClassModifierMask(Utils.getBigEndian2Int(inStream));
        final int thisIndex = Utils.getBigEndian2Int(inStream);
        final int superIndex = Utils.getBigEndian2Int(inStream);

        thisClassReference = pool.get(thisIndex, ClassReferenceEntry.class);
        superClassReference = pool.get(superIndex, ClassReferenceEntry.class);

        interfaceTable = new InterfaceTable(inStream, pool);
        fieldTable = new FieldTable(inStream, pool, factory);
        methodTable = new MethodTable(inStream, pool, factory);
    }

    private static void registerClass(Set<ClassReference> set, JavaType type) {
        if (type instanceof JavaClassType) {
            set.add(((JavaClassType) type).getReference());
        }
    }

    public ClassReference getReference() {
        return thisClassReference.getReference();
    }

    /**
     * Returns a set with all classes that this class depends on
     */
    public Set<ClassReference> getReferencedClasses() {
        Set<ClassReference> set = new HashSet<ClassReference>();
        set.add(superClassReference.getReference());

        ClassReferenceEntry interfaces[] = interfaceTable.interfaces;
        if (interfaces.length != 0) {
            for (int i=0; i<interfaces.length; i++) {
                set.add(interfaces[i].getReference());
            }
        }

        for (FieldInfo field : fieldTable.fields) {
            registerClass(set, field.type);
        }

        for (MethodInfo method : methodTable.methods) {

            registerClass(set, method.type.getReturningType());

            JavaType paramTypeList[] = method.type.getParameterTypeList().toArray();
            for (JavaType javaType : paramTypeList) {
                registerClass(set, javaType);
            }

            final MethodCode methodCode = method.getMethodCode();
            if (methodCode == null) {
                continue;
            }

            final Set<FieldEntry> fieldEntries = methodCode.getKnownReferencedFields();
            for (FieldEntry fieldEntry : fieldEntries) {
                set.add(fieldEntry.getReference().getJavaParentReference());
            }

            final Set<AbstractMethodEntry> methodEntries = methodCode.getKnownInvokedMethods();
            for (AbstractMethodEntry methodEntry : methodEntries) {
                set.add(methodEntry.getReference().getJavaParentReference());
            }

            final Set<ClassReferenceEntry> classReferenceEntries = methodCode.getKnownReflectionClassReferences();
            for (ClassReferenceEntry classReference : classReferenceEntries) {
                set.add(classReference.getReference());
            }
        }

        set.remove(thisClassReference.getReference());
        return set;
    }

    @Override
    public String toString() {
        String output = "Class file for version " + majorVersion + "\n";

        output = output + "Class declaration: " + accessMask.getModifiersString() +
                ' ' + thisClassReference + " extends " + superClassReference;

        ClassReferenceEntry interfaces[] = interfaceTable.interfaces;
        if (interfaces.length != 0) {
            output = output + " implements";
            for (int i=0; i<interfaces.length; i++) {
                output = output + ' ' + interfaces[i];
                if (i < interfaces.length - 1) {
                    output = output + ',';
                }
            }
        }

        output = output + "\n\nFields:\n" + fieldTable;

        output = output + "\n\nMethods:\n" + methodTable;

        return output;
    }
}
