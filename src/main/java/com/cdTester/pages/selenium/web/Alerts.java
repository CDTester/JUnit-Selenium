package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class Alerts {
  public WebDriver driver;
  protected WebDriverWait wait;

  @FindBy(id = "alert")
  public WebElement alertLink;

  @FindBy(id = "empty-alert")
  public WebElement emptyAlertLink;

  @FindBy(id = "prompt")
  public WebElement promptLink;

  @FindBy(id = "prompt-with-default")
  public WebElement promptWithDefaultLink;

  @FindBy(id = "double-prompt")
  public WebElement doublePromptLink;

  @FindBy(id = "slow-alert")
  public WebElement slowAlertLink;

  @FindBy(id = "confirm")
  public WebElement confirmAlertLink;

  public By iframeWindow = By.name("iframeWithAlert");

  @FindBy(id = "alertInFrame")
  public WebElement iframeLink;

  public By nestedIframeWindow = By.name("iframeWithIframe");

  public Alerts(WebDriver driver) {
    this.driver = driver;
    this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
    PageFactory.initElements(driver, this);
  }

  public void click(WebElement element) throws InterruptedException {
    Highlight.highlightElement(this.driver, element);
    element.click();
    this.wait.until(ExpectedConditions.alertIsPresent());
  }

  public void createJsAlert(String jsCode) {
    JavascriptExecutor js = (JavascriptExecutor) this.driver;
    js.executeScript(jsCode);
    this.wait.until(ExpectedConditions.alertIsPresent());
  }

  public String getAlertMessage() throws InterruptedException {
    Alert alert = this.driver.switchTo().alert();
    Thread.sleep(500);
    return alert.getText();
  }

  public String getAlertMessageAndClose(String acceptDismiss) throws InterruptedException {
    Alert alert = this.driver.switchTo().alert();
    String message = alert.getText();
    Thread.sleep(500);

    if (acceptDismiss == "accept") {
      alert.accept();
    }
    else {
      alert.dismiss();
    }
    return message;
  }

  public void sendKeysToAlertAndClose(String text, String acceptDismiss) throws InterruptedException {
    Alert alert = this.driver.switchTo().alert();
    alert.sendKeys(text);
    Thread.sleep(500);
    if (acceptDismiss == "accept") {
      alert.accept();
    }
    else {
      alert.dismiss();
    }
  }

  public String getUrl() {
    return this.driver.getCurrentUrl();
  }

  public WebDriver switchToIframe(By iframe) {

    WebElement iframeElement = this.driver.findElement(iframe);
    System.out.println("Switching to iFrame: " + iframeElement.getAttribute("name"));

    return this.driver.switchTo().frame(iframeElement);
  }

}
