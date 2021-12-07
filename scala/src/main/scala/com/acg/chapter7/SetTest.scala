package com.acg.chapter7

import scala.collection.mutable

/**
 * immutable and mutable set
 */
object SetTest {

  def main(args: Array[String]): Unit = {

    /**
     * create immutable set
     */
    val people: Set[Int] = Set(1, 1, 2, 3)
    for (elem <- people) {
      printf("%d\t", elem)
    } //1 2 3
    println()
    //add elements
    val people_1: Set[Int] = people + 5 + 6 + 3
    people_1.foreach(printf("%d\t", _))
    println()
    //combine set
    val people_combine: Set[Int] = people ++ people_1
    people_combine.foreach(printf("%d\t", _))
    println()
    //remove elements
    val people_remove: Set[Int] = people_1 - 6
    people_remove.foreach(printf("%d\t", _))
    println()

    /**
     * create mutable set
     */
    val cash: mutable.Set[Int] = mutable.Set(100, 90, 80)
    //add elements
    cash.add(4)
    cash += 10 += 11 += 12
    cash.foreach(printf("%d\t", _))
    println()
    //remove elements
    cash.remove(100)
    cash -= 90
    cash.foreach(printf("%d\t", _))
    println()
    //combine set
    val dollar: mutable.Set[Int] = mutable.Set(-3, -2, -6)
    val cash_dollar_combine: mutable.Set[Int] = cash ++ dollar
    cash_dollar_combine.foreach(printf("%d\t", _))
    println()
    dollar ++= cash
    dollar.foreach(printf("%d\t", _))
    println()
  }
}
