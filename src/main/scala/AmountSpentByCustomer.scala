
import org.apache.spark.SparkContext;
import org.apache.spark.SparkConf;

object AmountSpentByCustomer {
  def parseLines(line: String): (Int, Float) = {
    val fields = line.split(",")
    val id = fields(0).toInt
    val amount: Float = fields(2).toFloat
    (id, amount)
  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("AmountByCustomer")

    val sc = new SparkContext(conf)

    val data = sc.textFile("../ApacheSparkScala/customer-orders.csv")

    val rdd = data.map(parseLines)

    // now we want to combine all the amounts

    val amountById = rdd.reduceByKey((x, y) => x + y)

    val sortedAmountById = amountById.map(x => (x._2, x._1)).sortByKey().map(x => (x._2, x._1))

    val results = sortedAmountById.collect()

    results.foreach(println)
  }
}
