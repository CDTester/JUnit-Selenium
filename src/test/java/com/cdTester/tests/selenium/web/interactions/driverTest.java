package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;


@Epic("Epic: Working with web drivers")
@ExtendWith(TestResultListener.class)
@Tag("driver")
public class driverTest extends BaseTest {
  Urls url = new Urls(BaseTest.config, "selenium");
  protected final String URL = url.base;


  @BeforeEach
  public void createSession() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
  }

  @Test
  @Tag("smoke")
  @Feature("Feature: ChromeDriver")
  @Story("Story: Navigate to a URL")
  @TmsLink("TC-091")
  @DisplayName("Browser title should be 'Selenium'")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-197")
  public void getTitle() {
    Allure.step("WHEN a the browser is navigated to a URL", step -> {
      step.parameter("URL", "http://www.example.com");
      driver.get(URL);
    });

    Allure.step("THEN the browser title should be 'Selenium'", step -> {
      String title = driver.getTitle();
      step.parameter("Page Title", title);
      Assertions.assertEquals("Helenium", title, "Page title is not as expected");
    });
  }

  @ParameterizedTest(name = "{0}")
  @ValueSource(strings = { "https://www.selenium.dev/", "https://docs.junit.org/current/user-guide/" })
  @Tag("smoke")
  @Feature("Feature: ChromeDriver")
  @Story("Story: Navigate to a URL")
  @TmsLink("TC-091")
  @DisplayName("Parameterised test to get current URL of :")
  public void getCurrentUrl(String UrlParam) {
    Allure.step("WHEN a the browser is navigated to a URL", step -> {
      step.parameter("URL", UrlParam);
      driver.get(UrlParam);
    });

    Allure.step("THEN the current URL can be retrieved", step -> {
      String url = driver.getCurrentUrl();
      step.parameter("Current URL", url);
      Assertions.assertEquals(UrlParam, url, "Current URL is not as expected");
    });
  }
}