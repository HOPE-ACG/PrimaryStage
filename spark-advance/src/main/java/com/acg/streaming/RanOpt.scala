package com.acg.streaming

/**
 * 随机生成的具体类的包装
 * @param value 具体类
 * @param weight 此类的比重
 * @tparam T 具体类的泛型
 */
case class RanOpt[T] (value: T, weight: Int)
