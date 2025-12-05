package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.selenium.web.Inputs;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Epic: Working with interactions of WebElements")
@Feature("Feature: Interactions Tests")
@Tag("interactions")
@ExtendWith(TestResultListener.class)
public class InteractionTest extends BaseTest {
  protected Inputs inputPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(2);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.inputs);
      driver.get(url.inputs);
      String title=driver.getTitle();
      step.parameter("title", title);
      assertEquals(Inputs.title, title);
    });
    inputPage = new Inputs(driver);
  }


  @Test
  @Tag("smoke")
  @Story("Story: Click")
  @TmsLink("TC-051")
  @DisplayName("should be able to click on a checkbox")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1051")
  public void clickCheckbox() {
    WebElement radio = inputPage.radioInput2;
    Allure.step("WHEN the second radio input is displayed and unchecked", step -> {
      inputPage.highlightElement(radio);
      step.parameter("name:", radio.getAttribute("name"));
      step.parameter("type:", radio.getAttribute("type"));
      step.parameter("isDisplayed():", radio.isDisplayed());
      step.parameter("isSelected():", radio.isSelected());
      assertTrue(radio.isDisplayed());
      assertFalse(radio.isSelected());
    });
    Allure.step("THEN the radio button can be clicked ", step -> {
      radio.click();
      step.parameter("isSelected():", radio.isSelected());
      assertTrue(radio.isSelected());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Clear")
  @TmsLink("TC-052")
  @DisplayName("Should be able to clear the text from an input field")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1052")
  public void clearText() {
    WebElement number = inputPage.numberInput;
    Allure.step("WHEN the number input isDisplayed() and has the value 42", step -> {
      inputPage.highlightElement(number);
      step.parameter("name:", number.getAttribute("name"));
      step.parameter("type:", number.getAttribute("type"));
      step.parameter("value:", number.getAttribute("value"));
      step.parameter("isDisplayed():", number.isDisplayed());
      assertTrue(number.isDisplayed());
      assertEquals("42", number.getAttribute("value"));
    });
    Allure.step("THEN the number can be cleared", step -> {
      number.clear();
      step.parameter("value:", number.getAttribute("value"));
      assertEquals("", number.getAttribute("value"));
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Type")
  @TmsLink("TC-053")
  @DisplayName("Should be able to enter text in an input field")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1053")
  public void sendKeys() {
    WebElement text = inputPage.textInput;
    Allure.step("WHEN the text input isDisplayed() and has a default value", step -> {
      inputPage.highlightElement(text);
      step.parameter("name:", text.getAttribute("name"));
      step.parameter("type:", text.getAttribute("type"));
      step.parameter("value:", text.getAttribute("value"));
      step.parameter("isDisplayed():", text.isDisplayed());
      assertTrue(text.isDisplayed());
      assertEquals("text input", text.getAttribute("value"));
    });
    Allure.step("AND default value is cleared", step -> {
      text.clear();
      step.parameter("value:", text.getAttribute("value"));
      assertEquals("", text.getAttribute("value"));
    });
    Allure.step("THEN the value 'new text' can be entered", step -> {
      String newText = "new text";
      text.sendKeys(newText);
      step.parameter("value:", text.getAttribute("value"));
      assertEquals(newText, text.getAttribute("value"));
    });
  }

}
