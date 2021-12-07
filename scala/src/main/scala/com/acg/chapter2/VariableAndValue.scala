package com.acg.chapter2

import java.io.File

import scala.io.{BufferedSource, Source, StdIn}

object VariableAndValue {

  def main(args: Array[String]): Unit = {
    //variable
    var acg: Int = "11".toInt
    //final variable
    val yyyy = 18
    acg = 11
    //state as val type, can't reassign value
    //yy = 16
    println(acg + ";" + yyyy)

    /**
     * symbol named
     */
    //can't start with {number}
    //val 1abc = 13
    //allow to start with {underline}
    val _123a = "code"
    println(_123a)

    /**
     * quote data type
     */
    val yy = new VariableAndValue("yy", 18)
    yy.info()
    printf("name: %s, age: %d \n", yy.name, yy.age)
    yy.name = "ACG"
    //state as val type after class name, can't reassign value
    //if dont state val type after class name, can't catch this variable via {object quote}
    //yy.age = 19
    yy.info()
    printf("name: %s, age: %d \n", yy.name, yy.age)

    /**
     * String type
     */
    val name = "yy"
    println(name * 3)
    //template
    val temp = 36.5
    val template_string = s"$acg years old and $yyyy years old"
    val template_format = f"$temp%3.2f"
    val template_raw = raw"${yy.age}%2.3f"
    println(template_string + ";" + template_format + ";" + template_raw)
    val origin: String =
      s"""|select * from hnu
          |  where name = $name
                     """.stripMargin
    println(origin)

    /**
     * input from keyboard
     */
    val str: String = StdIn.readLine()
    val fl: Float = StdIn.readFloat()
    println(str + ";" + fl)
    //read file
    val source: BufferedSource = Source.fromFile(new File("D:/hadoop/myproject/PrimaryStage/Scala/src/main/resources/UsedByScala.txt"), "UTF-8")
    source.foreach(print)
    source.close()
  }
}

class VariableAndValue(var name: String, val age: Int) {
  def info(): Unit = {
    printf("name: %s; age: %d \n", name, age)
  }
}
