package com.acg.chapter7

import scala.collection.mutable.ListBuffer

/**
 * immutable and mutable list
 */
object ListTest {

  def main(args: Array[String]): Unit = {

    /**
     * create immutable list
     */
    val nums: List[Int] = List(1, 2, 3)
    println(nums(2))
    //Nil is a {case object}, and extends List[Nothing]
    //add elements in the front
    val names: List[String] = Nil.::("acg").::("yy").::("oo")
    names.foreach(printf("%s\t", _))
    println()
    //shorthand, also int the front
    val names_update: List[String] = "RR" :: "TT" :: names
    names_update.foreach(printf("%s\t", _))
    println()
    /**
     * add elements
     */
    val nums_1: List[Int] = nums :+ 123
    nums_1.foreach(printf("%d\t", _))
    println()
    val nums_2: List[Int] = 321 +: nums
    nums_2.foreach(printf("%d\t", _))
    println()
    /**
     * combine lists
     */
    val nums_combine: List[Int] = nums_1 ::: nums_2
    nums_combine.foreach(printf("%d\t", _))
    println()
    val nums_names_combine: List[Any] = names ++ nums
    nums_names_combine.foreach((ele: Any) => print(ele + "\t"))
    println("\n================================")

    /**
     * create mutable list
     */
    val ids: ListBuffer[Int] = new ListBuffer[Int]()
    val cars: ListBuffer[String] = ListBuffer("R8", "Panamera", "Ferrari")
    cars.foreach(printf("%s\t", _))
    println()

    /**
     * add elements
     */
    ids.append(1, 2, 3)
    ids.prepend(-2, -1, 0)
    ids.insert(3, 12)
    ids.foreach(printf("%s\t", _))
    println()
    /**
     * combine lists
     * {++=} combine to right list, {++=:} combine to left list
     */
    val ids_cars_combine: ListBuffer[Any] = ids ++ cars
    ids_cars_combine.foreach((ele: Any) => print(ele + "\t"))
    println()

    /**
     * remove and update
     */
    ids.update(0, -10)
    ids(1) = -5
    //remove according to position and number
    ids.remove(3, 2)
    ids -= 2
    ids.foreach(printf("%s\t", _))
  }
}
