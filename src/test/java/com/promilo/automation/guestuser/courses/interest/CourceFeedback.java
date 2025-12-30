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
import com.promilo.automation.courses.intrestspages.CourseFeedbackPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class CourceFeedback extends BaseClass {

    @Test
    public void CourceFeedbackTest() throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 CourceFeedback Functionality | Data-Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            if (excel.getCellData(i, 0) == null || excel.getCellData(i, 0).isEmpty()) break;
            rowCount++;
        }

        for (int i = 1; i < rowCount; i++) {

            String keyword = excel.getCellData(i, 1);
            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) continue;

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            Thread.sleep(3000);

            // Close popup
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try { mayBeLaterPopUp.getPopup().click(); } catch (Exception e) {}

            CourseFeedbackPage feedBack = new CourseFeedbackPage(page);

            Thread.sleep(3000);
            feedBack.coursesTab().click();
            
            
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
            
            
            

            feedBack.feedbackTextarea().fill("something");
            feedBack.submitFeedbackButton().click();

            feedBack.userNameField().fill("karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));

            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

            feedBack.userEmailField().fill(randomEmail);
            feedBack.userMobileField().fill(randomPhone);

            feedBack.finalSubmitButton().click();

            // OTP Entry
            String otp = "9999";
            for (int d = 1; d <= 4; d++) {
                Locator otpField = feedBack.otpDigit(d);
                otpField.waitFor(new Locator.WaitForOptions()
                        .setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

                otpField.click();
                otpField.fill(String.valueOf(otp.charAt(d - 1)));
            }

            feedBack.verifyAndProceedButton().click();

            feedBack.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            Assert.assertEquals(feedBack.thankYouPopup().innerText().trim(), "Thank You!");

            feedBack.thankYouCloseIcon().click();

            Thread.sleep(2000);
            feedBack.notificationIcon().click();

            String notifText = feedBack.thankYouNotificationText().textContent();

            // MAILOSAUR LOGIN
            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(page);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MialsaurMail();

            feedBack.feedbackEmailCard().click();
            feedBack.emailGreeting().textContent();
            feedBack.emailThankYouText().textContent();
            feedBack.emailWhatsNext().textContent();
            feedBack.emailBodyText().textContent();
            feedBack.emailFooterText().textContent();

        }

        extent.flush();
    }
}
