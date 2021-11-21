package com.acg.streaming.caseClass

/**
 * 记录点击广告的所有属性
 * @param timestamp 时间戳
 * @param area 地区
 * @param city 城市
 * @param userId 用户id
 * @param adId 广告id
 */
case class AdsLog(timestamp: String,
                  area: String,
                  city: String,
                  userId: String,
                  adId: String)
