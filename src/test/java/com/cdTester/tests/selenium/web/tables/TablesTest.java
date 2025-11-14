package com.cdTester.tests.selenium.web.tables;

import com.cdTester.pages.Urls;
import com.cdTester.pages.selenium.web.TablesPage;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TablesTest extends BaseTest {
  protected TablesPage tablesPage;
  protected WebElement textInput;

  @BeforeEach
  void createSession() throws InterruptedException {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(0);
    driver.get(url.tables);
    tablesPage = new TablesPage(driver);
  }

  @AfterEach
  void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @DisplayName("Get Table Cell value by Row and Column")
  void cellByRowColumn() throws InterruptedException {
    WebElement row1Cell2 = tablesPage.getCellByRowAndColumn(tablesPage.baseTable, 1, 2);
    String cellText = row1Cell2.getText();
    assertEquals("World", cellText);
    Thread.sleep(1000);
  }

  @Test
  @Tag("regression")
  @DisplayName("Get column number by header name")
  void colNumberByHeader() throws InterruptedException {
    int colNumberTbody = tablesPage.getColumnNumberByHeader(tablesPage.countryTable, "Continent");
    WebElement headerValue1 = tablesPage.getHeaderByColumn(tablesPage.countryTable, colNumberTbody);
    assertEquals(3, colNumberTbody);
    Thread.sleep(500);
    tablesPage.unhighlightElement(headerValue1);

    int colNumberThead = tablesPage.getColumnNumberByHeader(tablesPage.namesTable, "City");
    WebElement headerValue2 = tablesPage.getHeaderByColumn(tablesPage.namesTable, colNumberThead);
    assertEquals(5, colNumberThead);
    Thread.sleep(500);
    tablesPage.unhighlightElement(headerValue2);
  }

  @Test
  @Tag("regression")
  @DisplayName("Get row number by cell value")
  void rowNumberByCellValue() throws InterruptedException {
    int row1 = tablesPage.getRowNumberByCellValue(tablesPage.namesTable, "Stark", true);
    assertEquals(2, row1);
    Thread.sleep(500);

    int row2 = tablesPage.getRowNumberByCellValues(tablesPage.namesTable, "Stark", "Brandon", true);
    assertEquals(6, row2);
    Thread.sleep(500);
  }

}