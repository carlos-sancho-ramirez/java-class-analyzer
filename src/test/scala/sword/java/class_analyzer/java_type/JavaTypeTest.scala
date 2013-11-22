package sword.java.class_analyzer.java_type

import org.scalatest.FunSuite

class JavaTypeTest extends FunSuite {

  val primitiveSignatures = "V" :: "Z" :: "B" :: "C" :: "S" :: "I" :: "J" :: "F" :: "D" :: List()
  val arraySignatures = primitiveSignatures.map( "[" + _ )
  val someClassRef = "Ljava/util/List;" :: "Ljava/lang/Class;" :: "Lorg/scalatest/FunSuite;" :: List()
  val wrongClassRefs = null :: "" :: "L;" :: "Ljava/util/List" :: "java/util/List;" :: "Java/util/List" :: "List" :: "Ljava.util.List;" :: List()

  val validTypeLists = "[ZI" :: "D[[B" :: "ZLjava/lang/String;[I" :: "[Ljava/util/List;D" :: List()
  val validMethodSignatures = validTypeLists.map(x => "(" + x + ")I") ::: ("()V" :: List())

  val allValidValues = primitiveSignatures ::: arraySignatures ::: someClassRef ::: validTypeLists ::: validMethodSignatures
  val allTestingValues = wrongClassRefs ::: allValidValues

  test("Valid values for signatures never gives null references") {
    allValidValues foreach { x => assert( JavaType.getFromSignature(x) ne null, "null reference for " + x) }
  }

  test("Invalid signatures returns null references") {
    wrongClassRefs foreach { x => assert( JavaType.getFromSignature(x) eq null, "not null reference for " + x) }
  }

  test("Signature is the one given") {
    allValidValues foreach { x =>
      assert(JavaType.getFromSignature(x).signature() === x)
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
    allValidValues foreach {
      x => allValidValues.foreach {
        y => if (x != y) {
          val a = JavaType getFromSignature x
          val b = JavaType getFromSignature y
          assert(a ne b)
        }
      }
    }
  }

  test("only JavaTypeList return true on isTypeList method") {
    allValidValues foreach { x =>
      val isList = validTypeLists.contains(x)
      val javaTypeList = JavaType.getFromSignature(x)
      assert(javaTypeList.isTypeList() == isList, "isList returns" + !isList + " for " + x)
    }
  }

  test("only JavaMethod returns a non null instance on tryCastingToMethod") {
    allValidValues foreach { x =>
      val isMethod = validMethodSignatures.contains(x)
      val javaType = JavaType.getFromSignature(x)
      if (isMethod) {
        val method = javaType.tryCastingToMethod
        assert((method ne null) && method.isInstanceOf[JavaMethod])
      }
      else {
        val method = javaType.tryCastingToMethod
        assert(method eq null)
      }
    }
  }
}