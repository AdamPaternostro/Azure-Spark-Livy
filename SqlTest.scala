package com.adampaternostro.spark.example

import org.apache.spark.sql.SparkSession

object SqlTest {
  def main (arg: Array[String]): Unit = {

    // https://databricks.com/blog/2016/08/15/how-to-use-sparksession-in-apache-spark-2-0.html
    val spark  = SparkSession
      .builder()
      .appName("SqlTest")
      // .config("spark.sql.warehouse.dir", warehouseLocation)
      // "file:/usr/hdp/2.6.0.2-76/spark2/bin/spark-warehouse"
      .config("hive.metastore.warehouse.dir", arg(0))
      .config("spark.sql.catalogImplementation", "hive")
      .getOrCreate()

    // "SELECT * FROM hivesampletable LIMIT 100"
    val sql = spark.sql(arg(1))

    // adl://sampleazuredatalakestore.azuredatalakestore.net/livy/output/SqlTestOut
    sql.rdd.saveAsTextFile(arg(2))

    spark.stop()
  }
}