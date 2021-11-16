package cn.edu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {

    //TODO build connection with Spark framework
    val sparkConf = new SparkConf()
    sparkConf.setMaster("local").setAppName("Words Count")
    val sparkContext = new SparkContext(sparkConf)

    //TODO execute relevant operation
    //1.read files by path
    val lines: RDD[String] = sparkContext.textFile("file/scala.txt")

    //2.divide every line into single word(flat operation)
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //3.words is grouped by name
    val group: RDD[(String, Iterable[String])] = words.groupBy(word => word)

    //4.count the number of same words,change form to {word, count},such as {spark, 3}
    val group_WordCount: RDD[(String, Int)] = group.map {
                              case (word, list) =>
                                (word, list.size)
    }
    //5.print results
    val array: Array[(String, Int)] = group_WordCount.collect()
    array.foreach(print)
    println()

    //TODO shutdown connection
    sparkContext.stop()
  }
}
