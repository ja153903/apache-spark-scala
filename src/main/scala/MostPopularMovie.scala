import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object MostPopularMovie {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("MostPopularMovie")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("../ApacheSparkScala/ml-100k/u.data")

    // Map to (movieId, 1) tuples
    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    // Count up all the 1's for each movie
    val movieCounts = movies.reduceByKey((x, y) => x + y)

    // Flip (movieId, count) to (count, movieId)
    val flipped = movieCounts.map(x => (x._2, x._1))

    // sort
    val sortedMovies = flipped.sortByKey()

    // Collect and print results
    val results = sortedMovies.collect()

    results.foreach(println)

  }
}
