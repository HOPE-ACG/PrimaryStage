package com.acg.chapter7

import scala.collection.{immutable, mutable}

/**
 * some common functions about list
 */
object CommonFunction {

  def main(args: Array[String]): Unit = {
    //child class of Seq
    val ids: List[Int] = List(1, 5, 3)
    //child class of Set
    val names: Set[String] = Set("YY", "RRRR", "QQQ", "AA")
    //child class ofMap
    val ranking: Map[Int, String] = Map(1 -> "YY", 3 -> "RR", 2 -> "QQ")

    /**
     * derive from list
     */
    //get n elements from beginning or end
    //other: drop, dropRight
    val childList1: Set[String] = names.take(2)
    val childList2: Set[String] = names.takeRight(1)
    childList1.foreach(printf("%s\t", _))
    println()
    childList2.foreach(printf("%s\t", _))
    println()
    //intersection, other: union, diff
    val sharedPart: List[Int] = ids.intersect(List(1, 2, 5))
    sharedPart.foreach(printf("%s\t", _))
    println()
    //zip function
    val zipResult: Set[(String, Int)] = names.zip(ids)
    zipResult.foreach(printf("%s\t", _))
    println()
    //sliding window
    val sildingResult: Iterator[Map[Int, String]] = ranking.sliding(2, 1)
    sildingResult.foreach((ele: Map[Int, String]) => print(s"$ele\t"))
    println("\n==========================")

    /**
     * simple function
     */
    //product, others: summation, max, min
    println(ids.product)
    //specify ranking's attribute
    //shorthand: ranking.maxBy(_._1)
    println(ranking.maxBy((ele: (Int, String)) => ele._1))
    //sort list
    val sortedIds: immutable.Seq[Int] = ids.sorted(Ordering[Int].reverse)
    sortedIds.foreach(printf("%s\t", _))
    println()
    val sortByNames: List[String] = names.toList.sortBy(name => (name.length, name.head))
    sortByNames.foreach(printf("%s\t", _))
    println()
    val sortWithNames: List[String] = sortByNames.sortWith(_.last > _.head)
    sortWithNames.foreach(printf("%s\t", _))
    println("\n=======================")

    /**
     * advanced function
     * filter, map, flatten, group, reduce, fold
     */
    //flatten & map
    val text: List[String] = List("Hello acg", "Hi yy", "Good morning")
    val mapText: List[Array[String]] = text.map(_.split(" "))
    val flattenText: List[String] = mapText.flatten
    flattenText.foreach(printf("%s\t", _))
    println()
    val flattenMapText: List[String] = text.flatMap(_.split(" "))
    flattenMapText.foreach(printf("%s\t", _))
    println()
    //group
    val groupText: Map[Boolean, List[String]] = flattenMapText.groupBy(_.length > 3)
    groupText.foreach((ele: (Boolean, List[String])) => print(s"${ele._1}, ${ele._2}\n"))
    //reduce
    val expense: List[Int] = List(22, 33, 55, 66, 99)
    //equals to reduceLeft
    val reduceEx: Int = expense.reduce(_ - _)
    println(reduceEx)
    //necessary see original code
    println(expense.reduceRight(_ - _))
    //fold, equals to foldLeft
    println(expense.fold(11)(_ - _))
    //necessary see original code
    println(expense.foldRight(11)(_ - _))
    //combine map
    val superCars: Map[Int, String] = Map(22 -> "RR", 33 -> "AA", 88 -> "TT")
    val orCars: mutable.Map[Int, String] = mutable.Map(12 -> "FF", 33 -> "BB", 22 -> "YY")
    superCars.foldLeft(orCars)(
      (map, tuple) => {
        val res: String = map.getOrElse(tuple._1, "") + tuple._2
        map.update(tuple._1, res)
        map
      }
    )
    orCars.foreach((x: (Int, String)) => print(s"$x\t"))
  }
}
