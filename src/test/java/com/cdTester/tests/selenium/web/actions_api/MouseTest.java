package com.cdTester.tests.selenium.web.actions_api;

import com.cdTester.pages.selenium.web.MouseInteraction;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.Collections;

@Epic("Epic: Working with mouse")
@Feature("Feature: Mouse Tests")
@Tag("mouse")
@ExtendWith(TestResultListener.class)
public class MouseTest extends BaseTest {
  protected MouseInteraction mousePage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.mouseInteraction);
      driver.get(url.mouseInteraction);
      String title=driver.getTitle();
      step.parameter("title", title);
      assertEquals(MouseInteraction.title, title);
    });
    mousePage = new MouseInteraction(driver);
  }

  @AfterEach
  public void endSession() {
    /* An important thing to note is that the driver remembers the state of all the input items throughout a session.
       Even if you create a new instance of an actions class, the depressed keys and the location of the pointer
       will be in whatever state a previously performed action left them.
    */
    ((RemoteWebDriver) driver).resetInputState();
  }

  @Test
  @Tag("smoke")
  @Story("Story: Actions")
  @TmsLink("TC-021")
  @DisplayName("Should be able to left click an element using Actions")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1021")
  public void clickLeft() {
    WebElement click = mousePage.clickLink;
    Allure.step("AND the 'Click for Results Page' is visible", step -> {
      mousePage.highlightElement(click);
      step.parameter("id:", click.getAttribute("id"));
      step.parameter("isDisplayed()", click.isDisplayed());
      assertTrue(click.isDisplayed());
    });
    Allure.step("WHEN the link is left-clicked using Actions", step -> {
      step.parameter("Actions(driver)", ".click(WebElement)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .click(click)
            .perform();
    });
    Allure.step("THEN the browser is navigated to resultPage.html", step -> {
      step.parameter("Current URL:", driver.getCurrentUrl());
      assertTrue(driver.getCurrentUrl().contains("resultPage.html"));
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-022")
  @DisplayName("Should be able to right click an element using Actions")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1022")
  public void clickRight() {
    WebElement clickable = mousePage.clickableInput;
    Allure.step("AND the 'Clickable' input field is visible", step -> {
      mousePage.highlightElement(clickable);
      step.parameter("id:", clickable.getAttribute("id"));
      step.parameter("isDisplayed()", clickable.isDisplayed());
      assertTrue(clickable.isDisplayed());
    });
    Allure.step("WHEN the link is right-clicked using Actions", step -> {
      step.parameter("Actions(driver)", ".contextClick(WebElement)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .contextClick(clickable)
            .perform();
    });
    Allure.step("THEN the text 'context-clicked' is displayed", step -> {
      WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
      step.parameter("text:", clickStatus.getText());
      assertEquals("context-clicked", clickStatus.getText(),
            "message does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-023")
  @DisplayName("Should be able to double click an element using Actions")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1023")
  public void clickDouble() {
   WebElement clickable = mousePage.clickableInput;
    Allure.step("AND the 'Clickable' input field is visible", step -> {
      mousePage.highlightElement(clickable);
      step.parameter("id:", clickable.getAttribute("id"));
      step.parameter("isDisplayed()", clickable.isDisplayed());
      assertTrue(clickable.isDisplayed());
    });
    Allure.step("WHEN the link is double-clicked using Actions", step -> {
      step.parameter("Actions(driver)", ".doubleClick(WebElement)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .doubleClick(clickable)
            .perform();
    });
    Allure.step("THEN the text 'double-clicked' is displayed", step -> {
      WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
      step.parameter("text:", clickStatus.getText());
      assertEquals("double-clicked", clickStatus.getText(),
            "message does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-024")
  @DisplayName("Should be able to click and hold")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1024")
  public void clickAndHold() {
    WebElement clickable = mousePage.clickableInput;
    long start = System.currentTimeMillis();
    Allure.step("AND the 'Clickable' input field is visible", step -> {
      mousePage.highlightElement(clickable);
      step.parameter("id:", clickable.getAttribute("id"));
      step.parameter("isDisplayed()", clickable.isDisplayed());
      assertTrue(clickable.isDisplayed());
    });
    Allure.step("WHEN the link is click and held using Actions", step -> {
      step.parameter("Actions(driver)", ".clickAndHold(WebElement)");
      step.parameter("", ".pause(Duration.ofSeconds(2))");
      step.parameter("", ".perform()");
      new Actions(driver)
            .clickAndHold(clickable)
            .pause(Duration.ofSeconds(2))
            .perform();
    });
    long duration = System.currentTimeMillis() - start;
    Allure.step("THEN the total length of time the click was held for should be over 2 seconds", step -> {
      step.parameter("held duration in ms:", duration);
      assertTrue(duration > 2000);
    });
    Allure.step("AND the text 'focused' is displayed", step -> {
      WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
      step.parameter("text:", clickStatus.getText());
      assertEquals("focused", clickStatus.getText(),
            "message does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-025")
  @DisplayName("Should be able to hover over an element using Actions")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1025")
  public void hover() {
    WebElement hoverable = mousePage.hoverableButton;
    Allure.step("AND the 'Hoverable' button is visible", step -> {
      mousePage.highlightElement(hoverable);
      step.parameter("id:", hoverable.getAttribute("id"));
      step.parameter("onmouseover:", hoverable.getDomAttribute("onmouseover"));
      step.parameter("value:", hoverable.getDomAttribute("value"));
      step.parameter("isDisplayed()", hoverable.isDisplayed());
      assertTrue(hoverable.isDisplayed());
    });
    Allure.step("WHEN the link is hovered over using Actions", step -> {
      step.parameter("Actions(driver)", ".moveToElement(WebElement)");
      step.parameter("", ".pause(Duration.ofMillis(500))");
      step.parameter("", ".perform()");
      new Actions(driver)
            .pause(Duration.ofMillis(500))
            .moveToElement(hoverable)
            .perform();
    });
    Allure.step("AND the text 'hovered' is displayed", step -> {
      WebElement hoverStatus = mousePage.highlightElement(mousePage.hoverableStatus);
      step.parameter("text:", hoverStatus.getText());
      assertEquals("hovered",
            hoverStatus.getText(),
            "message does not match the action performed"
      );
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-026")
  @DisplayName("Should be able to drag and drop an element using Actions")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1026")
  public void dragAndDrop() {
    WebElement draggable = mousePage.draggableBox;
    WebElement droppable = mousePage.droppableBox;
    Allure.step("AND the 'Draggable' box is visible", step -> {
      mousePage.highlightElement(draggable);
      step.parameter("id:", draggable.getAttribute("id"));
      step.parameter("isDisplayed()", draggable.isDisplayed());
      assertTrue(draggable.isDisplayed());
      mousePage.unhighlightElement(mousePage.draggableBox);
    });
    Allure.step("AND the 'Droppable' box is visible", step -> {
      mousePage.highlightElement(droppable);
      step.parameter("id:", droppable.getAttribute("id"));
      step.parameter("isDisplayed()", droppable.isDisplayed());
      assertTrue(droppable.isDisplayed());
    });
    Allure.step("WHEN the is dragged and dropped using Actions", step -> {
      step.parameter("Actions(driver)", ".dragAndDrop(dragWebElement, dropToWebElement)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .dragAndDrop(draggable, droppable)
            .perform();
    });
    Allure.step("THEN the text 'dropped' is displayed", step -> {
      WebElement droppedStatus = mousePage.highlightElement(mousePage.droppedStatus);
      step.parameter("text:", droppedStatus.getText());
      assertEquals("dropped", droppedStatus.getText(),
            "message does not match the action performed"
      );
    });
    Allure.step("AND the relative position of the dropped box can be verified", step -> {
      WebElement droppedStatus = mousePage.highlightElement(mousePage.droppedStatus);
      step.parameter("position should be:", "position: relative; left: 9px; top: 98px");
      step.parameter("position is:", draggable.getAttribute("style"));
      assertEquals("position: relative; left: 9px; top: 98px;",
            draggable.getAttribute("style"),
            "Box not dropped in the correct location"
      );
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: Sequence")
  @TmsLink("TC-027")
  @DisplayName("Should be able to move mouse pointer to an absolute location using Pointer and Sequence API")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1027")
  public void absoluteLocation() {
    WebElement absolute = mousePage.absoluteLocationValues;
    Allure.step("AND the 'Absolute Location' does not have any values", step -> {
      mousePage.highlightElement(absolute);
      step.parameter("id:", absolute.getAttribute("id"));
      step.parameter("isDisplayed()", absolute.isDisplayed());
      step.parameter("text", absolute.getText());
      assertTrue(absolute.isDisplayed());
      assertEquals("", absolute.getText(),"Box is not empty");
    });
    Allure.step("WHEN the mouse is moved to a location using Sequence", step -> {
      step.parameter("Sequence(PointerInput, 0)", "where PointerInput(PointerInput.Kind.MOUSE, \"default mouse\")");
      step.parameter(".addAction(", "PointerInput.createPointerMove(");
      step.parameter("", "Duration.ZERO, PointerInput.Origin.viewport(), 70, 100");
      step.parameter("", ")");
      step.parameter(")", "");
      PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
      Sequence actions = new Sequence(mouse, 0)
           .addAction(mouse.createPointerMove(
                 Duration.ZERO, PointerInput.Origin.viewport(), 70, 100
                 )
           );
      ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));
    });
    Allure.step("THEN the text '70, 219' is displayed", step -> {
      step.parameter("text:", absolute.getText());
      assertEquals("70, 219", absolute.getText(), "message does not match the action performed");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Actions")
  @TmsLink("TC-028")
  @DisplayName("Should be able to move mouse pointer to a relative location using Actions API")
  @Severity(SeverityLevel.TRIVIAL)
  @Owner("QA/Chris")
  @Issue("BUG-1028")
  public void relativeLocation() {
    WebElement trackerBox = mousePage.mouseTrackerBox;
    WebElement relative = mousePage.relativeLocationValues;
    Allure.step("AND the 'Move mouse here' box is displayed", step -> {
      mousePage.highlightElement(trackerBox);
      step.parameter("id:", trackerBox.getAttribute("id"));
      step.parameter("isDisplayed()", trackerBox.isDisplayed());
      assertTrue(trackerBox.isDisplayed());
      mousePage.unhighlightElement(trackerBox);
    });
    Allure.step("AND the 'Relative Location in Box' does not have any values", step -> {
      mousePage.highlightElement(relative);
      step.parameter("id:", relative.getAttribute("id"));
      step.parameter("isDisplayed()", relative.isDisplayed());
      step.parameter("text", relative.getText());
      assertTrue(relative.isDisplayed());
      assertEquals("", relative.getText(),"Box is not empty");
    });
    Allure.step("WHEN the mouse is moved to position 10, 20 using Action", step -> {
      step.parameter("Actions(driver)", ".moveToElement(WebElement, 10, 20)");
      step.parameter("", ".perform()");
      new Actions(driver)
            .moveToElement(trackerBox, 10, 20)
            .perform();
    });
    Allure.step("THEN the text '111, 120' is displayed", step -> {
      step.parameter("text:", relative.getText());
      assertEquals("111, 120", relative.getText(), "message does not match the action performed"
      );
    });
  }
}