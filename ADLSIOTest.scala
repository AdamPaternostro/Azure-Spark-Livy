package com.adampaternostro.spark.example

import org.apache.spark.sql.SparkSession

object ADLSIOTest {
  def main (arg: Array[String]): Unit = {
    val spark  = SparkSession
      .builder()
      .appName("ADLSIOTest")
      .getOrCreate()

    // "adl://sampleazuredatalakestore.azuredatalakestore.net/livy/input/HVAC.csv"
    val rdd = spark.sparkContext.textFile(arg(0))

    //find the rows which have only one digit in the 7th column in the CSV
    val rdd1 = rdd.filter(s => s.split(",")(6).length() == 1)

    // "adl://sampleazuredatalakestore.azuredatalakestore.net/livy/output/ADLSIOTest"
    rdd1.saveAsTextFile(arg(1))

    // new
    spark.stop()
  }
}