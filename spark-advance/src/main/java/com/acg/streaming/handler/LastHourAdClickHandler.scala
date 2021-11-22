package com.acg.streaming.handler

import java.text.SimpleDateFormat
import java.util.Date

import com.acg.streaming.caseClass.AdsLog
import org.apache.spark.streaming.Minutes
import org.apache.spark.streaming.dstream.DStream

/**
 * 最近一小时广告点击量统计管理
 */
object LastHourAdClickHandler {

  private val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  /**
   * 获取最近一小时的点击广告
   * @param filterDStream 过滤后的数据流
   * @return 统计聚合后的一小时内数据流
   */
  def lastHourAdClickStatistics(filterDStream: DStream[AdsLog]):
    DStream[(String,List[(String,Long)])] = {

    //获取窗口内流数据
    val minuteDStream: DStream[AdsLog] = filterDStream.window(Minutes(5))

    //映射并聚合数据
    val minuteDStreamMapReduce: DStream[((String, String), Long)] =
      minuteDStream.map(adsLog => {
      val timestamp: String = adsLog.timestamp
      val date: String = dateFormat.format(new Date(timestamp.toLong))
      ((adsLog.adId, date), 1L)
    }).reduceByKey(_+_)
    //转换形式
    val minuteDStreamMap: DStream[(String, (String, Long))] = minuteDStreamMapReduce.map {
      case ((adId, date), count) => (adId, (date, count))
    }
    //数据分组
    val minuteDStreamGroupSort: DStream[(String, List[(String, Long)])] =
      minuteDStreamMap.groupByKey().mapValues(
      iter => iter.toList.sortWith(_._2 < _._2)
    )
    minuteDStreamGroupSort
  }
}
