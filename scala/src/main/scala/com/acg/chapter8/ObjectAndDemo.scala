package com.acg.chapter8

/**
 * match object and demo class
 */
object ObjectAndDemo {

  def main(args: Array[String]): Unit = {

    /**
     * match object
     */
    val stu: Student = Student("acg", 28)
    def objects(student: Student): Unit = student match {
      case Student("yy", 18) => println("I am yaoyao")
      case Student("acg", 28) => println("I am acg")
      case _ => println("no one")
    }
    objects(stu)

    /**
     * demo class
     */
    val animal: Animal = Animal("monkey", 100000)
    def demo(animal: Animal): Unit = animal match {
      case Animal("monkey", 100000) => println("I am a monkey")
      case _ => println("other animal")
    }
    demo(animal)
  }

  class Student(val name: String, val age: Int)

  object Student {

    def apply(name: String, age: Int): Student = new Student(name, age)

    def unapply(stu: Student): Option[(String, Int)] = {

      if(stu == null) None
      else Some((stu.name, stu.age))
    }
  }

  case class Animal(kind: String, amount: Int)
}
