package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.selenium.web.FormPage;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Epic: Working with Lists")
@Feature("Feature: Select List Tests")
@Tag("selectList")
@ExtendWith(TestResultListener.class)
public class SelectListTest extends BaseTest {
  protected FormPage formsPage;

  @BeforeEach
  public void navigate() {
    Urls url = new Urls(BaseTest.config, "selenium");
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", url.formPage);
      driver.get(url.formPage);
    });
    formsPage = new FormPage(driver);
  }

  @Test
  @Tag("smoke")
  @Story("Story: Single select list")
  @TmsLink("TC-151")
  @DisplayName("Should be able to select options from a single select list")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1151")
  public void selectOption() {
    AtomicReference<Select> select = new AtomicReference<>();

    Allure.step("WHEN a non-multi select list option is available", step -> {
      select.set(formsPage.getSelector(formsPage.nonMultiSelect));
      step.parameter("Options available", formsPage.getOptionsText(select.get()));
    });
    Allure.step("THEN the option 'Four' can be selected using selectByVisibleText('Four')", step -> {
      select.get().selectByVisibleText("Four");
      step.parameter("Option 'Four' selected", formsPage.nonMultiOptionFour.isSelected());
      assertTrue(formsPage.nonMultiOptionFour.isSelected());
    });
    Allure.step("AND the option 'Two' can be selected using selectByValue('two')", step -> {
      select.get().selectByValue("two");
      step.parameter("Option 'Two' selected", formsPage.nonMultiOptionTwo.isSelected());
      assertTrue(formsPage.nonMultiOptionTwo.isSelected());
    });
    Allure.step("AND the option 'Still learning how to count, apparently' can be selected using selectByIndex(3)", step -> {
      select.get().selectByIndex(3);
      step.parameter("Option 'Still learning how to count, apparently' selected", formsPage.nonMultiOptionStillLearning.isSelected());
      assertTrue(formsPage.nonMultiOptionStillLearning.isSelected());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Multi select list")
  @TmsLink("TC-152")
  @DisplayName("Should be able to select multiple options from a multi select list")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1152")
  public void selectMultipleOption() {
    WebElement hamOption = formsPage.multiOptionHam;
    WebElement gravyOption = formsPage.multiOptionGravy;
    WebElement eggOption = formsPage.multiOptionEggs;
    WebElement sausageOption = formsPage.multiOptionSausages;
    AtomicReference<Select> select = new AtomicReference<>();
    AtomicReference<List<WebElement>> optionsBySelectGetOptions = new AtomicReference<>();

    Allure.step("AND a multi select list option is available", step -> {
      select.set(formsPage.getSelector(formsPage.multiSelect));
      step.parameter("Options available", formsPage.getOptionsText(select.get()));
      List<WebElement> optionsByFindingElements = formsPage.multiSelect.findElements(By.tagName("option"));
      optionsBySelectGetOptions.set(select.get().getOptions());
      assertEquals(optionsByFindingElements, optionsBySelectGetOptions.get());
    });
    Allure.step("AND the current selected items are 'Eggs' and 'Sausages'", step -> {
      List<WebElement> getSelectedOptionList = select.get().getAllSelectedOptions();
      List<WebElement> expectedSelection = new ArrayList<WebElement>() {{
        add(eggOption);
        add(sausageOption);
      }};
      step.parameter("Options selected", formsPage.getOptionsText(getSelectedOptionList));
      assertEquals(expectedSelection, getSelectedOptionList);
    });
    Allure.step("WHEN the options 'ham' and 'onion gravy' are selected", step -> {
      select.get().selectByValue("ham");
      select.get().selectByValue("onion gravy");
    });
    Allure.step("THEN all the options are selected", step -> {
      step.parameter("Option 'Eggs' selected", formsPage.multiOptionEggs.isSelected());
      step.parameter("Option 'Ham' selected", formsPage.multiOptionHam.isSelected());
      step.parameter("Option 'Sausages' selected", formsPage.multiOptionSausages.isSelected());
      step.parameter("Option 'Onion gravy' selected", formsPage.multiOptionGravy.isSelected());
      Assertions.assertTrue(eggOption.isSelected(), "for Eggs");
      Assertions.assertTrue(hamOption.isSelected(), "for Ham");
      Assertions.assertTrue(sausageOption.isSelected(), "for Sausages");
      Assertions.assertTrue(gravyOption.isSelected(), "for Onion gravy");
    });
    Allure.step("WHEN the options 'eggs' and 'sausages' are de-selected", step -> {
      select.get().deselectByValue("eggs");
      select.get().deselectByValue("sausages");
    });
    Allure.step("THEN all the options are not selected", step -> {
      step.parameter("Option 'Eggs' selected", formsPage.multiOptionEggs.isSelected());
      step.parameter("Option 'Ham' selected", formsPage.multiOptionHam.isSelected());
      step.parameter("Option 'Sausages' selected", formsPage.multiOptionSausages.isSelected());
      step.parameter("Option 'Onion gravy' selected", formsPage.multiOptionGravy.isSelected());
      Assertions.assertFalse(eggOption.isSelected(), "for Eggs");
      Assertions.assertTrue(hamOption.isSelected(), "for Ham");
      Assertions.assertFalse(sausageOption.isSelected(), "for Sausages");
      Assertions.assertTrue(gravyOption.isSelected(), "for Onion gravy");
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: Multi select list")
  @TmsLink("TC-153")
  @DisplayName("Should not be able to select a disabled option from a select list")
  @Severity(SeverityLevel.MINOR)
  @Owner("QA/Chris")
  @Issue("BUG-1153")
  public void disabledOption() {
      WebElement selectDisabled = formsPage.singleSelectDisabled;
      AtomicReference<Select> select = new AtomicReference<>();

      Allure.step("AND a single select list option is available", step -> {
        formsPage.highlightElement(selectDisabled);
        select.set(formsPage.getSelector(selectDisabled));
        step.parameter("Options available", formsPage.getOptionsText(select.get()));
        step.parameter("Enabled option is displayed()", formsPage.singleOptionEnabled.isDisplayed());
        step.parameter("Disabled option is displayed()", formsPage.singleOptionDisabled.isDisplayed());
      });
      Allure.step("WHEN the select list is clicked", step -> {
        selectDisabled.click();
        step.parameter("Enabled option isDisplayed()", formsPage.singleOptionEnabled.isDisplayed());
        step.parameter("Disabled option isDisplayed()", formsPage.singleOptionDisabled.isDisplayed());
      });
      Allure.step("THEN the option 'Enabled' is enabled", step -> {
        step.parameter("Enabled option isEnabled()", formsPage.singleOptionEnabled.isEnabled());
        assertTrue(formsPage.singleOptionEnabled.isEnabled());
      });
      Allure.step("AND the option 'Disabled' is disabled", step -> {
        step.parameter("Disabled option isEnabled()", formsPage.singleOptionDisabled.isEnabled());
        assertFalse(formsPage.singleOptionDisabled.isEnabled());
      });
      Allure.step("AND trying to select disabled throws an exception error", step -> {
        step.parameter("Select.selectByValue(\"disabled\") throws", "UnsupportedOperationException");
        assertThrows(UnsupportedOperationException.class, () -> {
          select.get().selectByValue("disabled");
        });
      });
  }
}