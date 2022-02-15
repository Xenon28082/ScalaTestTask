import Exceptions.{IncorrectArgsCountError, InfiniteRecursionError}

import java.io.{BufferedWriter, File, FileWriter}
import java.util
import java.util.Scanner
import scala.Array.ofDim
import scala.collection.mutable.ListBuffer

object Main {

  import scala.io.Source


  def main(args: Array[String]): Unit = {

    def getArraySize(fileName: String) = {
      val file: File = new File(fileName)
      val scanner: Scanner = new Scanner(file)
      val firstString = scanner.nextLine()
      firstString
    }

    def getMainTable(fileName: String, xSize: Int, ySize: Int) = {
      val inputArray = ofDim[String](xSize, ySize)
      var i: Int = 0

      for (line <- Source.fromFile(fileName).getLines().drop(1)) {
        val buf: ListBuffer[String] = splitString(line)
        inputArray(i) = buf.toArray
        i += 1
      }
      inputArray
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

    def printArray(array: Array[Array[String]]) = {
      println("-------------------------------------")
      for (i <- 0 to array.length - 1) {
        for (j <- 0 to array(i).length - 1) {
          print(s"${array(i)(j)}\t\t")
        }
        println("")
      }
    }

    def openReferences(array: Array[Array[String]]): Unit = {
      var needIteration = false
      for (i <- 0 to array.length - 1) {
        for (j <- 0 to array(i).length - 1) {
          val currentWord = array(i)(j)
          var currentChar = currentWord(0)
          if (currentChar == '=') {
            val sb: StringBuilder = new StringBuilder("=")
            var k: Int = 1
            while (k < currentWord.length) {
              currentChar = currentWord(k)
              if (currentChar < 91 && currentChar > 64) {
                val first = currentWord(k + 1) - '1'
                val second = currentChar - 'A'
                if (checkInfiniteRecursion(array(first)(second), first, second)) {
                  throw new InfiniteRecursionError(first, second)
                }
                if (array(first)(second)(0) == '\'') {
                  sb.clear()
                  sb.append(array(first)(second))
                } else {
                  if (array(first)(second)(0) == '=') {
                    needIteration = true
                    sb.append(array(first)(second).drop(1))
                  } else {
                    sb.append(array(first)(second))
                  }
                }
                k += 1
              }
              else {
                sb.append(currentChar)
              }
              k += 1
            }
            if (sb.toString()(1) == '\'') {
              sb.drop(1)
            }
            array(i)(j) = sb.toString()
          }
        }
      }

      if (needIteration) {
        openReferences(array)
      }
    }

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

    def calculateExpressions(array: Array[Array[String]]) = {
      var parser = new ExpressionParser()
      for (i <- 0 to array.length - 1) {
        for (j <- 0 to array(i).length - 1) {
          var sb: StringBuilder = new StringBuilder()
          if (array(i)(j)(0) == '=') {
            for (k <- 1 to array(i)(j).length - 1) {
              sb.append(array(i)(j)(k))
            }
            array(i)(j) = calc(parser.parse(sb.toString())).toString
          }
          if (array(i)(j)(0) == '\'') {
            for (k <- 1 to array(i)(j).length - 1) {
              sb.append(array(i)(j)(k))
            }
            array(i)(j) = sb.toString()
          }
        }
      }
    }

    def checkInfiniteRecursion(line: String, x: Int, y: Int): Boolean = {
      var k: Int = 1
      var res: Boolean = false
      while (k < line.length) {
        val currentChar = line(k)
        if (currentChar < 91 && currentChar > 64) {
          val first = line(k + 1) - '1'
          val second = currentChar - 'A'
          if (first == x && second == y) {
            res = true
          }
          k += 1
        }
        k += 1
      }
      res
    }

    def writeFile(filename: String, lines: Array[Array[String]]): Unit = {
      val file = new File(filename)
      val bw = new BufferedWriter(new FileWriter(file))
      for (i <- 0 to lines.length - 1) {
        for (j <- 0 to lines(i).length - 1) {
          bw.write(lines(i)(j) + "\t\t")
        }
        bw.write("\n")
      }
      bw.close()
    }

    var fileToReadName: String = new String()
    var fileToSaveName: String = new String("defaultOutput.txt")

    println(s"args count - ${args.length}")

    val parser:ExpressionParser = new ExpressionParser()
    println(parser.parse("7+5*2").toString())

    args.length match {
      case 1 => fileToReadName = args(0)
      case 2 => {
        fileToReadName = args(0)
        fileToSaveName = args(1)
      }
      case _ => throw new IncorrectArgsCountError()
    }

    val firstString = getArraySize(fileToReadName)

    val devidedString = splitString(firstString)

    val mainTable = getMainTable(fileToReadName, devidedString(1).toInt, devidedString(0).toInt)

    println("Start:")
    printArray(mainTable)

    openReferences(mainTable)

    calculateExpressions(mainTable)
    println("Finish:")
    printArray(mainTable)

    writeFile(fileToSaveName, mainTable)

  }

}
