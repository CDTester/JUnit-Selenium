package com.cdTester.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Highlight {

  /**
   * Highlights a web element by adding a red border around it and scrolling it into view.
   * @param driver The WebDriver instance.
   * @param element The WebElement to be highlighted.
   * @throws InterruptedException If the thread is interrupted while sleeping (default 500ms).
   */
  public static void highlightElement(WebDriver driver, WebElement element) throws InterruptedException {
    highlightElement(driver, element, 5000);
  }

  /**
   * Highlights a web element by adding a red border around it and scrolling it into view.
   * @param driver The WebDriver instance.
   * @param element The WebElement to be highlighted.
   * @param sleep The time in milliseconds to keep the highlight before proceeding.
   * @throws InterruptedException If the thread is interrupted while sleeping.
   */
  public static void highlightElement(WebDriver driver, WebElement element, int sleep) throws InterruptedException {
    var jsDriver = (JavascriptExecutor) driver;
    jsDriver.executeScript(
            "arguments[0].scrollIntoView(true);" +
                    "arguments[0].style.border='3px solid gold';",
            element
    );
    Thread.sleep(sleep);
  }

  /**
   * Highlights a web element by adding a red border around it and scrolling it into view.
   * @param driver The WebDriver instance.
   * @param element The WebElement to be highlighted.
   */
  public static void unhighlightElement(WebDriver driver, WebElement element) {
    var jsDriver = (JavascriptExecutor) driver;
    jsDriver.executeScript(
            "arguments[0].style.border=''",
            element
    );
  }


}