package com.acg.chapter3

import scala.collection.immutable
import scala.io.StdIn
import scala.util.control.Breaks

object BranchGrammar {
  def main(args: Array[String]): Unit = {
    /**
     * {if} statement
     */
    val age: Int = StdIn.readInt()
    val name: String = if (age >= 18) "ACG" else "YY"
    println(name)

    /**
     * {for} circle
     * call method "1.to(10)"
     */
    for (i <- 1 to 10) {
      print(i + "\t")
    }
    println()
    for (i <- 1 until 10) {
      print(i + "\t")
    }
    println()
    //traverse array
    //after {by} adding step, else having {Reverse}
    for (i <- Array(3, 1, 4, 1, 5, 9, 2, 6) if i != 1) {
      print(i + "\t")
    }
    println()
    for (i <- 1 to 9; j <- 1 to i) {
      printf("%d * %d = %d \t", i, j, i * j)
      if (i == j) {
        println()
      }
    }
    //circle have return value
    val nums: immutable.IndexedSeq[Int] = for (i <- 1 to 10) yield i * 2
    println(nums)

    /**
     * interrupt circle
     * don't define {break} grammar
     */
    val dividend = 0
    try {
      println(10 / dividend)
    } catch {
      case _: Exception => println("dividend can't be zero")
    }

    Breaks.breakable(
      if (dividend == 0) {
        println("dividend can't be zero")
        Breaks.break()
      } else
        println(10 / dividend)
    )
  }
}
