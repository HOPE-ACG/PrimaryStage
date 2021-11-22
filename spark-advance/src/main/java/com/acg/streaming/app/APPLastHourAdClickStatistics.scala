package com.acg.streaming.app

import com.acg.streaming.caseClass.AdsLog
import com.acg.streaming.handler.{AdClickStatisticsHandler, BlackListHandler, LastHourAdClickHandler}
import com.acg.streaming.util.MyKafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 最近一小时数据统计
 */
object APPLastHourAdClickStatistics {

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
    //加入黑名单
    BlackListHandler.addBlackList(dStreamFilter)
    //保存实时流数据
    AdClickStatisticsHandler.saveAdClickInfo(dStreamFilter)

    //统计最近一小时流数据
    val lastHourDStream: DStream[(String, List[(String, Long)])] =
      LastHourAdClickHandler.lastHourAdClickStatistics(dStreamFilter)

    lastHourDStream.print()

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
