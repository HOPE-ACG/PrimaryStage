package com.acg.chapter6

object Student {

  def main(args: Array[String]): Unit = {
    val p: Person = new Student
    p.task(p.id)

    val person: Person = new Person {
      override val id: Int = 455
      override val name: String = "SS"

      override def task(id: Int): Unit = println(s"code $id, your task is to find $name")
    }
    person.name = "RR"
    person.task(person.id)

    val acg: Singleton = Singleton("001", "acg")
    acg.info()
    val yy: Singleton = Singleton.sluggard("002", "yy")
    yy.info()
    println(acg + ";" + yy)
  }
}

/**
 * implement abstract class
 */
class Student extends Person {

  val id: Int = 11086

  var name: String = "AA"

  def task(id: Int): Unit = println(s"code $id, your task is to find $name")
}
