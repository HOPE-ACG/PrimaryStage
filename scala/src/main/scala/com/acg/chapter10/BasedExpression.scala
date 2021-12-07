package com.acg.chapter10

/**
 * implicit function
 */
object BasedExpression {

  /**
   * convert function
   * @param arg being converted to MyRichInt
   * @return MyRichInt
   */
  implicit def convert(arg: Int): MyRichInt = new MyRichInt(arg)

  /**
   * implicit function
   */
  implicit val str: String = "world"

  def hi(implicit arg: String = "peace"): Unit = printf("arg's value is %s\n", arg)
  //simplified
  def hii(): Unit = printf("arg's value is %s\n", implicitly[String])

  /**
   * implicit class
   */
  implicit class MyRichRichInt(arg: Int) {

    def absolute(): Int = {
      if(arg < 0) -arg else arg
    }

    def sqrt(): Double = Math.sqrt(arg)
  }

  def main(args: Array[String]): Unit = {

    val num: Int = 66
    println(num.maxx(55))
    println(num.minn(33))

    //implicit function
    hi
    hi("peaceful")
    hii()

    //implict class
    val amount = 100
    println(amount.sqrt())
    println((amount * -1).absolute())
  }
}

/**
 * convert to this class implicitly
 * @param self self
 */
class MyRichInt(val self: Int) {

  def maxx(arg: Int): Int = {
    if(arg > self) arg else self
  }

  def minn(arg: Int): Int = {
    if(arg < self) arg else self
  }
}
