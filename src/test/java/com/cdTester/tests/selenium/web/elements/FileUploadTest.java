package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.Urls;
import com.cdTester.pages.theInternetHerokuapp.Upload;
import com.cdTester.tests.selenium.web.BaseTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@Epic("Epic: Working with file uploads")
@Feature("Feature: Upload File Tests")
@Tag("uploadFile")
@ExtendWith(TestResultListener.class)
public class FileUploadTest extends BaseTest {
  protected Upload uploadPage;
  protected String filePath = "src/test/resources/";
  protected String fileName = "selenium-snapshot.png";

  @Test
  @Tag("regression")
  @Story("Story: Upload Files")
  @TmsLink("TC-031")
  @DisplayName("Should be able to Upload a png file")
  public void fileUploadTest() {
    Urls url = new Urls(BaseTest.config, "herokuapp");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.upload);
      driver.get(url.upload);
      String title=driver.getTitle();
      if (!title.equals(Upload.title)) {
        driver.navigate().refresh();
        title=driver.getTitle();
      }
      step.parameter("title", title);
      assertEquals(Upload.title, title);
    });
    uploadPage = new Upload(driver);

    Allure.step("WHEN a png file is uploaded to a file path", step -> {
      step.parameter("file name: ", fileName);
      step.parameter("file path: ", filePath);
      /* The following is replaced by Page Object Model
       *    File uploadFile = new File(filePath + fileName);
       *    WebElement fileInput = driver.findElement(By.cssSelector("input[type=fileName]"));
       *    fileInput.sendKeys(uploadFile.getAbsolutePath());
       *    driver.findElement(By.id("fileName-submit")).click();
       */
      uploadPage.uploadFile(filePath, fileName);
    });
    Allure.step("THEN the heading 'File Uploaded!' will be displayed", step -> {
      step.parameter("Heading:", uploadPage.getHeading());
      assertEquals("File Uploaded!", uploadPage.getHeading());
    });
    Allure.step("THEN the message '" + fileName + "' will be displayed", step -> {
      step.parameter("file name:", uploadPage.getUploadedFile());
      assertEquals(fileName, uploadPage.getUploadedFile());
    });
  }
}
