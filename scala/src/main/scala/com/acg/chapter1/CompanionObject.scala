package com.acg.chapter1

/**
 * Companion Object test
 *
 * @param name certain person's name
 * @param age  certain person's age
 */
class CompanionObject(name: String, age: Int) {
  /**
   * define a finction
   */
  def info(): Unit = {
    printf("name: %s, age: %d, school: %s", name, age, CompanionObject.school)
  }
}

/**
 * CompanionObject class's Companion Object
 */
object CompanionObject {
  /**
   * certain person's school
   * state as global variable, make as object-oriented model
   * when call it by Class
   */
  val school: String = "HNU"

  def main(args: Array[String]): Unit = {
    /**
     * create a final variable
     */
    val acg = new CompanionObject("acg", 28)
    acg.info()
  }

}


