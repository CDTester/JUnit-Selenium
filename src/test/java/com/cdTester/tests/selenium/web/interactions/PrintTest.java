package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.bidi.browsingcontext.BrowsingContext;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.print.PageSize;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.utils.AllurePDFAttachment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Printing a webpage is a common task, whether for sharing information or maintaining archives.
 * Selenium simplifies this process through its PrintOptions, PrintsPage, and browsingContext classes,
 * which provide a flexible and intuitive interface for automating the printing of web pages.
 * These classes enable you to configure printing preferences, such as page layout, margins, and scaling,
 * ensuring that the output meets your specific requirements.
 */

@Epic("Epic: Working with print")
@Tag("print")
@ExtendWith(TestResultListener.class)
public class PrintTest extends BaseTest {

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      String[] args = {};
      HashMap<String, Object> capabilities = new HashMap<String, Object>();
      capabilities.put("webSocketUrl", true);
      driver = startChromeDriver(1, args, capabilities);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.base);
      driver.get(url.base);
    });
  }

  @Test
  @Tag("smoke")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Orientation")
  @TmsLink("TC-121")
  @DisplayName("Should be able to print in landscape")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1121")
  public void TestOrientation() {
    PrintOptions printOptions = new PrintOptions();
    Allure.step("WHEN the print orientation is set to landscape", step -> {
      printOptions.setOrientation(PrintOptions.Orientation.LANDSCAPE);
    });
    Allure.step("THEN the print orientation should be landscape", step -> {
      step.parameter("Print Orientation", printOptions.getOrientation().toString());
      assertEquals(PrintOptions.Orientation.LANDSCAPE, printOptions.getOrientation());
    });
  }


  @Test
  @Tag("regression")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Print Range")
  @TmsLink("TC-122")
  @DisplayName("Should be able set print page range")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1122")
  public void TestRange() {
    PrintOptions printOptions = new PrintOptions();
    Allure.step("WHEN the print page range is set to 1-2", step -> {
      printOptions.setPageRanges("1-2");
    });
    Allure.step("THEN the print page range should be 1-2", step -> {
      step.parameter("Print Page Range", Arrays.toString(printOptions.getPageRanges()));
      String[] expectedRange = {"1-2"};
      assertEquals(Arrays.toString(expectedRange), Arrays.toString(printOptions.getPageRanges()));
    });
  }

  @Test
  @Tag("regression")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Paper Size")
  @TmsLink("TC-123")
  @DisplayName("Should be able set page paper size")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1123")
  public void TestSize() {
    PrintOptions printOptions = new PrintOptions();

    Allure.step("WHEN the page size is set to A4", step -> {
      printOptions.setPageSize(new PageSize(27.94, 21.59)); // A4 size in cm
    });
    Allure.step("THEN the page height should be 27.94", step -> {
      step.parameter("Page Size Height", printOptions.getPageSize().getHeight());
      assertEquals(27.94, printOptions.getPageSize().getHeight());
    });
    Allure.step("AND the page width should be 21.59", step -> {
      step.parameter("Page Size Width", printOptions.getPageSize().getWidth());
      assertEquals(21.59, printOptions.getPageSize().getWidth());
    });
  }

  @Test
  @Tag("regression")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Print Margins")
  @TmsLink("TC-124")
  @DisplayName("Should be able set page paper margins")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1124")
  public void TestMargins() {
    PrintOptions printOptions = new PrintOptions();
    PageMargin margins = new PageMargin(1.1,1.2,1.3,1.4);

    Allure.step("WHEN the page margins are set", step -> {
      step.parameter("margins", margins.toString());
      printOptions.setPageMargin(margins);
    });
    Allure.step("THEN the top margin should be 1.1", step -> {
      step.parameter("Margin", printOptions.getPageMargin().getTop());
      assertEquals(1.1, printOptions.getPageMargin().getTop());
    });
    Allure.step("AND the bottom margin should be 1.2", step -> {
      step.parameter("Margin", printOptions.getPageMargin().getBottom());
      assertEquals(1.2, printOptions.getPageMargin().getBottom());
    });
    Allure.step("AND the left margin should be 1.3", step -> {
      step.parameter("Margin", printOptions.getPageMargin().getLeft());
      assertEquals(1.3, printOptions.getPageMargin().getLeft());
    });
    Allure.step("AND the right margin should be 1.4", step -> {
      step.parameter("Margin", printOptions.getPageMargin().getRight());
      assertEquals(1.4, printOptions.getPageMargin().getRight());
    });
  }

  @Test
  @Tag("regression")
  @TmsLink("TC-125")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Print Scale")
  @DisplayName("Should be able set print scale")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1125")
  public void TestScale() {
    PrintOptions printOptions = new PrintOptions();
    Allure.step("WHEN the print scale is set to 0.50", step -> {;
      step.parameter("Scale", 0.50);
      printOptions.setScale(.50);
    });
    Allure.step("THEN the print scale should be 0.50", step -> {
      step.parameter("Scale", printOptions.getScale());
      assertEquals(0.50, printOptions.getScale());
    });
  }

  @Test
  @Tag("regression")
  @TmsLink("TC-125")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Print Scale")
  @DisplayName("Should be able shrink the scale to fit on a page")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1126")
  public void TestShrinkToFit() {
    PrintOptions printOptions = new PrintOptions();

    Allure.step("WHEN the print scale is set to shrink to fit", step -> {;
      step.parameter("Shrink to fit", true);
      printOptions.setShrinkToFit(true);
    });
    Allure.step("THEN the print scale should be set to shrink to fit", step -> {
      step.parameter("Shrink to fit", printOptions.getShrinkToFit());
      assertTrue(printOptions.getShrinkToFit());
    });
  }

  @Test
  @Tag("regression")
  @TmsLink("TC-127")
  @Feature("Feature: PrintOptions Tests")
  @Story("Story: Print background images")
  @DisplayName("Should be able set the background images to print")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1127")
  public void TestBackground() {
    PrintOptions printOptions = new PrintOptions();

    Allure.step("WHEN the print background images is set to true", step -> {
      step.parameter("Print Background", true);
      printOptions.setBackground(true);
    });
    Allure.step("THEN the print background images should be set to true", step -> {
      step.parameter("Print Background", printOptions.getBackground());
      assertTrue(printOptions.getBackground());
    });
  }

  @Test
  @Tag("regression")
  @Feature("Feature: Print Tests")
  @Story("Story: Print")
  @TmsLink("TC-131")
  @DisplayName("Should be able to print current page using PrintsPage interface")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1131")
  public void PrintWithPrintsPage() {
    PrintOptions printOptions = new PrintOptions();
    printOptions.setOrientation(PrintOptions.Orientation.LANDSCAPE);
    printOptions.setScale(.50);
    PrintsPage printer = (PrintsPage) driver;
    AtomicReference<Pdf> printedPage = new AtomicReference<>();

    Allure.step("WHEN the current page is printed", step -> {
      printedPage.set(printer.print(printOptions));
      AllurePDFAttachment.attachPDF(printedPage.get(), "printed with PrintsPage");
    });
    Allure.step("THEN the printed page is not null", step -> {
      step.parameter("Printed with PrintsPage", printedPage.get().toString());
      assertNotNull(printedPage);
    });
  }

  @Test
  @Tag("regression")
  @Feature("Feature: Print Tests")
  @Story("Story: Print")
  @TmsLink("TC-132")
  @DisplayName("Should be able to print current page using BrowsingContext interface")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1132")
  public void PrintWithBrowsingContextTest() {
    BrowsingContext browsingContext = new BrowsingContext(driver, driver.getWindowHandle());
    PrintOptions printOptions = new PrintOptions();
    printOptions.setOrientation(PrintOptions.Orientation.LANDSCAPE);
    printOptions.setShrinkToFit(true);
    AtomicReference<String> printer = new AtomicReference<>();

    Allure.step("WHEN the current page is printed", step -> {
      printer.set(browsingContext.print(printOptions));
      AllurePDFAttachment.attachPDF(printer.get().toString(),"printed with BrowsingContext");
    });
    Allure.step("THEN the printed page is not null", step -> {
      step.parameter("Printed Page", printer.get());
      assertTrue(printer.get().length() > 0);
    });
  }
}