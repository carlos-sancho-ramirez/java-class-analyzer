package sword.java.class_analyzer.ref;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.java_type.JavaMethod;
import sword.java.class_analyzer.java_type.JavaType;

public class PackageReference extends JavaReference {

    private final Set<PackageReference> mSubPackages = new HashSet<PackageReference>();
    private final Set<ClassReference> mClasses = new HashSet<ClassReference>();
    private final PackageReference mParent;

    PackageReference(PackageReference parent, String name) {
        super(name);
        mParent = parent;
    }

    @Override
    public PackageReference getJavaParentReference() {
        return mParent;
    }

    private static final AddNodeLambda<FieldReference, PackageReference, JavaType> addFieldLambda = new AddNodeLambda<FieldReference, PackageReference, JavaType>() {

        @Override
        public FieldReference addIt(PackageReference instance, String first,
                String rest, JavaType javaType) {
            if (rest.contains(".")) {
                return instance.addPackage(first).addField(rest, javaType);
            } else {
                return instance.addClass(first).addField(rest, javaType);
            }
        }

        @Override
        public FieldReference createIt(PackageReference instance, String name,
                JavaType javaType) {
            throw new IllegalArgumentException(
                    "Illegal field reference from package");
        }
    };

    public FieldReference addField(String qualifiedName, JavaType fieldType) {
        return addNode(qualifiedName, null, addFieldLambda, fieldType);
    }

    private static final AddNodeLambda<MethodReference, PackageReference, JavaMethod> addMethodLambda = new AddNodeLambda<MethodReference, PackageReference, JavaMethod>() {

        @Override
        public MethodReference addIt(PackageReference instance, String first,
                String rest, JavaMethod javaType) {
            if (rest.contains(".")) {
                return instance.addPackage(first).addMethod(rest, javaType);
            } else {
                return instance.addClass(first).addMethod(rest, javaType);
            }
        }

        @Override
        public MethodReference createIt(PackageReference instance, String name,
                JavaMethod javaType) {
            throw new IllegalArgumentException(
                    "Illegal field reference from package");
        }
    };

    public MethodReference addMethod(String qualifiedName, JavaMethod javaType) {
        return addNode(qualifiedName, null, addMethodLambda, javaType);
    }

    private static final AddNodeLambda<ClassReference, PackageReference, JavaType> addClassLambda = new AddNodeLambda<ClassReference, PackageReference, JavaType>() {

        @Override
        public ClassReference addIt(PackageReference instance, String first,
                String rest, JavaType javaType) {
            return instance.addPackage(first).addClass(rest);
        }

        @Override
        public ClassReference createIt(PackageReference instance, String name,
                JavaType javaType) {
            return new ClassReference(instance, name);
        }
    };

    public ClassReference addClass(String qualifiedName) {
        return addNode(qualifiedName, mClasses, addClassLambda, null);
    }

    public ArrayClassReference addArrayClass(ClassReference element) {
        final ArrayClassReference array = new ArrayClassReference(element);
        mClasses.add(element);
        mClasses.add(array);
        return array;
    }

    private static final AddNodeLambda<PackageReference, PackageReference, JavaType> addPackageLambda = new AddNodeLambda<PackageReference, PackageReference, JavaType>() {

        @Override
        public PackageReference addIt(PackageReference instance, String first,
                String rest, JavaType javaType) {
            return instance.addPackage(first).addPackage(rest);
        }

        @Override
        public PackageReference createIt(PackageReference instance,
                String name, JavaType javaType) {
            return new PackageReference(instance, name);
        }
    };

    /**
     * Returns a PackageReference for the given qualified name. This method also
     * registers the instance. A new instance will be created only if the
     * reference was not registered for this namespace root.
     *
     * @param qualifiedName
     *            , relative to the current package, for the package to add.
     *            This should be in the format "java.util"
     *
     * @throws NullPointerException
     *             if qualifiedName is null
     * @throws IllegalArgumentException
     *             if an invalid reference is provided.
     */
    public PackageReference addPackage(String qualifiedName) {
        return addNode(qualifiedName, mSubPackages, addPackageLambda, null);
    }

    @Override
    public File getFile(File classPath) {
        return new File(mParent.getFile(classPath), mName);
    }

    @Override
    public Set<ClassReference> setOfClasses() {
        final Set<ClassReference> set = new HashSet<ClassReference>();

        for (ClassReference classRef : mClasses) {
            set.add(classRef);
        }

        for (PackageReference packageRef : mSubPackages) {
            set.addAll(packageRef.setOfClasses());
        }

        return set;
    }
}
