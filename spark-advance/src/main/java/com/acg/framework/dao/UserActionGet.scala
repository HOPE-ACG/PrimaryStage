package com.acg.framework.dao

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 数据获取层
 */
class UserActionGet {

  //创建spark context
  private val sparkContext = new SparkContext(new SparkConf().setMaster("local[*]").setAppName("productOfTop10"))

  /**
   * 读取txt文件
   *
   * @return 文本文件
   */
  def textFile: RDD[String] = {

    val textFile: RDD[String] = sparkContext.textFile("file/user_visit_action.txt")

    textFile
  }
}
