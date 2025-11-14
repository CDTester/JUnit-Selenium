package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.Urls;
import com.cdTester.pages.theInternetHerokuapp.Upload;
import com.cdTester.tests.selenium.web.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class FileUploadTest extends BaseTest {

  protected String filePath = "src/test/resources/";
  protected String fileName = "selenium-snapshot.png";

  @Test
  @Tag("regression")
  @DisplayName("Should be able to Upload a fileName")
  public void fileUploadTest() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.upload);
    Upload uploadPage = new Upload(driver);

    /* The following is replaced by Page Object Model
     *    File uploadFile = new File(filePath + fileName);
     *    WebElement fileInput = driver.findElement(By.cssSelector("input[type=fileName]"));
     *    fileInput.sendKeys(uploadFile.getAbsolutePath());
     *    driver.findElement(By.id("fileName-submit")).click();
    */
    uploadPage.uploadFile(filePath, fileName);


    /* The following is replaced by Page Object Model
     *    WebElement fileName = driver.findElement(By.id("uploaded-files"));
    */
    assertEquals(fileName, uploadPage.getUploadedFile());
  }
}
