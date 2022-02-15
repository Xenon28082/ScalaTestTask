import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class ParserTest extends FunSuite{
    test("ExpressionParser.parse"){
      val parser:ExpressionParser = new ExpressionParser()
      val listBuffer:ListBuffer[String] = new ListBuffer[String]()
      listBuffer += "7"
      listBuffer += "5"
      listBuffer += "2"
      listBuffer += "*"
      listBuffer += "+"
      assert(parser.parse("7+5*2").toString().equals(listBuffer.toString()))
    }

}
