package com.cdTester.tests.selenium.web.elements;

import com.cdTester.pages.selenium.web.Login;
import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
  protected Login loginPage;

  @BeforeEach
  public void createSession() {
    Urls url = new Urls(BaseTest.config, "selenium");
    driver = startChromeDriver(1);
    driver.get(url.login);
    loginPage = new Login(driver);
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @AfterEach
  public void endSession() {
    driver.quit();
  }


  @Test
  @Tag("smoke")
  @DisplayName("Username field should have placeholder text")
  public void username() {
    String userPlaceholder = loginPage.usernameField.getAttribute("placeholder");
    assertEquals("Username", userPlaceholder, "Expected username placeholder not as expected");
  }

  @Test
  @Tag("regression")
  @DisplayName("Password field should have placeholder text")
  public void password() {
    String passwordPlaceholder = loginPage.passwordField.getAttribute("placeholder");
    assertEquals("Password", passwordPlaceholder, "Expected password placeholder not as expected");
  }

  @Test
  @Tag("regression")
  @DisplayName("Should get an error message when logging in with invalid credentials")
  public void invalidLogin() {
    String username = config.getProperty("selenium.username");
    String password = config.getProperty("selenium.password");
    System.out.println("username: " + username);
    System.out.println("password: " + password);
    loginPage.login(username, password);

    wait.until(ExpectedConditions.alertIsPresent());
    String status = loginPage.getLoginStatusAndClose();
    System.out.println("Login Message: " + status);
    assertEquals("Please enter valid credentials", status, "Login message not as expected");
  }

  @Test
  @Tag("regression")
  @DisplayName("Should get a success message when logging in with valid credentials")
  public void validLogin() {
    System.out.println("username: " + "username");
    System.out.println("password: " + "password");
    loginPage.login("username", "password");

    wait.until(ExpectedConditions.alertIsPresent());
    String status = loginPage.getLoginStatusAndClose();
    System.out.println("Login Message: " + status);
    assertEquals("You have successfully logged in.", status, "Login message not as expected");
  }
}