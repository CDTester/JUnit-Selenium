package com.cdTester.pages.selenium.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frames {
  public WebDriver driver;

  public By iframeWindowById = By.id("iframe1");
  public By iframeWindowByName = By.name("iframe1-name");

  public Frames(WebDriver driver) {
    this.driver = driver;
  }

  public WebDriver switchToIframe(By iframe) {

    WebElement iframeElement = this.driver.findElement(iframe);
    System.out.println("Switching to iFrame: " + iframeElement.getAttribute("name"));

    return this.driver.switchTo().frame(iframeElement);
  }

  public String getUrl() {
    return this.driver.getCurrentUrl();
  }

  public String getTitle() {
    return this.driver.getTitle();
  }

  public String getPageSourceTitle() {
    String source = this.driver.getPageSource().toString();
    String regex = "<title>(.*?)</title>";
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    Matcher matcher = pattern.matcher(source);

    if (matcher.find()) {
      return matcher.group(1).trim();
    }
    return "";
  }


}
