package com.acg.chapter6

/**
 * single instance object
 */
object Singleton {

  /**
   * privatize object
   */
  private var employee: Singleton = _

  //starving style
  def apply(id: String, name: String): Singleton = {
    employee = new Singleton(id, name)
    employee
  }

  //sluggard style
  def sluggard(id: String, name: String): Singleton = {
    if (employee == null) {
      employee = new Singleton(id, name)
    }
    employee
  }
}

/**
 * private main constructor
 *
 * @param id   employee id
 * @param name employee name
 */
class Singleton private(val id: String, val name: String) {

  def info(): Unit = println(s"id is $id and name is $name")
}
