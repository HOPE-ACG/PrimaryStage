package cn.edu.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount_secondWay {

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

    //4.words is grouped by name
    val group: RDD[(String, Iterable[(String, Int)])] = wordAndCount.groupBy(t => t._1)

    //5.count the number of same words,change form to {word, count},such as {spark, 3}
    val group_WordCount: RDD[(String, Int)] = group.map {
                              case (_, list) =>
                                list.reduce(
                                    (t1, t2) => {
                                        (t1._1, t1._2 + t2._2)
                                    }
                                )
    }
    //6.print results
    val array: Array[(String, Int)] = group_WordCount.collect()
    array.foreach(print)
    println()

    //TODO shutdown connection
    sparkContext.stop()
  }
}
