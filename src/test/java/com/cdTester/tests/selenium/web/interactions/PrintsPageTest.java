package com.cdTester.tests.selenium.web.interactions;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.PrintsPage;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;

import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintsPageTest extends BaseTest{

  @BeforeEach
  public void setup() {
    String[] args = {};
    HashMap<String, Object> capabilities = new HashMap<String, Object>();
    capabilities.put("webSocketUrl", true);
    driver = startChromeDriver(1, args, capabilities);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("regression")
  @Tag("printPage")
  @DisplayName("Should be able to print current page using PrintsPage interface")
  public void PrintWithPrintsPageTest() {
    driver.get(Urls.base);
    PrintsPage printer = (PrintsPage) driver;
    PrintOptions printOptions = new PrintOptions();
    Pdf printedPage = printer.print(printOptions);

    assertNotNull(printedPage);
  }

  @Test
  @Tag("regression")
  @Tag("printPage")
  @DisplayName("Should be able to print current page using BrowsingContext interface")
  public void PrintWithBrowsingContextTest() {
    BrowsingContext browsingContext = new BrowsingContext(driver, driver.getWindowHandle());
    driver.get(Urls.formPage);
    PrintOptions printOptions = new PrintOptions();
    String printPage = browsingContext.print(printOptions);
    assertTrue(printPage.length() > 0);
  }
}
