package cn.edu.wordcount2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountSpark {

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
    //use Spark's method
    val result: RDD[(String, Int)] = map.reduceByKey(_ + _)
    //output result
    //change to Array
    val array: Array[(String, Int)] = result.collect()
    array.foreach(println)

    /**
     * close
     */
    context.stop()
  }
}
