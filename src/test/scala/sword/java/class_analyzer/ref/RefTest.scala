package sword.java.class_analyzer.ref

import scala.collection.Seq
import org.scalatest.FunSuite
import scala.collection.immutable.Map
import scala.reflect.Manifest
import scala.runtime.BoxedUnit
import java.lang.reflect.Method
import sword.java.class_analyzer.java_type.JavaType
import sword.java.class_analyzer.java_type.JavaMethod

class RefTest extends FunSuite {

  val someValidPackages = "java.util" :: "scala.collection.immutable" :: "java.io" :: "java.net" :: "java.lang" :: List()
  val someValidClasses = "java.util.List" :: "java.util.Set" :: "java.lang.Enum" :: "org.scalatest.FunSuite" :: List()

  val someValidFields = "System.out" :: "my_package.MyClass.myField" :: List()
  val someValidMethods = "my_package.MyClass.myMethod" :: "Math.pow" :: "System.out.println" :: List()

  val allValidReferences = someValidPackages ::: someValidClasses

  val root = RootReference.getInstance

  test("Root Reference returns true on isRootReference") {
    assert(root.isRootReference)
  }

  test("Root reference returns itself as parent") {
    assert(root.getJavaParentReference() eq root)
  }

  test("None but the Root Reference returns true on isRootReference") {
    someValidPackages foreach { x =>
      assert(!root.addPackage(x).isRootReference)
    }
    someValidClasses foreach { x =>
      assert(!root.addClass(x).isRootReference)
    }
  }

  test("All references have the root reference as final parent") {
    def looksForRoot(root: RootReference, reference: JavaReference, tries: Int): Boolean =
      tries > 0 && ((reference eq root) || looksForRoot(root, reference.getJavaParentReference, tries - 1))

    someValidPackages foreach { x =>
      assert(looksForRoot(root, root.addPackage(x), 5))
    }
    someValidClasses foreach { x =>
      assert(looksForRoot(root, root.addClass(x), 5))
    }
  }

  test("Same reference retrieves the same instance") {
    someValidPackages foreach { x =>
      val a = root.addPackage(x)
      val b = root.addPackage(x)
      assert(a eq b)
    }
    someValidClasses foreach { x =>
      val a = root.addClass(x)
      val b = root.addClass(x)
      assert(a eq b)
    }
    someValidFields foreach { x =>
      val javaType = JavaType.getFromSignature("I")
      val a = root.addField(x, javaType)
      val b = root.addField(x, javaType)
      assert(a eq b)
    }
    someValidClasses foreach { x =>
      val retType = JavaType.getFromSignature("()V").asInstanceOf[JavaMethod]
      val a = root.addMethod(x, retType)
      val b = root.addMethod(x, retType)
      assert(a eq b)
    }
  }

  test("Method reference generates a package, a class and a nested method") {
    val methodRef = "my_package.MyClass.myMethod"
    val methodType = JavaType.getFromSignature("()V").asInstanceOf[JavaMethod]
    val methodReference = root.addMethod(methodRef, methodType)
    assert(methodReference.isInstanceOf[MethodReference])
    assert(methodReference.getSimpleName === "myMethod")

    val classReference = methodReference.getJavaParentReference
    assert(classReference.isInstanceOf[ClassReference])
    assert(classReference.getSimpleName === "MyClass")

    val packageReference = classReference.getJavaParentReference
    assert(packageReference.isInstanceOf[PackageReference])
    assert(packageReference.getSimpleName === "my_package")

    assert(packageReference.getJavaParentReference.isRootReference)
  }

  test("Java reference instance returns the expected qualified name") {
    someValidPackages foreach { x =>
      assert(x === root.addPackage(x).getQualifiedName)
    }
    someValidClasses foreach { x =>
      assert(x === root.addClass(x).getQualifiedName)
    }
    someValidFields foreach { x =>
      val javaType = JavaType.getFromSignature("I")
      assert(x === root.addField(x, javaType).getQualifiedName)
    }
    someValidMethods foreach { x =>
      val methodType = JavaType.getFromSignature("()V").asInstanceOf[JavaMethod]
      assert(x === root.addMethod(x, methodType).getQualifiedName)
    }
  }
}