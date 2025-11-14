package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import org.junit.jupiter.api.*;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class SavingTest extends BaseTest {

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.base);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @Tag("saving")
  @DisplayName("Should be able to save page as PDF")
  public void prints() throws IOException {
    String content = ((RemoteWebDriver) driver).print(new PrintOptions()).getContent();
    byte[] bytes = Base64.getDecoder().decode(content);
    Files.write(Paths.get("selenium.pdf"), bytes);

    assertTrue(Files.exists(Paths.get("selenium.pdf")));
  }
}