package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SingleTextInput {
  WebDriver driver;

  @FindBy(id = "textInput")
  public WebElement textInput;


  public SingleTextInput(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  /**
   * Highlights a web element on the page except checkboxes and radio buttons.
   * @param element The WebElement to be highlighted.
   * @return The same WebElement after highlighting.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement highlightElement(WebElement element) throws InterruptedException {
    Highlight.highlightElement(this.driver, element);
    return element;
  }

  /**
   * Un-Highlights a web element on the page except checkboxes and radio buttons.
   * @param element The WebElement to be highlighted.
   * @return The same WebElement after highlighting.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement unhighlightElement(WebElement element) throws InterruptedException {
    Highlight.unhighlightElement(this.driver, element);
    return element;
  }
}
