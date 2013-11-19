package sword.java.class_analyzer.ref;

import java.util.HashSet;
import java.util.Set;

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

    private static final AddNodeLambda<FieldReference, PackageReference> addFieldLambda = new AddNodeLambda<FieldReference, PackageReference>() {

        @Override
        public FieldReference addIt(PackageReference instance, String first,
                String rest, JavaType returningType, JavaType... paramTypes) {
            if (rest.contains(".")) {
                return instance.addPackage(first).addField(rest, returningType);
            } else {
                return instance.addClass(first).addField(rest, returningType);
            }
        }

        @Override
        public FieldReference createIt(PackageReference instance, String name,
                JavaType returningType, JavaType... paramTypes) {
            throw new IllegalArgumentException(
                    "Illegal field reference from package");
        }
    };

    public FieldReference addField(String qualifiedName, JavaType fieldType) {
        return addNode(qualifiedName, null, addFieldLambda, fieldType,
                (JavaType[]) null);
    }

    private static final AddNodeLambda<MethodReference, PackageReference> addMethodLambda = new AddNodeLambda<MethodReference, PackageReference>() {

        @Override
        public MethodReference addIt(PackageReference instance, String first,
                String rest, JavaType returningType, JavaType... paramTypes) {
            if (rest.contains(".")) {
                return instance.addPackage(first).addMethod(rest,
                        returningType, paramTypes);
            } else {
                return instance.addClass(first).addMethod(rest, returningType,
                        paramTypes);
            }
        }

        @Override
        public MethodReference createIt(PackageReference instance, String name,
                JavaType returningType, JavaType... paramTypes) {
            throw new IllegalArgumentException(
                    "Illegal field reference from package");
        }
    };

    public MethodReference addMethod(String qualifiedName,
            JavaType returningType, JavaType... paramTypes) {
        return addNode(qualifiedName, null, addMethodLambda, returningType,
                paramTypes);
    }

    private static final AddNodeLambda<ClassReference, PackageReference> addClassLambda = new AddNodeLambda<ClassReference, PackageReference>() {

        @Override
        public ClassReference addIt(PackageReference instance, String first,
                String rest, JavaType returningType, JavaType... paramTypes) {
            return instance.addPackage(first).addClass(rest);
        }

        @Override
        public ClassReference createIt(PackageReference instance, String name,
                JavaType returningType, JavaType... paramTypes) {
            return new ClassReference(instance, name);
        }
    };

    public ClassReference addClass(String qualifiedName) {
        return addNode(qualifiedName, mClasses, addClassLambda, null,
                (JavaType[]) null);
    }

    private static final AddNodeLambda<PackageReference, PackageReference> addPackageLambda = new AddNodeLambda<PackageReference, PackageReference>() {

        @Override
        public PackageReference addIt(PackageReference instance, String first,
                String rest, JavaType returningType, JavaType... paramTypes) {
            return instance.addPackage(first).addPackage(rest);
        }

        @Override
        public PackageReference createIt(PackageReference instance,
                String name, JavaType returningType, JavaType... paramTypes) {
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
        return addNode(qualifiedName, mSubPackages, addPackageLambda, null,
                (JavaType[]) null);
    }
}
