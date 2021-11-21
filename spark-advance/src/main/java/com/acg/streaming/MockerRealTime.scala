package com.acg.streaming

import java.util.Properties

import com.acg.streaming.caseClass.{CityInfo, RanOpt}
import com.acg.streaming.util.PropertiesUtil
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * 生成实时数据模块
 */
object MockerRealTime {


  def main(args: Array[String]): Unit = {

    val properties: Properties = PropertiesUtil.load("streamingConfig.properties")
    //获取kafka集群
    val broker: String = properties.getProperty("kafka.broker.list")

    val kafkaProducer: KafkaProducer[String, String] = createKafkaProducer(broker)

    //随机生成实时数据，并发送到Kafka集群
    while (true) {
      for(line <- generateMockData()) {
        kafkaProducer.send(new ProducerRecord[String, String]("randomData", line))
      }
      Thread.sleep(2000)
    }
  }

  /**
   * 生成模拟数据
   * @return 模拟数据数组
   */
  def generateMockData(): Array[String] = {

    val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
    //随机对象范围
    val randomOptions: RandomOptions[CityInfo] = RandomOptions(RanOpt(CityInfo(1, "北京", "华北"), 30),
      RanOpt(CityInfo(2, "上海", "华东"), 30),
      RanOpt(CityInfo(3, "广州", "华南"), 10),
      RanOpt(CityInfo(4, "深圳", "华南"), 20),
      RanOpt(CityInfo(5, "天津", "华北"), 10))

    val random = new Random()
    //创建50个随机对象
    for(_ <- 0 to 50) {
      val time: Long = System.currentTimeMillis()
      val cityInfo: CityInfo = randomOptions.getRandomOpt
      val area: String = cityInfo.area
      val cityName: String = cityInfo.city_name
      val adId: Int = random.nextInt(6) + 1
      val userId: Int = random.nextInt(6) + 1

      arrayBuffer += time + " " + area + " " + cityName + " " + userId + " " + adId
    }

    arrayBuffer.toArray
  }

  /**
   * 创建kafka生产者
   * @param broker 服务器集群
   * @return kafka生产者
   */
  def createKafkaProducer(broker: String): KafkaProducer[String, String] = {

    val properties = new Properties()
    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker)
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
      "org.apache.kafka.common.serialization.StringSerializer")

    new KafkaProducer[String, String](properties)
  }
}
