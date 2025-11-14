package com.cdTester.pages.selenium.web;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {
  WebDriver driver;

  @FindBy(id = "username-field")
  public WebElement usernameField;

  @FindBy(id = "password-field")
  public WebElement passwordField;

  @FindBy(id = "login-form-submit")
  public WebElement loginButton;

  public Login(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void login(String user, String password){
    usernameField.sendKeys(user);
    passwordField.sendKeys(password);
    loginButton.click();
  }

  public String getLoginStatusAndClose() {
    Alert alert = this.driver.switchTo().alert();
    String message = alert.getText();
    alert.accept();
    return message;
  }
}
