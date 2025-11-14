package com.cdTester.tests.selenium.web.interactions;


import com.cdTester.pages.selenium.web.Frames;
import com.cdTester.pages.selenium.web.FormPage;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FramesTest extends BaseTest {
  protected Frames framesPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.frames);
    framesPage = new Frames(driver);
//    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @Tag("frames")
  @DisplayName("Should switch to iFrame using id")
  public void iframeWithId() throws InterruptedException {

    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using Web Element
    framesPage.switchToIframe(framesPage.iframeWindowById);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement emailInput = forms.loginFormEmailInput;
    forms.type(emailInput, "admin@selenium.dev");
    forms.clearText(emailInput);

    // exit current iframe
    driver.switchTo().defaultContent();
  }

  @Test
  @Tag("regression")
  @Tag("frames")
  @DisplayName("Should switch to iFrame using name")
  public void iframeWithName() throws InterruptedException {
    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using Web Element
    framesPage.switchToIframe(framesPage.iframeWindowByName);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement emailInput = forms.loginFormEmailInput;
    forms.type(emailInput, "admin@selenium.dev");
    forms.clearText(emailInput);

    // exit current iframe
    driver.switchTo().defaultContent();
  }

  @Test
  @Tag("regression")
  @Tag("frames")
  @DisplayName("Should switch to iFrame using index")
  public void iframeWithIndex() throws InterruptedException {
    String title = framesPage.getTitle();
    assertEquals("This page has iframes", title);

    //switch To IFrame using index
    driver.switchTo().frame(0);
    String iframeTitle = framesPage.getPageSourceTitle();
    assertEquals("We Leave From Here", iframeTitle);

    FormPage forms = new FormPage(framesPage.driver);
    WebElement ageInputInput = forms.loginFormAgeInput;
    forms.type(ageInputInput, "21");
    forms.clearText(ageInputInput);

    //leave frame
    driver.switchTo().defaultContent();
  }

}
