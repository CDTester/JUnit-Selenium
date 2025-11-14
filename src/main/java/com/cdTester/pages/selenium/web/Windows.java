package com.cdTester.pages.selenium.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Windows {
  WebDriver driver;

  @FindBy(id = "a-link-that-opens-a-new-window")
  public WebElement openNewWindowLink;


  public Windows(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

}
