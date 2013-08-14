package sword.java.class_analyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sword.java.class_analyzer.FileError.Kind;

public class ClassAnalyzer {

    public static void main(String args[]) {
        System.out.println("ClassAnalyzer v0.1");

        if (args.length < 1) {
            System.out.println("Syntax: java " + ClassAnalyzer.class.getName() + " <class-file>");
            System.out.println("");
        }
        else {
            try {
                final String filePath = args[0];
                try {
                    InputStream inStream = new FileInputStream(filePath);
                    try {
                        ClassFile classFile = new ClassFile(inStream);
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
