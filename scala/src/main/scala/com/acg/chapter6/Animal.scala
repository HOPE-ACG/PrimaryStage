package com.acg.chapter6

/**
 * dynamic with trait
 */
class Rabbit extends Dish {

  override val name: String = "baking rabbit"

  def eat(): Unit = println("eat everything except grass around cave")
}

class Panda extends Animal {
  override val name: String = "panda"
  override val species: String = "mammal"
  /**
   * specific ability
   */
  override val feature: String = "body's color in black and white"
  /**
   * species remain amount
   */
  override var amount: Int = 100

  /**
   * common characteristic
   */
  override def ability(): Unit = println("eat bamboos")

  override def protectionLevel(): Unit = {
    if (amount < 1000) println("national first-class protected animal")
    else println("natural survive")
  }
}

class Pheasant extends Dish with Animal {

  //override val name: String = "pheasant"
  override val species: String = "BIRDS"
  /**
   * specific ability
   */
  override val feature: String = "flying chicken"
  /**
   * species remain amount
   */
  override var amount: Int = 100

  /**
   * common characteristic
   */
  override def ability(): Unit = println("Meat is very compaction")

  override def protectionLevel(): Unit = super[Dish].protectionLevel()

  override def know(): String = super.know()
}

/**
 * equals to Interface
 */
trait Animal extends Human {

  val animalName: String = "animal"

  val species: String

  /**
   * specific ability
   */
  val feature: String

  /**
   * species remain amount
   */
  var amount: Int

  /**
   * common characteristic
   */
  def ability(): Unit

  /**
   * protected level
   */
  def protectionLevel(): Unit = {
    if (amount < 100) println("national first-class protected animal;")
    else println("natural survive;")
  }

  override def know(): String = s"$animalName-" + super.know()
}

/**
 * multi trait
 */
trait Dish extends Human {

  val dishName: String = "dish"

  /**
   * make deal
   *
   * @param animal meat
   */
  def food(animal: Animal): Unit = println(s"bake ${animal.name}")

  /**
   * protected level
   */
  def protectionLevel(): Unit = {
    println("don't eat national first-class protected animal;")
  }

  override def know(): String = s"$dishName-" + super.know()
}

/**
 * problem of trait overlying
 */
trait Human {

  val name: String = "human"

  /**
   * diamond problem
   */
  def know(): String = s"$name"
}
