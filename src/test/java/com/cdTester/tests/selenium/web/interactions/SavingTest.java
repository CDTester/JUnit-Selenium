package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Epic("Epic: Working with saving")
@Feature("Feature: Saving Tests")
@Tag("saving")
@ExtendWith(TestResultListener.class)
public class SavingTest extends BaseTest {

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
    });
  }


  @Test
  @Tag("smoke")
  @Story("Story: Save page")
  @TmsLink("TC-141")
  @DisplayName("Should be able to save page as PDF")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1141")
  public void prints() {
    String content = ((RemoteWebDriver) driver).print(new PrintOptions()).getContent();
    byte[] bytes = Base64.getDecoder().decode(content);

    Allure.step("WHEN the current page is printed", step -> {
      Files.write(Paths.get("selenium.pdf"), bytes);
      step.parameter("Saved File", Files.list(Path.of("."))
            .filter(p -> p.getFileName().toString().equals("selenium.pdf"))
            .findFirst()
            .get()
            .toAbsolutePath()
            .toString()
      );
    });
    Allure.step("THEN the PDF file exists", step -> {
      step.parameter("selenium.pdf exists", Files.exists(Paths.get("selenium.pdf")));
      assertTrue(Files.exists(Paths.get("selenium.pdf")));
    });
  }
}