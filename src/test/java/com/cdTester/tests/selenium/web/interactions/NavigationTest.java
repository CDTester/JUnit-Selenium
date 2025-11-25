package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.Menu;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Epic: Working with page navigation")
@Feature("Feature: Navigation Tests")
@Tag("navigation")
@ExtendWith(TestResultListener.class)
public class NavigationTest extends BaseTest{
  protected Urls url;

  @BeforeEach
  public void createSession() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    url = new Urls(BaseTest.config, "selenium");
  }

  @Test
  @Tag("smoke")
  @Story("Story: driver")
  @TmsLink("TC-111")
  @DisplayName("Should be able to navigate to a URL using the driver.get() method")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1111")
  public void navigateBrowserGet() {
    Allure.step("WHEN the web page has loaded using driver.get() method", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
    });

    Allure.step("THEN the page title should be 'Selenium'", step -> {
      step.parameter("Title", driver.getTitle());
      assertEquals("Selenium", driver.getTitle());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: navigate")
  @TmsLink("TC-112")
  @DisplayName("Should be able to navigate to a URL using the navigate().to() method")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1112")
  public void navigateBrowserTo() {
    Allure.step("WHEN the web page has loaded using navigate().to() method", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
    });

    Allure.step("THEN the page title should be 'Selenium'", step -> {
      step.parameter("Title", driver.getTitle());
      assertEquals("Selenium", driver.getTitle());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: navigate")
  @TmsLink("TC-113")
  @DisplayName("Should be able to navigate back using the navigate().back() method")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1113")
  public void navigateBrowserBack() {
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
      step.parameter("Title", driver.getTitle());
    });

    Allure.step("AND the page is navigated to a new URL", step -> {
      Menu menu = new Menu(driver);
      step.parameter("Navigate using menu link", menu.documentationLink.getText());
      menu.clickMenuLink(menu.documentationLink);
      step.parameter("Title", driver.getTitle());
      assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());
    });

    Allure.step("WHEN the navigate().back() is used", step -> {
      step.parameter("Navigate", "driver.navigate().back();");
      driver.navigate().back();
    });

    Allure.step("THEN the page title should be 'Selenium'", step -> {
      step.parameter("Title", driver.getTitle());
      assertEquals("Selenium", driver.getTitle());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: navigate")
  @TmsLink("TC-114")
  @DisplayName("Should be able to navigate forward using the navigate().forward() method")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1114")
  public void navigateBrowserForward () {
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
      step.parameter("Title", driver.getTitle());
    });

    Allure.step("AND the page is navigated to a new URL", step -> {
      Menu menu = new Menu(driver);
      step.parameter("Navigate using menu link", menu.documentationLink.getText());
      menu.clickMenuLink(menu.documentationLink);
      step.parameter("Title", driver.getTitle());
      assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());
    });

    Allure.step("AND the navigate().back() is used", step -> {
      step.parameter("Navigate", "driver.navigate().back();");
      driver.navigate().back();
      step.parameter("Title", driver.getTitle());
      assertEquals("Selenium", driver.getTitle());
    });

    Allure.step("WHEN the navigate().forward() is used", step -> {
      step.parameter("Navigate", "driver.navigate().forward();");
      driver.navigate().forward();
    });

    Allure.step("THEN the page title should be 'The Selenium Browser Automation Project | Selenium'", step -> {
      step.parameter("Title", driver.getTitle());
      assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: navigate")
  @TmsLink("TC-115")
  @DisplayName("Should be refresh a page using the navigate().refresh() method")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1115")
  public void navigateBrowserRefresh() {
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
      step.parameter("Title", driver.getTitle());
    });

    Allure.step("WHEN the navigate().refresh() is used", step -> {
      step.parameter("Navigate", "driver.navigate().refresh();");
      driver.navigate().refresh();
    });

    Allure.step("THEN the page title should be 'Selenium'", step -> {
      step.parameter("Title", driver.getTitle());
      assertEquals("Selenium", driver.getTitle());
    });
 }
}