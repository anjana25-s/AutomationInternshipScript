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
import com.promilo.automation.courses.intrestspages.DownloadsIntrestPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class DownloadBrochure extends BaseClass {

    @Test
    public void DownloadBrochureTest() throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Download Brochure Functionality | Data-Driven");

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
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            DownloadsIntrestPage downloadsPage = new DownloadsIntrestPage(page);

            // Close popup
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click();
            } catch (Exception ignored) {}

            Thread.sleep(3000);

            // Navigate to Courses
            downloadsPage.coursesTab().click();
            test.info("📚 Clicked Courses Menu");

            // Download Brochure
            downloadsPage.downloadBrochureButton().click();
            test.info("📂 Clicked 'Download Brochure'");

            // Fill form
            downloadsPage.userNameField().fill("karthik Download Validation");

            // Random Data
            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

            downloadsPage.userEmailField().fill(randomEmail);
            downloadsPage.userMobileField().fill(randomPhone);

            test.info("📧 Email: " + randomEmail + " | 📱 Phone: " + randomPhone);

            downloadsPage.brochureSubmitButton().click();
            test.info("🚀 Clicked 'Download Brochure'");

            // OTP
            String otp = "9999";
            test.info("🔑 Entering OTP: " + otp);

            for (int x = 0; x < 4; x++) {
                String digit = Character.toString(otp.charAt(x));

                Locator otpField = downloadsPage.otpDigit(x + 1);

                otpField.waitFor(new Locator.WaitForOptions()
                        .setTimeout(10000)
                        .setState(WaitForSelectorState.VISIBLE));

                otpField.click();
                otpField.fill("");
                otpField.fill(digit);
            }

            downloadsPage.verifyAndProceedButton().click();
            test.info("☑️ Clicked 'Verify & Proceed'");

            // Validate: Thank You popup
            downloadsPage.thankYouPopup().waitFor(
                    new Locator.WaitForOptions().setTimeout(15000));

            String popupText = downloadsPage.thankYouPopup().innerText().trim();

            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);

            test.pass("🎉 Thank You popup validated successfully.");

            // MAILOSAUR
            Page mailsaurPage = page.context().newPage();
            mailsaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(mailsaurPage);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MailsaurLogin();

            // Open mail
            downloadsPage.mailosaurEmailCard().click();
            test.info("📨 Opened Mailosaur Email");

            downloadsPage.mailosaurGreeting().textContent();
            downloadsPage.mailosaurInterestText().textContent();

            downloadsPage.mailosaurDownloadBrochureLink().click();
            test.info("📂 Clicked 'Download Brochure' link in email");

            downloadsPage.mailosaurFinalDownloadLink().click();
            test.info("⬇️ Download link clicked (Final)");

        }

        extent.flush();
    }
}
