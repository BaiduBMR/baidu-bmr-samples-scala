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

import java.util.Properties
import org.apache.commons.logging.LogFactory
import java.io.IOException

private[this] class PropertyLoader {
  private val logger = LogFactory.getLog(classOf[PropertyLoader])

  def loadProperties(): Properties = {
    val properties = new Properties()
    val urlOpt = Option(classOf[PropertyLoader].getClassLoader().getResource(COMMON_CONFIG_FILE_NAME))
    urlOpt match {
      case Some(url) => try {
        properties.load(url.openStream())
      } catch {
        case ex: IOException => logger.error("No common.properties found!")
      }
      case None => logger.error("No common.properties found!")
    }
    properties
  }
}

private[config] object PropertyLoader extends PropertyLoader {
  private val props = loadProperties()
  def loadProp(name: String): String = this.props.getProperty(name)
}
