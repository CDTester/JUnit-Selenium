package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.selenium.web.Windows;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import static org.junit.jupiter.api.Assertions.*;

public class WindowsTest extends BaseTest {
  protected Windows windowPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.windows);
    windowPage = new Windows(driver);
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @Tag("windows")
  @DisplayName("Should be able to get window handle")
  public void windowsHandle() throws InterruptedException{
    //fetch handle of this
    String currHandle=driver.getWindowHandle();
    System.out.println("Current handle is: "+currHandle);

    Thread.sleep(500);
    assertNotNull(currHandle);
  }

  @Test
  @Tag("regression")
  @Tag("windows")
  @DisplayName("Should be able to get all window handles")
  public void windowsHandles() throws InterruptedException{
    //click on link to open a new window
    windowPage.openNewWindowLink.click();

    //fetch handles of all windows, there will be two, [0]- default, [1] - new window
    Object[] windowHandles = driver.getWindowHandles().toArray();
    driver.switchTo().window((String) windowHandles[1]);

    //assert all window handles are not null
    System.out.println("windowHandles are: ");
    for(Object handle: windowHandles){
      System.out.println(handle);
      assertNotNull(handle);
    }

    Thread.sleep(500);
  }

  @Test
  @Tag("regression")
  @Tag("windows")
  @DisplayName("Should be able to switch to a new tab using window handles")
  public void SwitchToNewTab() throws InterruptedException {
    //click on link to open a new window
    windowPage.openNewWindowLink.click();

    //fetch handles of all windows, there will be two, [0]- default, [1] - new window
    Object[] windowHandles = driver.getWindowHandles().toArray();
    driver.switchTo().window(windowHandles[1].toString());

    Thread.sleep(1000);

    //assert on title of new window
    String title=driver.getTitle();
    assertEquals("Simple Page",title);
  }

  @Test
  @Tag("regression")
  @Tag("windows")
  @DisplayName("Should be able to open a new tab and switch to it")
  public void opensNewTab() throws InterruptedException {
    //Opens a new tab and switches to new tab
    driver.switchTo().newWindow(WindowType.TAB);
    Thread.sleep(1000);

    assertEquals("",driver.getTitle());
  }


  @Test
  @Tag("regression")
  @Tag("windows")
  @DisplayName("Should be able to open a new window and switch to it")
  public void opensNewWindow() throws InterruptedException {
    //Opens a new window and switches to new window
    driver.switchTo().newWindow(WindowType.WINDOW);
    Thread.sleep(1000);

    assertEquals("",driver.getTitle());

    driver.close();
  }
}