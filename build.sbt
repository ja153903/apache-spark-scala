name := "ApacheSparkScala"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= {
  val sparkVer = "2.1.0"
  Seq(
    "org.apache.spark" %% "spark-core" % sparkVer
  )
}