# recordLinkageMapreduce

A Record Linkage / Entity Resolution Framework in hadoop mapreduce

















# Steps to add Hadoop Dependency

Step by step :

* Add cloudera your settings.xml (under ${HOME}/.m2/settings.xml) to access hadoop dependencies
```
<repository>
     <id>cloudera</id>
     <url>https://repository.cloudera.com/artifactory/cloudera-repos</url>
     <releases>
          <enabled>true</enabled>
     </releases>
     <snapshots>
          <enabled>true</enabled>
     </snapshots>
</repository>
```
* Add hadoop dependencies to your pom.xml .
```
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-hdfs</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-auth</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-common</artifactId>
    <version>2.6.0</version>
</dependency>
<dependency>
    <groupId>org.apache.hadoop</groupId>
    <artifactId>hadoop-core</artifactId>
    <version>1.2.1</version>
</dependency>
```
* Then try to "mvn clean install" command into project folder that contains pom.xml file
