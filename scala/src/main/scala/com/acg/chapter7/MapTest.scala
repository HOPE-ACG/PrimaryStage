package com.acg.chapter7

import scala.collection.mutable

/**
 * immutable and mutable map
 */
object MapTest {

  def main(args: Array[String]): Unit = {

    /**
     * create immutable map
     */
    val sportsman: Map[String, Int] = Map("Su bingtian" -> 2, "Liu xiang" -> 1, "Sun yang" -> 3)
    sportsman.foreach((kv: (String, Int)) => print(kv + "\t"))
    println()
    //get key/value
    val names: Iterable[String] = sportsman.keys
    //    val names: Set[String] = sportsman.keySet
    names.foreach(printf("%s\t", _))
    println()
    val ranking: Int = sportsman.getOrElse("acg", 100)
    val ranking_liu: Int = sportsman.getOrElse("Liu xiang", 0)
    val ranging_sun: Int = sportsman("Sun yang")
    //shorthand
    val ranging_su: Int = sportsman("Su bingtian")
    println(s"acg-$ranking, liu-$ranking_liu, su-$ranging_su, sun-$ranging_sun")

    /**
     * create mutable map
     */
    val breakfast: mutable.Map[String, Int] = mutable.Map("Jianbing guozi" -> 1, "Dou funao" -> 2, "Ga bacai" -> 3)
    //add
    breakfast.put("Guo zi", 4)
    breakfast += (("Dou jiang", 4))
    breakfast.foreach((kv: (String, Int)) => print(s"$kv\t"))
    println()
    //remove
    breakfast.remove("Dou funao")
    breakfast -= "Ga bacai"
    breakfast.foreach((kv: (String, Int)) => print(s"$kv\t"))
    println()
    //update
    breakfast.update("Jianbing guozi", 0)
    breakfast.foreach((kv: (String, Int)) => print(s"$kv\t"))
    println()
    //combine
    val breakfast_2: Map[String, Int] = Map("Dou funao" -> 2)
    val breakfast_combine: Map[String, Int] = breakfast_2 ++ breakfast
    breakfast_combine.foreach((kv: (String, Int)) => print(s"$kv\t"))
    println()
    breakfast ++= breakfast_2
    breakfast.foreach((kv: (String, Int)) => print(s"$kv\t"))
  }
}
