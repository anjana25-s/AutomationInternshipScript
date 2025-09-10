package com.promilo.automation.courses;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class RegisterWithUs extends Baseclass{

	
	@Test
	public void RegisterWithUs() throws IOException, InterruptedException {
	ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🧪 Comment Functionality | Simplified");

    String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
            "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
    ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

    int rowCount = 0;
    for (int i = 1; i <= 1000; i++) {
        String testCaseId = excel.getCellData(i, 0);
        if (testCaseId == null || testCaseId.trim().isEmpty())
            break;
        rowCount++;
    }

    test.info("📘 Loaded " + rowCount + " rows from Excel.");

    for (int i = 1; i < rowCount; i++) {
        String testCaseId = excel.getCellData(i, 0);
        String keyword = excel.getCellData(i, 1);
        if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
            continue;
        }

        String email = excel.getCellData(i, 7); // MailPhone
        String password = excel.getCellData(i, 6); // Password

        test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        Thread.sleep(3000);

        // Close popup if present
        try {
            new LandingPage(page).getPopup().click();
        } catch (Exception ignored) {}

        Thread.sleep(3000);

        // Navigate to Courses
        page.locator("//a[text()='Courses']").click();
        Thread.sleep(5000);
        
        page.locator("//input[@name='userName']").fill("karthik");
        page.locator("//input[@placeholder='Mobile*']").fill("9000098610");
        page.locator("#userEmail").fill("testuser00888@gmail.com");
        page.locator("#preferredLocation").click();
        Thread.sleep(2000);
        page.locator("//label[text()='Ahmedabad']").click();
        Thread.sleep(2000);
        page.locator("//input[@placeholder='Preferred Course to Study*']").click();
        Thread.sleep(2000);
        page.locator("//label[text()='Engineering']").click();
        Thread.sleep(2000);
        
        page.locator("//label[text()='B.E. / B.Tech']").click();
        Thread.sleep(2000);
        page.locator("#password").fill("123456kk@");
        
        page.locator("//button[text()='Register Now']").click();
        
        
        String otp = "9999";
        // OTP input logic
        if (otp == null || otp.length() < 4)
            throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

        for (int i1 = 0; i1 < 4; i1++) {
            String digit = Character.toString(otp.charAt(i1));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);

                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
        }
        
        
        page.locator("//button[text()='Verify & Proceed']").click();
        // ✅ Validate Thank You popup
        Locator thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000)); // wait max 10s

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, found: " + popupText);
        test.pass("🎉 Thank You popup validated: " + popupText);
        
        
        
        
    }
}
	
}
