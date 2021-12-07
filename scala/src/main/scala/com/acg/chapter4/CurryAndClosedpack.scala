package com.acg.chapter4

import scala.annotation.tailrec

object CurryAndClosedpack {
  def main(args: Array[String]): Unit = {
    //create object
    val cac = new CurryAndClosedpack()
    //closed pack
    val res: Int = cac.add(2)(3)
    println(res)
    //curry
    val res2: Int = cac.add2(5)(4)
    println(res2)
    //tail recursion
    println(cac.factorial(5))
  }
}

class CurryAndClosedpack {
  //closed pack
  def add(a: Int): Int => Int = {
    b: Int => a + b
    /*def addB(b: Int): Int = {
      a + b
    }
    addB*/
  }

  //curry
  def add2(a: Int)(b: Int): Int = a + b

  //recursion
  def factorial(a: Int): Int = {
    @tailrec
    def tailRec(b: Int, res: Int): Int = {
      if (b == 1) res
      else tailRec(b - 1, res * b)
    }

    tailRec(a, 1)
  }
}
