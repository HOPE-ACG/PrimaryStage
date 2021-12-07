package com.acg.chapter7

import scala.collection.immutable.Queue
import scala.collection.mutable

/**
 * Queue and Parallel List
 */
object QueueAndParallel {

  def main(args: Array[String]): Unit = {

    /**
     * Queue
     * first in first out
     */
    //mutable queue
    val queue: mutable.Queue[String] = new mutable.Queue[String]()
    queue.enqueue("aa", "bb", "cc")
    queue.foreach(printf("%s\t", _))
    println()
    val out: String = queue.dequeue()
    printf("out: %s\t in: ", out)
    queue.foreach(printf("%s\t", _))
    println()
    //immutable queue
    val nums: Queue[Int] = Queue(55, 66, 88)
    val outEle: (Int, Queue[Int]) = nums.dequeue
    println("out: " + outEle._1 + " ,remain queue: " + outEle._2)

    /**
     * Parallel List
     */
    val threads: List[Int] = (1 to 20).toList
    threads.map(_ => Thread.currentThread().getId).foreach(printf("%d\t", _))
    println()
    threads.par.map(_ => Thread.currentThread().getId).foreach(printf("%d\t", _))
  }
}
