package sword.java.class_analyzer.ref

import scala.collection.Seq
import org.scalatest.FunSuite
import scala.collection.immutable.Map
import scala.reflect.Manifest
import scala.runtime.BoxedUnit
import java.lang.reflect.Method
import sword.java.class_analyzer.independent_type.JavaType
import sword.java.class_analyzer.java_type.JavaMethod
import sword.java.class_analyzer.independent_type.JavaTypeFactory
import sword.java.class_analyzer.java_type.ExtendedTypeFactory

class RefTest extends FunSuite {

  val testFieldType = JavaTypeFactory.getIndependentTypeFromSignature("I")

  val someValidPackages = "java.util" :: "scala.collection.immutable" :: "java.io" :: "java.net" :: "java.lang" :: List()
  val someValidClasses = "java.util.List" :: "java.util.Set" :: "java.lang.Enum" :: "org.scalatest.FunSuite" :: List()

  val someValidAbsoluteFields = "System.out" :: "System.in" :: "my_package.MyClass.myField" :: List()
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

  def looksForRoot(root: JavaReference, reference: JavaReference, tries: Int): Boolean =
      tries > 0 && ((reference eq root) || looksForRoot(root, reference.getJavaParentReference, tries - 1))

  test("All references have the root reference as final parent") {
    val root = new RootReference

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

  /** Tests for multiple root references **/

  test("Same package reference from different roots returns true on calling equals") {
    for (x <- someValidPackages) {
      val package1 = (new RootReference).addPackage(x)
      val package2 = (new RootReference).addPackage(x)
      assert(package1 ne package2)
      assert(package1.equals(package2))
    }
  }

  test("Same class reference from different roots returns true on calling equals") {
    for (x <- someValidClasses) {
      val class1 = (new RootReference).addClass(x)
      val class2 = (new RootReference).addClass(x)
      assert(class1 ne class2)
      assert(class1.equals(class2))
    }
  }

  test("Same field reference from different roots returns true on calling equals") {
    for (x <- someValidAbsoluteFields) {
      val field1 = (new RootReference).addField(x, testFieldType)
      val field2 = (new RootReference).addField(x, testFieldType)
      assert(field1 ne field2)
      assert(field1.equals(field2))
    }
  }

  test("Same method reference from different roots returns true on calling equals") {
    for (x <- someValidAbsoluteFields) {
      def obtainMethod = {
        val root = new RootReference
        val methodType = (new ExtendedTypeFactory(root)).getFromSignature("()V").asInstanceOf[JavaMethod]
        root.addMethod(x, methodType)
      }

      val method1 = obtainMethod
      val method2 = obtainMethod
      assert(method1 ne method2)
      assert(method1.equals(method2))
    }
  }

  test("Adding a field with class reference to another root") {
    for (x <- someValidClasses; y <- someValidAbsoluteFields) {
      val classSignature = "L" + x.replace(".","/") + ";"
      val root1 = new RootReference
      val fieldType = (new ExtendedTypeFactory(root1)).getFromSignature(classSignature)

      val root2 = new RootReference
      val field = root2.addField(y, fieldType);
      assert(looksForRoot(root2, field, 5))
    }
  }

  test("Adding a method with class reference to another root") {
    for (x <- someValidClasses; y <- someValidAbsoluteMethods) {
      val methodSignature = "(L" + x.replace(".","/") + ";)V"
      val root1 = new RootReference
      val methodType = (new ExtendedTypeFactory(root1)).getFromSignature(methodSignature).asInstanceOf[JavaMethod]

      val root2 = new RootReference
      val field = root2.addMethod(y, methodType);
      assert(looksForRoot(root2, field, 5))
    }
  }

  /** Tests for setOfClasses **/

  test("Set of classes is empty for new root instances") {
    val root = new RootReference
    assert(root.setOfClasses.isEmpty)
  }

  test("Set of classes is still empty when a new package is added") {
    someValidPackages foreach { x =>
      val root = new RootReference
      root.addPackage(x)
      assert(root.setOfClasses.isEmpty)
    }
  }

  test("Set of classes is not empty when a new class is added") {
    someValidClasses foreach { x =>
      val root = new RootReference
      root.addClass(x)
      assert(root.setOfClasses.size == 1)
    }
  }

  test("Set of classes only includes a class when added a field") {
    someValidAbsoluteFields foreach { x =>
      val root = new RootReference
      root.addField(x, testFieldType)
      assert(root.setOfClasses.size == 1)

      val addedClass = root.setOfClasses.iterator.next
      val expectedClass = x.substring(0, x.lastIndexOf('.'))
      assert(addedClass.getQualifiedName.equals(expectedClass))
    }
  }

  test("Set of classes only includes a class when added a method") {
    someValidAbsoluteFields foreach { x =>
      val root = new RootReference
      val factory = new ExtendedTypeFactory(root)
      val methodType = factory.getFromSignature("()V").asInstanceOf[JavaMethod]

      root.addMethod(x, methodType)
      assert(root.setOfClasses.size == 1)

      val addedClass = root.setOfClasses.iterator.next
      val expectedClass = x.substring(0, x.lastIndexOf('.'))
      assert(addedClass.getQualifiedName.equals(expectedClass))
    }
  }

  test("Set of classes only includes common classes for different fields and methods") {
    someValidAbsoluteFields foreach { x =>
      val root = new RootReference
      root.addField(x, testFieldType)
      assert(root.setOfClasses.size == 1)

      val addedClass = root.setOfClasses.iterator.next
      val expectedClass = x.substring(0, x.lastIndexOf('.'))
      assert(addedClass.getQualifiedName.equals(expectedClass))
    }
  }
}