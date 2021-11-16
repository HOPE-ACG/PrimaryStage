package com.acg.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 提取日志中每条URL
 */
object ExtractURLPath {

  def main(args: Array[String]): Unit = {

    /**
     * 创建Spark Context
     */
    val conf: SparkConf = new SparkConf().setMaster("local[3]").setAppName("ExtractURL")
    val sc = new SparkContext(conf)

    //读取log文件
    val rddFile: RDD[String] = sc.textFile("file/apache.log",2)
    //分割每行数据
    val rddReserveData: RDD[String] = rddFile.map(
      line => {
        val lineSplit: Array[String] = line.split(" ")
        lineSplit(6)
      }
    )
    //输出到console
    rddReserveData.foreach(println)

    sc.stop()
  }

}
