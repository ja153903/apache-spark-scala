package com.sundogsoftware.spark

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.log4j._

object FriendsByAge {
  def parseLine(line: String): (Int, Int) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val numFriends = fields(3).toInt
    (age, numFriends) // return value
  }

  def main(args: Array[String]): Unit = {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Create a spark context using every core of the local machine named FriendsByAge
    val sc = new SparkContext("local[*]", "FriendsByAge")

    // Load up each line of the ratings data into an RDD
    val lines = sc.textFile("../ApacheSparkScala/fakefriends.csv")

    val rdd = lines.map(parseLine)

    // map and reduce
    val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

    val averageByAge = totalsByAge.mapValues(x => x._1 / x._2)

    val results = averageByAge.collect()

    results.sorted.foreach(println)
  }
}
