package com.acg.chapter6

import scala.beans.BeanProperty

/**
 * Based definition about class and object
 */
object ClassAndObject {

  def main(args: Array[String]): Unit = {
    val yy = new ClassAndObject
    yy.job(id)
    yy.setName("acg")
    println(yy.getName)
    task(yy.id)

    val student = new MultiClass("003", "yy")
    student.info()
  }
}

/**
 * define class
 */
class ClassAndObject {

  val id: String = "001"

  @BeanProperty
  protected var name: String = _

  def job(id: String): Unit = println(s"no.$id, your job is ***")
}

/**
 * define more class
 *
 * @param id   student's number
 * @param name student's name
 */
private[chapter6] class MultiClass(val id: String, val name: String) {

  def info(): Unit = println(s"student's number is $id and name is $name")
}