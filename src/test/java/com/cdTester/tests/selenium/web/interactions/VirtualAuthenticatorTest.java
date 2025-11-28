package com.cdTester.tests.selenium.web.interactions;

import com.cdTester.tests.selenium.web.BaseTest;
import com.cdTester.pages.Urls;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import com.cdTester.utils.TestResultListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.virtualauthenticator.Credential;
import org.openqa.selenium.virtualauthenticator.HasVirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticator;
import org.openqa.selenium.virtualauthenticator.VirtualAuthenticatorOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A representation of the Web Authenticator model.
 * Web applications can enable a public key-based authentication mechanism known as Web Authentication to authenticate users
 * in a passwordless manner.
 * Web Authentication defines APIs that allows a user to create a public-key credential and register it with an authenticator.
 * An authenticator can be a hardware device or a software entity that stores user’s public-key credentials and retrieves
 * them on request.
 * As the name suggests, Virtual Authenticator emulates such authenticators for testing.
 */
@Epic("Epic: Working with Virtual Authenticator")
@Feature("Feature: Virtual Authenticator Tests")
@Tag("virtualAuthenticator")
@ExtendWith(TestResultListener.class)
public class VirtualAuthenticatorTest extends BaseTest {
  //  A pkcs#8 encoded encrypted RSA private key as a base64url string.
  private final static PKCS8EncodedKeySpec rsaPrivateKey = new PKCS8EncodedKeySpec(
        Base64.getMimeDecoder().decode(Urls.base64EncodedRsaPK)
  );

  // A pkcs#8 encoded unencrypted EC256 private key as a base64url string.
  private final static PKCS8EncodedKeySpec ec256PrivateKey = new PKCS8EncodedKeySpec(
        Base64.getUrlDecoder().decode(Urls.base64EncodedEC256PK)
  );

  @BeforeEach
  public void setup() {
    Allure.step("GIVEN ChromeDriver has been initiated", step -> {
      driver = startChromeDriver(1);
    });
  }


