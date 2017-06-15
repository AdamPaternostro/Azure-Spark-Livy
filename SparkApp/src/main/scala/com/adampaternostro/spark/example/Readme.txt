https://github.com/cloudera/livy#post-batches

** Create a file called input.txt **
{ "args":
[
"adl://sampleazuredatalakestore.azuredatalakestore.net/livy/input/HVAC.csv",
"adl://sampleazuredatalakestore.azuredatalakestore.net/livy/output/ADLSIOTest"
],
"file":"adl://sampleazuredatalakestore.azuredatalakestore.net/livy/code/SparkApp.jar",
"className":"com.adampaternostro.spark.example.ADLSIOTest" }



** Run the Livy / Spark job **
** Delete your HVACout in data lake **
curl -k --user "admin:#MyHadoopPa55word#" -v -H "Content-Type: application/json" -X POST --data @SparkApp1.txt "https://adampaternostro.azurehdinsight.net/livy/batches"

** Run this over and over to get your status **
curl -k --user "admin:#MyHadoopPa55word#" -v -X GET "https://adampaternostro.azurehdinsight.net/livy/batches/0"

** Delete the Livy batch **
curl -k --user "admin:#MyHadoopPa55word#" -v -X DELETE "https://adampaternostro.azurehdinsight.net/livy/batches/0"


** Create a file called input.txt **
{ "args":
[
"file:/usr/hdp/2.6.0.2-76/spark2/bin/spark-warehouse",
"SELECT * FROM hivesampletable LIMIT 100",
"adl://sampleazuredatalakestore.azuredatalakestore.net/livy/output/SqlTestOut"
],
"file":"adl://sampleazuredatalakestore.azuredatalakestore.net/livy/code/SparkApp.jar",
"className":"com.adampaternostro.spark.example.SqlTest" }

** Run the Livy / Spark job **
** Delete your SqlTestOut in data lake **
curl -k --user "admin:#MyHadoopPa55word#" -v -H "Content-Type: application/json" -X POST --data @SparkApp2.txt "https://adampaternostro.azurehdinsight.net/livy/batches"

** Run this over and over to get your status **
curl -k --user "admin:#MyHadoopPa55word#" -v -X GET "https://adampaternostro.azurehdinsight.net/livy/batches/0"

** Delete the Livy batch **
curl -k --user "admin:#MyHadoopPa55word#" -v -X DELETE "https://adampaternostro.azurehdinsight.net/livy/batches/0"
