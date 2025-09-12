package com.promilo.automation.courses.myintrests;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.FreeVideoCounsellingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExcelReadUtil;  // ✅ added import
import com.promilo.automation.resources.ExtentManager;

public class MyIntrestCancelFunctionality extends Baseclass {

    @Test
    public void MyIntrestCancelFunctionalityTest() throws InterruptedException, IOException {
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
            String email = excel.getCellData(i, 7); // MailPhone
            String password = excel.getCellData(i, 6); // Password
            String comment = excel.getCellData(i, 10); // Comment text

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
            } catch (Exception ignored) {
            }

            FreeVideoCounsellingPage counsellingPage = new FreeVideoCounsellingPage(page);

            Thread.sleep(3000);
            counsellingPage.coursesMenu().click();
            Thread.sleep(3000);
            counsellingPage.seeMoreLink().click();
            Thread.sleep(3000);

            counsellingPage.lpuOption().click();
            counsellingPage.freeVideoCounsellingBtn().click();

            counsellingPage.userNameField().fill("karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String randomEmail = "testuserautomation" + System.currentTimeMillis() + "@mail.com";

            counsellingPage.userMobileField().fill(randomPhone);
            counsellingPage.userEmailField().fill(randomEmail);

            counsellingPage.preferredLocationDropdown().click();
            Thread.sleep(1000);

            List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
            Locator options = counsellingPage.locationOptions();
            for (String industry : industries) {
                boolean found = false;
                for (int i1 = 0; i1 < options.count(); i1++) {
                    String optionText = options.nth(i1).innerText().trim();
                    if (optionText.equalsIgnoreCase(industry)) {
                        options.nth(i1).click();
                        test.info("✅ Selected industry: " + industry);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    test.warning("⚠️ Industry not found: " + industry);
                }
            }

            counsellingPage.textContent().textContent();
            counsellingPage.preferredLocationDropdown().click();
            counsellingPage.enableNotificationsCheckbox().click();
            counsellingPage.finalCounsellingBtn().click();

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

            counsellingPage.verifyProceedBtn().click();
            counsellingPage.datePickerDay().click();
            counsellingPage.timeSlot().click();
            Thread.sleep(3000);
            counsellingPage.submitBtn().click();

            counsellingPage.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(5000));
            String popupText = counsellingPage.thankYouPopup().innerText().trim();
            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);
            test.pass("🎉 Job applied successfully — Popup: " + popupText);

            counsellingPage.thankYouCloseIcon().click();
            counsellingPage.myInterestTab().click();
            
            System.out.println(page.locator("//div[@class='my-preferance-card-body card-body']").textContent());  
page.locator("//span[text()='Cancel']").click();
page.locator("//span[text()='Yes']").click();
            
            



            


            
        }
    }
}
