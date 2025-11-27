package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.Arrays;
import java.util.List;


public class FormPage {
  public WebDriver driver;

  @FindBy(id = "email")
  public WebElement loginFormEmailInput;

  @FindBy(id = "age")
  public WebElement loginFormAgeInput;

  @FindBy(id = "submitButton")
  public WebElement loginFormSubmitButton;

  @FindBy(name = "selectomatic")
  public WebElement nonMultiSelect;
  @FindBy(css = "option[value=one]")
  public WebElement nonMultiOptionOne;
  @FindBy(css = "option[value=two]")
  public WebElement nonMultiOptionTwo;
  @FindBy(css = "option[value=four]")
  public WebElement nonMultiOptionFour;
  @FindBy(xpath = "//option[@value='still learning how to count, apparently']")
  public WebElement nonMultiOptionStillLearning;

  @FindBy(name = "multi")
  public WebElement multiSelect;
  @FindBy(css = "option[value=eggs]")
  public WebElement multiOptionEggs;
  @FindBy(css = "option[value=ham]")
  public WebElement multiOptionHam;
  @FindBy(css = "option[value=sausages]")
  public WebElement multiOptionSausages;
  @FindBy(xpath = "//option[@value='onion gravy']")
  public WebElement multiOptionGravy;

  @FindBy(name = "single_disabled")
  public WebElement singleSelectDisabled;
  @FindBy(id = "sinlge_disabled_1")
  public WebElement singleOptionEnabled;
  @FindBy(id = "sinlge_disabled_2")
  public WebElement singleOptionDisabled;

  public FormPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  /**
   * returns the Select class of the webElement.
   * @param element The WebElement to initiate a Select class for.
   * @return the Select Class of the webElement.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public Select getSelector(WebElement element) throws InterruptedException {
    Select select = new Select(element);
    Thread.sleep(500);

    return select;
  }


  /**
   * Highlights a web element (except radio and checkbox) and returns the webElement.
   * @param element The WebElement to be highlighted.
   * @return The same WebElement after highlighting.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement highlightElement(WebElement element) throws InterruptedException {
    Highlight.highlightElement(this.driver, element);
    return element;
  }

  /**
   * Highlights a web element (except radio and checkbox) then clicks on it.
   * @param element The WebElement to be highlighted.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public void click(WebElement element) throws InterruptedException {
    highlightElement(element);
    element.click();
  }

  public void type(WebElement element, String text) throws InterruptedException {
    highlightElement(element);
    element.sendKeys(text);
  }

  public void clearText(WebElement element) throws InterruptedException {
    highlightElement(element);
    element.clear();
  }

  public String getUrl() {
    return this.driver.getCurrentUrl();
  }

  public String getOptionsText(Select options) throws InterruptedException {
    return Arrays.toString(options.getOptions().stream().map(WebElement::getText).toArray());
  }

  public String getOptionsText(List<WebElement> options) throws InterruptedException {
    return Arrays.toString(options.stream().map(WebElement::getText).toArray());
  }
}
