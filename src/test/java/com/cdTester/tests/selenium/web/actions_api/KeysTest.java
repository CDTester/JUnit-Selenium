package com.cdTester.tests.selenium.web.actions_api;

import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.SingleTextInput;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Epic: Working with keys")
@Feature("Feature: keys Tests")
@Tag("keyboard")
@ExtendWith(TestResultListener.class)
public class KeysTest extends BaseTest {
  protected SingleTextInput keysPage;
  protected WebElement textInput;

  @BeforeEach
  void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.singleTextInput);
      driver.get(url.singleTextInput);
      String title=driver.getTitle();
      step.parameter("title", title);
      assertEquals(SingleTextInput.title, title);
    });
    keysPage = new SingleTextInput(driver);
    Allure.step("WHEN the text input box is visible", step -> {
      textInput = keysPage.highlightElement(keysPage.textInput);
      step.parameter("id:", textInput.getAttribute("id"));
      step.parameter("isDisplayed()", textInput.isDisplayed());
      assertTrue(textInput.isDisplayed());
    });
  }

  @AfterEach
  void endSession() {
    /* An important thing to note is that the driver remembers the state of all the input items throughout a session.
       Even if you create a new instance of an actions class, the depressed keys and the location of the pointer
       will be in whatever state a previously performed action left them.
    */
    ((RemoteWebDriver) driver).resetInputState();
  }

  @Test
  @Tag("smoke")
  @Story("Story: Actions")
  @TmsLink("TC-011")
  @DisplayName("Should be able to send keys to a focused element using Actions")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1011")
  void sendKeysFocused() {
    Allure.step("AND the text input box is autofocused", step -> {
      step.parameter("autofocus:", textInput.getAttribute("autofocus"));
      System.out.println(textInput.isDisplayed());
    });
    Allure.step("THEN 'abc' can be written to the input field using Actions", step -> {
      step.parameter("Actions(driver)", ".sendKeys('abc')");
      step.parameter("", ".perform()");
      new Actions(driver)
            .sendKeys("abc")
            .perform();
      step.parameter("Inputted text:", textInput.getAttribute("value"));
      assertEquals("abc", textInput.getAttribute("value"),
            "Text input does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-012")
  @DisplayName("Should be able to send keys to an element using Actions")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1012")
  void sendKeysToElement() {
    Allure.step("THEN 'def' can be written to the input field using Actions", step -> {
      step.parameter("Actions(driver)", ".sendKeys(WebElement, 'def')");
      step.parameter("", ".perform()");
      new Actions(driver)
            .sendKeys(textInput,"def")
            .perform();
      step.parameter("Inputted text:", textInput.getAttribute("value"));
      assertEquals("def", textInput.getAttribute("value"),
            "Text input does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-013")
  @DisplayName("Should be able to hold SHIFT key down whilst sending keys using Actions")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1013")
  void keyDown() {
    Allure.step("THEN 'abc' can be written to the input field using Actions", step -> {
      step.parameter("Actions(driver)", ".keyDown(Keys.SHIFT)");
      step.parameter("", ".sendKeys('holding shift key down')");
      step.parameter("", ".perform()");
      new Actions(driver)
            .keyDown(Keys.SHIFT)
            .sendKeys("holding shift key down")
            .perform();
      step.parameter("Inputted text:", textInput.getAttribute("value"));
      assertEquals("HOLDING SHIFT KEY DOWN",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-014")
  @DisplayName("Should be able to copy and paste using Actions")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1014")
  void copyPaste() {
    Allure.step("THEN 'abc' can be written to the input field using Actions", step -> {
      Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;

      step.parameter("Actions(driver)", ".sendKeys(WebElement, \"Selenium!\")");
      step.parameter("", ".sendKeys(Keys.ARROW_LEFT)");
      step.parameter("", ".keyDown(Keys.SHIFT)");
      step.parameter("", ".sendKeys(Keys.ARROW_UP)");
      step.parameter("", ".keyUp(Keys.SHIFT)");
      step.parameter("", ".keyDown(Keys.CONTROL or Keys.COMMAND)");
      step.parameter("", ".sendKeys(\"xvv\")");
      step.parameter("", ".keyUp(Keys.CONTROL or Keys.COMMAND)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .sendKeys(textInput,"Selenium!")
            .sendKeys(Keys.ARROW_LEFT)
            .keyDown(Keys.SHIFT)
            .sendKeys(Keys.ARROW_UP)
            .keyUp(Keys.SHIFT)
            .keyDown(cmdCtrl)
            .sendKeys("xvv")
            .keyUp(cmdCtrl)
            .perform();
      step.parameter("Inputted text:", textInput.getAttribute("value"));
      assertEquals("SeleniumSelenium!",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
      );
    });
  }

}