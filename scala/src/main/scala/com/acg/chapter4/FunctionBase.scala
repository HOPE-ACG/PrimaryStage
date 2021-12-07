package com.acg.chapter4

/**
 * Introduce some base definitions about function
 *
 * @author ACHENGE
 */
object FunctionBase {

  def main(args: Array[String]): Unit = {
    val tool = new FunctionBase()
    println(tool.sum(23, 32))
    //variable parameters
    tool.info(100, "ACG", "28", "TJ")
    //simplify function
    println(tool.sum2(23, 32))
    //lambda expression
    //val f: String => Int = (arg: String) => {123}
    //val f = (_: Int, _: String) => {123}
    //println(tool.lambda(f))
    println(tool.lambda("def", (_: Int, _: String) => {
      123
    }))
    //simplify lambda expression
    println(tool.lambda("abc", (_, _) => 456))
    //operation
    println(tool.add(58, (x, y) => x + y))
    println(tool.add(23, _ + _))
  }
}

class FunctionBase {

  //fundamental grammar
  def sum(x: Int, y: Int): Int = {
    x + y
  }

  //variable parameters
  def info(id: Int, others: String*): Unit = {
    printf("id: %d, other info: %s", id, others)
    println()
  }

  /**
   * simplify function
   * the last line of body as return value automatically
   * save "{}" when only having one line of body
   * save type of return value
   *
   * @param x inputted parameter
   * @param y inputted parameter
   * @return sum of inputted parameters
   */
  def sum2(x: Int, y: Int): Int = x + y

  //lambda expression
  def lambda(arg1: String, fun2: (Int, String) => Int): Int = {
    val code: Int = fun2(1, arg1)
    code
  }

  //addition operation
  def add(arg: Int, fun: (Int, Int) => Int): Int = {
    fun(arg, arg * 2)
  }
}
