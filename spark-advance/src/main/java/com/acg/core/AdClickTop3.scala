package com.acg.core

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计每一个省份每个广告点击量的TOP3
 */
object AdClickTop3 {

  def main(args: Array[String]): Unit = {

    /**
     * 配置Spark Context
     */
    val conf: SparkConf = new SparkConf().setMaster("local[4]").setAppName("adClick")
    val sc = new SparkContext(conf)
    /**
     * 加载日志文件，创建RDD
     */
    val agentFile: RDD[String] = sc.textFile("file/agent.log", 2)
    /**
     * 分割每行数据, 保留有效数据
     **/
    val splitToTuple: RDD[((String, String), Int)] = agentFile.map(
      line => {
        val lineSplitToArray: Array[String] = line.split(" ")
        ((lineSplitToArray(1), lineSplitToArray(4)), 1)
      }
    )
    /**
     * 按照(省份,广告)分组
     */
    val reduce: RDD[((String, String), Int)] = splitToTuple.reduceByKey(_ + _)
    /**
     * 重新映射
     */
    val map: RDD[(String, (String, Int))] = reduce.map(
      tuple => (tuple._1._1, (tuple._1._2, tuple._2))
    )
    /**
     * 排序
     */
    val sort: RDD[(String, (String, Int))] = map.sortBy(
      tuple => tuple._2._2, ascending = false, 2
    )
    /**
     * 分组
     */
    val group: RDD[(String, Iterable[(String, Int)])] = sort.groupByKey()
    /**
     * 取三名
     */
    val res: RDD[(String, List[(String, Int)])] = group.mapValues(
      iter => iter.toList.take(3)
    )
    res.foreach(
      /*tuple =>
        tuple._2.foreach(
          list => println(s"省份${tuple._1}, 广告${list._1}, 点击量${list._2}")
        )*/
      println
    )
    /**
     * 输出到文件
     */
    //res.saveAsTextFile("file/adClick")
    /**
     * 关闭资源
     */
    sc.stop()
  }
}
