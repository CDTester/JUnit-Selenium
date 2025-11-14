package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class driverTest extends BaseTest {
  Urls url = new Urls(BaseTest.config, "selenium");
  protected final String URL = url.base;


  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
  }

  @AfterEach
  public void tearDown(TestInfo testInfo) {
    // Check if test failed
    if (testInfo.getTags().contains("failed")) {
      String text =  testInfo.getDisplayName().replace(" ", "_").replace("'", "");
      takeScreenshot(driver, text).join();
      driver.quit();
    }
    else {
      driver.quit();
    }
  }

  @Test
  @Tag("failed")
  @Tag("driver")
  @Tag("smoke")
  @DisplayName("Browser title should be 'Selenium'")
  public void getTitle(TestReporter testReporter) {
    try {
      driver.get(URL);
      String title = driver.getTitle();
      testReporter.publishEntry("Page Title", title);
      Assertions.assertEquals("Selenium", title, "Page title is not as expected");
    }
    catch (Exception e) {
      System.out.println("Exception occurred: " + e.getMessage());
    }
  }

  @DisplayName("Should have current URL of :")
  @ParameterizedTest(name = "{0}")
  @ValueSource(strings = { "https://www.selenium.dev/", "https://docs.junit.org/current/user-guide/" })
  @Tag("driver")
  @Tag("smoke")
  public void getCurrentUrl(String UrlParam, TestReporter testReporter) {
    try {
      driver.get(UrlParam);
      String url = driver.getCurrentUrl();
      testReporter.publishEntry("Current URL", url);
      Assertions.assertEquals(UrlParam, url, "Current URL is not as expected");
    }
    catch (Exception e) {
      System.out.println("Exception occurred: " + e.getMessage());
    }
  }
}