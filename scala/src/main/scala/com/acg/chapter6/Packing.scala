package com.acg.chapter6

/**
 * one of three features: packing
 */
object Packing {

  def main(args: Array[String]): Unit = {
    val yueLu = new Packing(35000000, "yueLu", "tourism")
    yueLu.info()
    yueLu.tax = 565555
    //yueLu.capital = 12345678 can't reassign
    yueLu.info()
  }
}

/**
 * constructor
 *
 * @param capital registered capital
 * @param name    corporation name
 */
class Packing(val capital: Int, val name: String) {

  //member variable
  var business: String = _
  var tax: Int = _

  println("Packing's main constructor")

  //assisted constructor
  def this(capital: Int, name: String, business: String) {
    this(capital, name)
    this.business = business
    println("Packing's assisted constructor")
  }

  //assisted constructor
  def this(capital: Int, name: String, tax: Int) {
    this(capital, name)
    this.tax = tax
    println("Packing's another assisted constructor")
  }

  def info(): Unit = println(s"corporation:$name, capital:$capital, " +
    s"business:$business, tax:$tax")
}
