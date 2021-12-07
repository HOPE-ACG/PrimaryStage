package com.acg.chapter4

object AbstractControl {
  def main(args: Array[String]): Unit = {
    val control = new AbstractControl
    //value transmission parameter
    control.valueTrans(isNot = false, name => if (name == "acg") false else true)
    //name transmission parameter
    var n = 10
    control.nameTrans(n > 1) {
      n -= 1
      n
    }
    println("================")
    control.nameTrans2(n < 10) {
      n += 1
      n
    }
    //inert function
    lazy val sum: Int = control.inert(5, 3)
    println("haven't called inert func yet?")
    println(sum)
    println("should have called inert func")
  }
}

class AbstractControl {
  //value transmission parameter
  def valueTrans(isNot: Boolean, fun: String => Boolean): Unit = {
    if (isNot) println(fun("acg")) else println(fun("yy"))
  }

  //name transmission parameter
  def nameTrans(condition: => Boolean): (=> Int) => Unit = {
    def loop(isNot: => Int): Unit = {
      if (condition) {
        println("condition: " + condition + ";n = " + isNot)
        nameTrans(condition)(isNot)
      }
    }

    loop
  }

  //curry
  def nameTrans2(condition: => Boolean)(isNot: => Int): Unit = {
    if (condition) {
      println("condition: " + condition + ";n = " + isNot)
      nameTrans2(condition)(isNot)
    }
  }

  //inert function
  def inert(a: Int, b: Int): Int = {
    println("call this inert function")
    a + b
  }
}
