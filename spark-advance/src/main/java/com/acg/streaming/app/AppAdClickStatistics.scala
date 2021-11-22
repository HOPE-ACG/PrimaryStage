package com.acg.streaming.app

import java.sql.Connection

import com.acg.streaming.caseClass.AdsLog
import com.acg.streaming.handler.{AdClickStatisticsHandler, BlackListHandler}
import com.acg.streaming.util.{JDBCUtil, MyKafkaUtil}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}

/**
 * 广告点击量实时统计
 */
object AppAdClickStatistics {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparkStreaming")
    val streamingContext = new StreamingContext(conf, Seconds(5))

    //读取kafka数据
    val inputDStream: InputDStream[ConsumerRecord[String, String]] =
      MyKafkaUtil.getKafkaStream("randomData", streamingContext)

    //实时随机数据map为样例类
    val dStream: DStream[AdsLog] = inputDStream.map(
      record => {
        val value: String = record.value()
        val array: Array[String] = value.split(" ")
        AdsLog(array(0), array(1), array(2), array(3), array(4))
      }
    )

    //过滤掉已保存到黑名单用户的数据
    val dStreamFilter: DStream[AdsLog] = BlackListHandler.filterByBlackList(dStream)

    dStreamFilter.cache()

    //判断是否加入黑名单
    BlackListHandler.addBlackList(dStreamFilter)
    //保存到实时统计表
    AdClickStatisticsHandler.saveAdClickInfo(dStreamFilter)

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
