package com.acg.streaming.handler

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

import com.acg.streaming.caseClass.AdsLog
import com.acg.streaming.util.JDBCUtil
import org.apache.spark.streaming.dstream.DStream

/**
 * 广告实时点击量统计管理
 */
object AdClickStatisticsHandler {

  private val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  /**
   * 保存广告点击信息到MySQL
   *
   * @param adClickDStream 过滤后点击信息数据流
   */
  def saveAdClickInfo(adClickDStream: DStream[AdsLog]): Unit = {

    val adClickMap: DStream[((String, String, String, String), Long)] = adClickDStream.map(
      adsLog => {
        val timestamp: String = adsLog.timestamp
        val date: String = dateFormat.format(new Date(timestamp.toLong))
        ((date, adsLog.area, adsLog.city, adsLog.adId), 1L)
      }
    ).reduceByKey(_+_)

    //按分区遍历rdd
    adClickMap.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          partition => {
            val connection: Connection = JDBCUtil.getConnection
            partition.foreach {
              case ((dt, area, city, adId), count) =>
                JDBCUtil.executeUpdate(connection,
                  """
                    |insert into area_city_ad_count
                    |values(?,?,?,?,?)
                    |on duplicate key
                    |update count=count+?
                    |""".stripMargin,
                  Array(dt,area,city,adId,count,count)
                )
            }
            connection.close()
          }
        )
      }
    )
  }
}
