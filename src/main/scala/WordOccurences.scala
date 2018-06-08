import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object WordOccurences {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val sc = new SparkContext("local", "WordOccurences")

    val lines = sc.textFile("../ApacheSparkScala/book.txt")

    // W => words + => more than one word so \\W+ => returns words
    val rdd = lines.flatMap(x => x.split("\\W+"))

    val lowercaseWords = rdd.map(x => x.toLowerCase())

    val wordCounts = lowercaseWords.map(x => (x, 1)).reduceByKey((x, y) => x + y)

    val wordCountsSorted = wordCounts.map(x => (x._2, x._1)).sortByKey()

    for (result <- wordCountsSorted) {
      val count = result._1
      val word = result._2
      println(s"$word: $count")
    }
  }
}
