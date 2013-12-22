package sword.java.class_analyzer.independent_type

import org.scalatest.FunSuite

object IndependentTypeTest extends FunSuite {

  val primitiveSignatures = "V" :: "Z" :: "B" :: "C" :: "S" :: "I" :: "J" :: "F" :: "D" :: List()
  val primitiveRep = "void" :: "boolean" :: "byte" :: "char" :: "short" :: "int" :: "long" :: "float" :: "double" :: List()

  val arraySignatures = primitiveSignatures.map( "[" + _ )
  val array2Signatures = arraySignatures.map( "[" + _ )

  val allValidValues = primitiveSignatures ::: arraySignatures ::: array2Signatures
  val someWrongValues = "X" :: "S[" :: "D[I" :: List()

  test("Valid values for signatures never gives null references") {
    val factory = new JavaTypeFactory
    allValidValues foreach { x => assert( factory.getFromSignature(x) ne null, "null reference for " + x) }
  }

  test("Invalid signatures returns null references") {
    val factory = new JavaTypeFactory
    someWrongValues foreach { x => assert( factory.getFromSignature(x) eq null, "not null reference for " + x) }
  }

  test("Signature is the one given") {
    val factory = new JavaTypeFactory
    allValidValues foreach { x =>
      assert(factory.getFromSignature(x).signature() === x)
    }
  }

  test("Same instance") {
    val factory = new JavaTypeFactory
    allValidValues foreach { x =>
      val a = factory getFromSignature x
      val b = factory getFromSignature x
      assert(a eq b, s"Calling getFromSignature for $x do not always returns the same instance. " + (if (a != null) a.signature else "null"))
    }
  }

  test("different instances") {
    val factory = new JavaTypeFactory
    allValidValues foreach {
      x => allValidValues.foreach {
        y => if (x != y) {
          val a = factory getFromSignature x
          val b = factory getFromSignature y
          assert(a ne b)
        }
      }
    }
  }

  test("all simple signature return a PrimitiveType instance") {
    val factory = new JavaTypeFactory
    primitiveSignatures foreach { x =>
      assert(factory.getFromSignature(x).isInstanceOf[PrimitiveType])
    }
  }

  test("all no primitive signatures return a JavaArrayType instance") {
    val factory = new JavaTypeFactory
    allValidValues foreach { x => if (!primitiveSignatures.contains(x))
      assert(factory.getFromSignature(x).isInstanceOf[JavaArrayType])
    }
  }

  test("All 1 depth array are indeed it") {
    val factory = new JavaTypeFactory
    arraySignatures foreach { x =>
      val javaType = factory.getFromSignature(x).asInstanceOf[JavaArrayType]
      assert(javaType.getElementType.isInstanceOf[PrimitiveType])
    }
  }

  test("All 2 depth array are indeed it") {
    val factory = new JavaTypeFactory
    array2Signatures foreach { x =>
      val javaType = factory.getFromSignature(x).asInstanceOf[JavaArrayType]
      val javaElement = javaType.getElementType
      assert(javaElement.isInstanceOf[JavaArrayType])
      assert(javaElement.asInstanceOf[JavaArrayType].getElementType.isInstanceOf[PrimitiveType])
    }
  }

  test("Java representation for primitive types") {
    val factory = new JavaTypeFactory

    def assertNext(signature :List[String], rep :List[String]) {
      if (!signature.isEmpty) {
        assert(factory.getFromSignature(signature.head).getJavaRepresentation() === rep.head)
        assertNext(signature.tail, rep.tail)
      }
    }

    assertNext(primitiveSignatures, primitiveRep);
  }

  test("Java representation for 1-depth array types") {
    val factory = new JavaTypeFactory

    def assertNext(signature :List[String], rep :List[String]) {
      if (!signature.isEmpty) {
        assert(factory.getFromSignature(signature.head).getJavaRepresentation() === rep.head + "[]")
        assertNext(signature.tail, rep.tail)
      }
    }

    assertNext(arraySignatures, primitiveRep);
  }

  test("getArrayType works properly") {
    val factory = new JavaTypeFactory

    allValidValues foreach { x =>
      val instance = factory getFromSignature x
      val array = instance.getArrayType
      assert(instance eq array.getElementType)
    }
  }
}