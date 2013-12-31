java-class-analyzer
===================

Author: Carlos Sancho Ramirez

## Objectives

J2SE project that loads class files and retrieves information about them. Most of the stuff in this project can be also achived by using the javap command tool. The aim of this project is to understand better what a java class file has inside through a single and clean code.

## Main features

### Version 0.2

This project has 2 main classes, both located under the package sword.java.class_analyzer: ClassAnalyzer and ProjectAnalyzer

#### ClassAnalyzer

ClassAnalyzer is expected to be executed by command-line and expects a path for a class file as the only parameter. When executed, this main class will trigger the extraction of data from the file given and all relevant data will be displayed in the output. The data displayed will include the bytecode as well. This can be used as a learning tool to understand what is inside or how the compiler compiles the program. This may be really verbose if the class is big, currently there is not option the restrict the amount of data to be shown.

As an example, if using SBT for compiling and running, the following command will allow you examine what this main class includes:

Windows like: sbt "run target\scala-2.10\classes\sword\java\class_analyzer\ClassAnalyzer.class"
Linux like: sbt "run target/scala-2.10/classes/sword/java/class_analyzer/ClassAnalyzer.class"

#### ProjectAnalyzer

ProjectAnalyzer is expected to be executed by command-line as well an it expects 2 parameters, the first one is the path to the folder including all classes (class path) and the second one is the full qualified name for the main class of your project in dot-separated format.

ProjectAnalyzer will extract all dependencies from the given file and will also load the dependencies if found until all references are solved if possible. A list with all checked classes will be made and will be contrasted with all class files found in the given class path, giving to the user in the output 3 lists including:
 * all dependencies, and also the main class provided, that have been found in the given class path.
 * all dependencies that are not resolved because they are not in the given class path. If all is fine, this list is usually filled with references to classes in the java J2SE standard packages, such as java.lang or java.util among others.
 * all classes found in the class path that are not used when executing the main class given. This means that usually you can remove these classes from the class file as they are useless. Some common useless classes are all classes only including static final field with constant values.

As an example, if using SBT for compiling and running, the following command will allow you examine which classes can be removed when executing ProjectAnalyzer. Note that one of the resulting classes is ClassAnalyzer, this is OK because ProjectAnalyzer does not require that class to work. The rest of classes reflects all inner classes having only static final values.

Windows like: sbt "run target\scala-2.10\classes sword.java.class_analyzer.ProjectAnalyzer"
Linux like: sbt "run target/scala-2.10/classes sword.java.class_analyzer.ProjectAnalyzer"
