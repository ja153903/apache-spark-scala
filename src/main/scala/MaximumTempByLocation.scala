import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object MaximumTempByLocation {
  def parseLine(line: String) = {
    val fields = line.split(",")
    val location = fields(0)
    val typeTemp = fields(2)
    val temp = fields(3).toFloat * 0.1f * (9.0f / 5.0f) + 32.0f
    (location, typeTemp, temp)
  }

  def main(args: Array[String]): Unit = {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a spark context using every core of the local machine named FriendsByAge
    val sc = new SparkContext("local[*]", "MaxTempByLocation")

    // Load up each line of the ratings data into an RDD
    val lines = sc.textFile("../ApacheSparkScala/1800.csv")

    val rdd = lines.map(parseLine)

    val maxTemps = rdd.filter(x => x._2.equals("TMAX"))

    val stationTemps = maxTemps.map(x => (x._1, x._3.toFloat))

    val maxTempByLoc = stationTemps.reduceByKey((x, y) => List(x, y).max)

    val result = maxTempByLoc.collect()

    result.sorted.foreach(println)



  }
}
