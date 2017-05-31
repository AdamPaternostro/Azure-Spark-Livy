# Azure-Spark-Livy
Run a job in Spark 2.x with HDInsight and submit the job through Livy

## Steps to run this script:
1 - Create a Azure Data Lake Storage account
- Create a root folder called "livy"
- Create a folder under livy called "code" and upload the SparkApp.jar inside of the folder
- Create a folder under livy called "input" and upload the HVAC.csv inside of the folder
- Create a folder under livy called "output"

2 - Create a Spark cluster (Spark 2.x) that uses Data Lake as its main storage and when you create the Service Principle grant acess to the /clusters directory and the /livy directory

3 - Run a job via Livy (open a Windows Bash or Linux prompt)

## ADLS Job
Read data to/from Azure Data Lake Storage

    1 - Type "nano SparkApp1.txt" (or use VI or whatever) and place the below in the file.  Change the << >> items.
    { "args":
    [
    "adl://<<YOUR-DATA-LAKE>>.azuredatalakestore.net/livy/input/HVAC.csv",
    "adl://<<YOUR-DATA-LAKE>>.azuredatalakestore.net/livy/output/ADLSIOTest"
    ],
    "file":"adl://<<YOUR-DATA-LAKE>>.azuredatalakestore.net/livy/code/SparkApp.jar",
    "className":"com.adampaternostro.spark.example.ADLSIOTest" }

    2 - Run the job via Livy.  You need to delete your output folder if it exists (e.g. /livy/output/ADLSIOTest)
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -H "Content-Type: application/json" -X POST --data @SparkApp1.txt "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches"

    3 - Get the status.  The prior command will return a "id": ? (replace the 0 below with the ?)  You can run this over and over to see the jobs status.
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -X GET "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches/0"

    4 - Delete the batch
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -X DELETE "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches/0"

## SQL Job
Run a Spark SQL Statement using the Hive metastore

    1 - Type "nano SparkApp2.txt" (or use VI or whatever) and place the below in the file.  Change the << >> items.
    { "args":
    [
    "file:/usr/hdp/2.6.0.2-76/spark2/bin/spark-warehouse",
    "SELECT * FROM hivesampletable LIMIT 100",
    "adl://<<YOUR-DATA-LAKE>>.azuredatalakestore.net/livy/output/SqlTestOut"
    ],
    "file":"adl://<<YOUR-DATA-LAKE>>.azuredatalakestore.net/livy/code/SparkApp.jar",
    "className":"com.adampaternostro.spark.example.SqlTest" }


    2 - Run the job via Livy.  You need to delete your output folder if it exists (e.g. /livy/output/SqlTestOut)
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -H "Content-Type: application/json" -X POST --data @SparkApp2.txt "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches"

    3 - Get the status.  The prior command will return a "id": ? (replace the 0 below with the ?)  You can run this over and over to see the jobs status.
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -X GET "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches/0"

    4 - Delete the batch
    curl -k --user "admin:<<YOUR-HDI-PASSWORD>>" -v -X DELETE "<<YOUR-HDI-CLUSTERNAME>>.azurehdinsight.net/livy/batches/0"

## Notes
- Depending on your Spark version the value "file:/usr/hdp/2.6.0.2-76/spark2/bin/spark-warehouse" might change.  To get the latest value you can SSH into your HDInsight cluster.

    ssh sshuser@<<MY CLUSTER>>-ssh.azurehdinsight.net
    cd $SPARK_HOME/bin
    spark-shell
    sc.getConf.getAll.foreach(println)
    look for: (hive.metastore.warehouse.dir,file:/usr/hdp/2.6.0.2-76/spark2/bin/spark-warehouse).  This might change to "spark.sql.warehouse.dir" in the future.

