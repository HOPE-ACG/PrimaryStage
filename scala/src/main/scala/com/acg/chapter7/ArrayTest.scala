package com.acg.chapter7

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * immutable and mutable array
 */
object ArrayTest {

  def main(args: Array[String]): Unit = {

    /**
     * create immutable array
     */
    val ids: Array[Int] = new Array[Int](10)
    //call apply() to create array, this is a shorthand
    val names: Array[String] = Array("acg", "yy", "ww")

    /**
     * traverse all elements
     */
    for (elem <- ids) printf("%d\t", elem)
    println()
    for (i <- ids.indices) printf("%d\t", ids(i))
    println()
    //(elem: Int) => printf("%d\t", elem)
    ids.foreach(printf("%d\t", _))
    println()
    for (elem <- names.iterator) printf("%s-", elem)
    println()
    val str: String = names.mkString("aa", "-", "ww")
    println(str)
    /**
     * add element
     */
    //add in the end, ids. :+ (99)
    val ids_1: Array[Int] = ids :+ 99
    ids_1.foreach(printf("%d\t", _))
    println()
    //add in the begin, names. +: ("uu")
    val names_1: Array[String] = "uu" +: names
    names_1.foreach(printf("%s\t", _))
    println()
    //call update() on background, ids_1.update(0, 66)
    ids_1(0) = 66
    ids_1.update(1, 55)
    ids_1(ids_1.length - 1) = 97
    ids_1.foreach(printf("%d\t", _))
    //call apply() on background, ids_1.apply(1)
    print(s"\n${ids_1(1)}")
    println(s"\t${ids_1.apply(0)}")
    println("=============================")

    /**
     * create mutable array
     */
    //default value is 12
    val nums: ArrayBuffer[Int] = new ArrayBuffer[Int]()
    //call apply()
    val stars: ArrayBuffer[String] = ArrayBuffer("atg", "acg", "yy")

    /**
     * add element
     */
    //add int the end
    nums += 15
    nums.foreach(printf("%d\t", _))
    println()
    //add int the begin
    "oo" +=: stars
    stars.foreach(printf("%s\t", _))
    println()
    //advise to call method
    nums.append(25)
    nums.foreach(printf("%d\t", _))
    println()
    stars.prepend("rr")
    stars.insert(3, "kk")
    stars.foreach(printf("%s\t", _))
    println()
    /**
     * remove element
     */
    val nums_remove: nums.type = nums -= 15
    println(s"reserved number: $nums_remove")
    nums.foreach(printf("%d\t", _))
    println()
    val star_remove: String = stars.remove(3)
    println(s"removed star: $star_remove")
    stars.foreach(printf("%s\t", _))
    println()
    println("=======================")

    /**
     * transfer
     */
    val mu_immu: Array[String] = stars.toArray
    val mu_immu_add: Array[String] = mu_immu :+ "ss"
    mu_immu_add.foreach(printf("%s\t", _))
    println(mu_immu.eq(mu_immu_add))
    val immu_mu: mutable.Buffer[Int] = ids.toBuffer
    immu_mu.append(123)
    immu_mu.foreach(printf("%d\t", _))
    println()

    /**
     * multidimensional array
     */
    val mutilArray: Array[Array[Array[Int]]] = Array.ofDim[Int](2, 3, 1)
    val array_1: Array[Int] = Array(100, 100, 110, 130)
    val array_2: Array[Int] = Array(90, 80)
    val array_3: Array[Int] = Array(1, 2, 3)
    //    val array_second_1: Array[Array[Int]] = Array(array_1, array_2)
    //    val array_second_2: Array[Array[Int]] = Array(array_1, array_3)
    mutilArray(0)(0) = array_1
    mutilArray(0)(1) = array_2
    mutilArray(0)(2) = array_3
    mutilArray(1)(0) = array_1
    mutilArray(1)(1) = array_2
    mutilArray(1)(2) = array_3
    mutilArray(0)(1)(1) = 120
    mutilArray.foreach(_.foreach((row: Array[Int]) => {
      row.foreach(printf("%s\t", _))
      println()
    }))
    println(mutilArray(1)(0)(2))
  }
}
