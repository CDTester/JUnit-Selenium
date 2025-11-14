package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Inputs {
  WebDriver driver;

  @FindBy(tagName = "h1")
  public WebElement header1;

//  @FindBy(name = "no_type")
//  WebElement noTypeInput;

//  @FindBy(name = "number_input")
//  WebElement numberInput;

  @FindBy(name = "range_input")
  public WebElement rangeInput;

  @FindBy(name = "email_input")
  public WebElement emailInput;

//  @FindBy(name = "password_input")
//  WebElement passwordInput;

//  @FindBy(name = "search_input")
//  WebElement searchInput;

//  @FindBy(name = "tel_input")
//  WebElement telephoneInput;

//  @FindBy(name = "text_input")
//  WebElement textInput;

//  @FindBy(name = "url_input")
//  WebElement urlInput;

  @FindBy(name = "checkbox_input")
  public WebElement checkboxInput;

//  @FindBy(name = "radio_input")
//  WebElement radioInput;

  @FindBy(name = "color_input")
  public WebElement colourInput;

//  @FindBy(name = "date_input")
//  WebElement dateInput;

//  @FindBy(name = "datetime_input")
//  WebElement dateTimeInput;

//  @FindBy(name = "datetime_local_input")
//  WebElement dateTimeLocalInput;

//  @FindBy(name = "time_input")
//  WebElement timeInput;

//  @FindBy(name = "month_input")
//  WebElement monthInput;

//  @FindBy(name = "week_input")
//  WebElement weekInput;

  @FindBy(name = "button_input")
  public WebElement buttonInput;

//  @FindBy(name = "image_input")
//  WebElement imageInput;

//  @FindBy(name = "reset_input")
//  WebElement resetInput;

//  @FindBy(name = "submit_input")
//  WebElement submitInput;

//  @FindBy(name = "hidden_input")
//  WebElement hiddenInput;


  public Inputs(WebDriver driver) {
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
