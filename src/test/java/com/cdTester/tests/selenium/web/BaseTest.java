package com.cdTester.tests.selenium.web;

import java.io.*;
import java.time.Duration;
import java.util.HashMap;
import com.cdTester.utils.ConfigManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseTest {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected static String browser;
  protected String trustStorePassword = "seleniumkeystore";
  protected static ConfigManager config;

  @BeforeAll
  protected static void setUpSuite() {
    File outputDir = new File("test-output");
    if (!outputDir.exists()) {
      outputDir.mkdirs();
    }
    config = ConfigManager.getInstance();
    browser = (config.getBrowser() != null) ? config.getBrowser() : "chrome";
    setEnvironmentInfo();
  }

  @BeforeEach
  protected void allureBeforeEachReporter() {
    Allure.parameter("Browser", System.getProperty("browser", "chrome"));
  }

  public static void setEnvironmentInfo() {
    String resultsDir = "target/allure-results";

    // Create directory if it doesn't exist
    File directory = new File(resultsDir);
    if (!directory.exists()) {
      directory.mkdirs();
    }

    // Write environment.properties (this shows in the report)
    try (PrintWriter writer = new PrintWriter(new FileWriter(resultsDir + "/environment.properties"))) {
      writer.println("Environment=" + System.getProperty("env", "qa"));
      writer.println("Browser=" + System.getProperty("browser", "chrome"));
      writer.println("Headless=" + System.getProperty("headless", "false"));
      writer.println("Java.Version=" + System.getProperty("java.version"));
      writer.println("OS=" + System.getProperty("os.name") + " " + System.getProperty("os.version"));
      writer.println("User=" + System.getProperty("user.name"));
      writer.println("Test.Date=" + java.time.LocalDateTime.now().toString());
    } catch (IOException e) {
      System.err.println("Failed to write environment.properties: " + e.getMessage());
    }
  }

//  @AfterEach
//  public void quit() {
//    System.out.println("BaseTest: AfterEach");
//    if (driver != null) {
//      System.out.println("quiting browser in baseTest");
//      driver.quit();
//    }
//  }

  protected WebDriver startBrowserDriver() {
    if (browser.equalsIgnoreCase("firefox")) {
      return startFirefoxDriver();
    }
    else {
      return startChromeDriver(1);
    }
  }

  protected FirefoxDriver startFirefoxDriver() {
    return startFirefoxDriver(new FirefoxOptions());
  }

  protected FirefoxDriver startFirefoxDriver(FirefoxOptions options) {
    options.setImplicitWaitTimeout(Duration.ofSeconds(1));
    driver = new FirefoxDriver(options);
    return (FirefoxDriver) driver;
  }

//  protected ChromeDriver startChromeDriver() {
//    return startChromeDriver(1);
//  }

  protected ChromeDriver startChromeDriver(int waitInSeconds) {
    ChromeOptions options = Allure.step("ChromeOptions", step -> {
      ChromeOptions option = new ChromeOptions();
      option.setImplicitWaitTimeout(Duration.ofSeconds(waitInSeconds));
      option.addArguments("disable-search-engine-choice-screen");
      if (config.getEnv().equals("prod")) {
        option.addArguments("--headless=new");
      }
      step.parameter("Options", option.toString());
      return option;
    });
    return startChromeDriver(options);
  }

  protected ChromeDriver startChromeDriver(int waitInSeconds, String[] arguments, HashMap<String, Object> capabilities) {
    ChromeOptions options = Allure.step("ChromeOptions", step -> {
      ChromeOptions option = new ChromeOptions();
      option.setImplicitWaitTimeout(Duration.ofSeconds(waitInSeconds));
      for (String args : arguments ) {
        option.addArguments(args);
      }
      for (String opt : capabilities.keySet()) {
        option.setCapability(opt, capabilities.get(opt));
      }
      if (config.getEnv().equals("prod")) {
        option.addArguments("--headless=new");
      }
      step.parameter("Options", option.toString());
      return option;
    });
    return startChromeDriver(options);
  }

  protected ChromeDriver startChromeDriver(ChromeOptions options) {
    Allure.step("Starting ChromeDriver", step -> {
      driver = new ChromeDriver(options);
      driver.manage().window().maximize();
      step.parameter("Driver", driver.toString());
    });
    return (ChromeDriver) driver;
  }

//  protected static ChromeOptions getDefaultChromeOptions() {
//    ChromeOptions options = new ChromeOptions();
//    options.addArguments("--no-sandbox");
//    return options;
//  }

//  protected static EdgeOptions getDefaultEdgeOptions() {
//    EdgeOptions options = new EdgeOptions();
//    options.addArguments("--no-sandbox");
//    return options;
//  }


//  protected URL startStandaloneGrid() {
//    int port = PortProber.findFreePort();
//    try {
//      Main.main(
//              new String[] {
//                      "standalone",
//                      "--port",
//                      String.valueOf(port),
//                      "--selenium-manager",
//                      "true",
//                      "--enable-managed-downloads",
//                      "true",
//                      "--log-level",
//                      "WARNING"
//              });
//      return new URL("http://localhost:" + port);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }

//  protected URL startStandaloneGridAdvanced() {
//    int port = PortProber.findFreePort();
//    try {
//      System.setProperty("javax.net.ssl.trustStore", Path.of("src/test/resources/server.jks").toAbsolutePath().toString());
//      System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
//      System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
//      Main.main(
//              new String[] {
//                      "standalone",
//                      "--port",
//                      String.valueOf(port),
//                      "--selenium-manager",
//                      "true",
//                      "--enable-managed-downloads",
//                      "true",
//                      "--log-level",
//                      "WARNING",
//                      "--username",
//                      username,
//                      "--password",
//                      password,
//                      "--https-certificate",
//                      Path.of("src/test/resources/tls.crt").toAbsolutePath().toString(),
//                      "--https-private-key",
//                      Path.of("src/test/resources/tls.key").toAbsolutePath().toString()
//              });
//      return new URL("https://localhost:" + port);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }

//  protected void takeScreenshotAllure(String name) {
//    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
//  }


//  protected CompletableFuture<String> takeScreenshot(WebDriver driver, String fileName) {
//    return CompletableFuture.supplyAsync(() -> {
//      try {
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        TakesScreenshot screenshot = (TakesScreenshot) driver;
//        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
//        File destFile = new File("./" + fileName + "_" + timestamp + ".png");
//        FileHandler.copy(sourceFile, destFile);
//        System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
//      }
//      catch (IOException e) {
//        System.out.println("Failed to save screenshot: " + e.getMessage());
//      }
//      return "ScreenshotCompleted";
//    });
//  }
//
//  protected CompletableFuture<String> takeScreenshotElement(WebElement element) {
//    return CompletableFuture.supplyAsync(() -> {
//      String img = null;
//      try {
//        img = element.getScreenshotAs(OutputType.BASE64);
//        File sourceFile = element.getScreenshotAs(OutputType.FILE);
//        File destFile = new File(element.toString() + "_screenshot.png");
//        FileHandler.copy(sourceFile, destFile);
//      }
//      catch (IOException e) {
//        System.out.println("Failed to save screenshot: " + e.getMessage());
//      }
//      return img;
//    });
//  }



}