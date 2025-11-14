package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MouseInteraction {
  WebDriver driver;

  @FindBy(id = "click")
  public WebElement clickLink;

  @FindBy(id = "clickable")
  public WebElement clickableInput;

  @FindBy(id = "click-status")
  public WebElement clickableStatus;

  @FindBy(id = "draggable")
  public WebElement draggableBox;

  @FindBy(id = "droppable")
  public WebElement droppableBox;

  @FindBy(id = "drop-status")
  public WebElement droppedStatus;

  @FindBy(id = "hover")
  public WebElement hoverableButton;

  @FindBy(id = "move-status")
  public WebElement hoverableStatus;

  @FindBy(id = "absolute-location")
  public WebElement absoluteLocationValues;

  @FindBy(id = "relative-location")
  public WebElement relativeLocationValues;

  @FindBy(id = "mouse-tracker")
  public WebElement mouseTrackerBox;


  public MouseInteraction(WebDriver driver) {
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
