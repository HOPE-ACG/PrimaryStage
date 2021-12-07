package com.acg.chapter11

/**
 * Coordinate generics and reverse generics
 */
object CoordinateAndReverse {

  def main(args: Array[String]): Unit = {

    val list: MyList[Son] = new MyList[Son]
    println(list.list)
  }

}

class Father(name: String) {

  def getName: String = name
}

class Son() extends Father("gxm")

class MyList[+Father] {

  val list: List[String] = List("gxm", "hxm")
}

