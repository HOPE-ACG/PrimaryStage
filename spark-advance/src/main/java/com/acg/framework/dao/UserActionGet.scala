package com.acg.framework.dao

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 数据获取层
 */
object UserActionGet {

  /**
   * 需求一：TOP10热门品类
   * 排名：点击数 -> 下单数 -> 支付数
   *
   * @return 有效数据RDD
   */
  def productOfTop10: RDD[Array[String]] = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("productOfTop10")
    val sparkContext = new SparkContext(sparkConf)
    val textFile: RDD[String] = sparkContext.textFile("file/user_visit_action.txt")

    //分割每行数据
    val lines: RDD[Array[String]] = textFile.map(line => line.split("_"))
    //保留需求数据
    val targetArray: RDD[Array[String]] = lines.map(
      array => Array(array(6), array(8), array(10))
    )

    targetArray
  }
}
