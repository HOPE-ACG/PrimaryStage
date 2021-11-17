package com.acg.framework.application

import java.io.{BufferedWriter, File, PrintWriter}

import com.acg.framework.service.UserActionAnalysis
import org.apache.spark.rdd.RDD

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
 * 电商网站数据分析
 * 程序主入口
 */
object Application extends App {

  //业务层对象
  private val userActionAnalysis = new UserActionAnalysis

  //TOP10热门品类
  private val productOfTop10: Array[(String, ArrayBuffer[Int])] = userActionAnalysis.productOfTop10
  productOfTop10.foreach(println)
  private val bufferedWriter = new BufferedWriter(new PrintWriter(new File("file/case/hotProduct.txt")))
  productOfTop10.foreach(
    tuple => {
      bufferedWriter.write(tuple._1+"\t")
      bufferedWriter.write(tuple._2+"\r\n")
    }
  )
  bufferedWriter.flush()
  bufferedWriter.close()

  println("=============================")

  //Top10 热门品类中每个品类的 Top10 活跃 Session 统计
  private val sessionOfTop10: RDD[(String, ListBuffer[(String, Int)])] = userActionAnalysis.sessionOfTop10
  sessionOfTop10.foreach(println)
  sessionOfTop10.saveAsTextFile("file/case/activeSession.txt")

  println("=============================")

  //页面单跳转换率统计
  private val rateOfPageJump: RDD[(String, ListBuffer[(String, Float)])] = userActionAnalysis.rateOfPageJump
  rateOfPageJump.foreach(println)
  rateOfPageJump.saveAsTextFile("file/case/pageJump.txt")
}
