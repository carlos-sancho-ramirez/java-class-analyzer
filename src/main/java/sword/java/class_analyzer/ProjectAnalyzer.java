package sword.java.class_analyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import sword.java.class_analyzer.ref.ClassReference;
import sword.java.class_analyzer.ref.RootReference;

public class ProjectAnalyzer {

    private static final class ProgramArguments {
        /**
         * System dependet path to the folder where class fiels are located.
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
            return reference.equals(object);
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
    }

    private static final ClassHolder findFirstNotChecked(Set<ClassHolder> holders) {
        for (ClassHolder holder : holders) {
            if (!holder.isChecked()) {
                return holder;
            }
        }

        return null;
    }

    public static void main(String args[]) {
        System.out.println("ClassAnalyzer v0.1");

        if (args.length < ProgramArguments.LENGTH) {
            System.out.println("Syntax: java " + ProjectAnalyzer.class.getName() + " <class-path> <full-qualified-java-class>");
            System.out.println("");
        }
        else {
            final File classPath = new File(args[ProgramArguments.CLASS_PATH]);
            final ClassReference firstClass = RootReference.getInstance().addClass(args[ProgramArguments.MAIN_JAVA_CLASS]);

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
                Set<ClassHolder> classHolders = new HashSet<ClassHolder>();
                classHolders.add(new ClassHolder(firstClass));

                int checkedAmount = 0;
                int foundAmount = 0;
                int successfullyRead = 0;

                while (checkedAmount < classHolders.size()) {
                    final ClassHolder currentHolder = findFirstNotChecked(classHolders);
                    final File file = currentHolder.getFile(classPath);

                    InputStreamWrapper inStream = null;
                    ClassFile classFile = null;

                    try {
                        try {
                            inStream = new InputStreamWrapper(new FileInputStream(file));
                            foundAmount++;
                            System.out.println("Class " + currentHolder.reference.getQualifiedName() + " found at file " + file.getPath() + "...");
    
                            try {
                                classFile = new ClassFile(inStream);
                                successfullyRead++;
                                System.out.println("  Successfully loaded!");

                                Set<ClassReference> dependencies = classFile.getReferencedClasses();
                                for (ClassReference dependency : dependencies) {
                                    System.out.println("Dependency: " + dependency.getQualifiedName());
                                }
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
                            System.out.println("Class " + currentHolder.reference.getQualifiedName() + " not found in the classPath");
                        }
                    }
                    catch (FileError e) {
                        final int filePosition = inStream.readBytes();
                        System.out.println("  Error found when loading the file at position " + filePosition + " (0x" + Integer.toHexString(filePosition) + ')');
                        e.printStackTrace();
                    }

                    currentHolder.setChecked(classFile);
                    checkedAmount++;
                }

                System.out.println("Referenced " + checkedAmount + " classes. " +
                        foundAmount + " classes found in the classpath " + classPath +
                        " and " + successfullyRead + " read with no major errors.");
            }
        }
    }
}
