package com.acg.chapter6

object TraitSelf {

  def main(args: Array[String]): Unit = {
    val worker = new Worker
    worker.whatJob()
  }
}

/**
 * if TraitSelf is self-class,
 * must be extends ScientificResearch , and then implement TraitSelf
 */
class Worker extends ScientificResearch with TraitSelf

/**
 * specify other class as self
 */
trait TraitSelf {

  noName: ScientificResearch =>

  def whatJob(): Unit = println(s"$direction, $workHard work Hard")
}

class ScientificResearch {

  val direction: String = "big data"

  val isSuccessful: Boolean = true

  def workHard: String = "must be"
}
