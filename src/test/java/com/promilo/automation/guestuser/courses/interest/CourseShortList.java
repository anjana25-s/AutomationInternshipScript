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
import com.promilo.automation.courses.intrestspages.CoursesShortlist;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class CourseShortList extends BaseClass {

    @Test
    public void courseShortListTest() throws InterruptedException, IOException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 CourseShortList Functionality | Data-Driven");

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

            // Close landing popup
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try { mayBeLaterPopUp.getPopup().click(); } catch (Exception e) {}

            CoursesShortlist shortList = new CoursesShortlist(page);

            // Navigate to Courses & search
            shortList.coursesTab().click();
            shortList.searchCourseInput().fill("BTWIN");
            page.keyboard().press("Enter");

            // Click Shortlist
            shortList.shortlistIcon().click();

            // Fill user details
            shortList.userNameField().fill("karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

            shortList.userEmailField().fill(randomEmail);
            shortList.userMobileField().fill(randomPhone);

            // Select locations
            shortList.preferredLocationDropdown().click();
            List<String> locations = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
            Locator options = shortList.locationOptions();
            for (String loc : locations) {
                for (int j = 0; j < options.count(); j++) {
                    if (options.nth(j).innerText().trim().equalsIgnoreCase(loc)) {
                        options.nth(j).click();
                        break;
                    }
                }
            }
            

            // Submit shortlist form
            shortList.submitShortlistButton().click();

            // OTP
            String otp = "9999";
            for (int d = 1; d <= 4; d++) {
                Locator otpField = shortList.otpDigit(d);
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
                otpField.fill(String.valueOf(otp.charAt(d - 1)));
            }
            shortList.verifyAndProceedButton().click();

            // Validate Thank You popup
            shortList.thankYouPopup().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            Assert.assertEquals(shortList.thankYouPopup().innerText().trim(), "Thank You!");
            shortList.thankYouCloseIcon().click();

            // Mailosaur
            Page mailsaurPage = page.context().newPage();
            mailsaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

            MailsaurCredentials approveMail = new MailsaurCredentials(mailsaurPage);
            approveMail.MialsaurMail();
            approveMail.MailsaurContinue();
            approveMail.MailsaurPassword();
            approveMail.MailsaurLogin();

            shortList.emailCard().click();
            shortList.emailGreeting().textContent();
            shortList.emailGreatNewsText().textContent();
            shortList.emailHelpText().textContent();
            shortList.emailDoText().textContent();

            // Open Counseling link
            Page counselingPage = mailsaurPage.waitForPopup(() -> { shortList.counselingLink().click(); });
            counselingPage.waitForLoadState();
            counselingPage.close();

            // Open Talk to Expert link
            Page expertPage = mailsaurPage.waitForPopup(() -> { shortList.talkToExpertLink().click(); });
            expertPage.waitForLoadState();
        }

        extent.flush();
    }
}
