package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.Menu;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigationTest extends BaseTest{
  protected Urls url;

  @BeforeEach
  public void createSession() {
    driver = startChromeDriver(1);
    url = new Urls(BaseTest.config, "selenium");
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @Tag("navigation")
  @DisplayName("Should be able to navigate to a URL using the driver.get() method")
  public void navigateBrowserGet() {
    //Convenient
    driver.get(url.base);
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @Tag("regression")
  @Tag("navigation")
  @DisplayName("Should be able to navigate to a URL using the navigate().to() method")
  public void navigateBrowserTo() {
    //Longer way
    driver.navigate().to(url.base);
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @Tag("regression")
  @Tag("navigation")
  @DisplayName("Should be able to navigate back")
  public void navigateBrowserBack() {
    driver.get(url.base);
    assertEquals("Selenium", driver.getTitle());

    Menu menu = new Menu(driver);
    menu.clickMenuLink(menu.documentationLink);
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());

    //Back
    driver.navigate().back();
    assertEquals("Selenium", driver.getTitle());
  }

  @Test
  @Tag("regression")
  @Tag("navigation")
  @DisplayName("Should be able to navigate forward")
  public void navigateBrowserForward () {
    driver.get(url.base);
    assertEquals("Selenium", driver.getTitle());

    Menu menu = new Menu(driver);
    menu.clickMenuLink(menu.documentationLink);
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());

    //Back
    driver.navigate().back();
    assertEquals("Selenium", driver.getTitle());

    //Forward
    driver.navigate().forward();
    assertEquals("The Selenium Browser Automation Project | Selenium", driver.getTitle());
  }

  @Test
  @Tag("regression")
  @Tag("navigation")
  @DisplayName("Should be able to get text from alert and accept it")
  public void navigateBrowserRefresh() {
    driver.get(url.base);
    assertEquals("Selenium", driver.getTitle());

    //Refresh
    driver.navigate().refresh();
  }
}