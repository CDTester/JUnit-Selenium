package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.selenium.web.Windows;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Epic: Working with browser windows")
@Feature("Feature: Windows Tests")
@Tag("windows")
@ExtendWith(TestResultListener.class)
public class WindowsTest extends BaseTest {
  protected Windows windowPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("WHEN the web page has loaded", step -> {
      step.parameter("URL", url.windows);
      driver.get(url.windows);
      String title=driver.getTitle();
      step.parameter("Window title", title);
    });
    windowPage = new Windows(driver);
  }


  @Test
  @Tag("smoke")
  @Story("Story: Handles")
  @TmsLink("TC-171")
  @DisplayName("Should be able to get window handle")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1171")
  public void windowsHandle() {
    AtomicReference<String> currHandle = new AtomicReference<>();

    Allure.step("THEN that browser window will have a unique handle", step -> {
      currHandle.set(driver.getWindowHandle());
      step.parameter("Handle:", currHandle.get());
      assertNotNull(currHandle.get());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Handles")
  @TmsLink("TC-172")
  @DisplayName("Should be able to get all window handles")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1172")
  public void windowsHandles() {
    Allure.step("AND an new browser tab is opened", step -> {
      windowPage.openNewWindowLink.click();
      step.parameter(".openNewWindowLink", ".click()");
      String title=driver.getTitle();
      step.parameter("Window title", title);
    });
    Allure.step("THEN you can get all the window handles", step -> {
      step.parameter(".getWindowHandles()", "fetch handles of all windows, [0] = default and [1] = new window");
      Object[] windowHandles = driver.getWindowHandles().toArray();
      for(Object handle: windowHandles){
        step.parameter("handle:", handle);
        assertNotNull(handle);
      }
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Switching tabs")
  @TmsLink("TC-173")
  @DisplayName("Should be able to switch to a new tab using window handles")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1173")
  public void SwitchToNewTab() {
    AtomicReference<Object[]> handles = new AtomicReference<>();

    Allure.step("AND an new browser tab is opened", step -> {
      windowPage.openNewWindowLink.click();
      step.parameter(".openNewWindowLink", ".click()");
      String title=driver.getTitle();
      step.parameter("Window title", title);
    });
    Allure.step("AND the window handles are collected", step -> {
      step.parameter(".getWindowHandles()", "fetch handles of all windows, [0] = default and [1] = new window");
      handles.set(driver.getWindowHandles().toArray());
      for (Object handle : handles.get()) {
        step.parameter("handle:", handle);
        assertNotNull(handle);
      }
    });
    Allure.step("THEN you can switch to the new tab using the handle", step -> {
      driver.switchTo().window(handles.get()[1].toString());
      String title=driver.getTitle();
      step.parameter("Window title", title);
      assertEquals("Simple Page",title);
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Switching tabs")
  @TmsLink("TC-174")
  @DisplayName("Should be able to switch to a new tab using newWindow")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1174")
  public void opensNewTab() {
    Allure.step("THEN a new browser tab can be opened and switched to", step -> {
      step.parameter(".newWindow(WindowType.TAB)", "opens and switches to the new tab)");
      driver.switchTo().newWindow(WindowType.TAB);
      String title=driver.getTitle();
      step.parameter("Window title", title);
      assertEquals("",driver.getTitle());
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Switching windows")
  @TmsLink("TC-175")
  @DisplayName("Should be able to open a new window and switch to it")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1175")
  public void opensNewWindow() {
    Allure.step("THEN a new browser can be opened and switched to", step -> {
      step.parameter(".newWindow(WindowType.TAB)", "opens and switches to the new window)");
      driver.switchTo().newWindow(WindowType.WINDOW);
      String title=driver.getTitle();
      step.parameter("Window title", title);
      assertEquals("",driver.getTitle());
    });
    Allure.step("AND the new browser can be closed and switched to original browser", step -> {
      step.parameter(".close()", "When you are finished with a window or tab and it is not the last window " +
                  "or tab open in your browser, you should close it and switch back to the window you were using previously."
      );
      Object[] windowHandles=driver.getWindowHandles().toArray();
      driver.close();
      driver.switchTo().window((String) windowHandles[0]);
      String title=driver.getTitle();
      step.parameter("Window title", title);
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: window size")
  @TmsLink("TC-176")
  @DisplayName("Should be able to change the window size")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1176")
  public void windowSize() {
    AtomicInteger width = new AtomicInteger();
    AtomicInteger height = new AtomicInteger();

    Allure.step("THEN the size of the window can be determined", step -> {
      width.set(driver.manage().window().getSize().getWidth());
      step.parameter("width", width.get());
      height.set(driver.manage().window().getSize().getHeight());
      step.parameter("height", height.get());
    });
    Allure.step("AND the window size can be changed", step -> {
      driver.manage().window().setSize(new Dimension(width.get() / 2, height.get() / 2));
      int newWidth = driver.manage().window().getSize().getWidth();
      int newHeight = driver.manage().window().getSize().getHeight();
      step.parameter("new width", newWidth);
      step.parameter("height", newHeight);
      assertNotEquals(width, newWidth);
      assertNotEquals(height, newHeight);
    });
  }

}