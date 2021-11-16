package com.acg.framework.service

import com.acg.framework.dao.UserActionGet
import org.apache.spark.rdd.RDD

/**
 * 业务逻辑层
 */
object UserActionAnalysis {

  /**
   * 业务一：TOP10热门品类
   * 排名：点击数 -> 下单数 -> 支付数
   *
   * @return 热门品类id集合
   */
  def productOfTop10: RDD[(String, Int)] = {

    val validData: RDD[Array[String]] = UserActionGet.productOfTop10
    //扁平映射为kv
    val validDataKV: RDD[(String, Int)] = validData.flatMap(
      array => array.map((_, 1))
    )
    //聚合相同的类别
    val validDataStatistics: RDD[(String, Int)] = validDataKV.reduceByKey(_ + _)

    validDataStatistics
  }
}
