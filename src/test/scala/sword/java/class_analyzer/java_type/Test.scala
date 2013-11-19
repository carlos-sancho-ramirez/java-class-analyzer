package sword.java.class_analyzer.java_type

import org.scalatest.FunSuite

class Test extends FunSuite {

  val primitiveSignatures = "V" :: "Z" :: "B" :: "C" :: "S" :: "I" :: "J" :: "F" :: "D" :: List()
  val arraySignatures = primitiveSignatures.map( "[" + _ )
  val someClassRef = "Ljava/util/List;" :: "Ljava/lang/Class;" :: "Lorg/scalatest/FunSuite;" :: List()
  val wrongClassRefs = null :: "" :: "L;" :: "Ljava/util/List" :: "java/util/List;" :: "Java/util/List" :: "List" :: "Ljava.util.List;" :: List()

  val allValidValues = primitiveSignatures ::: arraySignatures ::: someClassRef
  val allTestingValues = wrongClassRefs ::: allValidValues

  test("Valid values for signatures never gives null references") {
    allValidValues foreach { x => assert( JavaType.getFromSignature(x) ne null) }
  }

  test("Invalid signatures returns null references") {
    wrongClassRefs foreach { x => assert( JavaType.getFromSignature(x) eq null) }
  }

  test("Signature is the one given") {
    allValidValues foreach { x =>
      assert(JavaType.getFromSignature(x).signature().equals(x))
    }
  }

  test("Same instance") {
    allTestingValues.foreach { x =>
      val a = JavaType getFromSignature x
      val b = JavaType getFromSignature x
      assert(a eq b, s"Calling getFromSignature for $x do not always returns the same instance. " + (if (a != null) a.signature else "null"))
    }
  }

  test("different instances") {
    allValidValues.foreach {
      x => allValidValues.foreach {
        y => if (x != y) {
          val a = JavaType getFromSignature x
          val b = JavaType getFromSignature y
          assert(a ne b)
        }
      }
    }
  }
}