package com.promilo.automation.advertiser.mymeetings;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CommentFunctionality extends Baseclass {

    @Test
    public void verifyCommentFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("💬 Comment Functionality | Data-Driven Test");

        // Load Excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);
            String comment = excel.getCellData(i, 12);
            String editedComment = excel.getCellData(i, 13);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            // UI assertions
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

            // Login
            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigate to My Meetings
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();

            AdverstiserMyaccount account = new AdverstiserMyaccount(page);
            account.myMeeting().click();

            AdvertiserProspects prospects = new AdvertiserProspects(page);
            prospects.Jobs().click();
            Thread.sleep(3000);

            // Comment Add
            prospects.commentButton().first().click();
            prospects.CommentTextfield().fill(comment);
            prospects.SendButton().first().click();

            // Validate Comment Count
            Locator commentCount = prospects.CommentCount().first();
            Assert.assertTrue(commentCount.isVisible(), "❌ Comment count should be visible");
            System.out.println("Comment count: " + commentCount.textContent().trim());

            // Print Comment Authors
            Locator commentAuthors = page.locator("//p[@class='comment-by']");
            int count = commentAuthors.count();
            for (int j = 0; j < count; j++) {
                String author = commentAuthors.nth(j).textContent().trim();
                System.out.println("Comment Author " + (j + 1) + ": " + author);
            }

            // Edit Comment
            page.locator("div[class='comment-list']").first().hover();
            page.locator("img[class='edit-button CursorPointer']").first().click();
            prospects.CommentTextfield().clear();
            prospects.CommentTextfield().fill(editedComment);
            prospects.SendButton().first().click();

            Thread.sleep(2000);

            // Delete Comment
            prospects.EditComment().hover();
            page.locator("//img[@alt='Delete comment']").first().click();
            System.out.println("✅ Comment edited and deleted for: " + email);

            page.close(); // Clean up
        }
    }
}
