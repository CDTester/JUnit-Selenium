package com.cdTester.tests.selenium.web.waits;

import com.cdTester.pages.selenium.web.Dynamic;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

@Epic("Epic: Working with waits")
@Feature("Feature: Waits Tests")
@Tag("wait")
@ExtendWith(TestResultListener.class)
public class WaitsTest extends BaseTest {
  protected Dynamic dynamicPage;
  protected Urls url;

  @BeforeEach
  public void loadUrls() {
    url = new Urls(BaseTest.config, "selenium");
  }

  @Test
  @Tag("regression")
  @Story("Story: No Waits")
  @TmsLink("TC-191")
  @DisplayName("Should fail to find box without waits")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1191")
  public void noWaits() {
    Allure.step("GIVEN ChromeDriver has been initiated with setImplicitWaitTimeout=0", step -> {
      driver = startChromeDriver(0);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.dynamic);
      driver.get(url.dynamic);
      String title=driver.getTitle();
      step.parameter(Dynamic.title, title);
    });
    dynamicPage = new Dynamic(driver);
    Allure.step("WHEN the 'Add a box!' button is clicked", step -> {
      WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
      addButton.click();
      step.parameter("onclick:", addButton.getAttribute("onclick"));
    });
    Allure.step("THEN this step will throw a NoSuchElementException" , step -> {
      step.parameter("Behaviour:", "onclick adds a box after 1 second delay, but no waits are set");
      assertThrows(
            NoSuchElementException.class,
            () -> dynamicPage.highlightElement(dynamicPage.box1).isDisplayed(),
            "Box should not be displayed"
      );
    });
  }

  @Test
  @Tag("smoke")
  @Story("Story: Waits")
  @TmsLink("TC-192")
  @DisplayName("Should be able to find box using implicit wait")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1192")
  public void implicit() {
    Allure.step("GIVEN ChromeDriver has been initiated with setImplicitWaitTimeout=2", step -> {
      driver = startChromeDriver(2);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.dynamic);
      driver.get(url.dynamic);
      String title=driver.getTitle();
      step.parameter(Dynamic.title, title);
    });
    dynamicPage = new Dynamic(driver);
    Allure.step("WHEN the 'Add a box!' button is clicked", step -> {
      WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
      addButton.click();
      step.parameter("onclick:", addButton.getAttribute("onclick"));
    });
    Allure.step("THEN the box will be found using implicit wait" , step -> {
      assertTrue(dynamicPage.box1.isDisplayed(), "Box not displayed");
      assertEquals("redbox", dynamicPage.box1.getDomAttribute("class"), "Box class name not as expected");
      step.parameter("Behaviour:", "onclick adds a box after 1 second delay, implicit wait is set to 2 seconds");
      step.parameter("isDisplayed():", dynamicPage.box1.isDisplayed());
      step.parameter("Dom Attribute:", dynamicPage.box1.getDomAttribute("class"));
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Waits")
  @TmsLink("TC-193")
  @DisplayName("Should be able to find box using explicit wait")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1193")
  public void explicit() {
    AtomicReference<WebElement> revealedInput = new AtomicReference<>();

    Allure.step("GIVEN ChromeDriver has been initiated with setImplicitWaitTimeout=0", step -> {
      driver = startChromeDriver(0);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.dynamic);
      driver.get(url.dynamic);
      String title=driver.getTitle();
      step.parameter(Dynamic.title, title);
    });
    dynamicPage = new Dynamic(driver);
    Allure.step("WHEN the 'Reveal a new input' button is clicked", step -> {
      WebElement revealButton = dynamicPage.highlightElement(dynamicPage.revealNewInputButton);
      revealButton.click();
      step.parameter("onclick:", revealButton.getAttribute("onclick"));
    });
    Allure.step("THEN the box will be found using explicit wait" , step -> {
      step.parameter("Wait<WebDriver> wait = ", "new WebDriverWait(driver, Duration.ofSeconds(2));");
      step.parameter("wait", ".until(driver -> WebElement.isDisplayed());");

      revealedInput.set(dynamicPage.highlightElement(dynamicPage.revealedInput));
      Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
      wait.until(driver -> revealedInput.get().isDisplayed());

      step.parameter("isDisplayed():", revealedInput.get().isDisplayed());
      assertTrue(revealedInput.get().isDisplayed(), "Box not displayed");
    });
    Allure.step("AND 'Displayed' is written in the input field" , step -> {
      revealedInput.get().sendKeys("Displayed");
      assertEquals("Displayed", revealedInput.get().getDomProperty("value"));
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Waits")
  @TmsLink("TC-194")
  @DisplayName("Should be able to find box using sleep")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1194")
  public void sleep() {
    Allure.step("GIVEN ChromeDriver has been initiated with setImplicitWaitTimeout=0", step -> {
      driver = startChromeDriver(0);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.dynamic);
      driver.get(url.dynamic);
      String title=driver.getTitle();
      step.parameter(Dynamic.title, title);
    });
    dynamicPage = new Dynamic(driver);
    Allure.step("AND the 'Add a box!' button is clicked", step -> {
      WebElement addButton = dynamicPage.highlightElement(dynamicPage.addAboxButton);
      addButton.click();
      step.parameter("onclick:", addButton.getAttribute("onclick"));
    });
    Allure.step("WHEN a sleep period of 2 seconds is applied", step -> {
      Thread.sleep(2000);
      step.parameter("Thread", ".sleep(2000)");
    });
    Allure.step("THEN the box will be found using explicit wait" , step -> {
      WebElement box = dynamicPage.highlightElement(dynamicPage.box1);
      step.parameter("isDisplayed():", box.isDisplayed());
      step.parameter("Dom Attribute:", dynamicPage.box1.getDomAttribute("class"));
      assertEquals("redbox", dynamicPage.box1.getDomAttribute("class"), "Box class name not as expected");
      assertTrue(dynamicPage.box1.isDisplayed(), "Box not displayed");
    });
  }

  @Test
  @Tag("regression")
  @Tag("wait")
  @Story("Story: Waits")
  @TmsLink("TC-195")
  @DisplayName("Should be able to find box using fluent wait")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1195")
  public void explicitWithOptions() {
    AtomicReference<WebElement> revealedInput = new AtomicReference<>();

    Allure.step("GIVEN ChromeDriver has been initiated with setImplicitWaitTimeout=0", step -> {
      driver = startChromeDriver(0);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.dynamic);
      driver.get(url.dynamic);
      String title=driver.getTitle();
      step.parameter(Dynamic.title, title);
    });
    dynamicPage = new Dynamic(driver);
    Allure.step("WHEN the 'Reveal a new input' button is clicked", step -> {
      WebElement revealButton = dynamicPage.highlightElement(dynamicPage.revealNewInputButton);
      revealButton.click();
      step.parameter("onclick:", revealButton.getAttribute("onclick"));
    });
    Allure.step("THEN the box will be found using explicit wait" , step -> {
      step.parameter("Wait<WebDriver> wait = ", """
            new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(2))
            .pollingEvery(Duration.ofMillis(300))
            .ignoring(ElementNotInteractableException.class);
            """
      );
      step.parameter("wait", ".until(driver -> WebElement.isDisplayed());");
      revealedInput.set(dynamicPage.highlightElement(dynamicPage.revealedInput));
      Wait<WebDriver> wait = new FluentWait<>(driver)
            .withTimeout(Duration.ofSeconds(2))
            .pollingEvery(Duration.ofMillis(300))
            .ignoring(ElementNotInteractableException.class);
      wait.until(driver -> revealedInput.get().isDisplayed());
      step.parameter("isDisplayed():", revealedInput.get().isDisplayed());
      assertTrue(revealedInput.get().isDisplayed(), "Box not displayed");
    });
    Allure.step("AND 'Displayed' is written in the input field" , step -> {
      revealedInput.get().sendKeys("Displayed");
      assertEquals("Displayed", revealedInput.get().getDomProperty("value"));
    });
  }
}