package com.cdTester.tests.selenium.web.tables;

import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.TablesPage;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Epic("Epic: Working with web tables")
@Feature("Feature: Tables Tests")
@Tag("Tables")
@ExtendWith(TestResultListener.class)
public class TablesTest extends BaseTest {
  protected TablesPage tablesPage;

  @BeforeEach
  void createSession() {
    Urls url = new Urls(BaseTest.config, "tables");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(0);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.tables);
      driver.get(url.tables);
      String title=driver.getTitle();
      step.parameter("Window title", title);
    });
    tablesPage = new TablesPage(driver);
  }


  @Test
  @Tag("smoke")
  @Story("Story: Get table cell value")
  @TmsLink("TC-181")
  @DisplayName("Get Table Cell value by Row and Column")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1181")
  void cellByRowColumn() {
    AtomicReference<WebElement> row1Cell2 = new AtomicReference<>();

    Allure.step("AND the table is present on the page", step -> {
      step.parameter("Table id:", tablesPage.baseTable.getAttribute("id"));
      step.parameter("Table data:", tablesPage.baseTable.getText());
      assert(tablesPage.baseTable.isDisplayed());
    });
    Allure.step("WHEN the table cell has been found for column 2, row 1", step -> {
      WebElement element = tablesPage.getCellByRowAndColumn(tablesPage.baseTable, 1, 2);
      step.parameter("value:", element.getText());
      row1Cell2.set(element);
    });
    Allure.step("THEN the value in that cell will be 'World" , step -> {
      String cellText = row1Cell2.get().getText();
      step.parameter("value:", cellText);
      assertEquals("World", cellText);
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Find a table column")
  @TmsLink("TC-182")
  @DisplayName("Get column number by header name in a table without a <thead>")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1182")
  void colNumberByHeaderWithoutTHead() {
    AtomicInteger colNumberTbody = new AtomicInteger();

    Allure.step("AND the table is present on the page", step -> {
      step.parameter("Table id:", tablesPage.countryTable.getAttribute("id"));
      step.parameter("Table data:", tablesPage.countryTable.getText());
      assert(tablesPage.countryTable.isDisplayed());
    });
    Allure.step("WHEN the 'Continent' header is found in a table without a <thead>", step -> {
      colNumberTbody.set(tablesPage.getColumnNumberByHeader(tablesPage.countryTable, "InContinent"));
      step.parameter("column index:", colNumberTbody.get());
    });
    Allure.step("THEN the column index should be 3", step -> {
      step.parameter("column index:", colNumberTbody.get());
      assertEquals(3, colNumberTbody.get());
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Find a table column")
  @TmsLink("TC-183")
  @DisplayName("Get column number by header name in a table with <thead>")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1183")
  void colNumberByHeaderWithTHead() {
    AtomicInteger colNumberTbody = new AtomicInteger();

    Allure.step("AND the table is present on the page", step -> {
      step.parameter("Table id:", tablesPage.namesTable.getAttribute("id"));
      step.parameter("Table data:", tablesPage.namesTable.getText());
      assert(tablesPage.namesTable.isDisplayed());
    });
    Allure.step("WHEN the 'City' header is found in a table without a <thead>", step -> {
      int element = tablesPage.getColumnNumberByHeader(tablesPage.namesTable, "City");
      step.parameter("column index:", element);
      colNumberTbody.set(element);
    });
    Allure.step("THEN the column index should be 5", step -> {
      // should be 5, example to show assertion error
      step.parameter("column index:", colNumberTbody.get());
      assertEquals(4, colNumberTbody.get());
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Find a table row")
  @TmsLink("TC-184")
  @DisplayName("Get row number by cell value")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1184")
  void rowNumberByCellValue() {
    AtomicInteger row1 = new AtomicInteger();

    Allure.step("AND the table is present on the page", step -> {
      step.parameter("Table id:", tablesPage.namesTable.getAttribute("id"));
      step.parameter("Table data:", tablesPage.namesTable.getText());
      assert(tablesPage.namesTable.isDisplayed());
    });
    Allure.step("WHEN a row is found that contains a cell value of 'Stark'", step -> {
      int element = tablesPage.getRowNumberByCellValue(tablesPage.namesTable, "Stark", true);
      step.parameter("row:", tablesPage.getRowByCellValue(tablesPage.namesTable, "Stark").getText());
      row1.set(element);
    });
    Allure.step("THEN the row index should be 2", step -> {
      step.parameter("row index:", row1.get());
      assertEquals(2, row1.get());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Find a table row")
  @TmsLink("TC-185")
  @DisplayName("Get row number by cell value that repeats in other rows")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1185")
  void rowNumberByCellValueDuplicate() {
    AtomicInteger row1 = new AtomicInteger();

    Allure.step("AND the table is present on the page", step -> {
      step.parameter("Table id:", tablesPage.namesTable.getAttribute("id"));
      step.parameter("Table data:", tablesPage.namesTable.getText());
      assert(tablesPage.namesTable.isDisplayed());
    });
    Allure.step("WHEN a row is found that contains cells with value of 'Stark' and 'Brandon'", step -> {
      int element = tablesPage.getRowNumberByCellValues(tablesPage.namesTable, "Stark", "Brandon", true);
      step.parameter("row:", tablesPage.getRowByCellValues(tablesPage.namesTable, "Stark", "Brandon").getText());
      row1.set(element);
    });
    Allure.step("THEN the row index should be 6", step -> {
      step.parameter("row index:", row1.get());
      assertEquals(6, row1.get());
    });
  }
}