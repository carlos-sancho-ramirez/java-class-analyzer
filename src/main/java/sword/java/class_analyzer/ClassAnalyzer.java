package sword.java.class_analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;
import sword.java.class_analyzer.ref.RootReference;

public class ClassAnalyzer {

    public static void main(String args[]) {
        System.out.println("ClassAnalyzer v0.2");

        if (args.length < 1) {
            System.out.println("Syntax: java " + ClassAnalyzer.class.getName() + " <class-file>");
            System.out.println("");
        }
        else {
            final RootReference rootReference = new RootReference();

            try {
                final String filePath = args[0];
                try {
                    InputStream inStream = new FileInputStream(filePath);
                    try {
                        ClassFile classFile = new ClassFile(inStream, rootReference);
                        System.out.println(classFile);
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
                    throw new FileError(Kind.FILE_NOT_FOUND);
                }
            }
            catch (FileError e) {
                e.printStackTrace();
            }
        }
    }
}
