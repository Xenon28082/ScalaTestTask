import java.util
import scala.collection.mutable.ListBuffer

class MainFunctions {
  def calc(postfix: ListBuffer[String]): Double = {
    val stack: util.Deque[Double] = new util.ArrayDeque[Double]
    for (x <- postfix) {
      if (x == "+") stack.push(stack.pop + stack.pop)
      else if (x == "-") {
        val b: Double = stack.pop
        val a: Double = stack.pop
        stack.push(a - b)
      }
      else if (x == "*") stack.push(stack.pop * stack.pop)
      else if (x == "/") {
        val b: Double = stack.pop
        val a: Double = stack.pop
        stack.push((a / b).toInt)
      }
      else if (x == "u-") stack.push(-stack.pop)
      else stack.push(x.toInt)
    }
    stack.pop
  }

  def splitString(line: String): ListBuffer[String] = {
    var i: Int = 0
    val builder: StringBuilder = new StringBuilder()
    val devidedString = ListBuffer[String]()
    while (i < line.length) {
      while (i != line.length && line(i) != ' ') {
        builder.append(line(i))
        i += 1
      }
      if (builder.length != 0) {
        devidedString += builder.toString()
        builder.clear()
      }
      i += 1
    }
    devidedString
  }
}
