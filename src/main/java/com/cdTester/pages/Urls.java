package com.cdTester.pages;

import com.cdTester.utils.ConfigManager;

public class Urls {
  public String base;
  public String alerts;
  public String dynamic;
  public String formPage;
  public String frames;
  public String inputs;
  public String login;
  public String mouseInteraction;
  public String singleTextInput;
  public String tables;
  public String upload;
  public String windows;

  public final static String base64EncodedRsaPK =
        "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDbBOu5Lhs4vpowbCnmCyLUpIE7JM9sm9QXzye2G+jr+Kr"
              + "MsinWohEce47BFPJlTaDzHSvOW2eeunBO89ZcvvVc8RLz4qyQ8rO98xS1jtgqi1NcBPETDrtzthODu/gd0sjB2Tk3TLuB"
              + "GVoPXt54a+Oo4JbBJ6h3s0+5eAfGplCbSNq6hN3Jh9YOTw5ZA6GCEy5l8zBaOgjXytd2v2OdSVoEDNiNQRkjJd2rmS2oi"
              + "9AyQFR3B7BrPSiDlCcITZFOWgLF5C31Wp/PSHwQhlnh7/6YhnE2y9tzsUvzx0wJXrBADW13+oMxrneDK3WGbxTNYgIi1P"
              + "vSqXlqGjHtCK+R2QkXAgMBAAECggEAVc6bu7VAnP6v0gDOeX4razv4FX/adCao9ZsHZ+WPX8PQxtmWYqykH5CY4TSfsui"
              + "zAgyPuQ0+j4Vjssr9VODLqFoanspT6YXsvaKanncUYbasNgUJnfnLnw3an2XpU2XdmXTNYckCPRX9nsAAURWT3/n9ljc/"
              + "XYY22ecYxM8sDWnHu2uKZ1B7M3X60bQYL5T/lVXkKdD6xgSNLeP4AkRx0H4egaop68hoW8FIwmDPVWYVAvo8etzWCtib"
              + "RXz5FcNld9MgD/Ai7ycKy4Q1KhX5GBFI79MVVaHkSQfxPHpr7/XcmpQOEAr+BMPon4s4vnKqAGdGB3j/E3d/+4F2swyko"
              + "QKBgQD8hCsp6FIQ5umJlk9/j/nGsMl85LgLaNVYpWlPRKPc54YNumtvj5vx1BG+zMbT7qIE3nmUPTCHP7qb5ERZG4CdMC"
              + "S6S64/qzZEqijLCqepwj6j4fV5SyPWEcpxf6ehNdmcfgzVB3Wolfwh1ydhx/96L1jHJcTKchdJJzlfTvq8wwKBgQDeCnK"
              + "ws1t5GapfE1rmC/h4olL2qZTth9oQmbrXYohVnoqNFslDa43ePZwL9Jmd9kYb0axOTNMmyrP0NTj41uCfgDS0cJnNTc63"
              + "ojKjegxHIyYDKRZNVUR/dxAYB/vPfBYZUS7M89pO6LLsHhzS3qpu3/hppo/Uc/AM /r8PSflNHQKBgDnWgBh6OQncChPUl"
              + "OLv9FMZPR1ZOfqLCYrjYEqiuzGm6iKM13zXFO4AGAxu1P/IAd5BovFcTpg79Z8tWqZaUUwvscnl+cRlj+mMXAmdqCeO8V"
              + "ASOmqM1ml667axeZDIR867ZG8K5V029Wg+4qtX5uFypNAAi6GfHkxIKrD04yOHAoGACdh4wXESi0oiDdkz3KOHPwIjn6B"
              + "hZC7z8mx+pnJODU3cYukxv3WTctlUhAsyjJiQ/0bK1yX87ulqFVgO0Knmh+wNajrb9wiONAJTMICG7tiWJOm7fW5cfTJw"
              + "WkBwYADmkfTRmHDvqzQSSvoC2S7aa9QulbC3C/qgGFNrcWgcT9kCgYAZTa1P9bFCDU7hJc2mHwJwAW7/FQKEJg8SL33KI"
              + "NpLwcR8fqaYOdAHWWz636osVEqosRrHzJOGpf9x2RSWzQJ+dq8+6fACgfFZOVpN644+sAHfNPAI/gnNKU5OfUv+eav8fB"
              + "nzlf1A3y3GIkyMyzFN3DE7e0n/lyqxE4HBYGpI8g==";
  // A pkcs#8 encoded unencrypted EC256 private key as a base64url string.
  public final static String base64EncodedEC256PK =
        "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQg8_zMDQDYAxlU-Q"
              + "hk1Dwkf0v18GZca1DMF3SaJ9HPdmShRANCAASNYX5lyVCOZLzFZzrIKmeZ2jwU"
              + "RmgsJYxGP__fWN_S-j5sN4tT15XEpN_7QZnt14YvI6uvAgO0uJEboFaZlOEB";


  public Urls(ConfigManager config, String site) {
    this.base = config.getBaseUrl(site);
    this.alerts = base + "selenium/web/alerts.html";
    this.dynamic = base + "selenium/web/dynamic.html";
    this.formPage = base + "selenium/web/formPage.html";
    this.frames = base + "selenium/web/iframes.html";
    this.inputs = base + "selenium/web/inputs.html";
    this.login = base + "selenium/web/login.html";
    this.mouseInteraction = base + "selenium/web/mouse_interaction.html";
    this.singleTextInput = base + "selenium/web/single_text_input.html";
    this.tables = base + System.getProperty("user.dir") + "/src/test/resources/tables.html";
    this.upload = base + "upload";
    this.windows = base + "selenium/web/window_switching_tests/page_with_frame.html";

  }


}
