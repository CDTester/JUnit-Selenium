package com.cdTester.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
  private static Properties properties;
  private static String environment;

  static {
    // Get environment from system property or default to "dev"
    environment = System.getProperty("env", "dev");
    loadProperties();
  }

  private static void loadProperties() {
    properties = new Properties();
    String configFile = "src/test/resources/config/" + environment + ".properties";

    try (FileInputStream fis = new FileInputStream(configFile)) {
      properties.load(fis);
      System.out.println("Loaded configuration for environment: " + environment);
    }
    catch (IOException e) {
      throw new RuntimeException("Failed to load config file: " + configFile, e);
    }
  }

//  public static String getProperty(String key) {
//    return properties.getProperty(key);
//  }

  /**
   * getBaseUrl   gets the base URL address for the current environment
   * @return      String value of the base URL address
   */
  public static String getEnv() {
    return properties.getProperty("environment");
  }

  public static String getBaseUrl() {
    return properties.getProperty("base.url");
  }

  public static String getUsername() {
    return properties.getProperty("username");
  }

  public static String getPassword() {
    return properties.getProperty("password");
  }

  public static String getBrowser() {
    return properties.getProperty("browser");
  }

}