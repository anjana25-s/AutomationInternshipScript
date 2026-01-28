package com.promilo.automation.guestuser.courses.interest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class RegisterWithUs extends BaseClass {

    @Test
    public void RegisterWithUsTest() throws IOException, InterruptedException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 RegisterWithUs Functionality | Simplified");

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
                test.info("⏭️ Skipping TestCaseID: " + testCaseId + " (Keyword mismatch)");
                continue;
            }

            String email = excel.getCellData(i, 7); // MailPhone
            String password = excel.getCellData(i, 6); // Password
            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Browser launched and Playwright initialized.");

            page.navigate(prop.getProperty("url"));
            test.info("🔗 Navigated to URL: " + prop.getProperty("url"));

            page.setViewportSize(1000, 768);
            test.info("🖥️ Set viewport size to 1000x768.");

            Thread.sleep(3000);
            test.info("⏳ Waited 3 seconds for page load.");

            // Close popup if present
            try {
                new MayBeLaterPopUp(page).getPopup().click();
                test.info("✅ Closed popup successfully.");
            } catch (Exception ignored) {
                test.warning("⚠️ No popup found to close.");
            }

            Thread.sleep(3000);
            test.info("⏳ Waited 3 seconds before navigation.");

            // Navigate to Courses
            page.locator("//a[text()='Courses']").click();
            test.info("📚 Clicked on 'Courses' link.");
            Thread.sleep(5000);
            test.info("⏳ Waited 5 seconds for Courses page to load.");

            page.locator("//input[@name='userName']").fill("karthik");
            test.info("👤 Entered Name: karthik");

            page.locator("//input[@placeholder='Mobile*']").fill("9000098610");
            test.info("📱 Entered Mobile: 9000098610");

            // Generate random email with timestamp
            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";
            page.locator("#userEmail").fill(randomEmail);
            test.info("📧 Entered Email: " + randomEmail);

            page.locator("#preferredLocation").click();
            test.info("📍 Clicked Preferred Location dropdown.");
            Thread.sleep(2000);

            page.locator("//label[text()='Ahmedabad']").click();
            test.info("✅ Selected Location: Ahmedabad");
            Thread.sleep(2000);

            page.locator("//input[@placeholder='Preferred Course to Study*']").click();
            test.info("🎓 Clicked Preferred Course to Study field.");
            Thread.sleep(2000);

            page.locator("//label[text()='Engineering']").click();
            test.info("✅ Selected Course: Engineering");
            Thread.sleep(2000);

            page.locator("//label[text()='B.E. / B.Tech']").click();
            test.info("✅ Selected Specialization: B.E. / B.Tech");
            Thread.sleep(2000);

            page.locator("#password").fill("123456kk@");
            test.info("🔑 Entered Password: 123456kk@");

            page.locator("//button[text()='Register Now']").click();
            test.info("🚀 Clicked 'Register Now' button.");

            // OTP
            String otp = "9999";
            test.info("📨 Using OTP: " + otp);

            if (otp == null || otp.length() < 4)
                throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

            for (int i1 = 0; i1 < 4; i1++) {
                String digit = Character.toString(otp.charAt(i1));
                Locator otpField = page.locator(
                        "//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000)
                        .setState(WaitForSelectorState.VISIBLE));

                for (int retry = 0; retry < 3; retry++) {
                    otpField.click();
                    otpField.fill("");
                    otpField.fill(digit);
                    test.info("⌨️ Entered OTP digit " + digit + " at position " + (i1 + 1));

                    if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                        break;
                    page.waitForTimeout(500);
                }
            }

            page.locator("//button[text()='Verify & Proceed']").click();
            test.info("✅ Clicked 'Verify & Proceed'.");

            // Validate Thank You popup
            Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            test.info("⏳ Waiting for Thank You popup...");

            String popupText = thankYouPopup.innerText().trim();
            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);
            test.pass("🎉 Thank You popup validated successfully: " + popupText);
        }
        extent.flush();
    }
}
