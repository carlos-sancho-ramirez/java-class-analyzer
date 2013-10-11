package sword.java.class_analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProjectAnalyzer {

    private static final class ClassHolder {

        public final String javaReference;
        private boolean mChecked; // set to true if the classFile has been tried to be retrieved
        private ClassFile mClassFile;

        public ClassHolder(String javaReference) {
            this.javaReference = javaReference;

            if (javaReference == null) {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public int hashCode() {
            return javaReference.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            return javaReference.equals(object);
        }

        public String getPath(String classPath) {
            return classPath + '/' + javaReference.replace('.', '/') + ".class";
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

        if (args.length < 2) {
            System.out.println("Syntax: java " + ProjectAnalyzer.class.getName() + " <class-path> <full-qualified-java-class>");
            System.out.println("");
        }
        else {
            final String classPath = args[0];
            Set<ClassHolder> classHolders = new HashSet<ClassHolder>();
            classHolders.add(new ClassHolder(args[1]));

            int checkedAmount = 0;
            int foundAmount = 0;
            int successfullyRead = 0;

            while (checkedAmount < classHolders.size()) {
                final ClassHolder currentHolder = findFirstNotChecked(classHolders);
                final String filePath = currentHolder.getPath(classPath);

                InputStreamWrapper inStream = null;
                ClassFile classFile = null;

                try {
                    try {
                        inStream = new InputStreamWrapper(new FileInputStream(filePath));
                        foundAmount++;
                        System.out.println("Class " + currentHolder.javaReference + " found at file " + filePath + "...");

                        try {
                            classFile = new ClassFile(inStream);
                            successfullyRead++;
                            System.out.println("  Successfully loaded!");
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
                        System.out.println("Class " + currentHolder.javaReference + " not found in the classPath");
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
