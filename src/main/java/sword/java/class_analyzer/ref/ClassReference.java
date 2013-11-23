package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.java_type.JavaMethod;
import sword.java.class_analyzer.java_type.JavaType;

public class ClassReference extends JavaReference {

    private final Set<FieldReference> mFields = new HashSet<FieldReference>();
    private final Set<MethodReference> mMethods = new HashSet<MethodReference>();

    private final PackageReference mPackage;

    ClassReference(PackageReference javaPackage, String name) {
        super(name);
        mPackage = javaPackage;
    }

    @Override
    public PackageReference getJavaParentReference() {
        return mPackage;
    }

    private static final AddNodeLambda<FieldReference, ClassReference, JavaType> addFieldLambda = new AddNodeLambda<FieldReference, ClassReference, JavaType>() {

        @Override
        public FieldReference addIt(ClassReference instance, String first,
                String rest, JavaType javaType) {
            throw new UnsupportedOperationException(
                    "Only simple names are allowed for fields from the class namespace");
        }

        @Override
        public FieldReference createIt(ClassReference instance, String name,
                JavaType javaType) {
            return new FieldReference(instance, name, javaType);
        }
    };

    public FieldReference addField(String qualifiedName, JavaType fieldType) {
        return addNode(qualifiedName, mFields, addFieldLambda, fieldType);
    }

    private static final AddNodeLambda<MethodReference, ClassReference, JavaMethod> addMethodLambda = new AddNodeLambda<MethodReference, ClassReference, JavaMethod>() {

        @Override
        public MethodReference addIt(ClassReference instance, String first,
                String rest, JavaMethod javaType) {
            throw new UnsupportedOperationException(
                    "Only simple names are allowed for fields from the class namespace");
        }

        @Override
        public MethodReference createIt(ClassReference instance, String name,
                JavaMethod javaType) {
            return new MethodReference(instance, name, javaType);
        }
    };

    public MethodReference addMethod(String qualifiedName, JavaMethod methodType) {
        return addNode(qualifiedName, mMethods, addMethodLambda, methodType);
    }

    @Override
    public File getFile(File classPath) {
        return new File(mPackage.getFile(classPath), mName + ".class");
    }
}
