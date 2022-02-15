import ExpressionParser.{delimiters, flag, isDelimiter, isFunction, isOperator, priority, result}

import java.util
import java.util.{ArrayDeque, ArrayList, Deque, List, StringTokenizer}
import scala.collection.mutable.ListBuffer

object ExpressionParser {
  private val operators: String = "+-*/"
  private val delimiters: String = "() " + operators
  var result: ListBuffer[String] = null
  var flag: Boolean = true

  private def isDelimiter(token: String): Boolean = {
    if (token.length != 1) return false
    for (i <- 0 until delimiters.length) {
      if (token.charAt(0) == delimiters.charAt(i)) return true
    }
    false
  }

  private def isOperator(token: String): Boolean = {
    if (token == "u-") return true
    for (i <- 0 until operators.length) {
      if (token.charAt(0) == operators.charAt(i)) return true
    }
    false
  }

  private def isFunction(token: String): Boolean = {
    if (token == "sqrt" || token == "cube" || token == "pow10") return true
    false
  }

  private def priority(token: String): Int = {
    if (token == "(") return 1
    if (token == "+" || token == "-") return 2
    if (token == "*" || token == "/") return 3
    4
  }




}

class ExpressionParser() {

  def parse(infix: String): ListBuffer[String] = {
    val postfix: ListBuffer[String] = new ListBuffer[String]
    val stack: util.Deque[String] = new util.ArrayDeque[String]
    val tokenizer: StringTokenizer = new StringTokenizer(infix, delimiters, true)
    var prev: String = ""
    var curr: String = ""
    while ( {
      tokenizer.hasMoreTokens
    }) {
      curr = tokenizer.nextToken
      if (!tokenizer.hasMoreTokens && isOperator(curr)) {
        System.out.println("Некорректное выражение.")
        flag = false
        return postfix
      }
      if (curr != " ")
        if (isFunction(curr)) stack.push(curr)
        else if (isDelimiter(curr)) if (curr == "(") stack.push(curr)
        else if (curr == ")") {
          while ( {
            !(stack.peek == "(")
          }) {
            postfix += (stack.pop)
            if (stack.isEmpty) {
              System.out.println("Скобки не согласованы.")
              flag = false
              return postfix
            }
          }
          stack.pop
          if (!stack.isEmpty && isFunction(stack.peek)) postfix += (stack.pop)
        }
        else {
          if (curr == "-" && (prev == "" || (isDelimiter(prev) && !(prev == ")")))) { // унарный минус
            curr = "u-"
          }
          else while ( {
            !stack.isEmpty && (priority(curr) <= priority(stack.peek))
          }) postfix += (stack.pop)
          stack.push(curr)
        }
        else postfix += (curr)
      prev = curr
    }
    while ( {
      !stack.isEmpty
    }) if (isOperator(stack.peek)) postfix += (stack.pop)
    else {
      System.out.println("Скобки не согласованы.")
      flag = false
      return postfix
    }
    postfix
  }

//  def getResult(): ListBuffer[String] = {
//    result
//  }

//  result = parse(infix)
}