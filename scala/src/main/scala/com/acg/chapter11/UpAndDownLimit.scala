package com.acg.chapter11

/**
 * generics up and down bound
 */
object UpAndDownLimit {

  def main(args: Array[String]): Unit = {

    def sure[A <: Person](a: A): Unit = {
      println(a.getName)
    }

    sure(new Person("gxm"))
  }
}

class Person(name: String) {

  def getName: String = name
}

//T is subclass or equivalence
class PersonListUp[T <: Person]

//T is superclass or equivalence
class PersonListDown[T >: Person]