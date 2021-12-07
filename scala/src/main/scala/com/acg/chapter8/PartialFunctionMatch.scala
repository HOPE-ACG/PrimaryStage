package com.acg.chapter8

/**
 * Partial function mode matching
 */
object PartialFunctionMatch {

  def main(args: Array[String]): Unit = {

    val animal: List[(String, Int)] = List(("monkey", 100), ("bird", 30), ("chicken", 500))
    val newAnimal: List[(String, Int)] = animal.map(
      (tuple: (String, Int)) => {
        tuple match {
          case (a, b) => (a, b + 3)
        }
      }
    )
    println(newAnimal)
    //simplified
    val new2Animal: List[(String, Int)] = animal.map {
      case (a, b) => (a, b + 5)
    }
    println(new2Animal)

    /**
     * partial function
     */
    val absolute_positive: PartialFunction[Int, Int] = {
      case x if x >= 0 => x
    }
    val absolute_nagtive: PartialFunction[Int, Int] = {
      case x if x < 0 => -x
    }
    def absolute(x: Int): Int = (absolute_nagtive orElse absolute_positive)(x)
    println(absolute(-10))
    println(absolute(0))
  }

}
