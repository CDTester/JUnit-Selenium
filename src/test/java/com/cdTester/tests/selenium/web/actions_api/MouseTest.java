package com.cdTester.tests.selenium.web.actions_api;

import com.cdTester.pages.selenium.web.MouseInteraction;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Collections;

public class MouseTest extends BaseTest {
  protected MouseInteraction mousePage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(0);
    driver.get(url.mouseInteraction);
    mousePage = new MouseInteraction(driver);
  }

  @AfterEach
  public void endSession() {
    /* An important thing to note is that the driver remembers the state of all
       the input items throughout a session. Even if you create a new instance
       of an actions class, the depressed keys and the location of the pointer
       will be in whatever state a previously performed action left them.
    */
    ((RemoteWebDriver) driver).resetInputState();
    driver.quit();
  }

  @Test
  @Tag("smoke")
  @DisplayName("Should be able to left click an element using Actions")
  public void clickLeft() throws InterruptedException {
    WebElement click = mousePage.highlightElement(mousePage.clickLink);
    new Actions(driver)
            .pause(Duration.ofMillis(500))
            .click(click)
            .perform();

    assertTrue(driver.getCurrentUrl().contains("resultPage.html"));
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to right click an element using Actions")
  public void clickRight() throws InterruptedException {
    WebElement clickable = mousePage.highlightElement(mousePage.clickableInput);
    new Actions(driver)
            .moveToElement(clickable)
            .contextClick(clickable)
            .pause(Duration.ofMillis(500))
            .perform();

    WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
    assertEquals("context-clicked",
            clickStatus.getText(),
            "message does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to double click an element using Actions")
  public void clickDouble() throws InterruptedException {
    WebElement clickable = mousePage.highlightElement(mousePage.clickableInput);
    new Actions(driver)
            .doubleClick(clickable)
            .pause(Duration.ofMillis(500))
            .perform();

    WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
    assertEquals("double-clicked",
            clickStatus.getText(),
            "message does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to click and hold")
  public void clickAndHold() throws InterruptedException {
    WebElement clickable = mousePage.highlightElement(mousePage.clickableInput);
    long start = System.currentTimeMillis();

    new Actions(driver)
            .pause(Duration.ofSeconds(1))
            .clickAndHold(clickable)
            .pause(Duration.ofSeconds(1))
            .sendKeys("abc")
            .perform();

    long duration = System.currentTimeMillis() - start;
    assertTrue(duration > 2000);

    WebElement clickStatus = mousePage.highlightElement(mousePage.clickableStatus);
    assertEquals("focused",
            clickStatus.getText(),
            "message does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to hover over an element using Actions")
  public void hover() throws InterruptedException {
    WebElement hoverable = mousePage.highlightElement(mousePage.hoverableButton);
    new Actions(driver)
            .moveToElement(hoverable)
            .pause(Duration.ofMillis(500))
            .perform();

    WebElement hoverStatus = mousePage.highlightElement(mousePage.hoverableStatus);
    assertEquals("hovered",
            hoverStatus.getText(),
            "message does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to drag and drop an element using Actions")
  public void dragAndDrop() throws InterruptedException {
    WebElement draggable = mousePage.highlightElement(mousePage.draggableBox);
    WebElement droppable = mousePage.highlightElement(mousePage.droppableBox);
    mousePage.unhighlightElement(mousePage.draggableBox);
    new Actions(driver)
            .dragAndDrop(draggable, droppable)
            .perform();

    WebElement droppedStatus = mousePage.highlightElement(mousePage.droppedStatus);
    assertEquals("dropped",
            droppedStatus.getText(),
            "message does not match the action performed"
    );
    assertEquals("position: relative; left: 9px; top: 98px;",
            draggable.getAttribute("style"),
            "Box not dropped in the correct location"
    );
  }


  @Test
  @Tag("regression")
  @DisplayName("Should be able to move mouse pointer to an absolute location using Pointer and Sequence API")
  public void absoluteLocation() throws InterruptedException {
    PointerInput mouse = new PointerInput(PointerInput.Kind.MOUSE, "default mouse");
    Sequence actions = new Sequence(mouse, 0)
            .addAction(mouse.createPointerMove(
                    Duration.ZERO,
                    PointerInput.Origin.viewport(),
                    70, 500
                    )
            );
    ((RemoteWebDriver) driver).perform(Collections.singletonList(actions));

    WebElement absolute = mousePage.highlightElement(mousePage.absoluteLocationValues);
    assertEquals("70, 500",
            absolute.getText(),
            "message does not match the action performed"
    );
  }

  @Test
  @Tag("regression")
  @DisplayName("Should be able to move mouse pointer to a relative location using Actions API")
  public void relativeLocation() throws InterruptedException {
    WebElement trackerBox = mousePage.highlightElement(mousePage.mouseTrackerBox);
    mousePage.unhighlightElement(mousePage.mouseTrackerBox);
    new Actions(driver)
            .moveToElement(trackerBox, 10, 20)
            .perform();

    WebElement relative = mousePage.highlightElement(mousePage.relativeLocationValues);
    assertEquals("111, 120",
            relative.getText(),
            "message does not match the action performed"
    );
  }
}