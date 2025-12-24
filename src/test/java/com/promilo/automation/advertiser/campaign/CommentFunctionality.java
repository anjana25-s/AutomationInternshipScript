package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.*;

public class CommentFunctionality extends Baseclass {

    @Test
    public void verifyBasicDetailsFunctionality() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 Comment Functionality | Data-Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);        // MailPhone
            String password = excel.getCellData(i, 6);     // Password
            String comment = excel.getCellData(i, 10);     // Use TextArea or any other column for comment

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content should be visible");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.hamburger().click();
            myaccount.myAccount().click();

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myMeeting().click();

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            approveFunctionality.Jobs().click();
            Thread.sleep(3000);

            approveFunctionality.commentButton().first().click();
            approveFunctionality.CommentTextfield().fill(comment);
            approveFunctionality.SendButton().first().click();

            Locator commentCount = approveFunctionality.CommentCount().first();
            Assert.assertTrue(commentCount.isVisible(), "Comment button should be visible");
            String commentText = commentCount.textContent().trim();
            test.info("✅ Comment Count after posting: " + commentText);

            // Print all comment authors
            Locator commentAuthors = page.locator("//p[@class='comment-by']");
            int count = commentAuthors.count();
            for (int j = 0; j < count; j++) {
                String authorText = commentAuthors.nth(j).textContent().trim();
                test.info("🗣 Comment Author " + (j + 1) + ": " + authorText);
            }

            // Hover and edit the comment
AdvertiserProspects hover= new AdvertiserProspects(page);
hover.EditComment().first().hover();

hover.EditcommentButton().click();

            approveFunctionality.CommentTextfield().clear();
            approveFunctionality.CommentTextfield().fill(comment + " - Edited");
            approveFunctionality.SendButton().first().click();
            test.info("✏️ Edited Comment submitted");

            Thread.sleep(2000);

            // Hover and delete
            page.locator("div[class='comment-list']").first().hover();
            Locator deleteButton = page.locator("//img[@alt='Delete comment']").first();
            deleteButton.click();
            test.info("🗑 Deleted the comment");

            page.close();  // Close after test iteration
        }
    }
}
