package com.acg.chapter7

/**
 * Tuple has 22 elements at most
 */
object TupleTest {

  def main(args: Array[String]): Unit = {
    /**
     * create tuple
     */
    val tuple: (String, Double, Int, Char) = ("acg", 3.14, 25, 'y')
    //visit
    println(s"${tuple._2}, ${tuple.productElement(3)}")
    //traverse
    val allElements: Iterator[Any] = tuple.productIterator
    for (elem <- allElements) print(elem + "\t")
    println()
    /**
     * nesting tuple
     */
    val nestTuple: (String, (String, Int), Int) = ("yy", ("RR", 180), 100)
    val iterator: Iterator[Any] = nestTuple.productIterator
    //iterator.foreach((x: Any) => print(s"$x\t"))
    //println()
    iterator.foreach((x: Any) => {
      x match {
        case tuple1: (String, Int) =>
          val nestedTuple: Iterator[Any] = tuple1.productIterator
          nestedTuple.foreach((x: Any) => print(s"$x\t"))
        case _ => print(s"$x\t")
      }
    })
  }
}
