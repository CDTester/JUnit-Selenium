package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.selenium.web.Alerts;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Epic: Working with alerts")
@Feature("Feature: Alerts Tests")
@Tag("alerts")
@ExtendWith(TestResultListener.class)
public class AlertsTest extends BaseTest {
  protected Alerts alertsPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.alerts);
      driver.get(url.alerts);
    });
    alertsPage = new Alerts(driver);
  }

  @Test
  @Tag("smoke")
  @Story("Story: Basic alert")
  @TmsLink("TC-071")
  @DisplayName("Should be able to get text from alert and accept it")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-171")
  public void alertInformationTest() {
    Allure.step("WHEN the first 'click me' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.alertLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.alertLink);
    });

    Allure.step("THEN the message in the alert should say 'cheese'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("cheese", alertMessage, "Alert text not as expected");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Basic alert")
  @TmsLink("TC-072")
  @DisplayName("Should be able to accept an empty alert")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-172")
  public void alertEmptyInformationTest() {
    Allure.step("WHEN the second 'click me' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.emptyAlertLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.emptyAlertLink);
    });

    Allure.step("THEN the message in the alert should be empty", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("", alertMessage, "Alert text not as expected");
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Alert with Prompt")
  @TmsLink("TC-073")
  @DisplayName("Should be able to enter text into a prompt and accept it")
  @Owner("QA/Chris")
  @Issue("BUG-173")
  public void promptDisplayAndInputTest() {
    Allure.step("WHEN the 'prompt happen' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.promptLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.promptLink);
    });

    Allure.step("THEN the message in the alert should be empty", step -> {
      String alertMessage = alertsPage.getAlertMessage();
      step.parameter("Alert Message", alertMessage);
      assertEquals("Enter something", alertMessage, "Alert text not as expected");
    });

    Allure.step("AND 'Selenium' can be entered into the prompt box", step -> {
        alertsPage.sendKeysToAlertAndClose("Selenium","accept");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert with Prompt")
  @TmsLink("TC-074")
  @DisplayName("Should not be able to read the default text in a prompt")
  @Owner("QA/Chris")
  @Issue("BUG-174")
  public void promptDefaultInputTest() {
    Allure.step("WHEN the 'prompt happen' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.promptWithDefaultLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.promptWithDefaultLink);
    });

    Allure.step("THEN the message in the alert should say 'Enter something'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("Enter something", alertMessage, "Alert message not as expected");
    });

    Allure.step("AND the prompt box has a default value", step -> {
      String head = alertsPage.driver.getPageSource()
            .replaceAll("\n","")
            .split("head>")[1];
      step.parameter("Selenium limitation", "Selenium cannot read default value from prompt boxes nor take screenshots of alerts");
      step.parameter("<head> contains 'This is a default value'", head);
      assertTrue(head.contains("This is a default value"));
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert  with Prompt")
  @TmsLink("TC-075")
  @DisplayName("Should be able to enter text into multiple prompts and accept them")
  @Owner("QA/Chris")
  @Issue("BUG-175")
  public void multiplePromptInputsTest() {
    Allure.step("WHEN the 'prompts happen' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.doublePromptLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.doublePromptLink);
    });

    Allure.step("THEN the message in the first alert should say 'First'", step -> {
      String alertMessage = alertsPage.getAlertMessage();
      step.parameter("Alert Message", alertMessage);
      assertEquals("First", alertsPage.getAlertMessage(), "Alert message not as expected");
    });

    Allure.step("AND WHEN the first alert box is closed", step -> {
      alertsPage.sendKeysToAlertAndClose("first", "accept");
      step.parameter("button clicked", "OK");
    });

    Allure.step("THEN the message in the second alert should say 'Second'", step -> {
      String alertMessage = alertsPage.getAlertMessage();
      step.parameter("Alert Message", alertMessage);
      assertEquals("Second", alertsPage.getAlertMessage(), "Alert message not as expected");
      alertsPage.sendKeysToAlertAndClose("second", "accept");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Slow alert")
  @TmsLink("TC-076")
  @DisplayName("Should be able to handle a delayed alert")
  @Owner("QA/Chris")
  @Issue("BUG-176")
  public void slowAlertTest() {
    Allure.step("WHEN the 'SLOW' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.slowAlertLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.slowAlertLink);
    });

    Allure.step("THEN the message in the alert should say 'Slow'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("Slow", alertMessage);
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert that redirects to another page")
  @TmsLink("TC-077")
  @DisplayName("Should be able to accept a confirmation alert that redirects to another URL")
  @Owner("QA/Chris")
  @Issue("BUG-177")
  public void confirmationAlertTest() {
    Allure.step("AND the 'SLOW' link is clicked", step -> {
      step.parameter("link onclick", alertsPage.confirmAlertLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.confirmAlertLink);
    });

    Allure.step("AND the message in the alert should say 'Are you sure'", step -> {
      String alertMessage = alertsPage.getAlertMessage();
      step.parameter("Alert Message", alertMessage);
      assertEquals("Are you sure?", alertMessage, "Alert message not as expected");
    });

    Allure.step("WHEN the 'OK' button is clicked", step -> {
      alertsPage.close("accept");
    });

    Allure.step("THEN the page is redirected to 'simpleTest.html'", step -> {
      String url = alertsPage.getUrl();
      step.parameter("Current URL", url);
      assertTrue(url.endsWith("simpleTest.html"),"Page was not redirected, URL = " + alertsPage.getUrl());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert in an iframe")
  @TmsLink("TC-078")
  @DisplayName("Should be able to click a link in an iframe that triggers an alert")
  @Owner("QA/Chris")
  @Issue("BUG-178")
  public void iframeAlertTest() {
    Allure.step("WHEN the 'click me' link is clicked within a single iframe", step -> {
      alertsPage.switchToIframe(alertsPage.iframeWindow);
      step.parameter("link onclick", alertsPage.iframeLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.iframeLink);
    });

    Allure.step("THEN the message in the alert should say 'framed cheese'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("framed cheese", alertMessage,"Alert message not as expected");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert in an iframe")
  @TmsLink("TC-079")
  @DisplayName("Should be able to click a link in a nested iframe that triggers an alert")
  @Owner("QA/Chris")
  @Issue("BUG-179")
  public void nestedIframeAlertTest() {
    Allure.step("WHEN the 'click me' link is clicked within an iframe in an iframe", step -> {
      alertsPage.switchToIframe(alertsPage.nestedIframeWindow);
      alertsPage.switchToIframe(alertsPage.iframeWindow);
      step.parameter("link onclick", alertsPage.iframeLink.getAttribute("onclick"));
      alertsPage.click(alertsPage.iframeLink);
  });

    Allure.step("THEN the message in the alert should say 'framed cheese'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("framed cheese", alertMessage,"Alert message not as expected");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert created by JavaScriptExecutor")
  @TmsLink("TC-0710")
  @DisplayName("Should be able to create a simple alert using JavaScriptExecutor")
  @Owner("QA/Chris")
  @Issue("BUG-1710")
  public void testForJsAlerts() {
    Allure.step("WHEN an alert is created using JavaScriptExecutor", step -> {
      step.parameter("JavaScript", "alert('Sample Alert');");
      alertsPage.createJsAlert("alert('Sample Alert');");
    });

    Allure.step("THEN the message in the alert should say 'Sample Alert'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("accept");
      step.parameter("Alert Message", alertMessage);
      assertEquals("Sample Alert", alertMessage);
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert created by JavaScriptExecutor")
  @TmsLink("TC-0711")
  @DisplayName("Should be able to create an alert with an option to cancel using JavaScriptExecutor")
  @Owner("QA/Chris")
  @Issue("BUG-1711")
  public void testForJsConfirmAlerts() {
    Allure.step("WHEN an alert is created using JavaScriptExecutor", step -> {
      step.parameter("JavaScript", "confirm('Are you sure?');");
      alertsPage.createJsAlert("confirm('Are you sure?');");
    });

    Allure.step("THEN the message in the alert should say 'Are you sure?'", step -> {
      String alertMessage = alertsPage.getAlertMessageAndClose("dismiss");
      step.parameter("Alert Message", alertMessage);
      assertEquals("Are you sure?", alertMessage);
      step.parameter("Alert button clicked", "cancel");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Alert created by JavaScriptExecutor")
  @TmsLink("TC-0712")
  @DisplayName("Should be able to create an alert with a prompt using JavaScriptExecutor")
  @Owner("QA/Chris")
  @Issue("BUG-1712")
  public void testForJsPromptAlerts() {
    Allure.step("WHEN an alert is created using JavaScriptExecutor", step -> {
      step.parameter("JavaScript", "prompt('What is your name?');");
      alertsPage.createJsAlert("prompt('What is your name?');");
    });

    Allure.step("THEN the message in the alert should say 'What is your name?'", step -> {
      String alertMessage = alertsPage.getAlertMessage();
      step.parameter("Alert Message", alertMessage);
      assertEquals("What is your name?", alertMessage);
    });

    Allure.step("AND the prompt should allow text to be entered", step -> {
      alertsPage.sendKeysToAlertAndClose("Dave","accept");
      step.parameter("Text entered", "Dave");
    });
  }
}