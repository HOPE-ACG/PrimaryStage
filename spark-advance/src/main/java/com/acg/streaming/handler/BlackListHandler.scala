package com.acg.streaming.handler

import java.sql.Connection
import java.text.SimpleDateFormat
import java.util.Date

import com.acg.streaming.caseClass.AdsLog
import com.acg.streaming.util.JDBCUtil
import org.apache.spark.streaming.dstream.DStream

/**
 * 黑名单管理
 */
object BlackListHandler {

  private val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")

  /**
   * 添加到黑名单
   * @param filterAdLogDStream 已过滤掉在黑名单中的用户DStream
   */
  def addBlackList(filterAdLogDStream: DStream[AdsLog]): Unit = {

    //转换结构 ads_log=>((date,userId,adId),1)
    val adsLogMap: DStream[((String, String, String), Long)] = filterAdLogDStream.map(
      adsLog => {
        val date: String = dateFormat.format(new Date(adsLog.timestamp.toLong))
        ((date, adsLog.userId, adsLog.adId), 1L)
      }
    )
    //统计单日每个用户点击每个广告的总次数 ((date,user,adId),1)=>((date,user,adId),count)
    val adsLogMapCount: DStream[((String, String, String), Long)] = adsLogMap.reduceByKey(_ + _)
    //插入到数据库
    adsLogMapCount.foreachRDD(
      rdd => {
        rdd.foreachPartition(
          partition => {
            val connection: Connection = JDBCUtil.getConnection
            //偏函数模式匹配
            partition.foreach {
              case ((dt, userId, adId), total) =>
                JDBCUtil.executeUpdate(connection,
                  """
                    |insert into user_ad_count
                    |values(?,?,?,?)
                    |on duplicate key
                    |update count = count+?
                    |""".stripMargin,
                  Array(dt,userId,adId,total,total)
                )
                //查询记录
                val count: Long = JDBCUtil.getDataFromMysql(connection,
                  """
                    |select count from user_ad_count
                    |where
                    |dt=? and userid=? and adid=?
                    |""".stripMargin,
                  Array(dt, userId, adId))
                //点击量大于100加入黑名单
                if(count > 100) {
                  JDBCUtil.executeUpdate(connection,
                  """
                      |insert into black_list
                      |values(?)
                      |on duplicate key
                      |update userid=?
                      |""".stripMargin,
                    Array(userId, userId))
                }
            }
            //改分区内DStream数据处理后，关闭连接
            connection.close()
          }
        )
      }
    )
  }

  //过滤掉已在黑名单的用户
  def filterByBlackList(adsLogDStream: DStream[AdsLog]): DStream[AdsLog] = {

    //只保留不在黑名单上的用户
    adsLogDStream.transform(
      rdd => {
        rdd.filter(
          adsLog => {
            val connection: Connection = JDBCUtil.getConnection
            val isExist: Boolean = JDBCUtil.isExist(connection,
              """
                |select userid from black_list
                |where userid=?
                |""".stripMargin,
              Array(adsLog.adId))
            connection.close()
            !isExist
          }
        )
      }
    )
  }
}
