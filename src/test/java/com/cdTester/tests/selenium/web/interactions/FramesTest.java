package com.cdTester.tests.selenium.web.interactions;


import com.cdTester.pages.selenium.web.Frames;
import com.cdTester.pages.selenium.web.FormPage;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Epic: Working with iframes")
@Feature("Feature: Iframe Tests")
@ExtendWith(TestResultListener.class)
@Tag("iframes")
public class FramesTest extends BaseTest {
  protected Frames framesPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.alerts);
      driver.get(url.frames);
    });
    framesPage = new Frames(driver);
  }


  @Test
  @Tag("smoke")
  @Story("Story: Forms iframe")
  @TmsLink("TC-101")
  @DisplayName("Should switch to iFrame using id")
  @Severity(SeverityLevel.BLOCKER)
  @Owner("QA/Chris")
  @Issue("BUG-1101")
  public void iframeWithId() {
    Allure.step("AND the page has a title of 'This page has iframes'", step -> {
      String title = framesPage.getTitle();
      step.parameter("Page Title", title);
      assertEquals("This page has iframes", title);
    });

    Allure.step("WHEN the iframe on the page is located using id attribute", step -> {
      step.parameter("iFrame locator", framesPage.iframeWindowById.toString());
      //switch To IFrame using Web Element
      framesPage.switchToIframe(framesPage.iframeWindowById);
    });

    Allure.step("THEN the iframe should have a title of 'We Leave From Here'", step -> {
      String iframeTitle = framesPage.getPageSourceTitle();
      step.parameter("iFrame title", iframeTitle);
      assertEquals("We Leave From Here", iframeTitle);
    });

    Allure.step("AND the elements inside the iframe can be interacted with", step -> {
      FormPage forms = new FormPage(framesPage.driver);
      WebElement emailInput = forms.loginFormEmailInput;
      Allure.step("Check Email input value", stepInner -> {
        stepInner.parameter("email input before typing", emailInput.getAttribute("value"));
      });
      Allure.step("Enter email address", stepInner -> {
        forms.type(emailInput, "admin@selenium.dev");
        stepInner.parameter("email input after typing", emailInput.getAttribute("value"));
      });
      Allure.step("Clear email address", stepInner -> {
        forms.clearText(emailInput);
        stepInner.parameter("email input after clearing field", emailInput.getAttribute("value"));
      });
    });

    Allure.step("AND the interactions can be returned to the main page", step -> {
      // exit current iframe
      driver.switchTo().defaultContent();
      step.parameter("Page Title after exiting iFrame", framesPage.getTitle());
   });
  }

  @Test
  @Tag("regression")
  @Story("Story: Forms iframe")
  @TmsLink("TC-102")
  @DisplayName("Should switch to iFrame using name")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1102")
  public void iframeWithName() {
    Allure.step("AND the page has a title of 'This page has iframes'", step -> {
      String title = framesPage.getTitle();
      step.parameter("Page Title", title);
      assertEquals("This page has iframes", title);
    });

    Allure.step("WHEN the iframe on the page is located using name attribute", step -> {
      step.parameter("iFrame locator", framesPage.iframeWindowByName.toString());
      //switch To IFrame using Web Element
      framesPage.switchToIframe(framesPage.iframeWindowByName);
    });

    Allure.step("THEN the iframe should have a title of 'We Leave From Here'", step -> {
      String iframeTitle = framesPage.getPageSourceTitle();
      step.parameter("iFrame title", iframeTitle);
      assertEquals("We Leave From Here", iframeTitle);
    });

    Allure.step("AND the elements inside the iframe can be interacted with", step -> {
      FormPage forms = new FormPage(framesPage.driver);
      WebElement emailInput = forms.loginFormEmailInput;
      Allure.step("Check Email input value", stepInner -> {
        stepInner.parameter("email input before typing", emailInput.getAttribute("value"));
      });
      Allure.step("Enter email address", stepInner -> {
        forms.type(emailInput, "admin@selenium.dev");
        stepInner.parameter("email input after typing", emailInput.getAttribute("value"));
      });
      Allure.step("Clear email address", stepInner -> {
        forms.clearText(emailInput);
        stepInner.parameter("email input after clearing field", emailInput.getAttribute("value"));
      });

    });

    Allure.step("AND the interactions can be returned to the main page", step -> {
      // exit current iframe
      driver.switchTo().defaultContent();
      step.parameter("Page Title after exiting iFrame", framesPage.getTitle());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Forms iframe")
  @TmsLink("TC-103")
  @DisplayName("Should switch to iFrame using index")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1103")
  public void iframeWithIndex() throws InterruptedException {
    Allure.step("AND the page has a title of 'This page has iframes'", step -> {
      String title = framesPage.getTitle();
      step.parameter("Page Title", title);
      assertEquals("This page has iframes", title);
    });

    Allure.step("WHEN the iframe on the page is located using the index of the iframe", step -> {
      step.parameter("iFrame locator", "driver.switchTo().frame(0);");
      //switch To IFrame using Web Element
      driver.switchTo().frame(0);
    });

    Allure.step("THEN the iframe should have a title of 'We Leave From Here'", step -> {
      String iframeTitle = framesPage.getPageSourceTitle();
      step.parameter("iFrame title", iframeTitle);
      assertEquals("We Leave From Here", iframeTitle);
    });

    Allure.step("AND the elements inside the iframe can be interacted with", step -> {
      FormPage forms = new FormPage(framesPage.driver);
      WebElement ageInputInput = forms.loginFormAgeInput;
      Allure.step("Check Age input value", stepInner -> {
        stepInner.parameter("age input before typing", ageInputInput.getAttribute("value"));
      });
      Allure.step("Enter age", stepInner -> {
        forms.type(ageInputInput, "21");
        stepInner.parameter("age input after typing", ageInputInput.getAttribute("value"));
      });
      Allure.step("Clear age", stepInner -> {
        forms.clearText(ageInputInput);
        stepInner.parameter("age input after clearing field", ageInputInput.getAttribute("value"));
      });

    });

    Allure.step("AND the interactions can be returned to the main page", step -> {
      // exit current iframe
      driver.switchTo().defaultContent();
      step.parameter("Page Title after exiting iFrame", framesPage.getTitle());
    });
  }

}
