package cn.edu.wordcount2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * use method of statistics to calculate words
 */
object WordCount2 {

  def main(args: Array[String]): Unit = {

    /**
     * connect to Spark framework
     */
    val conf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")
    val context: SparkContext = new SparkContext(conf)

    /**
     * execute business module
     */
    //read files
    val lines: RDD[String] = context.textFile("Spark/src/files")
    //flatMap every line
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //map all words and mark word's number
    val map: RDD[(String, Int)] = words.map((_, 1))
    //group all words
    val group: RDD[(String, Iterable[(String, Int)])] = map.groupBy(rdd => rdd._1)
    //map grouped words and accumulate number
    val result: RDD[(String, Int)] = group.map(rdd => {
      /*var sum: Int = 0
      rdd._2.foreach(sum += _._2)
      (rdd._1, sum)*/
      rdd._2.reduce((word1, word2) => (rdd._1, word1._2 + word2._2))
    })
    //output result
    result.foreach(println)

    /**
     * close
     */
    context.stop()
  }
}
