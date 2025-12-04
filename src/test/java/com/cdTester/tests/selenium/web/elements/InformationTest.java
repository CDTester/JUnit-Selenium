package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.selenium.web.Inputs;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

@Epic("Epic: Working with information about WebElements")
@Feature("Feature: Information Tests")
@Tag("information")
@ExtendWith(TestResultListener.class)
public class InformationTest extends BaseTest {
  protected Inputs inputPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("WHEN the web page has loaded", step -> {
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
  @Story("Story: States")
  @TmsLink("TC-041")
  @DisplayName("Email input should be displayed")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1041")
  public void isDisplayed() {
    Allure.step("THEN the email input isDisplayed()", step -> {
      WebElement email = inputPage.highlightElement(inputPage.emailInput);
      step.parameter("name:", email.getAttribute("name"));
      step.parameter("type:", email.getAttribute("type"));
      step.parameter("default value:", email.getAttribute("value"));
      step.parameter("isDisplayed():", email.isDisplayed());
      assertTrue(email.isDisplayed());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: States")
  @TmsLink("TC-042")
  @DisplayName("Button input should be enabled")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1042")
  public void isEnabled() {
    Allure.step("THEN the button input isEnabled()", step -> {
      WebElement button = inputPage.highlightElement(inputPage.buttonInput);
      step.parameter("name:", button.getAttribute("name"));
      step.parameter("type:", button.getAttribute("type"));
      step.parameter("default value:", button.getAttribute("value"));
      step.parameter("isEnabled():", button.isEnabled());
      assertTrue(button.isEnabled());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: States")
  @TmsLink("TC-043")
  @DisplayName("Checkbox input should be checked")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1043")
  public void isSelected() {
    Allure.step("THEN the button input isSelected()", step -> {
      WebElement checkbox = inputPage.highlightElement(inputPage.checkboxInput);
      step.parameter("name:", checkbox.getAttribute("name"));
      step.parameter("type:", checkbox.getAttribute("type"));
      step.parameter("checked:", checkbox.getAttribute("checked"));
      step.parameter("isSelected():", checkbox.isSelected());
      assertTrue(checkbox.isSelected());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Values")
  @TmsLink("TC-044")
  @DisplayName("Should be able to get the tag name of an element")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1044")
  public void getTagName() {
    Allure.step("THEN the tag name of the password field can be determined", step -> {
      WebElement password = inputPage.highlightElement(inputPage.passwordInput);
      step.parameter("name:", password.getAttribute("name"));
      step.parameter("type:", password.getAttribute("type"));
      step.parameter("default value:", password.getAttribute("value"));
      step.parameter("getTagName():", password.getTagName());
      assertEquals(inputPage.passwordTagName, password.getTagName());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Values")
  @TmsLink("TC-045")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1045")
  @DisplayName("Should be able to get the position and dimensions of an element")
  public void getRect() {
    Allure.step("THEN the dimensions and position of the range input field can be determined", step -> {
      WebElement range = inputPage.rangeInput;
      Rectangle rect = range.getRect();
      step.parameter("name:", range.getAttribute("name"));
      step.parameter("type:", range.getAttribute("type"));
      step.parameter("default value:", range.getAttribute("value"));
      step.parameter("offset left:", rect.getX());
      step.parameter("offset top:", rect.getY());
      step.parameter("point:", rect.getPoint().toString());
      step.parameter("width:", rect.getWidth());
      step.parameter("height:", rect.getHeight());
      step.parameter("dimension:", rect.getDimension().toString());

      assertEquals(10, rect.getX());
      assertEquals(128, rect.getY());
      assertEquals(129, rect.getWidth());
      assertEquals(16, rect.getHeight());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Values")
  @TmsLink("TC-046")
  @DisplayName("Should be able to get the CSS values  an element")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1046")
  public void getCssValue() {
    Allure.step("THEN the CSS values of the colour input field can be determined", step -> {
      WebElement colour = inputPage.highlightElement(inputPage.colourInput);
      String fontSize = colour.getCssValue("font-size");
      step.parameter("name:", colour.getAttribute("name"));
      step.parameter("type:", colour.getAttribute("type"));
      step.parameter("default value:", colour.getAttribute("value"));
      step.parameter("font-size:", colour.getCssValue("font-size"));
      step.parameter("font-family:", colour.getCssValue("font-family"));
      step.parameter("padding-bottom:", colour.getCssValue("padding-bottom"));
      step.parameter("padding-left:", colour.getCssValue("padding-left"));
      inputPage.unhighlightElement(inputPage.colourInput);
      step.parameter("border:", colour.getCssValue("border"));
      step.parameter("margin-bottom:", colour.getCssValue("margin-bottom"));
      step.parameter("margin-left:", colour.getCssValue("margin-left"));

      assertEquals("13.3333px", fontSize);
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Values")
  @TmsLink("TC-047")
  @DisplayName("Should be able to get text from an element")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1047")
  public void getText() {
    Allure.step("THEN the text value of the <h1> tag", step -> {
      WebElement header = inputPage.highlightElement(inputPage.header1);
      step.parameter("tag name:", header.getTagName());
      step.parameter("text:", header.getText());
      assertEquals("Testing Inputs", header.getText());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Values")
  @TmsLink("TC-048")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1048")
  @DisplayName("Should be able to get an attribute of an element")
  public void getAttribute() {
    Allure.step("THEN the attributes of the datetime local input field can be determined", step -> {
      WebElement date = inputPage.highlightElement(inputPage.dateTimeLocalInput);
      step.parameter("name:", date.getAttribute("name"));
      step.parameter("type:", date.getAttribute("type"));
      step.parameter("value:", date.getAttribute("value"));
      assertEquals("datetime_local_input", date.getAttribute("name"));
      assertEquals("datetime-local", date.getAttribute("type"));
      assertEquals("2017-11-22T11:22", date.getAttribute("value"));
    });
  }

}
