import org.apache.log4j.{Level, Logger}
import org.apache.spark._

object MostPopularSuperHero {
  // we want to return an option when it's possible that we would be returning None
  def parseNames(line: String): Option[(Int, String)] = {
    var fields = line.split('\"')
    if (fields.length > 1) {
      return Some(fields(0).trim().toInt, fields(1))
    } else {
      return None
    }
  }

  def countCoOccurences(line: String) = {
    var elements = line.split("\\s+")
    (elements(0).toInt, elements.length - 1)
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)

    val conf = new SparkConf().setMaster("local").setAppName("MostPopularHero")
    val sc = new SparkContext(conf)

    val names = sc.textFile("../ApacheSparkScala/Marvel-names.txt")
    val namesRdd = names.flatMap(parseNames)

    val lines = sc.textFile("../ApacheSparkScala/Marvel-graph.txt")

    val pairings = lines.map(countCoOccurences)

    val totalFriendsByCharacter = pairings.reduceByKey((x, y) => x + y)

    val flipped = totalFriendsByCharacter.map(x => (x._2, x._1))

    val mostPopular = flipped.max()

    val mostPopularName = namesRdd.lookup(mostPopular._2)(0)

    println(s"$mostPopularName is the most popular superhero with ${mostPopular._1} co-appearances")
  }

}
