package sword.java.class_analyzer.ref;

import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.java_type.JavaType;

public class ClassReference extends JavaReference {

    private final Set<FieldReference> mFields = new HashSet<FieldReference>();
    private final Set<MethodReference> mMethods =
            new HashSet<MethodReference>();

    private final PackageReference mPackage;

    ClassReference(PackageReference javaPackage, String name) {
        super(name);
        mPackage = javaPackage;
    }

    @Override
    public PackageReference getJavaParentReference() {
        return mPackage;
    }

    private static final AddNodeLambda<FieldReference, ClassReference> addFieldLambda =
            new AddNodeLambda<FieldReference, ClassReference>() {

                @Override
                public FieldReference addIt(ClassReference instance,
                        String first, String rest) {
                    throw new UnsupportedOperationException(
                            "Only simple names are allowed for fields from the class namespace");
                }

                @Override
                public FieldReference createIt(ClassReference instance,
                        String name, JavaType returningType,
                        JavaType... paramTypes) {
                    return new FieldReference(instance, name, returningType);
                }
            };

    public FieldReference addField(String qualifiedName, JavaType fieldType) {
        return addNode(qualifiedName, mFields, addFieldLambda, fieldType,
                (JavaType[]) null);
    }

    private static final AddNodeLambda<MethodReference, ClassReference> addMethodLambda =
            new AddNodeLambda<MethodReference, ClassReference>() {

                @Override
                public MethodReference addIt(ClassReference instance,
                        String first, String rest) {
                    throw new UnsupportedOperationException(
                            "Only simple names are allowed for fields from the class namespace");
                }

                @Override
                public MethodReference createIt(ClassReference instance,
                        String name, JavaType returningType,
                        JavaType... paramTypes) {
                    return new MethodReference(instance, name, returningType,
                            paramTypes);
                }
            };

    public MethodReference addMethod(String qualifiedName,
            JavaType returningType, JavaType... paramTypes) {
        return addNode(qualifiedName, mMethods, addMethodLambda, returningType,
                paramTypes);
    }
}
