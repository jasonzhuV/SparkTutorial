package com.tutorial.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Example_001_WordCount {
  def main(args: Array[String]): Unit = {
    // 创建spark运行配置对象
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCount")
    // 创建spark上下文环境对象 （连接对象）
    val sc: SparkContext = new SparkContext(sparkConf)
    // 读取文件数据
    val fileRDD: RDD[String] = sc.textFile("data/word.txt")
    // 将文件中的数据进行分词
    val wordRDD: RDD[String] = fileRDD.flatMap(_.split(" "))
    // 转换数据结构 word => (word, 1)
    val word2OneRDD: RDD[(String, Int)] = wordRDD.map((_, 1))
    // 分组 聚合
    val word2CountRDD: RDD[(String, Int)] = word2OneRDD.reduceByKey(_ + _)
    // 将数据聚合结果采集到内存里
    val word2Count: Array[(String, Int)] = word2CountRDD.collect()
    // 打印结果
    word2Count.foreach(println)
    // 关闭spark连接
    sc.stop()
  }
}
