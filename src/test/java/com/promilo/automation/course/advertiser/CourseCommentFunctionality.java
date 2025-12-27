package com.promilo.automation.course.advertiser;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CourseCommentFunctionality extends BaseClass {

    @Test
    public void CommentFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Course Comment Functionality | Data-Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);        // MailPhone
            String password = excel.getCellData(i, 6);     // Password
            String comment = excel.getCellData(i, 10);     // Comment text

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            System.out.println("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);
            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            System.out.println("✅ Navigated to stage URL and set viewport");
            test.info("✅ Navigated to stage URL and set viewport");

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

            System.out.println("🔑 Performing login with email");
            test.info("🔑 Performing login with email");

            login.loginMailField().fill("adv@yopmail.com");
            login.loginPasswordField().fill("devuttan2023");
            login.signInButton().click();
            System.out.println("✅ Login submitted");
            test.info("✅ Login submitted");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.hamburger().click();
            System.out.println("📂 Clicked hamburger menu");
            test.info("📂 Clicked hamburger menu");

            myaccount.myAccount().click();
            System.out.println("📂 Opened My Account section");
            test.info("📂 Opened My Account section");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myMeeting().click();
            System.out.println("📅 Navigated to My Meetings");
            test.info("📅 Navigated to My Meetings");

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            Thread.sleep(3000);
            test.info("💼 Opened Course section");

            approveFunctionality.commentButton().first().click();
            System.out.println("✏️ Clicked comment button");
            test.info("✏️ Clicked comment button");

            approveFunctionality.CommentTextfield().fill("comment1");
            approveFunctionality.SendButton().first().click();
            System.out.println("✅ Comment submitted: comment1");
            test.info("✅ Comment submitted: comment1");

            Locator commentCount = approveFunctionality.CommentCount().first();
            Assert.assertTrue(commentCount.isVisible(), "Comment count should be visible");
            String commentText = commentCount.textContent().trim();
            System.out.println("📝 Comment Count after posting: " + commentText);
            test.info("📝 Comment Count after posting: " + commentText);

            // Print all comment authors
            Locator commentAuthors = page.locator("//p[@class='comment-by']");
            int count = commentAuthors.count();
            for (int j = 0; j < count; j++) {
                String authorText = commentAuthors.nth(j).textContent().trim();
                System.out.println("🗣 Comment Author " + (j + 1) + ": " + authorText);
                test.info("🗣 Comment Author " + (j + 1) + ": " + authorText);
            }

            // Hover and edit the comment
            AdvertiserProspects hover = new AdvertiserProspects(page);
            page.getByText("comment1").first().click();
            System.out.println("✏️ Hovered on comment for edit");
            test.info("✏️ Hovered on comment for edit");

            approveFunctionality.CommentTextfield().clear();
            approveFunctionality.CommentTextfield().fill("comment1 - Edited");
            approveFunctionality.SendButton().first().click();
            System.out.println("✅ Edited comment submitted");
            test.info("✅ Edited comment submitted");

            Thread.sleep(2000);

            page.getByText("comment1").first().click();
            System.out.println("✏️ Hovered on comment for edit");
            test.info("✏️ Hovered on comment for edit");

            page.locator("//p[@class='comment-text']").first().hover();
            page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("Delete comment")).click();
            test.info("🗑️ Clicked delete comment button");

            page.close();
            System.out.println("🛑 Closed page after iteration");
            test.info("🛑 Closed page after iteration");
        }

        // ✅ Flush Extent Report after all iterations
        extent.flush();
    }
}
