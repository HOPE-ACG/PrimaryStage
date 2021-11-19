package com.acg.sql

import org.apache.spark.sql.{DataFrame, SparkSession, functions}

/**
 * 基于Hive数据库开发
 *
 * 需求：计算各个区域前三大热门商品，并备注上每个商品在主要城市中的分布比例，超过两个城市用其他显示
 *      这里的热门商品是从点击量的维度来看的
 */
object HotProductTop3 {

  def main(args: Array[String]): Unit = {

    //创建与hive的连接
    val sparkSession: SparkSession = SparkSession
      .builder()
      .enableHiveSupport()
      .config("spark.sql.warehouse.dir", "hdfs://hadoop103:8020/user/hive/warehouse")
      .master("local[3]")
      .appName("hotProduct")
      .getOrCreate()

    //注册函数
    sparkSession.udf.register("distributedRate", functions.udaf(new DistributedRate))

    //使用hotproduct数据库
    sparkSession.sql("use hotproduct")

    //查询目标所有数据
    sparkSession.sql(
      """
        |select u.click_product_id as pId,p.product_name as pName,c.city_name as cName,c.area
        |from user_visit_action u
        |join product_info p
        |on p.product_id = u.click_product_id
        |join city_info c
        |on c.city_id = u.city_id
        |where u.click_product_id != -1
        |""".stripMargin).createOrReplaceTempView("t1")

    //将目标数据分组，并统计组内产品数量
    sparkSession.sql(
      """
        |select area,pName,count(pId) as click,distributedRate(cName) as dRate
        |from t1
        |group by area,pName
        |""".stripMargin).createOrReplaceTempView("t2")

    //组内数据按照点击量排序
    sparkSession.sql(
      """
        |select *,rank() over(partition by area order by click desc) as rank
        |from t2
        |""".stripMargin).createOrReplaceTempView("t3")

    //取前三名
    val frame: DataFrame = sparkSession.sql(
      """
        |select *
        |from t3
        |where rank < 4
        |""".stripMargin)

    frame.rdd.coalesce(3).saveAsTextFile("file/case/hotProduct")

    //关闭spark session
    sparkSession.close()
  }

}
