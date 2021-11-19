package com.acg.sql

/**
 * 自定义聚合函数
 *
 * 计算每个商品在各城市分布比例
 */

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
 * 自定义聚合函数
 */
class DistributedRate extends Aggregator[String, Buffer, String] {

  //buffer初始化
  override def zero: Buffer = {
    Buffer(0, mutable.HashMap[String, Long]())
  }

  //buffer更新
  override def reduce(b: Buffer, city: String): Buffer = {
    val map: mutable.HashMap[String, Long] = b.map
    val l: Long = map.getOrElse(city, 0L) + 1
    map.update(city, l)
    Buffer(b.total+1, map)
  }

  //buffer相加
  override def merge(b1: Buffer, b2: Buffer): Buffer = {
    val total: Long = b1.total + b2.total
    val map1: mutable.HashMap[String, Long] = b1.map
    val map2: mutable.HashMap[String, Long] = b2.map
    //foldLeft第二个参数传递一个函数
    val map: mutable.HashMap[String, Long] = map1.foldLeft(map2) {
      case (map, (city, count)) =>
        val l: Long = map.getOrElse(city, 0L) + count
        map.update(city, l)
        map
    }
    Buffer(total, map)
  }

  //返回结果
  override def finish(buffer: Buffer): String = {
    val total: Long = buffer.total
    val map: mutable.HashMap[String, Long] = buffer.map
    val listBuffer = new ListBuffer[(String, Int)]
    map.foreach(
      tuple => {
        val rate: Long = tuple._2*100/total
        listBuffer.append((tuple._1, rate.toInt))
      }
    )
    listBuffer.sortWith(
      (t1,t2) => t2._2 > t1._2
    )
    var remain: Int = 100
    val builder = new StringBuilder()
    listBuffer.take(2).foreach(
      tuple => {
        remain -= tuple._2
        builder.append(tuple._1).append(":").append(tuple._2).append("%,")
      }
    )
    if(listBuffer.size > 2) builder.append("其它:").append(remain).append("%")
    else builder.delete(builder.size, builder.size)
    builder.toString()
  }

  override def bufferEncoder: Encoder[Buffer] = Encoders.product

  override def outputEncoder: Encoder[String] = Encoders.STRING
}

