import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class MainFunctionsTests extends FunSuite{
  test("MainFunctions.calc"){
    val functions = new MainFunctions()
    val listBuffer:ListBuffer[String] = new ListBuffer[String]()
    listBuffer += "7"
    listBuffer += "5"
    listBuffer += "2"
    listBuffer += "*"
    listBuffer += "+"

    assert(functions.calc(listBuffer) == 17)
  }

  test("MainFunctions.splitString"){
    val functions = new MainFunctions()
    val listBuffer:ListBuffer[String] = new ListBuffer[String]()
    listBuffer += "1"
    listBuffer += "2"
    listBuffer += "3"
    listBuffer += "4"
    listBuffer += "5"
    assert(functions.splitString("1  2         3    4        5").equals(listBuffer))
  }
}
