package com.acg.chapter11

object UpAndDownTextRestrict {

  def sure[Father : MyList](f: Father): Father = {
    println(implicitly[MyList[Father]].list)
    f
  }

  implicit val fa: MyList[Father] = new MyList

  def main(args: Array[String]): Unit = {
    println(sure(new Father("gxx")).getName)
  }
}