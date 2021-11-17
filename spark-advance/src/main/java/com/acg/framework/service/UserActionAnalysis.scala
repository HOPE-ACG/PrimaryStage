package com.acg.framework.service

import com.acg.framework.dao.UserActionGet
import org.apache.spark.rdd.RDD

import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.collection.mutable

/**
 * 业务逻辑层
 */
class UserActionAnalysis {

  //dao层对象
  private val userActionGet = new UserActionGet

  /**
   * 业务一：TOP10热门品类
   * 排名：点击数 -> 下单数 -> 支付数
   *
   * @return 热门品类id集合
   */
  def productOfTop10: Array[(String, ArrayBuffer[Int])] = {

    val text: RDD[String] = userActionGet.textFile
    val actionSplit: RDD[Array[String]] = text.map(_.split("_"))
    val validData: RDD[Array[String]] = actionSplit.map(array => Array(array(6), array(8), array(10)))
    //有效数据扁平映射为kv，（品类，（1，0，0）
    val validDataKV: RDD[(String, mutable.ArrayBuffer[Int])] = validData.flatMap(
      array => mutable.ListBuffer((array(0), mutable.ArrayBuffer(1, 0, 0)), (array(1), mutable.ArrayBuffer(0, 1, 0)), (array(2), mutable.ArrayBuffer(0, 0, 1)))
    )
    //过滤无效品类
    val validDataFilter: RDD[(String, ArrayBuffer[Int])] = validDataKV.filter(tuple => !tuple._1.equals("-1") && !tuple._1.equals("null"))
    //有效数据根据品类行为聚合
    val validDataSum: RDD[(String, mutable.ArrayBuffer[Int])] = validDataFilter.reduceByKey(
      (a1, a2) => mutable.ArrayBuffer(
        a1(0)+a2(0),
        a1(1)+a2(1),
        a1(2)+a2(2),
      )
    )
    //根据优先级排序
    val validDataSort: RDD[(String, ArrayBuffer[Int])] = validDataSum.sortBy(
      tuple => (tuple._2(0), tuple._2(1), tuple._2(2)),
      ascending = false
    )
    val validDataTop10: Array[(String, ArrayBuffer[Int])] = validDataSort.take(10)

    validDataTop10
  }

  /**
   * 业务二：Top10 热门品类中每个品类的 Top10 活跃 Session 统计
   *
   * @return Top10 session列表
   */
  def sessionOfTop10: RDD[(String, ListBuffer[(String, Int)])] = {

    val text: RDD[String] = userActionGet.textFile
    val actionSplit: RDD[Array[String]] = text.map(_.split("_"))
    val validData: RDD[Array[String]] = actionSplit.map(array => Array(array(2), array(6), array(8), array(10)))
    //有效数据扁平映射为kv，（品类，（1，0，0, 0）
    val validDataKV: RDD[(String, mutable.ArrayBuffer[Int])] = validData.flatMap(
      array => mutable.ListBuffer((array(1), mutable.ArrayBuffer(1, 0, 0)), (array(2), mutable.ArrayBuffer(0, 1, 0)), (array(3), mutable.ArrayBuffer(0, 0, 1)))
    )
    //过滤无效品类
    val validDataFilter: RDD[(String, ArrayBuffer[Int])] = validDataKV.filter(tuple => !tuple._1.equals("-1") && !tuple._1.equals("null"))
    //有效数据根据品类行为聚合
    val validDataSum: RDD[(String, mutable.ArrayBuffer[Int])] = validDataFilter.reduceByKey(
      (a1, a2) => mutable.ArrayBuffer(
        a1(0)+a2(0),
        a1(1)+a2(1),
        a1(2)+a2(2),
      )
    )
    //根据优先级排序
    val validDataSort: RDD[(String, ArrayBuffer[Int])] = validDataSum.sortBy(
      tuple => (tuple._2(0), tuple._2(1), tuple._2(2)),
      ascending = false
    )
    val validDataTop10: Array[(String, ArrayBuffer[Int])] = validDataSort.take(10)

    //处理每个品类的session id
    val validDataSession: RDD[(String, String)] = validData.flatMap(
      array => Array((array(1), array(0)), (array(2), array(0)), (array(3), array(0)))
    )
    val validDataSessionGroup: RDD[(String, Iterable[String])] = validDataSession.groupByKey()
    //分组后聚合相同的session id
    val validDataSessionReduce: RDD[(String, mutable.HashMap[String, Int])] = validDataSessionGroup.map(
      tuple => {
        val map = new mutable.HashMap[String, Int]()
        tuple._2.foreach(
          session => {
            val count: Int = map.getOrElse(session, 0) + 1
            map += ((session, count))
          }
        )
        (tuple._1, map)
      }
    )
    //过滤不在top10范围的品类
    val top10: Array[String] = validDataTop10.map(tuple => tuple._1)
    val sessionFilter: RDD[(String, mutable.HashMap[String, Int])] = validDataSessionReduce.filter(
      tuple => top10.contains(tuple._1)
    )
    //排序session 获取top10
    val sessionFilterSort: RDD[(String, ListBuffer[(String, Int)])] = sessionFilter.map(
      tuple => {
        val list = new ListBuffer[(String, Int)]
        tuple._2.foreach(tup => list += tup)
        val listMax: ListBuffer[(String, Int)] = list.sortBy(tuple => tuple._2).takeRight(10)
        (tuple._1, listMax)
      }
    )

    sessionFilterSort
  }

  /**
   * 业务三：每个用户页面单跳转化率
   *
   * @return 各用户页面间单跳转化率集合
   */
  def rateOfPageJump: RDD[(String, ListBuffer[(String, Float)])] = {

    val file: RDD[String] = userActionGet.textFile
    val lineSplit: RDD[Array[String]] = file.map(_.split("_"))
    val validData: RDD[(String, String)] = lineSplit.map(array => (array(2), array(3)))
    //根据 session id 分组
    val validDataGroup: RDD[(String, Iterable[String])] = validData.groupByKey()
    //统计页面id
    val validDataStatistics: RDD[(String, mutable.TreeMap[String, Int])] = validDataGroup.map(
      tuple => {
        val map = new mutable.TreeMap[String, Int]()
        tuple._2.foreach(
          ele => {
            val count: Int = map.getOrElse(ele, 0) + 1
            map += ((ele, count))
          }
        )
        (tuple._1, map)
      }
    )
    //计算转化率
    val validDataCalculation: RDD[(String, ListBuffer[(String, Float)])] = validDataStatistics.map(
      tuple => {
        val listBuffer = new ListBuffer[(String, Float)]
        var id: String = ""
        var num: Float = 0
        tuple._2.foreach(
          tup => {
            if (id.equals("")) {
              id = tup._1
              num = tup._2
            } else {
              val rate: Float = (tup._2.toFloat / num * 100).toInt / 100.0f
              listBuffer.append((id + "-" + tup._1, rate))
              id = tup._1
              num = tup._2
            }
          }
        )
        (tuple._1, listBuffer)
      }
    )

    validDataCalculation
  }
}
