package com.cdTester.pages.theInternetHerokuapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.File;

public class Upload {
  WebDriver driver;

  @FindBy(css = "input[type=file]")
  WebElement chooseFileButton;

  @FindBy(id = "uploaded-files")
  WebElement fileUploaded;

  @FindBy(id = "file-submit")
  WebElement uploadButton;

  public Upload(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  public void uploadFile(String filePath, String fileName){
    File uploadFile = new File(filePath + fileName);
    chooseFileButton.sendKeys(uploadFile.getAbsolutePath());
    uploadButton.click();
  }

  public String getUploadedFile() {
    return fileUploaded.getText();
  }
}
