package com.acg.chapter6

/**
 * Enumeration and App
 *
 * Type key word
 */
object Extension extends App {

  println(Research.BIGDATA + ";" + Research.DATA + ";" + Research.NUM.id)

  println(new Extension().s)

  val pointer1, pointer2: Int = 1
}

object Research extends Enumeration {

  val BIGDATA: Research.Value = Value("Big Data")

  val DATA: Research.Value = Value(1, "Data Mining")

  //number value must be unique
  val NUM: Research.Value = Value(3)
}

/**
 * Type defines new type
 */
class Extension {

  type str = String

  val s: str = "acg"
}

