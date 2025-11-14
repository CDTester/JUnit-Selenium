package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Dynamic {
  WebDriver driver;

  @FindBy(id = "adder")
  public WebElement addAboxButton;

  @FindBy(id = "reveal")
  public WebElement revealNewInputButton;

  @FindBy(id = "box0")
  public WebElement box1;

  @FindBy(id = "box1")
  public WebElement box2;

  @FindBy(id = "revealed")
  public WebElement revealedInput;


  public Dynamic(WebDriver driver) {
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

}
