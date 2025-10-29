package com.cdTester.tests.selenium.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;

import com.cdTester.utils.ConfigReader;

public class BaseTest {
  protected WebDriver driver;
  protected WebDriverWait wait;
  protected String username = (ConfigReader.getUsername() != null) ? ConfigReader.getUsername() : "baseUser";
  protected String password = (ConfigReader.getPassword() != null) ? ConfigReader.getPassword() : "basePassword";
  protected String browser = (ConfigReader.getBrowser() != null) ? ConfigReader.getBrowser() : "chrome";
  protected String trustStorePassword = "seleniumkeystore";

//  public WebElement getLocatedElement(WebDriver driver, By by) {
//    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//    return wait.until(d -> driver.findElement(by));
//  }

  protected WebDriver startBrowserDriver() {
    if (browser.equalsIgnoreCase("firefox")) {
      return startFirefoxDriver();
    }
    else {
      return startChromeDriver();
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

  protected ChromeDriver startChromeDriver() {
    return startChromeDriver(1);
  }

  protected ChromeDriver startChromeDriver(int waitInSeconds) {
    ChromeOptions options = new ChromeOptions();
    options.setImplicitWaitTimeout(Duration.ofSeconds(waitInSeconds));
    options.addArguments("disable-search-engine-choice-screen");
    if (Objects.equals(ConfigReader.getEnv(), "prod")) {
        options.addArguments("--headless=new");
    }
    return startChromeDriver(options);
  }

  protected ChromeDriver startChromeDriver(int waitInSeconds, String[] arguments, HashMap<String, Object> capabilities) {
    ChromeOptions options = new ChromeOptions();
    options.setImplicitWaitTimeout(Duration.ofSeconds(waitInSeconds));
    options.addArguments("disable-search-engine-choice-screen");
    for (String args : arguments ) {
      options.addArguments(args);
    }
    for (String option : capabilities.keySet()) {
      options.setCapability(option, capabilities.get(option));
    }
    return startChromeDriver(options);
  }

  protected ChromeDriver startChromeDriver(ChromeOptions options) {
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
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

//  protected File getTempDirectory(String prefix) {
//    File tempDirectory = null;
//    try {
//      tempDirectory = Files.createTempDirectory(prefix).toFile();
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    tempDirectory.deleteOnExit();
//
//    return tempDirectory;
//  }

//  protected File getTempFile(String prefix, String suffix) {
//    File logLocation = null;
//    try {
//      logLocation = File.createTempFile(prefix, suffix);
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//    logLocation.deleteOnExit();
//
//    return logLocation;
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

//  protected void enableLogging() {
//    Logger logger = Logger.getLogger("");
//    logger.setLevel(Level.FINE);
//    Arrays.stream(logger.getHandlers()).forEach(handler -> {
//      handler.setLevel(Level.FINE);
//    });
//  }

  protected CompletableFuture<String> takeScreenshot(WebDriver driver, String fileName) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("./" + fileName + "_" + timestamp + ".png");
        FileHandler.copy(sourceFile, destFile);
        System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
      }
      catch (IOException e) {
        System.out.println("Failed to save screenshot: " + e.getMessage());
      }
      return "ScreenshotCompleted";
    });
  }

  protected CompletableFuture<String> takeScreenshotElement(WebElement element) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        File sourceFile = element.getScreenshotAs(OutputType.FILE);
        File destFile = new File(element.toString() + "_screenshot.png");
        FileHandler.copy(sourceFile, destFile);
      }
      catch (IOException e) {
        System.out.println("Failed to save screenshot: " + e.getMessage());
      }
      return "ElementScreenshotCompleted";
    });
  }

  @AfterEach
  public void quit() {
    if (driver != null) {
      driver.quit();
    }
  }
}