#!/bin/bash

HADOOP_CLASSPATH=/home/dinesh/Documents/dissertation/sourcecode/recordLinkageMapreduce/*
export HADOOP_CLASSPATH

mvn clean install

echo "Removing the output dir"
hdfs dfs -rm -r /er_data/output/samplerun
hadoop jar target/entityresolution-0.0.1-SNAPSHOT.jar org.dinesh.entityresolution.EntityResolver -conf src/main/resource/recordLinkageConf.xml

echo "display the output dir"
hdfs dfs -ls /er_data/output/samplerun
echo "cat the contents"
hdfs dfs -cat /er_data/output/samplerun/part-r-00000
