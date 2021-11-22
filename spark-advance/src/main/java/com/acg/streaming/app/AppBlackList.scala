package com.acg.streaming.app

import com.acg.streaming.caseClass.AdsLog
import com.acg.streaming.handler.BlackListHandler
import com.acg.streaming.util.MyKafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 黑名单实时统计
 */
object AppBlackList {

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

    val dStreamFilter: DStream[AdsLog] = BlackListHandler.filterByBlackList(dStream)
    BlackListHandler.addBlackList(dStreamFilter)

    dStreamFilter.cache()
    dStreamFilter.count().print()

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
