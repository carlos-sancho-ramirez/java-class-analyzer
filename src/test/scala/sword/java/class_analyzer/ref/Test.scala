package sword.java.class_analyzer.ref

import scala.collection.Seq
import org.scalatest.FunSuite
import scala.collection.immutable.Map
import scala.reflect.Manifest
import scala.runtime.BoxedUnit
import java.lang.reflect.Method

class Test extends FunSuite {

  val someValidPackages = "java.util" :: "scala.collection.immutable" :: "java.io" :: "java.net" :: "java.lang" :: List()
  val someValidClasses = "java.util.List" :: "java.util.Set" :: "java.lang.Enum" :: "org.scalatest.FunSuite" :: List()

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
  }

  test("Java reference instance returns the expected qualified name") {
    someValidPackages foreach { x =>
      assert(x === root.addPackage(x).getQualifiedName)
    }
    someValidClasses foreach { x =>
      assert(x === root.addClass(x).getQualifiedName)
    }
  }
}