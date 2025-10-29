package com.cdTester.pages.selenium.web;

import com.cdTester.utils.Highlight;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.NoSuchElementException;


public class TablesPage {
  public WebDriver driver;

  @FindBy(id = "base")
  public WebElement baseTable;

  @FindBy(xpath = "//table[2]")
  public WebElement secondTable;

  @FindBy(id = "hidden_text")
  public WebElement secondTableHiddenCell;

  @FindBy(xpath = "//table[3]")
  public WebElement thirdTable;

  @FindBy(id = "th1")
  public WebElement thirdTableHeaderRow;

  @FindBy(id = "td1")
  public WebElement thirdTableCell1;

  @FindBy(id = "td2")
  public WebElement thirdTableCell2;

  @FindBy(id = "headerUnderTbody")
  public WebElement countryTable;

  @FindBy(id = "headerUnderThead")
  public WebElement namesTable;

  /*
  @FindBy(css = "option[value=one]")
  public WebElement nonMultiOptionOne;
  @FindBy(css = "option[value=four]")
  public WebElement nonMultiOptionFour;
  @FindBy(xpath = "//option[@value='still learning how to count, apparently']")
  public WebElement nonMultiOptionStillLearning;
*/

  public TablesPage(WebDriver driver) {
    this.driver = driver;
    PageFactory.initElements(driver, this);
  }

  /**
   * gets and highlights a table cell(<td>) by give row number and column number.
   * @param table The table which contains the cell.
   * @param row The row number the cell belongs to.
   * @param column The column number of the cell.
   * @return The WebElement of the cell.
   * @throws NoSuchElementException If the cell cannot be found.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement getCellByRowAndColumn(WebElement table, int row, int column) throws NoSuchElementException, InterruptedException {
    return highlightElement(table.findElement(By.xpath(".//tbody/tr[" + row + "]/td[" + column + "]")));
  }

  /**
   * gets and highlights the table header(<th>) by a given column number.
   * @param table The table which contains the cell.
   * @param column The column number of the table header.
   * @return The WebElement of the table header.
   * @throws NoSuchElementException If the cell cannot be found.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement getHeaderByColumn(WebElement table, int column) throws NoSuchElementException, InterruptedException {
    try {
      WebElement header = table.findElement(By.xpath("//th[" + column + "]"));
      return highlightElement(header);
    }
    catch (NoSuchElementException | InterruptedException e) {
      System.out.println("Header not found in table.");
      return table;
    }
  }

  /**
   * gets the column number of a table header.
   * @param table The table which contains the cell.
   * @param header The value of the table header.
   * @return The index (column number) of the table header.
   * @throws NoSuchElementException If the cell cannot be found.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public int getColumnNumberByHeader(WebElement table, String header) {
    List<WebElement> allHeaders = table.findElements(By.xpath(".//th"));

    for (int i = 0; i < allHeaders.size(); i++) {
      if (allHeaders.get(i).getText().trim().equalsIgnoreCase(header.trim())) {
        return i + 1;
      }
    }
    return -1;
  }


  /**
   * gets the table row(<tr>) that contains the cell value. Will return the first matching row.
   * @param table The table which contains the cell.
   * @param cellValue The value of the table cell.
   * @return The webElement of the table cell.
   * @throws NoSuchElementException If the cell cannot be found.
   */
  public WebElement getRowByCellValue(WebElement table, String cellValue) throws NoSuchElementException {
    return table.findElement(By.xpath("//td[text()='" + cellValue + "']/parent::tr"));
  }

  /**
   * gets the table row(<tr>) that contains the values of two cells. Will return the first matching row.
   * @param table The table which contains the cell.
   * @param cellValue1 The value of the first table cell.
   * @param cellValue2 The value of the second table cell.
   * @return The webElement of the table cell.
   * @throws NoSuchElementException If the cell cannot be found.
   */
  public WebElement getRowByCellValues(WebElement table, String cellValue1, String cellValue2) throws InterruptedException {
    return table.findElement(By.xpath("//tr[./td[contains(.,'" + cellValue1 +"')] and ./td[contains(.,'" + cellValue2 + "')]]"));
  }

  /**
   * gets the table row number that contains the cell value. Will return the first matching row.
   * @param table The table which contains the cell.
   * @param cellValue The value of the table cell.
   * @param hasHeader true if table has a header row
   * @return The index (row number) of the table row.
   * @throws NoSuchElementException If the cell cannot be found.
   */
  public int getRowNumberByCellValue(WebElement table, String cellValue, boolean hasHeader) {
    List<WebElement> allRows = table.findElements(By.xpath(".//tr"));

    for (int i = 0; i < allRows.size(); i++) {
      if (allRows.get(i).getText().trim().contains(cellValue.trim())) {
        int headerOffset = hasHeader ? 0 : 1;
        return i + headerOffset;
      }
    }
    return -1;
  }

  /**
   * gets the table row number that contains the value of two cells. Will return the first matching row.
   * @param table The table which contains the cell.
   * @param cellValue1 The value of the first table cell.
   * @param cellValue2 The value of the second table cell.
   * @param hasHeader true if table has a header row
   * @return The index (row number) of the table row.
   * @throws NoSuchElementException If the cell cannot be found.
   */
  public int getRowNumberByCellValues(WebElement table, String cellValue1, String cellValue2, boolean hasHeader) {
    List<WebElement> allRows = table.findElements(By.xpath(".//tr"));

    for (int i = 0; i < allRows.size(); i++) {
      if (allRows.get(i).getText().trim().contains(cellValue1.trim()) && allRows.get(i).getText().trim().contains(cellValue2.trim())) {
        int headerOffset = hasHeader ? 0 : 1;
        return i + headerOffset;
      }
    }
    return -1;
  }


  /**
   * Highlights a web element (except radio and checkbox) and returns the webElement.
   * @param element The WebElement to be highlighted.
   * @return The same WebElement after highlighting.
   * @throws InterruptedException If the thread is interrupted during sleep.
   */
  public WebElement highlightElement(WebElement element) throws InterruptedException {
    Highlight.highlightElement(this.driver, element);
    return element;
  }

  public WebElement unhighlightElement(WebElement element) throws InterruptedException {
    Highlight.unhighlightElement(this.driver, element);
    return element;
  }



}
