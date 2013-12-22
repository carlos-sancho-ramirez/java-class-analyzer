package sword.java.class_analyzer.ref

import scala.collection.Seq
import org.scalatest.FunSuite
import scala.collection.immutable.Map
import scala.reflect.Manifest
import scala.runtime.BoxedUnit
import java.lang.reflect.Method
import sword.java.class_analyzer.independent_type.JavaType
import sword.java.class_analyzer.java_type.JavaMethod
import sword.java.class_analyzer.java_type.ExtendedTypeFactory

class RefTest extends FunSuite {

  val someValidPackages = "java.util" :: "scala.collection.immutable" :: "java.io" :: "java.net" :: "java.lang" :: List()
  val someValidClasses = "java.util.List" :: "java.util.Set" :: "java.lang.Enum" :: "org.scalatest.FunSuite" :: List()

  val someValidAbsoluteFields = "System.out" :: "my_package.MyClass.myField" :: List()
  val someValidRelativeFields = someValidAbsoluteFields.map( x => x.substring(x.lastIndexOf(".") + 1))

  val someValidAbsoluteMethods = "my_package.MyClass.myMethod" :: "Math.pow" :: "System.out.println" :: List()
  val someValidRelativeMethods = someValidAbsoluteMethods.map( x => x.substring(x.lastIndexOf(".") + 1))

  test("Root Reference returns true on isRootReference") {
    val root = new RootReference
    assert(root.isRootReference)
  }

  test("Root reference returns itself as parent") {
    val root = new RootReference
    assert(root.getJavaParentReference() eq root)
  }

  test("None but the Root Reference returns true on isRootReference") {
    val root = new RootReference
    someValidPackages foreach { x =>
      assert(!root.addPackage(x).isRootReference)
    }
    someValidClasses foreach { x =>
      assert(!root.addClass(x).isRootReference)
    }

    val factory = new ExtendedTypeFactory(root)
    val fieldType = factory.getFromSignature("I")
    val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

    someValidAbsoluteFields foreach { x =>
      assert(!root.addField(x, fieldType).isRootReference)
    }
    someValidAbsoluteMethods foreach { x =>
      assert(!root.addMethod(x, methodType).isRootReference)
    }
  }

  test("All references have the root reference as final parent") {
    val root = new RootReference

    def looksForRoot(root: JavaReference, reference: JavaReference, tries: Int): Boolean =
      tries > 0 && ((reference eq root) || looksForRoot(root, reference.getJavaParentReference, tries - 1))

    someValidPackages foreach { x =>
      assert(looksForRoot(root, root.addPackage(x), 5))
    }
    someValidClasses foreach { x =>
      assert(looksForRoot(root, root.addClass(x), 5))
    }

    val factory = new ExtendedTypeFactory(root)
    val fieldType = factory.getFromSignature("I")
    val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

    someValidAbsoluteFields foreach { x =>
      assert(looksForRoot(root, root.addField(x, fieldType), 5))
    }
    someValidAbsoluteMethods foreach { x =>
      assert(looksForRoot(root, root.addMethod(x, methodType), 5))
    }
  }

  test("Same reference retrieves the same instance") {
    val root = new RootReference

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

    val factory = new ExtendedTypeFactory(root)
    val fieldType = factory.getFromSignature("I")
    val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

    someValidAbsoluteFields foreach { x =>
      val a = root.addField(x, fieldType)
      val b = root.addField(x, fieldType)
      assert(a eq b)
    }

    someValidClasses foreach { x =>
      val a = root.addMethod(x, methodType)
      val b = root.addMethod(x, methodType)
      assert(a eq b)
    }
  }

  test("Method reference generates a package, a class and a nested method") {
    val methodRef = "my_package.MyClass.myMethod"
    val root = new RootReference
    val factory = new ExtendedTypeFactory(root)
    val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

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
    val root = new RootReference
    someValidPackages foreach { x =>
      assert(x === root.addPackage(x).getQualifiedName)
    }
    someValidClasses foreach { x =>
      assert(x === root.addClass(x).getQualifiedName)
    }

    val factory = new ExtendedTypeFactory(root)
    val fieldType = factory.getFromSignature("I")
    val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

    someValidAbsoluteFields foreach { x =>
      assert(x === root.addField(x, fieldType).getQualifiedName)
    }
    someValidAbsoluteMethods foreach { x =>
      assert(x === root.addMethod(x, methodType).getQualifiedName)
    }
  }
}