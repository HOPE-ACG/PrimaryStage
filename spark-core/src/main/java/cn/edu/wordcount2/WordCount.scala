package cn.edu.wordcount2

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * "Hello World" in spark
 */
object WordCount {

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
    //split every line
    val words: RDD[String] = lines.flatMap(_.split(" "))
    //group all words
    val groupedWords: RDD[(String, Iterable[String])] = words.groupBy(word => word)
    //map grouped words
//    groupedWords.foreach(println)
    val result: RDD[(String, Int)] = groupedWords.map(rdd => (rdd._1, rdd._2.size))
    /*val result: RDD[(String, Int)] = groupedWords.map {
      case (word, list) => (word, list.size)
    }*/
    //print
    result.foreach(println)
    //output to text
    //need to be serializable
    /*val writer = new PrintWriter(new File("Spark/src/files/wcout.txt"))
    result.foreach(rdd => {
        writer.write(rdd._1)
        writer.write("\t")
        writer.write(rdd._2)
        writer.write("\n")
      }
    )
    writer.close()*/

    /**
     * close
     */
    context.stop()
  }
}
