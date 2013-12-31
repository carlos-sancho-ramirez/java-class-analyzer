package sword.java.class_analyzer.java_type

import org.scalatest.FunSuite
import sword.java.class_analyzer.independent_type.IndependentTypeTest
import sword.java.class_analyzer.ref.RootReference

class JavaTypeTest extends FunSuite {

  val primitiveSignatures = IndependentTypeTest.primitiveSignatures
  val arraySignatures = IndependentTypeTest.arraySignatures
  val someClassRef = "Ljava/util/List;" :: "Ljava/lang/Class;" :: "Lorg/scalatest/FunSuite;" :: List()
  val wrongClassRefs = null :: "" :: "L;" :: "Ljava/util/List" :: "java/util/List;" :: "Java/util/List" :: "List" :: "Ljava.util.List;" :: List()

  val typeLists: List[List[String]] =
    ("[Z" :: "I" :: List()) ::
    ("D" :: "[[B" :: List()) ::
    ("Z" :: "Ljava/lang/String;" :: "[I" :: List()) ::
    List()
  //val validTypeLists = "[ZI" :: "D[[B" :: "ZLjava/lang/String;[I" :: "[Ljava/util/List;D" :: List()
  val validTypeLists = typeLists.map( x => x.foldLeft("")(_ + _))
  val validMethodSignatures = validTypeLists.map(x => "(" + x + ")I") ::: ("()V" :: List())

  val allValidValues = primitiveSignatures ::: arraySignatures ::: someClassRef ::: validTypeLists ::: validMethodSignatures
  val allTestingValues = wrongClassRefs ::: allValidValues

  val someWrongValues = "X" :: "S[" :: List()
  val allWrongValues = wrongClassRefs ::: someWrongValues

  test("Valid values for signatures never gives null references") {
    val factory = new ExtendedTypeFactory(new RootReference)
    allValidValues foreach { x => assert( factory.getFromSignature(x) ne null, "null reference for " + x) }
  }

  test("Invalid signatures returns null references") {
    val factory = new ExtendedTypeFactory(new RootReference)
    allWrongValues foreach { x => assert( factory.getFromSignature(x) eq null, "not null reference for " + x) }
  }

  test("Signature is the one given") {
    val factory = new ExtendedTypeFactory(new RootReference)
    allValidValues foreach { x =>
      assert(factory.getFromSignature(x).signature === x)
    }
  }

  test("JavaTypeList.toArray return the expected values") {
    val factory = new ExtendedTypeFactory(new RootReference)
    var lists = typeLists
    validTypeLists foreach { x =>
      val list = factory.getFromSignature(x).asInstanceOf[JavaTypeList]
      var expectedList = lists.head
      list.toArray() foreach { element =>
        assert(element.signature === expectedList.head)
        expectedList = expectedList.tail
      }

      lists = lists.tail
    }
  }

  test("JavaMethod includes the expected list and returning value") {
    val factory = new ExtendedTypeFactory(new RootReference)
    validMethodSignatures foreach { signature =>
      val splitPosition = signature.lastIndexOf(")");
      val paramsSignature = signature.substring(1, splitPosition)
      val retSignature = signature.substring(splitPosition + 1)
      val method = factory.getFromSignature(signature).asInstanceOf[JavaMethod]
      assert(method.getParameterTypeList.signature === paramsSignature)
      assert(method.getReturningType.signature === retSignature)
    }
  }
}