  @Test
  @Tag("smoke")
  @Story("Story: virtual authenticator options")
  @TmsLink("TC-161")
  @DisplayName("Should be able to set virtual authenticator options")
  @Severity(SeverityLevel.CRITICAL)
  @Owner("QA/Chris")
  @Issue("BUG-1161")
  public void testVirtualOptions() {
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();

    Allure.step("WHEN the virtual authenticator options are set using U2F protocol", step -> {
      step.parameter("protocol set to:", "U2F");
      step.parameter("protocol options are:", "ctap1/u2f, ctap2 or ctap2_1");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.U2F));
    });
    Allure.step("AND the transport is set to USB", step -> {
      step.parameter("transport set to:", "USB");
      step.parameter("authenticator can be contacted:", "over usb, nfc(Near Field Communication), ble(Bluetooth Low Energy) " +
                  "or using internal."
      );
      options.set(options.get().setTransport(VirtualAuthenticatorOptions.Transport.USB));
    });
    Allure.step("AND the setHasResidentKey is set to false", step -> {
      step.parameter("setHasResidentKey", false);
      step.parameter("Resident Key", "If set to true the authenticator will support client-side discoverable credentials.");
      options.set(options.get().setHasResidentKey(false));
    });
    Allure.step("AND the setHasUserVerification is set to true", step -> {
      step.parameter("setHasUserVerification", true);
      step.parameter("Has User Verification", "If set to true, the authenticator supports user verification.");
      options.set(options.get().setHasUserVerification(true));
    });
    Allure.step("AND the setIsUserVerified is set to true", step -> {
      step.parameter("setIsUserVerified", true);
      step.parameter("is User Verification", "Determines the result of User Verification performed on the Virtual " +
            "Authenticator. If set to true, User Verification will always succeed. If set to false, it will fail."
      );
      options.set(options.get().setIsUserVerified(true));
    });
    Allure.step("AND the setIsUserConsenting is set to true", step -> {
      step.parameter("setIsUserConsenting", true);
      step.parameter("Is User Consenting", "Determines the result of all user consent authorization gestures, " +
            "and by extension, any test of user presence performed on the Virtual Authenticator. If set to true, " +
            "a user consent will always be granted. If set to false, it will not be granted."
      );
      options.set(options.get().setIsUserConsenting(true));
    });
    Allure.step("THEN there will be 6 options set to the VirtualAuthenticatorOptions", step -> {
      step.parameter("options set", options.get().toMap().entrySet().toString());
      assertEquals(6, options.get().toMap().size());
    });


  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-162")
  @DisplayName("Should be able to create a virtual authenticator using U2F protocol")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1162")
  public void testCreateAuthenticator() {
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using U2F protocol ", step -> {
      step.parameter("protocol set to:", "U2F");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.U2F));
    });
    Allure.step("AND the hasResidentKey is set to false", step -> {
      step.parameter("hasResidentKey", false);
      step.parameter("set to false", "The authenticator will not support client-side discoverable credentials.");
      options.set(options.get().setTransport(VirtualAuthenticatorOptions.Transport.USB));
    });
    Allure.step("WHEN the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("THEN the virtual authenticator will not have any credentials set up", step -> {
      step.parameter("Credentials", Arrays.toString(auth.get().getCredentials().toArray()));
      List<Credential> credentialList = auth.get().getCredentials();
      assertEquals(0, credentialList.size());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-163")
  @DisplayName("Should be able to remove a virtual authenticator")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1163")
  public void testRemoveAuthenticator() {
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using U2F protocol ", step -> {
      step.parameter("protocol set to:", "U2F");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.U2F));
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("WHEN the virtual authenticator is removed", step -> {
      ((HasVirtualAuthenticator) driver).removeVirtualAuthenticator(auth.get());
      step.parameter(".removeVirtualAuthenticator()", "This removes the authenticator from the browser session. The function does not have a return value so you cannot set the auth object to null here.");
      step.parameter(".getId()", "This gets the id from the Java local object, not the browser. It still exists in the test code until garbage collected.");
      step.parameter("Virtual Authenticator id:", auth.get().getId());
    });
    Allure.step("THEN the virtual authenticator will no longer available", step -> {
      try {
        step.parameter(".getCredentials():", "This tries to communicate with the browser, unlike .getId(). Since the authenticator has been removed, it will throw an InvalidArgumentException.");
        auth.get().getCredentials();
        step.parameter("Error:", "Virtual Authenticator was not removed");
      }
      catch (InvalidArgumentException e) {
        step.parameter("Virtual Authenticator:", "has been removed successfully.");
        assertThrows(InvalidArgumentException.class, auth.get()::getCredentials);
        step.parameter("Java auth object:", "has been made null by the test code");
        auth.set(null);
      }
      assertNull(auth.get());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-164")
  @DisplayName("Should be able to add a resident credential when setHasUserVerification=true")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1164")
  public void testCreateAndAddResidentialKey() {
    byte[] credentialId = {1, 2, 3, 4};
    byte[] userHandle = {1};
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using CTAP2 protocol", step -> {
      step.parameter("protocol set to:", "CTAP2");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.CTAP2));
    });
    Allure.step("AND hasResidentKey is set to true", step -> {
      step.parameter("hasResidentKey set to:", "true");
      options.set(options.get().setHasResidentKey(true));
    });
    Allure.step("AND setHasUserVerification is set to true", step -> {
      step.parameter("setHasUserVerification set to:", "true");
      options.set(options.get().setHasUserVerification(true));
    });
    Allure.step("AND setIsUserVerified is set to true", step -> {
      step.parameter("setIsUserVerified set to:", "true");
      options.set(options.get().setIsUserVerified(true));
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("options set", options.get().toMap().entrySet().toString());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("WHEN a resident credential is added to the authenticator", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));
      step.parameter("userHandle:", Arrays.toString(userHandle));
      step.parameter("sign count:", "0");
      Credential residentCredential = Credential.createResidentCredential(
            credentialId,
            "localhost",
            rsaPrivateKey,
            userHandle,
            0
      );
      auth.get().addCredential(residentCredential);
    });
    Allure.step("THEN the resident credential can be seen on the authenticator", step -> {
      List<Credential> credentialList = auth.get().getCredentials();
      credentialList.forEach( (n) -> step.parameter("Credential:", n.toMap().entrySet().toString()));
      assertEquals(1, credentialList.size());
      Credential credential = credentialList.getFirst();
      assertArrayEquals(credentialId, credential.getId());
      assertArrayEquals(rsaPrivateKey.getEncoded(), credential.getPrivateKey().getEncoded());
      assertArrayEquals(userHandle, credential.getUserHandle());
      assertEquals("localhost", credential.getRpId());
      assertEquals(0,credential.getSignCount());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-165")
  @DisplayName("Should not be able to add a resident credential when setHasUserVerification=false")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1165")
  public void residentCredentialNotSupportedWhenSetHasUserVerificationFalse() {
    byte[] credentialId = {1, 2, 3, 4};
    byte[] userHandle = {1};
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();
    AtomicReference<Credential> residentialCredential = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using U2F protocol", step -> {
      step.parameter("protocol set to:", "U2F");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.U2F));
    });
    Allure.step("AND hasResidentKey is set to true", step -> {
      step.parameter("hasResidentKey set to:", "true");
      options.set(options.get().setHasResidentKey(true));
    });
    Allure.step("AND setHasUserVerification is set to false", step -> {
      step.parameter("setHasUserVerification set to:", "false");
      options.set(options.get().setHasUserVerification(false));
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("options set", options.get().toMap().entrySet().toString());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("WHEN a resident credential is created", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(ec256PrivateKey.getEncoded()));
      step.parameter("userHandle:", Arrays.toString(userHandle));
      step.parameter("sign count:", "0");
      Credential credential = Credential.createResidentCredential(
            credentialId,
            "localhost",
            ec256PrivateKey,
            userHandle,
            0
      );
      residentialCredential.set(credential);
      step.parameter("Credential", credential.toMap().entrySet().toString());
    });
    Allure.step("THEN the resident credential can not be added to the authenticator", step -> {
      try {
        step.parameter("Attempting to add resident credential to U2F authenticator", "This should fail");
        auth.get().addCredential(residentialCredential.get());
      }
      catch (InvalidArgumentException e) {
        step.parameter("Error:", e.getMessage());
        assertThrows(InvalidArgumentException.class, () -> auth.get().addCredential(residentialCredential.get()));
      }
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-166")
  @DisplayName("Should be able to add a non-resident credential when setHasUserVerification=false")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1166")
  public void testCreateAndAddNonResidentialKey() {
    byte[] credentialId = {1, 2, 3, 4};
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using U2F protocol", step -> {
      step.parameter("protocol set to:", "U2F");
      options.set(new VirtualAuthenticatorOptions().setProtocol(VirtualAuthenticatorOptions.Protocol.U2F));
    });
    Allure.step("AND hasResidentKey is set to false", step -> {
      step.parameter("hasResidentKey set to:", "false");
      options.set(options.get().setHasResidentKey(false));
    });
    Allure.step("AND setHasUserVerification is set to false", step -> {
      step.parameter("setHasUserVerification set to:", "false");
      options.set(options.get().setHasUserVerification(false));
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("options set", options.get().toMap().entrySet().toString());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("WHEN a non-resident credential is added to the authenticator", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(ec256PrivateKey.getEncoded()));
      step.parameter("sign count:", "0");
      Credential nonResidentCredential = Credential.createNonResidentCredential(
            credentialId,
            "localhost",
            ec256PrivateKey,
            0
      );
      auth.get().addCredential(nonResidentCredential);
    });
    Allure.step("THEN the non-resident credential can be seen on the authenticator", step -> {
      List<Credential> credentialList = auth.get().getCredentials();
      credentialList.forEach( (n) -> step.parameter("Credential:", n.toMap().entrySet().toString()));
      assertEquals(1, credentialList.size());
      Credential credential = credentialList.getFirst();
      assertArrayEquals(credentialId, credential.getId());
    });
  }


  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-167")
  @DisplayName("Should be able to remove a non-resident credential")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1167")
  public void testRemoveCredential() {
    byte[] credentialId = {1, 2, 3, 4};
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using default options", step -> {
      options.set(new VirtualAuthenticatorOptions());
      step.parameter("options:", options.get().toMap().entrySet().toString());
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("AND a non-resident credential is added to the authenticator", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(ec256PrivateKey.getEncoded()));
      step.parameter("sign count:", "0");
      Credential nonResidentCredential = Credential.createNonResidentCredential(
            credentialId,
            "localhost",
            ec256PrivateKey,
            0
      );
      auth.get().addCredential(nonResidentCredential);
    });
    Allure.step("AND the non-resident credential can be seen on the authenticator", step -> {
      List<Credential> credentialList = auth.get().getCredentials();
      credentialList.forEach( (n) -> step.parameter("Credential:", n.toMap().entrySet().toString()));
      assertEquals(1, credentialList.size());
      Credential credential = credentialList.getFirst();
      assertArrayEquals(credentialId, credential.getId());
    });
    Allure.step("WHEN the non-resident credential is removed the authenticator", step -> {
      auth.get().removeCredential(credentialId);
      step.parameter("removeCredential()", "removes the credential from the browser session.");
    });
    Allure.step("THEN the non-resident credential is removed the authenticator", step -> {
      step.parameter("Credential", Arrays.toString(auth.get().getCredentials().toArray()));
      List<Credential> emptyList = new ArrayList<>();
      assertEquals(emptyList, auth.get().getCredentials());
    });
  }

  @Test
  @Tag("regression")
  @Story("Story: create virtual authenticator")
  @TmsLink("TC-168")
  @DisplayName("Should be able to remove all credentials")
  @Severity(SeverityLevel.NORMAL)
  @Owner("QA/Chris")
  @Issue("BUG-1168")
  public void testRemoveAllCredentials() {
    byte[] credentialId1 = {1, 2, 3, 4};
    byte[] credentialId2 = {5, 6, 7, 8};
    AtomicReference<VirtualAuthenticatorOptions> options = new AtomicReference<>();
    AtomicReference<VirtualAuthenticator> auth = new AtomicReference<>();

    Allure.step("AND the virtual authenticator options are set using default options", step -> {
      options.set(new VirtualAuthenticatorOptions());
      step.parameter("options:", options.get().toMap().entrySet().toString());
    });
    Allure.step("AND the virtual authenticator is created", step -> {
      VirtualAuthenticator authenticator = ((HasVirtualAuthenticator) driver).addVirtualAuthenticator(options.get());
      step.parameter("Virtual Authenticator id:", authenticator.getId());
      auth.set(authenticator);
    });
    Allure.step("AND the first non-resident credential is added to the authenticator", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId1));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));
      step.parameter("sign count:", "0");
      Credential nonResidentCredential = Credential.createNonResidentCredential(
            credentialId1,
            "localhost",
            rsaPrivateKey,
            0
      );
      auth.get().addCredential(nonResidentCredential);
    });
    Allure.step("AND the second non-resident credential is added to the authenticator", step -> {
      step.parameter("credential id:", Arrays.toString(credentialId2));
      step.parameter("rp id:", "localhost");
      step.parameter("Encoding:", "PKCS8EncodedKeySpec");
      step.parameter("privateKey:", Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));
      step.parameter("sign count:", "0");
      Credential nonResidentCredential = Credential.createNonResidentCredential(
            credentialId2,
            "localhost",
            rsaPrivateKey,
            0
      );
      auth.get().addCredential(nonResidentCredential);
    });
    Allure.step("AND the non-resident credentials can be seen on the authenticator", step -> {
      List<Credential> credentialList = auth.get().getCredentials();
      assertEquals(2, credentialList.size());
      credentialList.forEach( (n) -> step.parameter("Credential:", n.toMap().entrySet().toString()));
      Credential credential = credentialList.getFirst();
      assertArrayEquals(credentialId1, credential.getId());
      credential = credentialList.getLast();
      assertArrayEquals(credentialId2, credential.getId());
    });
    Allure.step("WHEN the non-resident credential is removed the authenticator", step -> {
      auth.get().removeAllCredentials();
      step.parameter("removeAllCredentials()", "removes all the credentials from the browser session.");
    });
    Allure.step("THEN the non-resident credential is removed the authenticator", step -> {
      List<Credential> credentialList = auth.get().getCredentials();
      assertEquals(0, credentialList.size());
      step.parameter("Credential", Arrays.toString(credentialList.toArray()));
      List<Credential> emptyList = new ArrayList<>();
      assertEquals(emptyList, auth.get().getCredentials());
    });
  }

}