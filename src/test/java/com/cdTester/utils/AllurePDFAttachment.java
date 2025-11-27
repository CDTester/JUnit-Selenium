package com.cdTester.utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.print.PrintOptions;

import java.util.Base64;

public class AllurePDFAttachment {

  @Attachment(value = "Page PDF", type = "application/pdf", fileExtension = ".pdf")
  public static byte[] printCurrentPage(WebDriver driver) {
    return printAndAttachPDF(driver, "Current Page");
  }

  @Attachment(value = "{name}", type = "application/pdf", fileExtension = ".pdf")
  public static byte[] printAndAttachPDF(WebDriver driver, String name) {
    if (driver instanceof PrintsPage) {
      PrintsPage printer = (PrintsPage) driver;
      PrintOptions printOptions = new PrintOptions();

      Pdf pdf = printer.print(printOptions);
      return Base64.getDecoder().decode(pdf.getContent());
    }
    return new byte[0];
  }


  @Attachment(value = "{name}", type = "application/pdf", fileExtension = ".pdf")
  public static byte[] attachPDF(Pdf pdf, String name) {
      return Base64.getDecoder().decode(pdf.getContent());
  }

  @Attachment(value = "{name}", type = "application/pdf", fileExtension = ".pdf")
  public static byte[] attachPDF(String browsingContext, String name) {
    return Base64.getDecoder().decode(browsingContext);
  }

}