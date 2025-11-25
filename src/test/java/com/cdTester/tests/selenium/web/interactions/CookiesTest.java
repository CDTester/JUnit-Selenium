package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.pages.Urls;
import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Cookie;
import java.util.Set;


@Epic("Epic: Working with cookies")
@Feature("Feature: Cookies Tests")
@Tag("regression")
@Tag("cookies")
@ExtendWith(TestResultListener.class)
public class CookiesTest extends BaseTest {
  protected Urls url = new Urls(BaseTest.config, "selenium");
  protected String URL = url.base + "selenium/web/blank.html";

  @Test
  @Story("Story: Add Cookie")
  @TmsLink("TC-081")
  @DisplayName("Should be able to add a cookie")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-181")
  public void addCookie() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("WHEN the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("THEN a cookie can be added to the site", step -> {
      driver.manage().addCookie(new Cookie("key", "value"));
      step.parameter("Cookie added", "key=value");
    });
  }

  @Test
  @Story("Story: Get Cookie")
  @TmsLink("TC-082")
  @DisplayName("Should be able to get a named cookie")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-182")
  public void getNamedCookie() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("WHEN a cookie is added to the site", step -> {
      driver.manage().addCookie(new Cookie("foo", "bar"));
      step.parameter("Cookie added", "foo=bar");
    });

    Allure.step("THEN that cookie can be read", step -> {
      Cookie cookie = driver.manage().getCookieNamed("foo");
      step.parameter("Cookie", cookie.getName() + "=" + cookie.getValue());
      step.parameter("Cookie domain", cookie.getDomain());
      step.parameter("Cookie expiry", cookie.getExpiry());
      step.parameter("Cookie path", cookie.getPath());
      Assertions.assertEquals("bar", cookie.getValue());
    });
  }

  @Test
  @Story("Story: Get Cookie")
  @TmsLink("TC-083")
  @DisplayName("Should be able to get all cookies")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-183")
  public void getAllCookies() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("WHEN 2 cookies is added to the site", step -> {
      driver.manage().addCookie(new Cookie("test1", "cookie1"));
      driver.manage().addCookie(new Cookie("test2", "cookie2"));
      step.parameter("Cookie added", "test1=cookie1");
      step.parameter("Cookie added", "test2=cookie2");
    });

    Allure.step("Then those cookies can be read", step -> {
      Set<Cookie> cookies = driver.manage().getCookies();
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("test1")) {
          step.parameter("Cookie", cookie.getName() + "=" + cookie.getValue());
          Assertions.assertEquals("cookie1", cookie.getValue());
        }

        if (cookie.getName().equals("test2")) {
          step.parameter("Cookie", cookie.getName() + "=" + cookie.getValue());
          Assertions.assertEquals("cookie2", cookie.getValue());
        }
      }
    });
  }

  @Test
  @Story("Story: Delete Cookie")
  @TmsLink("TC-084")
  @DisplayName("Should be able to delete a named cookie")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-184")
  public void deleteCookieNamed() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("AND a cookie is added to the site", step -> {
      driver.manage().addCookie(new Cookie("test1", "cookie1"));
      step.parameter("Cookie added", "test1=cookie1");
    });

    Allure.step("WHEN that cookie is deleted", step -> {
      driver.manage().deleteCookieNamed("test1");
    });

    Allure.step("THEN that cookie can no longer be read", step -> {
      Cookie cookie = driver.manage().getCookieNamed("test1");
      step.parameter("Cookie", String.valueOf(cookie));
      Assertions.assertNull(cookie);
    });
  }

  @Test
  @Story("Story: Delete Cookie")
  @TmsLink("TC-085")
  @DisplayName("Should be able to delete a cookie")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-185")
  public void deleteCookieObject() {
    Cookie cookie = new Cookie("test2", "cookie2");

    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("AND a cookie can be added to the site", step -> {
      driver.manage().addCookie(cookie);
      step.parameter("Cookie added", "test2=cookie2");
    });

    Allure.step("WHEN that cookie is deleted", step -> {
      driver.manage().deleteCookie(cookie);
    });

    Allure.step("THEN that cookie can no longer be read", step -> {
      Cookie cookieRead = driver.manage().getCookieNamed("test1");
      step.parameter("Cookie", String.valueOf(cookieRead));
      Assertions.assertNull(cookieRead);
      driver.quit();
    });
  }

  @Test
  @Story("Story: Delete Cookie")
  @TmsLink("TC-086")
  @DisplayName("Should be able to delete all cookies")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-186")
  public void deleteAllCookies() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", URL);
      driver.get(URL);
    });

    Allure.step("AND 2 cookies are added to the site", step -> {
      driver.manage().addCookie(new Cookie("test1", "cookie1"));
      driver.manage().addCookie(new Cookie("test2", "cookie2"));
      step.parameter("Cookie added", "test1=cookie1");
      step.parameter("Cookie added", "test2=cookie2");
    });

    Allure.step("WHEN all cookies are deleted", step -> {
      driver.manage().deleteAllCookies();
    });

    Allure.step("THEN those cookie can no longer be read", step -> {
      Cookie cookieRead1 = driver.manage().getCookieNamed("test1");
      step.parameter("Cookie", String.valueOf(cookieRead1));
      Assertions.assertNull(cookieRead1);

      Cookie cookieRead2 = driver.manage().getCookieNamed("test2");
      step.parameter("Cookie", String.valueOf(cookieRead2));
      Assertions.assertNull(cookieRead2);
    });
  }

  @Test
  @Story("Story: Add Cookie")
  @TmsLink("TC-081")
  @DisplayName("Should be able to add a same site cookie")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-187")
  public void sameSiteCookie() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });

    Allure.step("AND the web page has loaded", step -> {
      step.parameter("URL", "http://www.example.com");
      driver.get("http://www.example.com");
    });

    Allure.step("WHEN a cookie is added to the site", step -> {
      Cookie cookie = new Cookie.Builder("key", "value").sameSite("Strict").build();
      Cookie cookie1 = new Cookie.Builder("key", "value").sameSite("Lax").build();

      driver.manage().addCookie(cookie);
      step.parameter("Cookie added", "key=value where sameSite=Strict");
      driver.manage().addCookie(cookie1);
      step.parameter("Cookie added", "key=value where sameSite=Lax");
    });

    Allure.step("THEN cookies with sameSite set as Strict are not added", step -> {
      step.parameter("Strict", "When sameSite is set to Strict, the cookie will not be sent along with requests initiated by third party websites.");
      Set<Cookie> cookies = driver.manage().getCookies();
      for (Cookie cookie : cookies) {
        step.parameter("Cookie", cookie.getName() + "=" + cookie.getValue());
        step.parameter("Cookie domain", cookie.getDomain());
        step.parameter("Cookie site", cookie.getSameSite());
        Assertions.assertNotEquals("Strict", cookie.getSameSite());
      }
  });

    Allure.step("AND cookies with sameSite set as Lax are added", step -> {
      step.parameter("Lax", "When sameSite is set to Lax, the cookie will be sent along with GET requests initiated by third party websites.");
      Set<Cookie> cookies = driver.manage().getCookies();
      for (Cookie cookie : cookies) {
        step.parameter("Cookie", cookie.getName() + "=" + cookie.getValue());
        step.parameter("Cookie domain", cookie.getDomain());
        step.parameter("Cookie site", cookie.getSameSite());
        Assertions.assertEquals("Lax", cookie.getSameSite());
      }
    });
  }
}