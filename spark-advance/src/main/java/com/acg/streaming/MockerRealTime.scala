package com.acg.streaming

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
 * 生成实时数据模块
 */
object MockerRealTime {

  /**
   * 生成模拟数据
   * @return 模拟数据数组
   */
  def generateMockData(): Array[String] = {

    val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
    val randomOptions: RandomOptions[CityInfo] = RandomOptions(RanOpt(CityInfo(1, "北京", "华北"), 30),
      RanOpt(CityInfo(2, "上海", "华东"), 30),
      RanOpt(CityInfo(3, "广州", "华南"), 10),
      RanOpt(CityInfo(4, "深圳", "华南"), 20),
      RanOpt(CityInfo(5, "天津", "华北"), 10))

    val random = new Random()
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
}
