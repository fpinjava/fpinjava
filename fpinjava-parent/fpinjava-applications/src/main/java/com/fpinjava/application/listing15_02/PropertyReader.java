package com.fpinjava.application.listing15_02;


import com.fpinjava.common.Result;
import java.io.InputStream;
import java.util.Properties;


public class PropertyReader {

  private final Result<Properties> properties;

  public PropertyReader(String configFileName) {
    this.properties = readProperties(configFileName);
  }

  private Result<Properties> readProperties(String configFileName) {
    try (InputStream inputStream = getClass().getClassLoader()
        .getResourceAsStream(configFileName)) {
      Properties properties = new Properties();
      properties.load(inputStream);
      return Result.of(properties);
    } catch (Exception e) {
      return Result.failure(e);
    }
  }
}
