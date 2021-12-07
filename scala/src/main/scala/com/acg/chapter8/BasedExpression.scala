package com.acg.chapter8

/**
 * mode match
 */
object BasedExpression {

  def main(args: Array[String]): Unit = {

    /**
     * based grammar
     */
    "a" match {
      case "ac" => println("ac")
      case "a" => println("a")
      case _ => println("none")
    }
    //mode guard
    val a = 100
    a match {
      case 1 if a <= 30 => println("less than 30")
      case 2 if a > 30 && a < 100 => println("more than 30 and less than 100")
      case _ => println("more than or equals to 100")
    }

    /**
     * match type
     */
    def constant(x: Any): String = x match {
      case 123 => "123"
      case "abc" => "abc"
      case 3.14 => "3.14"
      case _ => "default"
    }
    println(constant(3.14))
    def types(x: Any): String = x match {
      case _: Int => "Integer"
      case _: String => "String"
      case _: List[Int] => "List"
      case _ => "Others"
    }
    println(types(List('a', 'b')))
    //match list
    def lists(list: List[Int]): String = list match {
      case _ :: _ :: _ :: _ => "at least four elements"
      case _ => "less than four elements"
    }
    println(lists(List(1, 2, 3, 4)))
  }

}
