package com.promilo.automation.guestuser.courses.interest;

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
import com.promilo.automation.courses.intrestspages.ConnectNowPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ConnectNow extends BaseClass {

    @Test
    public void TaltoExpert() throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 ConnectNow Functionality | Data-Driven");

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

            test.info("🔐 Executing TestCase: " + testCaseId);

            Page page = initializePlaywright();

            page.navigate(prop.getProperty("url"));
            test.info("🌍 Navigated to URL");

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try {
                popup.getPopup().click();
                test.info("Popup closed.");
            } catch (Exception e) {
                test.info("No popup.");
            }

            Thread.sleep(3000);

            // -------------------------------
            // Create POM Object
            // -------------------------------
            ConnectNowPage connectNow = new ConnectNowPage(page);

            // -------------------------------
            // COURSES SEARCH FLOW
            // -------------------------------
            page.locator("//a[text()='Courses']").click();
            test.info("Clicked 'Courses' menu");

            page.locator("//input[@placeholder='Search Colleges and Courses']")
                .fill("BTWIN");

            page.locator("//input[@placeholder='Search Colleges and Courses']").click();
            page.keyboard().press("Enter");
            test.info("Searched for BTWIN");

            // -------------------------------
            // CONNECT NOW POPUP
            // -------------------------------
            connectNow.connectNowButton().click();
            test.info("Clicked 'Connect Now'");

            connectNow.needHelpHeader().textContent().trim();
            connectNow.registerWithUsHeader().textContent().trim();
            connectNow.whatsappUpdatesLabel().textContent().trim();
            connectNow.whyShouldYouSignupText().textContent().trim();

            // -------------------------------
            // USER DETAILS
            // -------------------------------
            connectNow.userNameField().fill("karthik");
            test.info("Entered name");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            connectNow.mobileField().fill(randomPhone);
            test.info("Entered phone: " + randomPhone);

            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

            connectNow.emailField().fill(randomEmail);
            test.info("Entered email: " + randomEmail);

            // -------------------------------
            // STREAM SELECTION
            // -------------------------------
            connectNow.preferredStreamDropdown().click();
            connectNow.engineeringStreamOption().click();
            test.info("Selected Engineering");

            connectNow.firstCheckBox().click();

            // -------------------------------
            // LOCATION SELECTION
            // -------------------------------
            connectNow.preferredLocationDropdown().click();

            List<String> locations = Arrays.asList(
                    "Ahmedabad",
                    "Bengaluru/Bangalore",
                    "Chennai",
                    "Mumbai (All Areas)"
            );

            Locator options = connectNow.preferredLocationOptions();

            for (String loc : locations) {
                boolean found = false;
                for (int j = 0; j < options.count(); j++) {
                    String txt = options.nth(j).innerText().trim();
                    if (txt.equalsIgnoreCase(loc)) {
                        options.nth(j).click();
                        test.info("Selected location: " + loc);
                        found = true;
                        break;
                    }
                }
                if (!found) test.warning("Location not found: " + loc);
            }

            // -------------------------------
            // PASSWORD
            // -------------------------------
            connectNow.passwordField().fill("123456@ab");
            connectNow.registerButton().click();
            test.info("Clicked Register");

            // -------------------------------
            // OTP ENTER
            // -------------------------------
            String otp = "9999";

            for (int d = 1; d <= 4; d++) {
                Locator otpField = connectNow.otpDigitField(d);
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000)
                        .setState(WaitForSelectorState.VISIBLE));
                otpField.click();
                otpField.fill(String.valueOf(otp.charAt(d - 1)));
            }

            connectNow.verifyAndProceedButton().click();
            test.info("Clicked Verify & Proceed");

            // -------------------------------
            // THANK YOU POPUP
            // -------------------------------
            connectNow.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(10000));

            String text = connectNow.thankYouPopup().innerText().trim();
            Assert.assertEquals(text, "Thank You!", "Popup mismatch");
            connectNow.thankYouCloseIcon().click();

            Thread.sleep(2000);

            // -------------------------------
            // NOTIFICATION
            // -------------------------------
            connectNow.inAppNotificationIcon().click();


            test.pass("TestCase " + testCaseId + " executed successfully.");
        }

        extent.flush();
    }
}
