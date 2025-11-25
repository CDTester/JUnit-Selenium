package com.cdTester.utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;
import java.util.Optional;

public class TestResultListener implements TestWatcher {

  @Override
  public void testSuccessful(ExtensionContext context) {
    System.out.println("✅ Test PASSED: " + context.getDisplayName());
    WebDriver driver = getDriver(context);
    if (driver != null) {
      driver.quit();
    }
  }

  @Override
  public void testFailed(ExtensionContext context, Throwable cause) {
    System.out.println("❌ Test FAILED: " + context.getDisplayName());
    System.out.println("Reason: " + cause.getMessage());

    // Get driver and take screenshot
    WebDriver driver = getDriver(context);
    if (driver != null) {
      takeScreenshot(driver, "\uD83D\uDCF8 Failure Screenshot");
      driver.quit();
    }
  }

  @Override
  public void testAborted(ExtensionContext context, Throwable cause) {
    System.out.println("⊘ Test ABORTED: " + context.getDisplayName());
    WebDriver driver = getDriver(context);
    if (driver != null) {
      System.out.println("quiting browser in baseTest");
      driver.quit();
    }
  }

  @Override
  public void testDisabled(ExtensionContext context, Optional<String> reason) {
    System.out.println("⊗ Test DISABLED: " + context.getDisplayName());
    reason.ifPresent(r -> System.out.println("Reason: " + r));
    WebDriver driver = getDriver(context);
    if (driver != null) {
      driver.quit();
    }
  }

  private void takeScreenshot(WebDriver driver, String name) {
    try {
      System.out.println("\uD83D\uDCF8 Taking screenshot for failed test...");
      byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
      Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
    } catch (Exception e) {
      System.err.println("Failed to take screenshot: " + e.getMessage());
      driver.quit();
    }
  }

  private WebDriver getDriver(ExtensionContext context) {
    try {
      Object testInstance = context.getRequiredTestInstance();
      java.lang.reflect.Field field = testInstance.getClass().getSuperclass().getDeclaredField("driver");
      field.setAccessible(true);
      return (WebDriver) field.get(testInstance);
    } catch (Exception e) {
      return null;
    }
  }
}
