import java.nio.charset.CodingErrorAction

import org.apache.log4j.{Level, Logger}
import org.apache.spark._

import scala.io.{Codec, Source}

object MostPopularMovieBetter {
  def loadMovieNames(): Map[Int, String] = {
    // Handle character encoding issues
    implicit val codec = Codec("UTF-8")
    codec.onMalformedInput(CodingErrorAction.REPLACE)
    codec.onUnmappableCharacter(CodingErrorAction.REPLACE)

    var movieNames: Map[Int, String] = Map()

    val lines = Source.fromFile("../ApacheSparkScala/ml-100k/u.item").getLines()
    for (line <- lines) {
      var fields = line.split('|')
      if (fields.length > 1) {
        movieNames += (fields(0).toInt -> fields(1))
      }
    }

    movieNames
  }

  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("MostPopMovie")
    val sc = new SparkContext(conf)

    // Create a broadcast variables of our ID -> movie name map
    var nameDict = sc.broadcast(loadMovieNames)

    // Read in each rating line
    val lines = sc.textFile("../ApacheSparkScala/ml-100k/u.data")

    // Map to (movieId, 1) tuples
    val movies = lines.map(x => (x.split("\t")(1).toInt, 1))

    val movieCounts = movies.reduceByKey((x, y) => x + y)

    val flipped = movieCounts.map(x => (x._2, x._1))

    val sortedMovies = flipped.sortByKey()


    // Fold in the movie names from the broadcast variable
    val sortedMoviesWithNames = sortedMovies.map(x => (nameDict.value(x._2), x._1))

    val result = sortedMoviesWithNames.collect()

    result.foreach(println)

  }
}
