package sword.java.class_analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sword.java.class_analyzer.ref.ClassReference;
import sword.java.class_analyzer.ref.PackageReference;
import sword.java.class_analyzer.ref.RootReference;
import sword.java.class_analyzer.ref.SimpleClassReference;

public class ProjectAnalyzer {

    private static final class ProgramArguments {
        /**
         * System dependent path to the folder where class fiels are located.
         */
        public static final int CLASS_PATH = 0;

        /**
         * Qualified reference for a java class. It must be in the form "java.util.List"
         */
        public static final int MAIN_JAVA_CLASS = 1;

        public static final int LENGTH = 2;
    }

    private static final class ClassHolder {

        public final ClassReference reference;
        private boolean mChecked; // set to true if the classFile has been tried to be retrieved
        private ClassFile mClassFile;

        public ClassHolder(ClassReference reference) {
            this.reference = reference;

            if (reference == null) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int hashCode() {
            return reference.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return object != null && object instanceof ClassHolder &&
                    reference.equals(((ClassHolder) object).reference);
        }

        public File getFile(File classPath) {
            return reference.getFile(classPath);
        }

        /**
         * Sets the reference in state checked. This action can only be
         * performed once. The created ClassFile can be provided as a parameter.
         * If the file is not expected this method con be called with a null
         * reference.
         * @param classFile ClassFile data structure for the file once loaded.
         */
        public void setChecked(ClassFile classFile) {
            if (mChecked) {
                throw new UnsupportedOperationException();
            }

            mClassFile = classFile;
            mChecked = true;
        }

        public boolean isChecked() {
            return mChecked;
        }

        public boolean loaded() {
            return mClassFile != null;
        }

        public ClassFile getClassFile() {
            return mClassFile;
        }
    }

    private static final ClassHolder findFirstNotChecked(Set<ClassHolder> holders) {
        for (ClassHolder holder : holders) {
            if (!holder.isChecked()) {
                return holder;
            }
        }

        return null;
    }

    private static void checkClassPath(File packageFile, PackageReference reference) {
        final String list[] = packageFile.list();
        if (list == null) return;

        for (String name : list) {
            File file = new File(packageFile, name);

            if (file.isDirectory()) {
                PackageReference newReference = reference.addPackage(name);
                checkClassPath(file, newReference);
            }
            else if (file.isFile() && name.endsWith(SimpleClassReference.FILE_EXTENSION) &&
                    name.length() > SimpleClassReference.FILE_EXTENSION.length()) {
                final int extensionPosition = name.length() - SimpleClassReference.FILE_EXTENSION.length();
                reference.addClass(name.substring(0, extensionPosition));
            }
        }
    }

    private static RootReference checkClassPath(File classPath) {
        final RootReference root = new RootReference();
        checkClassPath(classPath, root);
        return root;
    }

    private static ClassFile loadClass(File file, RootReference dependenciesReference) {

        InputStreamWrapper inStream = null;

        try {
            try {
                inStream = new InputStreamWrapper(new FileInputStream(file));

                try {
                    return new ClassFile(inStream, dependenciesReference);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        inStream.close();
                    }
                    catch (IOException e) { }
                }
            }
            catch (FileNotFoundException e) {
            }
        }
        catch (FileError e) {
            final int filePosition = inStream.readBytes();
            System.out.println("  Error found when loading the file at position " + filePosition + " (0x" + Integer.toHexString(filePosition) + ')');
            e.printStackTrace();
        }

        return null;
    }

    public static Set<ClassFile> analyzeProject(ClassReference mainClass,
            File classPath, final Set<ClassReference> notFound) {

        final RootReference root = mainClass.getRootReference();
        Set<ClassHolder> classHolders = new HashSet<ClassHolder>();
        classHolders.add(new ClassHolder(mainClass));

        int checkedAmount = 0;
        int foundAmount = 0;

        while (checkedAmount < classHolders.size()) {
            final ClassHolder currentHolder = findFirstNotChecked(classHolders);
            final ClassFile classFile = loadClass(currentHolder.getFile(classPath), root);

            if (classFile != null) {
                foundAmount++;
                Set<ClassReference> dependencies = classFile.getReferencedClasses();
                for (ClassReference dependency : dependencies) {
                    classHolders.add(new ClassHolder(dependency));
                }
            }

            currentHolder.setChecked(classFile);
            checkedAmount++;
        }

        if (notFound != null) {
            for (ClassHolder holder : classHolders) {
                if (!holder.loaded()) {
                    notFound.add(holder.reference);
                }
            }
        }

        Set<ClassFile> loaded = new HashSet<ClassFile>(foundAmount);
        for (ClassHolder holder : classHolders) {
            if (holder.loaded()) {
                loaded.add(holder.getClassFile());
            }
        }

        return loaded;
    }

    public static void main(String args[]) {
        System.out.println("ClassAnalyzer v0.1");

        if (args.length < ProgramArguments.LENGTH) {
            System.out.println("Syntax: java " + ProjectAnalyzer.class.getName() + " <class-path> <full-qualified-java-class>");
            System.out.println("");
        }
        else {
            final File classPath = new File(args[ProgramArguments.CLASS_PATH]);
            final ClassReference firstClass = new RootReference().addClass(args[ProgramArguments.MAIN_JAVA_CLASS]);

            boolean error = true;
            try {
                if (firstClass == null || !classPath.isDirectory()) {
                    System.err.println("Wrong java reference provided.");
                }
                else {
                    error = false;
                }
            }
            catch (SecurityException e) {
                System.err.println("Unable to access the specified class path.");
            }

            if (!error) {
                Set<ClassReference> notFoundReferences = new HashSet<ClassReference>();
                Set<ClassFile> loadedClasses = analyzeProject(firstClass, classPath, notFoundReferences);

                System.out.println("");
                System.out.println("" + loadedClasses.size() + " classes referenced and found in classPath:");
                do {
                    final List<String> references = new ArrayList<String>(loadedClasses.size());
                    for (ClassFile loaded : loadedClasses) {
                        references.add(loaded.getReference().getQualifiedName());
                    }

                    java.util.Collections.sort(references);
                    for (String reference : references) {
                        System.out.println("  " + reference);
                    }
                } while (false);

                System.out.println("");
                System.out.println("" + notFoundReferences.size() + " classes referenced but not found in classPath:");
                do {
                    final List<String> references = new ArrayList<String>(notFoundReferences.size());
                    for (ClassReference reference : notFoundReferences) {
                        references.add(reference.getQualifiedName());
                    }

                    java.util.Collections.sort(references);
                    for (String reference : references) {
                        System.out.println("  " + reference);
                    }
                } while (false);

                final RootReference classesInPath = checkClassPath(classPath);
                Set<ClassReference> inPathSet = classesInPath.setOfClasses();

                System.out.println("");
                System.out.println("Found " + inPathSet.size() + " classes in the class path");

                inPathSet.removeAll(firstClass.getRootReference().setOfClasses());
                do {
                    final int inPathCount = inPathSet.size();
                    System.out.println("Found " + inPathCount + " files in the class path but not referenced:");
                    final List<String> references = new ArrayList<String>(inPathCount);
                    for (ClassReference ref : inPathSet) {
                        references.add(ref.getQualifiedName());
                    }

                    java.util.Collections.sort(references);

                    for (String reference : references) {
                        System.out.println("  " + reference);
                    }
                } while(false);
            }
        }
    }
}
