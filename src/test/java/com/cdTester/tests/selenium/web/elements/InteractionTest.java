package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.selenium.web.Inputs;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class InteractionTest extends BaseTest {
  protected Inputs inputPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(2);
    driver.get(url.inputs);
    inputPage = new Inputs(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }


  @Test
  @Tag("smoke")
  @DisplayName("should be able to click on a checkbox")
  public void clickCheckbox() throws InterruptedException {
    WebElement checkbox = inputPage.highlightElement(inputPage.checkboxInput);
    assertTrue(checkbox.isSelected(), "Expected checkbox to be selected");

    checkbox.click();
    assertFalse(checkbox.isSelected(), "Expected checkbox to be unselected");
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to clear the text from an input field")
  public void clearText() throws InterruptedException {
    WebElement email = inputPage.highlightElement(inputPage.emailInput);
    email.clear();

    assertEquals("",
            email.getAttribute("value"),
            "Email value not as expected"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to enter text in an input field")
  public void isEnabled() throws InterruptedException {
    WebElement input = inputPage.highlightElement(inputPage.emailInput);
    String email = "admin@localhost.dev";
    input.clear();
    input.sendKeys(email);

    assertEquals(email,
            input.getAttribute("value"),
            "Email value not as expected"
    );
  }

}
