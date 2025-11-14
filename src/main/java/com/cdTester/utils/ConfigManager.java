package com.cdTester.utils;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigManager {
  private static ConfigManager instance;
  private Properties properties;

  private ConfigManager() {
    loadProperties();
  }

  public static ConfigManager getInstance() {
    if (instance == null) {
      instance = new ConfigManager();
    }
    return instance;
  }

  private void loadProperties() {
    properties = new Properties();
    try {
      String env = System.getProperty("env", "dev");
      String configFile = "src/test/resources/config/" + env + ".properties";
      System.out.println("Loading configuration for environment: " + env);
      FileInputStream fis = new FileInputStream(configFile);
      properties.load(fis);
      fis.close();
    } catch (IOException e) {
      throw new RuntimeException("Failed to load configuration: " + e.getMessage());
    }
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

  public String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  public String getEnv() {
    return getProperty("environment") == null ? "": getProperty("environment");
  }

  public String getBrowser() {
    return getProperty("browser");
  }

  public String getBaseUrl(String site) {
    String url = getProperty(site + ".base.url");
    if (url == null) {
      throw new RuntimeException("Base URL not found for: " + site);
    }
    return url;
  }

  // Get environment name
  public String getEnvironment() {
    return getProperty("environment", "unknown");
  }

}