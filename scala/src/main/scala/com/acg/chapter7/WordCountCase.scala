package com.acg.chapter7

/**
 * simple & complicated word count case
 */
object WordCountCase {

  def main(args: Array[String]): Unit = {

    /**
     * simple case
     */
    val words: List[String] = List("BBA BMW", "BBA Benz", "BBA Audi", "BBA Benz", "SS Maybach")
    val wordFlatMap: List[String] = words.flatMap(_.split(" "))
    val wordGroup: Map[String, List[String]] = wordFlatMap.groupBy(word => word)
    val result: Map[String, Int] = wordGroup.map((word: (String, List[String])) => (word._1, word._2.length))
    println(result)
    val resultToList: List[(String, Int)] = result.toList
    val resultSorted: List[(String, Int)] = resultToList.sortBy(_._2)
    resultSorted.foreach((tuple: (String, Int)) => print(s"${tuple._1}-${tuple._2}, "))
    println()

    /**
     * complicated case
     */
    val animal: List[(String, Int)] = List(("Cat", 2), ("Dog", 1), ("Tiger", 3), ("Big Baby", 5), ("Big Dog", 2))
    val tempResult: List[(String, Int)] = animal.flatMap((tuple: (String, Int)) => {
      val name: Array[String] = tuple._1.split(" ")
      name.map(ele => (ele, tuple._2))
    })
    val fiResult: Map[String, Int] = tempResult
      .groupBy(_._1)
      /*.map((ele: (String, List[(String, Int)])) => {
        val sum: Int = ele._2.map(_._2).reduceLeft(_ + _)
        (ele._1, sum)
      }*/
      .mapValues(_.map(_._2).sum)
    println(fiResult)
  }
}