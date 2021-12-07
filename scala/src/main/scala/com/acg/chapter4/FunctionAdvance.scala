package com.acg.chapter4

/**
 * High-order function knowledge
 *
 * @author ACHENGE
 */
object FunctionAdvance {
  def main(args: Array[String]): Unit = {
    val tool = new FunctionAdvance
    //function as value
    val f1: (Int, Int) => Int = tool.add
    println(f1(2, 3))
    val f2: (Int, Int) => Int = tool.add
    println(f2(2, 3))
    //function as return value
    val sub: (Int, Int) => Unit = tool.operation()
    sub(5, 6)
    tool.operation()(8, 8)

    //example1
    def transform(num: Char): Int = {
      num.toInt
    }

    val nums: Array[Int] = tool.assign(Array('a', 'a', 'c', 'A', 'B', 'C'), transform)
    println(nums.mkString("", ",", ""))
    //example2
    val res1: Boolean = tool.judge()("abc")('a')
    val res2: Boolean = tool.judge()("cba")('b')
    val retVal: Boolean = tool.judge2(12)("abc")('b')
    println(res1 + "\t" + res2 + "\t" + retVal)
  }
}

class FunctionAdvance {
  //function as value
  def add(x: Int, y: Int): Int = x + y

  //function as return value
  def operation(): (Int, Int) => Unit = {
    def subtraction(x: Int, y: Int): Unit = {
      println(x - y)
    }

    subtraction
  }

  //example1
  def assign(nums: Array[Char], transform: Char => Int): Array[Int] = {
    val chars: Array[Int] = for (num <- nums) yield transform(num)
    chars
  }

  //example2
  def judge(): String => Char => Boolean = {
    s => c => if (s == "abc" && c == 'a') true else false
    /*def f1(c: Char): Boolean = if(c == 'a') true else false
    def f3(c: Char): Boolean = if(c == 'b') true else false
    def f2(s: String): Char => Boolean = {
      if(s == "abc") {
        f1
      }else {
        f3
      }
    }
    f2*/
  }

  //curry
  def judge2(i: Int)(s: String)(c: Char): Boolean = {
    if (i > 10 && s == "abc" && c != 'a') true else false
  }
}
