package com.acg.streaming

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * 保存所有随机产生的RanOpt及总权重
 */
object RandomOptions {

  def apply[T] (ranOpts: RanOpt[T]*): RandomOptions[T] = {

    val randomOptions = new RandomOptions[T]
    for (elem <- ranOpts) {
      randomOptions.totalWeight += elem.weight
      for(_ <- 1 to elem.weight) {
        randomOptions.listBuffer += elem.value
      }
    }
    randomOptions[T]
  }
}

/**
 * 封装具体的属性
 * @tparam T 具体的类
 */
class RandomOptions[T] {

  //所有RanOpt的总权重
  var totalWeight: Int = 0
  //保存所有的RanOpt
  var listBuffer: ListBuffer[T] = new ListBuffer[T]

  //随机获取保存在listBuffer中的泛型类中的一个
  def getRandomOpt: T = {

    val ranInt: Int = new Random().nextInt(totalWeight)
    listBuffer(ranInt)
  }
}

