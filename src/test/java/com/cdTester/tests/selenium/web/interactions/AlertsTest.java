package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.selenium.web.Alerts;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlertsTest extends BaseTest {
  protected Alerts alertsPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.alerts);
    alertsPage = new Alerts(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @Tag("alerts")
  @DisplayName("Should be able to get text from alert and accept it")
  public void alertInformationTest() throws InterruptedException {
    alertsPage.click(alertsPage.alertLink);
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("cheese", alertMessage, "Alert text not as expected");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to accept an empty alert")
  public void alertEmptyInformationTest() throws InterruptedException {
    alertsPage.click(alertsPage.emptyAlertLink);
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("", alertMessage, "Alert text not as expected");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to enter text into a prompt and accept it")
  public void promptDisplayAndInputTest() throws InterruptedException {
    alertsPage.click(alertsPage.promptLink);
    assertEquals("Enter something", alertsPage.getAlertMessage(), "Alert text not as expected");

    alertsPage.sendKeysToAlertAndClose("Selenium", "accept");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should not be able to read the default text in a prompt")
  public void promptDefaultInputTest() throws InterruptedException {
    alertsPage.click(alertsPage.promptWithDefaultLink);
    String alertMessage = alertsPage.getAlertMessageAndClose("accept");
    assertEquals("Enter something", alertMessage, "Alert message not as expected");
    assertNotEquals("This is a default value", alertMessage, "Should not have got the default prompt message");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to enter text into multiple prompts and accept them")
  public void multiplePromptInputsTest() throws InterruptedException {
    alertsPage.click(alertsPage.doublePromptLink);
    assertEquals("First", alertsPage.getAlertMessage(), "Alert message not as expected");

    alertsPage.sendKeysToAlertAndClose("first", "accept");
    assertEquals("Second", alertsPage.getAlertMessage(), "Alert message not as expected");

    alertsPage.sendKeysToAlertAndClose("second", "accept");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to handle a delayed alert")
  public void slowAlertTest() throws InterruptedException {
    alertsPage.click(alertsPage.slowAlertLink);
    assertEquals("Slow", alertsPage.getAlertMessageAndClose("accept"));
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to accept a confirmation alert that redirects to another URL")
  public void confirmationAlertTest() throws InterruptedException {
    alertsPage.click(alertsPage.confirmAlertLink);
    assertEquals("Are you sure?", alertsPage.getAlertMessageAndClose("accept"), "Alert message not as expected");
    assertTrue(alertsPage.getUrl().endsWith("simpleTest.html"),"Page was not redirected, URL = " + alertsPage.getUrl());
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to click a link in an iframe that triggers an alert")
  public void iframeAlertTest() throws InterruptedException {
    alertsPage.switchToIframe(alertsPage.iframeWindow);
    alertsPage.click(alertsPage.iframeLink);
    assertEquals("framed cheese", alertsPage.getAlertMessageAndClose("accept"),"Alert message not as expected");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to click a link in a nested iframe that triggers an alert")
  public void nestedIframeAlertTest() throws InterruptedException {
    alertsPage.switchToIframe(alertsPage.nestedIframeWindow);
    alertsPage.switchToIframe(alertsPage.iframeWindow);

    alertsPage.click(alertsPage.iframeLink);
    assertEquals("framed cheese", alertsPage.getAlertMessageAndClose("accept"),"Alert message not as expected");
  }

  @Test
  @Tag("regression")
  @Tag("alerts")
  @DisplayName("Should be able to handle alerts created by JavaScriptExecutor")
  public void testForAlerts() throws InterruptedException {
    alertsPage.createJsAlert("alert('Sample Alert');");
    assertEquals("Sample Alert", alertsPage.getAlertMessageAndClose("accept"));

    alertsPage.createJsAlert("confirm('Are you sure?');");
    assertEquals("Are you sure?", alertsPage.getAlertMessageAndClose("dismiss"));

    alertsPage.createJsAlert("prompt('What is your name?');");
    assertEquals("What is your name?", alertsPage.getAlertMessage());

    alertsPage.sendKeysToAlertAndClose("Selenium","accept");

    driver.quit();
  }
}