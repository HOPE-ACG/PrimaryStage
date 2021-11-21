package com.acg.streaming

import java.io.InputStreamReader
import java.util.Properties

/**
 * Properties文件类工具
 */
object PropertiesUtil {

  /**
   * 加载自定义Properties文件
   *
   * @param propertiesFile Properties文件名
   * @return 加载后的Properties文件
   */
  def load(propertiesFile: String): Properties = {

    val properties = new Properties()
    properties.load(
      new InputStreamReader(
        Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesFile),
        "utf-8"
      )
    )
    properties
  }
}
