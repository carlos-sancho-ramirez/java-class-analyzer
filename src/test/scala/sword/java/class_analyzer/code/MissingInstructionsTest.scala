package sword.java.class_analyzer.code

import org.scalatest.FunSuite
import java.io.File
import java.util.HashSet
import sword.java.class_analyzer.ProjectAnalyzer
import sword.java.class_analyzer.ref.ClassReference
import sword.java.class_analyzer.ref.RootReference

class MissingInstructionsTest extends FunSuite {

  // It is expected to find the folder target/scala-XXX/classes with the
  // compiled classes where XXX depnds on the version of scala
  test("All instructions within the project are implemented") {
    var validClassPathCount = 0
    val targetFile = new File("target")
    targetFile.list.filter(_.startsWith("scala-")).foreach { classPathFolderName =>
      
      val compilationClassPathFile = new File(targetFile, classPathFolderName)
      if (compilationClassPathFile.list.contains("classes")) {
        val classPathFile = new File(compilationClassPathFile, "classes")
        validClassPathCount += 1

        val root = new RootReference
        val mainClass = root.addClass("sword.java.class_analyzer.ProjectAnalyzer")
        val notFound = new HashSet[ClassReference]
        val loadedClasses = ProjectAnalyzer.analyzeProject(mainClass, classPathFile, notFound)
        assert(loadedClasses.size > 0, "Not classes loaded on path " + classPathFile.getAbsolutePath)

        val iterator = loadedClasses.iterator
        while (iterator.hasNext) {
          val loaded = iterator.next
          loaded.methodTable.methods.foreach { method =>
            assert(method.isValid, method.getInvalidReason + " [ " +
                loaded.getReference.getQualifiedName + "." + method.name + " ] ")
          }
        }
      }
    }

    assert(validClassPathCount > 0, "No valid class paths found")
  }
}