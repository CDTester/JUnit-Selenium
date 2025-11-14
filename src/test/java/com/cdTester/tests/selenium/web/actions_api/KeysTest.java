package com.cdTester.tests.selenium.web.actions_api;

import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.SingleTextInput;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeysTest extends BaseTest {
  protected SingleTextInput keysPage;
  protected WebElement textInput;

  @BeforeEach
  void createSession() throws InterruptedException {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(0);
    driver.get(url.singleTextInput);
    keysPage = new SingleTextInput(driver);
    textInput = keysPage.highlightElement(keysPage.textInput);
  }

  @AfterEach
  void endSession() {
    /* An important thing to note is that the driver remembers the state of all
       the input items throughout a session. Even if you create a new instance
       of an actions class, the depressed keys and the location of the pointer
       will be in whatever state a previously performed action left them.
    */
    ((RemoteWebDriver) driver).resetInputState();
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @DisplayName("Should be able to send keys to a focused element using Actions")
  void sendKeysFocused() {
    new Actions(driver)
            .sendKeys("abc")
            .perform();

    assertEquals("abc",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to send keys to an element using Actions")
  void sendKeysToElement() {
    new Actions(driver)
            .sendKeys(textInput,"def")
            .perform();

    assertEquals("def",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
    );
  }
  @Test
  @Tag("regression")
  @DisplayName("Should be able to hold SHIFT key down whilst sending keys using Actions")
  void keyDown() {
    new Actions(driver)
            .keyDown(Keys.SHIFT)
            .sendKeys("holding shift key down")
            .perform();

    assertEquals("HOLDING SHIFT KEY DOWN",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to copy and paste using Actions")
  void copyPaste() {
    Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;

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

    assertEquals("SeleniumSelenium!",
            textInput.getAttribute("value"),
            "Text input does not match the action performed"
    );
  }

}