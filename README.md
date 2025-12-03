# Selenium UI Framework on JUnit framework

[![Test Status](https://github.com/CDTester/JUnit-Selenium/actions/workflows/selenium.yml/badge.svg)](https://github.com/CDTester/JUnit-Selenium/actions/workflows/selenium.yml)
[![Test Report](https://github.com/CDTester/JUnit-Selenium/actions/workflows/pages/pages-build-deployment/badge.svg)](https://github.com/CDTester/JUnit-Selenium/actions/workflows/pages/pages-build-deployment)

## Latest Build Report
https://cdtester.github.io/JUnit-Selenium/


## Installation
Add Selenium to your [POM file](pom.xml) 
Then compile and run the project with:
```bash
mvn clean compile
```

## Folder Structure
Using the Page Object Model (POM) design pattern, the folder structure is as follows:
```
src
 ├─ main
 │  ├─ java
 │     └─ com.cdTester
 │        ├─ pages
 │        │  ├─ selenium.web
 │        │  │  ├─ Alerts.java  # POM class for Alerts page
 │        │  │  ├─ Login.java   # POM class for Login page
 │        │  │  └─ ...
 │        │  └─ Urls.java       # Class for storing URLs of the selenium web pages
 │        └─ Utils.java        # Utility class for common functions
 │          └─ configReader.java  # Class for reading configuration properties
 ├─ test
 │  ├─ java
 │  │  └─ com.cdTester
 │  │     └─ tests
 │  │        └─ selenium.web
 │  │           ├─ BaseTest.java  # Base test class for setup and teardown
 │  │           ├─ interactions  # Folder for interaction tests (click, sendKeys, etc.)
 │  │           │  ├─ AlertsTest.java
 │  │           │  ├─ CookiesTest.java
 │  │           │  └─ ...
 │  │           └─ elements  # Folder for page object classes
 │  └─ resources
 │    ├─ testdata.csv  # Test data file for storing test data
 │    └─ config
 │       └─ dev.properties  # Configuration file for environment variables
```

## JUnit
As a test framework, JUnit is more of a developers test framework and not a testers framework. It lacks tha ability to define tests steps, 
tests are contained in methods. The test report only shows the status of each method. It does not effectively communicate to the audience
what the tests are actually doing and does not provide any test evidence.

### Annotations
| Annotation         | Description                                                                     | Paramaters                             |
|--------------------|---------------------------------------------------------------------------------|----------------------------------------|
| @BeforeAll         | The annotated method will be run before all @Test methods.                      |                                        |
| @BeforeEach        | The annotated method will be run before each @Test methods.                     |                                        |
| @AfterEach         | The annotated method will be run after each @Test methods.                      |                                        |
| @AfterAll          | The annotated method will be run after all the @Test methods.                   |                                        |
| @Test              | The annotation declares a class/method as a test.                               |                                        |
| @DisplayName       | Declares a custom display name for the test class/method.                       | ("Test Name")                          |
| @Tag               | Adds a tag to a test which can be used to filter tests during execution cycles. | ("Tag Name")                           |
| @Disabled          | This annotation declares the class/method as a parameterised test               |                                        |
| @ParameterizedTest | Marks a method as supplying data for a test method.                             |                                        |
| @valueSource       | Specifies a String array as the source of arguments for the @ParameterizedTest  | (strings = { "value1", "value2" })     |
| @csvSource         | Specifies an csv list as the source of arguments for the @ParameterizedTest     | ( {"fruit, apple", "vegetable, pea"} ) |


### Running Tests with Maven
To run tests with Maven, use the following command to run all tests:
```bash
mvn clean test -Denv=dev
```
Or to run tests with a specific tag
```bash
mvn clean test -Denv=dev -Dgroups=smoke
```

To run a specific test class, use the following command:
```bash
mvn  -Denv=dev -Dtest="com.cdTester.tests.selenium.web.interactions"
```

To run tests on installation:
```bash
mvn clean install -Denv=dev
```

### Run Configurations
These tests use a configReader to read environment variables from a properties file.
To run Junit tests from the IntelliJ IDE, we need a VM option in JUnit run configuration.
In the navigation menu, go to Run > Edit Configurations.
- Click on "Edit configuration templates" and select "JUnit".
- Set the following fields: VM options: -Denv=dev
- Apply the changes and close the dialog.
- Click on the "+" icon to add a new configuration and select "JUnit". Set up a config like this:

![JUnit Run Configuration](docs/junit_run_config.png)




## Configuration Management
The ConfigManager class is used to read configuration values from property files based on the environment specified. 
The environment is set using the 'env' system property, which can be passed as a VM option when running the tests.

The following example only contains one set of configuration for 'users' in the 'dev' environment. 
dev.properties
```
# default properties
environment=dev
browser=chrome

# Selenium
selenium.base.url=https://www.selenium.dev/
selenium.username=admin
selenium.password=myStrongPassword

# the-internet-herokuapp
herokuapp.base.url=https://the-internet.herokuapp.com/

# tables
tables.base.url=file://
```

## Maven Surefire Test Reporter 
The Maven Surefire report collates data the JUnit xml test report file. The report is basic and only shows the summary of the results and test titles.

![Surefire report](docs/surefire_report.png)

## Allure Test Reporter
The Allure Reporter is a rich report that provides functionality for test steps. 

The next step for this repo is to add Allure reporter.

```bash
# Generate report and open report, machine is used as a server to host the report
mvn allure:serve

# Or generate report to target/site/allure-maven-plugin
mvn allure:report
```

There appears to be an issue with using:
```bash
# generate report
allure generate target/allure-results --clean - o target/allure-report 
## optional --single-file 

## open report
allure open target/allure-report
```

The issue is that assertion errors do not show the expected and actual values in the report
![Allure exception error missing values](docs/allure_issue.png)

But when `mvn allure:report` is used the values are displauyed:
![Mvn Allure exception error shows values](docs/mvn_allure_no_issue.png)


## Selenium 
### import drivers
Selenium supports multiple browsers. You need to import the driver for the browser you want to use.:
```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.safari.SafariDriver;
```

### Driver Options
You can set options for the driver. For example, to start Chrome in headless mode:
```java
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.safariOptions;

/*
You want to make sure that the element is on the page before you attempt to locate it, 
and the element is in an interactable state before you attempt to interact with it.
*/

ChromeOptions optionsChrome = new ChromeOptions()
        .optionsChrome.setImplicitWaitTimeout(Duration.ofMillis(500))
        .addArguments("--headless");

FirefoxOptions optionsFF = new FirefoxOptions()
        .addPreference("browser.startup.page", 1)
        .addPreference("browser.startup.homepage", "https://www.google.co.uk")
        .setAcceptInsecureCerts(true)
        .setHeadless(true);

EdgeOptions optionsChrome = new EdgeOptions()
        .addArguments("--headless");

SafariOptions optionsSafari = new SafariOptions()
        .setUseTechnologyPreview(true);
```

### Start a driver
Create a new Driver class object with:
```java
WebDriver chrome = new ChromeDriver(optionsChrome);
WebDriver firefox = new FirefoxDriver(optionsFF);
WebDriver edge = new EdgeDriver(optionsEdge);
WebDriver safari = new SafariDriver(optionsSafari);
```

### Navigate to a URL
Use the `get()` or `navigate()` method to navigate to a URL:
```java
WebDriver chrome = chrome.get("https://www.google.co.uk");
WebDriver chrome = chrome.navigate().to("https://www.selenium.dev/selenium/web/web-form.html");
WebDriver chrome = chrome.navigate().refresh();
WebDriver chrome = chrome.navigate().back();
WebDriver chrome = chrome.navigate().forward();
```

### Manage the browser window
You can manage the browser window with the `manage().window()` method:
```java
WebDriver chrome = chrome.manage().window().maximize();
WebDriver chrome = chrome.manage().window().minimize();
WebDriver chrome = chrome.manage().window().fullscreen();
WebDriver chrome = chrome.manage().window().setSize(new Dimension(800, 600));
WebDriver chrome = chrome.manage().window().setPosition(new Point(200, 100));
```
WebDriver does not make the distinction between windows and tabs. If your site opens a new tab or window, Selenium will let you work with it using a window handle.
Each window has a unique identifier which remains persistent in a single session. You can get the window handle of the current window by using:

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

WebDriver chrome = chrome.get("https://www.selenium.dev/selenium/web/window_switching_tests/page_with_frame.html");
String currentHandle = chrome.getWindowHandle();

//click on link to open a new window
WebElement click = chrome.findElement(By.linkText("Open new window")).click();
//fetch handles of all windows, there will be two, [0]- default, [1] - new window
Object[] windowHandles = driver.getWindowHandles().toArray();
WebDriver newTab = chrome.switchTo().window((String) windowHandles[1]);

//Close the new tab
WebDriver newTab = chrome.close();

//Switch back to the original tab
WebDriver chrome = chrome.switchTo().window((String) currentHandle[0]);

//Opens a new tab and switches to new tab
WebDriver openNewTab = chrome.switchTo().newWindow(WindowType.TAB);
//Opens a new window and switches to new window
WebDriver openNewWindow = chrome.switchTo().newWindow(WindowType.WINDOW);
```

### Browser Title
You can get the browser title with the `getTitle()` method:
```java
WebDriver chrome = new ChromeDriver();
String title = chrome.getTitle();
```

### Find Elements
You can find elements on the page with the `findElement()` and `findElements()` methods:
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

WebElement element = chrome.findElement(By.name("my-text"));
List<WebElement> elements = chrome.findElements(By.tagName("input"));

WebElement byClass = chrome.findElement(By.className("className"));
WebElement byCss1 = chrome.findElement(By.cssSelector("#idName"));
WebElement byCss2 = chrome.findElement(By.cssSelector("[attribute=value]"));
WebElement byId = chrome.findElement(By.id("idName"));
WebElement byName = chrome.findElement(By.name("nameValue"));
WebElement byLinkText = chrome.findElement(By.linkText("linkTextValue"));
WebElement byPartialLinkText = chrome.findElement(By.partialLinkText("partialLinkTextValue"));
WebElement byTagName = chrome.findElement(By.tagName("HtmlTagName"));
WebElement byXpath = chrome.findElement(By.xpath("//tagname[@attribute='value']"));
By emailLocator = RelativeLocator.with(By.tagName("input")).above(By.id("password"));
By passwordLocator = RelativeLocator.with(By.tagName("input")).below(By.id("email"));
By cancelLocator = RelativeLocator.with(By.tagName("button")).toLeftOf(By.id("submit"));
By submitLocator = RelativeLocator.with(By.tagName("button")).toRightOf(By.id("cancel"));
By emailLocator = RelativeLocator.with(By.tagName("input")).near(By.id("lbl-email"));
```
### Interact with elements
You can interact with elements using methods like `click()`, `sendKeys()`, and `clear()`:
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

WebElement element = chrome.findElement(By.name("my-text"));
List<WebElement> elements = chrome.findElements(By.tagName("input"))
                .click()
                .sendKeys("test")
                .clear();
```

### Actions
You can perform complex actions using the `Actions` class:

```java
import org.openqa.selenium.interactions.Actions;
WebElement clickable = driver.findElement(By.id("clickable"));

// key up and down
Actions a =  new Actions(driver).keyDown(Keys.SHIFT).sendKeys("a").keyUp(Keys.SHIFT).sendKeys("b").perform();

//Click and hold
Actions a = new Actions(driver).clickAndHold(clickable).perform();

// click and release
Actions a = new Actions(driver).click(clickable).perform();

// right click
Actions a = new Actions(driver).contextClick(clickable).perform();

// double click
Actions a = new Actions(driver).doubleClick(clickable).perform();

// hovers
Actions a = new Actions(driver).moveToElement(clickable).perform();
```


### Information about elements
You can get information about elements using methods like `getText()`, `getAttribute()`,
```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

WebElement element = chrome.findElement(By.name("my-text"));
List<WebElement> elements = chrome.findElements(By.tagName("input"))
        .isDisplayed()
        .isEnabled()
        .isSelected()
        .getText()
        .getAttribute("attributeName")
        .getCssValue("cssPropertyName")
        .getLocation()
        .getSize()
        .getRect();
```

### Waits
You can use waits to wait for elements to be in a certain state before interacting with them. There are two types of waits: implicit and explicit.
#### Implicit Wait
You can set an implicit wait with the `manage().timeouts().implicitlyWait()` method:
This is global setting that applies to all elements throughout the driver session. The default setting is 0.
```java
import java.time.Duration;

WebDriver driver = driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
```

#### Explicit Wait
You can set an explicit wait with the `WebDriverWait` class and `ExpectedConditions`:
This is a one-off wait that you can use for a specific element.
```java
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5))
        .until(ExpectedConditions.visibilityOfElementLocated(By.id("element_id")));
```


### Screenshots
You can take screenshots with the `getScreenshotAs()` method:
```java
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import org.openqa.selenium.io.FileHandler;

TakesScreenshot screenshot = (TakesScreenshot) driver;
File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
File destFile = new File("screenshots/" + fileName + ".png");
//FileHandler.copy(sourceFile, destFile);
```
see more examples:
</br>[BaseTest](src/test/java/com/cdTester/tests/selenium/web/BaseTest.java)

### Manage cookies
You can manage cookies with the `manage().cookies()` method:
```java
import org.openqa.selenium.Cookie;

WebDriver chrome = chrome.manage().addCookie(new Cookie("testCookie", "testValue"));
WebDriver chrome = chrome.manage().deleteAllCookies();
Cookie getAll = chrome.manage().getCookies();
Cookie getNamed = chrome.manage().getCookieNamed("testCookie");
```
see more examples [CookiesTest](src/test/java/com/cdTester/tests/selenium/web/interactions/CookiesTest.java).


### Alerts
You can handle alerts with the `switchTo().alert()` method:
```java
//WebElement alert = chrome.findElement(By.id("alert")).click();
//wait.until(ExpectedConditions.alertIsPresent());
Alert alert = driver.switchTo().alert();
String text = alert.getText();
//alert.accept();
//alert.dismiss();
//alert.sendKeys("test");
```

### Frames
You can switch to a frame with the `switchTo().frame()` method:
```java
WebDriver chrome = chrome.switchTo().frame("frameName");
WebDriver chrome = chrome.switchTo().frame(0);
WebDriver chrome = chrome.switchTo().frame(chrome.findElement(By.tagName("iframe")));
WebDriver chrome = chrome.switchTo().defaultContent();
```

### Quitting Session
You can quit the browser session with the `quit()` method:
```java
WebDriver chrome = chrome.quit();
```
Quit will:
- Close all the windows and tabs associated with that WebDriver session
- Close the browser process
- Close the background driver process
- Notify Selenium Grid that the browser is no longer in use so it can be used by another session (if you are using Selenium Grid)

Failure to call quit will leave extra background processes and ports running on your machine which could cause you problems later.
Some test frameworks offer methods and annotations which you can hook into to tear down at the end of a test.





