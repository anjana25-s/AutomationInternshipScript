package com.promilo.automation.courses.myintrests;

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
import com.promilo.automation.courses.intrestspages.DownloadsIntrestPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class DownloadsIntrestValidation extends Baseclass {

    @Test
    public void DownloadsIntrestValidation() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Comment Functionality | Data-Driven");

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

        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7); 
            String password = excel.getCellData(i, 6); 
            String comment = excel.getCellData(i, 10); 

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            System.out.println("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);
            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            LandingPage landingPage = new LandingPage(page);
            try {
                landingPage.getPopup().click();
            } catch (Exception ignored) {}

            DownloadsIntrestPage downloadsPage = new DownloadsIntrestPage(page);

            Thread.sleep(3000);
            downloadsPage.coursesMenu().click();
            downloadsPage.downloadBrochureBtn().click();
            downloadsPage.userNameField().fill("karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String randomEmail = "testuserautomation" + System.currentTimeMillis() + "@mail.com";

            downloadsPage.userMobileField().fill(randomPhone);
            downloadsPage.userEmailField().fill(randomEmail);
            downloadsPage.finalDownloadBtn().click();

            String otp = "9999";
            for (int i1 = 0; i1 < 4; i1++) {
                String digit = Character.toString(otp.charAt(i1));
                Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']"
                );
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

            downloadsPage.verifyProceedBtn().click();

            downloadsPage.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            String popupText = downloadsPage.thankYouPopup().innerText().trim();
            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);
            test.pass("🎉 Thank You popup validated: " + popupText);

            downloadsPage.thankYouCloseIcon().click();
            downloadsPage.myInterestTab().click();
            downloadsPage.myPreferenceTab().click();
            downloadsPage.downloadsTab().click();

            System.out.println(downloadsPage.totalResults().textContent());
        }
    }
}
