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

package com.baidu.bce.bmr.spark.config

import org.apache.commons.logging.LogFactory
import org.apache.hadoop.conf.Configuration

class ConfigProvider {
  private val LOG = LogFactory.getLog(classOf[ConfigProvider])
  private val propLoader = PropertyLoader

  val HBASE_SAMPLETABLE_NAME = propLoader.loadProp(HBASE_SAMPLETABLE_NAME_KEY)
  val HBASE_SAMPLETABLE_CF_NAME = propLoader.loadProp(HBASE_SAMPLETABLE_CF_NAME_KEY)

  def getHBaseConfig(): Configuration = {
    LOG.info("Load hadoop,hbase configuration.")
    val conf = new Configuration()
    conf.set(HBASE_ZK_CLIENT_PORT_KEY, propLoader.loadProp(HBASE_ZK_CLIENT_PORT_KEY))
    conf.set(HBASE_ZK_QUORUM_KEY, propLoader.loadProp(HBASE_ZK_QUORUM_KEY))
    conf.set(HBASE_ZK_ZNODE_PARENT_KEY, propLoader.loadProp(HBASE_ZK_ZNODE_PARENT_KEY))
    conf.set(HBASE_ROOT_DIR_KEY, propLoader.loadProp(HBASE_ROOT_DIR_KEY))
    conf
  }
}

object ConfigProvider extends ConfigProvider
