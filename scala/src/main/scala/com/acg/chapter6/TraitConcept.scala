package com.acg.chapter6

object TraitConcept {

  def main(args: Array[String]): Unit = {
    val panda: Animal = new Panda
    panda.ability()
    println(panda.amount)
    panda.protectionLevel()
    println("****************")

    val pheasant = new Pheasant
    pheasant.ability()
    println(pheasant.amount)
    pheasant.protectionLevel()
    pheasant.amount = 15
    pheasant.protectionLevel()
    println(pheasant.know())
    println()
    println("****************")

    val panda2: Panda with Animal = new Panda with Animal {
      amount = 5000

      override def ability(): Unit = println("climb trees")
    }
    panda2.ability()
    println(panda2.amount)
    println("****************")

    val rabbit = new Rabbit
    rabbit.food(new Animal {
      override val name: String = "rabbit"
      override val species: String = "mammal"
      /**
       * specific ability
       */
      override val feature: String = "large scale of amount"
      /**
       * species remain amount
       */
      override var amount: Int = 99999999

      /**
       * common characteristic
       */
      override def ability(): Unit = println("run fast")
    })
  }
}
