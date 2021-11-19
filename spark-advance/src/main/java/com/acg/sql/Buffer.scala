package com.acg.sql

import scala.collection.mutable

/**
 * 用于自定义聚合函数的样例类
 *
 * @param total 商品的总点击数
 * @param map 每个城市的商品点击数
 */
case class Buffer(total: Long, map: mutable.HashMap[String, Long])
