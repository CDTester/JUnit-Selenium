package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Set;


public class CookiesTest extends BaseTest{
  protected Urls url = new Urls(BaseTest.config, "selenium");
  protected String URL = url.base + "selenium/web/blank.html";
  WebDriver driver = new ChromeDriver();

  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to add a cookie")
  public void addCookie() {
    driver.get(URL);
    // Add cookie into current browser context
    driver.manage().addCookie(new Cookie("key", "value"));
    driver.quit();
  }

  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to get a named cookie")
  public void getNamedCookie() {
    driver.get(URL);
    // Add cookie into current browser context
    driver.manage().addCookie(new Cookie("foo", "bar"));
    // Get cookie details with named cookie 'foo'
    Cookie cookie = driver.manage().getCookieNamed("foo");
    Assertions.assertEquals("bar", cookie.getValue());

    driver.quit();
  }


  @Test
  @Tag("cookies")
  @Tag("regression")
  @DisplayName("Should be able to get all cookies")
  public void getAllCookies() {
    driver.get(URL);
    // Add cookies into current browser context
    driver.manage().addCookie(new Cookie("test1", "cookie1"));
    driver.manage().addCookie(new Cookie("test2", "cookie2"));
    // Get cookies
    Set<Cookie> cookies = driver.manage().getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("test1")) {
        Assertions.assertEquals("cookie1", cookie.getValue());
      }

      if (cookie.getName().equals("test2")) {
        Assertions.assertEquals("cookie2", cookie.getValue());
      }
    }
    driver.quit();
  }


  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to delete a named cookie")
  public void deleteCookieNamed() {
    driver.get(URL);
    driver.manage().addCookie(new Cookie("test1", "cookie1"));
    // delete cookie named
    driver.manage().deleteCookieNamed("test1");
    driver.quit();
  }

  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to delete a cookie")
  public void deleteCookieObject() {
    driver.get(URL);
    Cookie cookie = new Cookie("test2", "cookie2");
    driver.manage().addCookie(cookie);
    /*
     * Selenium Java bindings also provides a way to delete
     * cookie by passing cookie object of current browsing context
    */
    driver.manage().deleteCookie(cookie);

    driver.quit();
  }


  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to delete all cookies")
  public void deleteAllCookies() {
    driver.get(URL);
    // Add cookies into current browser context
    driver.manage().addCookie(new Cookie("test1", "cookie1"));
    driver.manage().addCookie(new Cookie("test2", "cookie2"));
    // Delete All cookies
    driver.manage().deleteAllCookies();

    driver.quit();
  }

  @Test
  @Tag("regression")
  @Tag("cookies")
  @DisplayName("Should be able to add a same site cookie")
  public void sameSiteCookie() {
    driver.get("http://www.example.com");

    Cookie cookie = new Cookie.Builder("key", "value").sameSite("Strict").build();
    Cookie cookie1 = new Cookie.Builder("key", "value").sameSite("Lax").build();

    driver.manage().addCookie(cookie);
    driver.manage().addCookie(cookie1);

    System.out.println(cookie.getSameSite());
    System.out.println(cookie1.getSameSite());

    driver.quit();
  }
}