package com.acg.chapter6

/**
 * extension and multi status
 */
object ExtensionAndMulti extends Packing(3356, "acg", "film") {

  def main(args: Array[String]): Unit = {
    business = "entertainment"
    tax = 5578
    info()

    val co: ClassAndObject = new ExtensionAndMulti
    co.job(co.getName)
  }
}

class ExtensionAndMulti extends ClassAndObject {

  name = "yy"

  override def job(id: String): Unit = println(s"your name is $name")
}


