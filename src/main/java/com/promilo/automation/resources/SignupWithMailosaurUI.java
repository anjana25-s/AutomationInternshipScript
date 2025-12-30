package com.promilo.automation.resources;

import java.nio.file.Paths;
import java.util.UUID;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.aventstack.extentreports.*;

import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;

import org.testng.annotations.Test;

public class SignupWithMailosaurUI extends BaseClass {

    private static final String SERVER_ID = "qtvjnqv9";
    private static final String MAILOSAUR_WEB_URL = "https://mailosaur.com/app/inboxes/" + SERVER_ID;

    public static String generateNewMail() {
        String randomPrefix = UUID.randomUUID().toString().substring(0, 8);
        return randomPrefix + "@" + SERVER_ID + ".mailosaur.net";
    }

    /**
     * Reusable signup method that performs signup and returns {email, password}
     * Can be called from other classes for programmatic signup.
     */
    public String[] performSignupAndReturnCredentials() throws Exception {
        String password = "Karthik@88";
        Page page = initializePlaywright();

        try {
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
            } catch (PlaywrightException e) {
                // No popup appeared
            }

            mayBeLaterPopUp.clickSignup();

            CreateAccountpage accountPage = new CreateAccountpage(page);

            String email = generateNewMail();
            accountPage.getPhoneMailTextField().fill(email);

            accountPage.getSendCodeButton().click();

            // Open new tab for Mailosaur UI
            BrowserContext context = page.context();
            Page mailosaurPage = context.newPage();
            mailosaurPage.navigate("https://mailosaur.com/app/servers/" + SERVER_ID + "/messages/inbox");

            // Login to Mailosaur
            mailosaurPage.locator("//input[@placeholder='Enter your email address']").fill("karthikmailsaur@gmail.com");
            mailosaurPage.locator("//button[text()='Continue']").click();

            mailosaurPage.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
            mailosaurPage.locator("//button[text()='Log in']").click();

            
            
            
            mailosaurPage.waitForTimeout(3000);
            mailosaurPage.reload();
            Locator firstEmail = mailosaurPage.locator("//p[text()='Verify Your Email Address to Complete Promilo Registration 🌐']").first();
            firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            firstEmail.click();

            Thread.sleep(5000);

            String otpText = mailosaurPage.locator("//p[contains(text(),'Verification Code')]").innerText().trim();
            String otp = otpText.replaceAll("\\D+", "");
            System.out.println("✅ OTP fetched from Mailosaur UI: " + otp);

            mailosaurPage.close();

            accountPage.getOtpField().fill(otp);
            accountPage.getPasswordField().fill(password);

            accountPage.getSubmitButton().click();

            page.waitForTimeout(5000);
           HomePage homePage = new HomePage(page);
           PlaywrightAssertions.assertThat(homePage.mystuff()).isVisible();

            return new String[] { email, password };

        } finally {
            closePlaywright();
        }
    }

    /**
     * Standalone TestNG test method for signup.
     * Calls the reusable method and reports the results.
     */
    @Test
    public void signupUsingUIOtpFetch() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Signup with Mailosaur OTP (UI Fetch)");

        try {
            String[] creds = performSignupAndReturnCredentials();

            test.pass("✅ Signup successful with email: " + creds[0]);

            String screenshotPath = System.getProperty("user.dir") + "/screenshots/signup_pass.png";
            test.addScreenCaptureFromPath(screenshotPath);

        } catch (Exception e) {
            test.fail("❌ Signup failed: " + e.getMessage());
            throw e;
            
        } finally {
            extent.flush();
        }
    }
}
