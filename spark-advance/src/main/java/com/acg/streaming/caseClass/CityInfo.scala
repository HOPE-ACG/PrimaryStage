package com.acg.streaming.caseClass

/**
 * 城市信息表样例类
 *
 * @param city_id   城市id
 * @param city_name 城市
 * @param area      城市所属地区
 */
case class CityInfo(city_id: Long,
                    city_name: String,
                    area: String)
