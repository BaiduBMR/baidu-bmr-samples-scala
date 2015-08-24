/**
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.baidu.bce.bmr.spark

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.hbase.client.{ Result, Scan }
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.protobuf.ProtobufUtil
import org.apache.hadoop.hbase.util.{ Base64, Bytes }
import org.apache.spark.{ SparkConf, SparkContext }

import com.baidu.bce.bmr.spark.config.ConfigProvider
import com.baidu.bce.bmr.spark.config.ConfigProvider.HBASE_SAMPLETABLE_CF_NAME
import com.baidu.bce.bmr.spark.config.ConfigProvider.HBASE_SAMPLETABLE_NAME

/** load data from hbase table sample */
object HBaseRDDSample {
  private val logger = LogFactory.getLog(this.getClass())

  private val config = ConfigProvider
  private val Batch = 20000
  private val Caching = 10000

  private val sc = {
    val sparkConf = new SparkConf(true).setAppName("HBaseTest")
    new SparkContext(sparkConf)
  }

  private def convertScanToString(scan: Scan) = {
    val proto = ProtobufUtil.toScan(scan)
    Base64.encodeBytes(proto.toByteArray())
  }

  def main(args: Array[String]) {

    logger.info("scan table:$TableName by Family 't'")
    val start = System.currentTimeMillis()

    val scan = new Scan()
    scan.setBatch(Batch)
    scan.setCaching(Caching)
    scan.addFamily(Bytes.toBytes(HBASE_SAMPLETABLE_CF_NAME))

    val conf = config.getHBaseConfig()
    conf.set(TableInputFormat.INPUT_TABLE, HBASE_SAMPLETABLE_NAME)
    conf.set(TableInputFormat.SCAN, convertScanToString(scan))

    // build rdd and get the count
    val count = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat],
      classOf[org.apache.hadoop.hbase.io.ImmutableBytesWritable],
      classOf[org.apache.hadoop.hbase.client.Result]).count

    val end = System.currentTimeMillis()
    logger.info(s"Total count: $count, total time: ${(end - start) / 1000} seconds")

    sc.stop()
  }
}
