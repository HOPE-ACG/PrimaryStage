package cn.edu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount_thirdWay {

  def main(args: Array[String]): Unit = {

    //TODO build connection with Spark framework
    val sparkConf = new SparkConf()
    sparkConf.setMaster("local").setAppName("Words Count")
    val sparkContext = new SparkContext(sparkConf)

    //TODO execute relevant operation
    //1.read files by path
    val lines: RDD[String] = sparkContext.textFile("file")

    //2.divide every line into single word(flat operation)
    val words: RDD[String] = lines.flatMap(_.split(" "))

    //3.add count to every word
    val wordAndCount: RDD[(String, Int)] = words.map(word => (word, 1))

    //4.merge group and reduce method into one method
    //reduce value by the same key
    val group_wordCount: RDD[(String, Int)] = wordAndCount.reduceByKey(_ + _) // simplified ((x, y) => {x + y})

    //5.print results
    val array: Array[(String, Int)] = group_wordCount.collect()
    array.foreach(print)
    println()

    //TODO shutdown connection
    sparkContext.stop()
  }
}
