package com.cdTester.tests.selenium.web.waits;

import com.cdTester.pages.selenium.web.Dynamic;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import java.time.Duration;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitsTest extends BaseTest {
  protected Dynamic dynamicPage;
  protected Urls url;

  @BeforeEach
  public void loadUrls() {
    Urls url = new Urls(BaseTest.config, "selenium");
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("regression")
  @Tag("wait")
  @DisplayName("Should fail to find box without waits")
  public void noWaits() throws InterruptedException {
    driver = startChromeDriver(0);
    driver.get(url.dynamic);
    dynamicPage = new Dynamic(driver);

    WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
    addButton.click();

    assertThrows(
      NoSuchElementException.class,
      () -> dynamicPage.highlightElement(dynamicPage.box1).isDisplayed(),
      "Box should not be displayed"
    );
  }

  @Test
  @Tag("smoke")
  @Tag("wait")
  @DisplayName("Should be able to find box using implicit wait")
  public void implicit() throws InterruptedException {
    driver = startChromeDriver(2);
    driver.get(url.dynamic);
    dynamicPage = new Dynamic(driver);

    WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
    addButton.click();

    assertTrue(dynamicPage.box1.isDisplayed(), "Box not displayed");
    assertEquals("redbox", dynamicPage.box1.getDomAttribute("class"), "Box class name not as expected");
  }

  @Test
  @Tag("regression")
  @Tag("wait")
  @DisplayName("Should be able to find box using explicit wait")
  public void explicit() throws InterruptedException {
    driver = startChromeDriver(0);
    driver.get(url.dynamic);
    dynamicPage = new Dynamic(driver);

    WebElement revealButton = dynamicPage.highlightElement(dynamicPage.revealNewInputButton);
    revealButton.click();

    WebElement revealedInput = dynamicPage.highlightElement(dynamicPage.revealedInput);

    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    wait.until(d -> revealedInput.isDisplayed());

    revealedInput.sendKeys("Displayed");
    assertEquals("Displayed", revealedInput.getDomProperty("value"));
  }

  @Test
  @Tag("regression")
  @Tag("wait")
  @DisplayName("Should be able to find box using sleep")
  public void sleep() throws InterruptedException {
    driver = startChromeDriver(0);
    driver.get(url.dynamic);
    dynamicPage = new Dynamic(driver);

    WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
    addButton.click();

    Thread.sleep(2000);

    WebElement box = dynamicPage.highlightElement(dynamicPage.box1);
    Assertions.assertEquals("redbox", box.getDomAttribute("class"));
  }

  @Test
  @Tag("regression")
  @Tag("wait")
  @DisplayName("Should be able to find box using explicit wait with polling option")
  public void explicitWithOptions() throws InterruptedException {
    driver = startChromeDriver(0);
    driver.get(url.dynamic);
    dynamicPage = new Dynamic(driver);

    WebElement revealButton = dynamicPage.highlightElement(dynamicPage.revealNewInputButton);
    revealButton.click();

    WebElement revealedInput = dynamicPage.highlightElement(dynamicPage.revealedInput);

    Wait<WebDriver> wait = new FluentWait<>(driver)
                          .withTimeout(Duration.ofSeconds(2))
                          .pollingEvery(Duration.ofMillis(300))
                          .ignoring(ElementNotInteractableException.class);
    wait.until(d -> {
      revealedInput.sendKeys("Displayed");
      return true;
    });

    Assertions.assertEquals("Displayed", revealedInput.getDomProperty("value"));
  }
}