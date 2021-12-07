package com.acg.chapter9

/**
 * Exception grammar
 */
object ExceptionProcession {

  def main(args: Array[String]): Unit = {

    try {
      println(3 / 0)
    }catch {
      case _: ArithmeticException => println("zero can't be divided")
      case _: Exception => println("other exception")
    }finally {
      println("ending")
    }

    println(calculate(1))
  }

  @throws(classOf[ArithmeticException])
  def calculate(arg: Int): Int = {
    arg / 0
  }
}
