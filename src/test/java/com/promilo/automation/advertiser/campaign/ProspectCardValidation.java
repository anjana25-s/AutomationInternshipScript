package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ProspectCardValidation extends BaseClass {

    @Test
    public void performLoginTest() throws IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Prospect Card Validation | Data Driven");

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
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"ProspectCardValidation".equalsIgnoreCase(keyword)) {
                continue;
            }

            try {
                Page page = initializePlaywright();
                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);

                Thread.sleep(5000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);
                Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
                Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content is visible");

                login.loginMailField().fill(email);
                login.loginPasswordField().fill(password);
                login.signInButton().click();

                page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();

                AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
                myaccount.myAccount().click();

                AdverstiserMyaccount prospectClick = new AdverstiserMyaccount(page);
                Thread.sleep(5000);
                prospectClick.myProspect().click();

                AdvertiserProspects prospect = new AdvertiserProspects(page);
                prospect.Jobs().click();

                Thread.sleep(4000);
                Assert.assertTrue(prospect.CandidateIntrestcount().first().isVisible(), "Candidate Interest Count should be visible");

                String interestText = prospect.CandidateIntrestcount().first().textContent().trim();
                String numberOnly = interestText.replaceAll("[^0-9]", "");

                Assert.assertTrue(prospect.CmpaignStatus().first().isVisible(), "Campaign Status should be visible");
                String campaignStatus = prospect.CmpaignStatus().first().textContent().trim();

                Assert.assertTrue(prospect.campaignInfo().first().isVisible(), "Campaign Info should be visible");
                String campaignInfo = prospect.campaignInfo().first().textContent().trim();

                Assert.assertTrue(prospect.ProfileCard().first().isVisible(), "Profile Card should be visible");
                String profileCardText = prospect.ProfileCard().first().textContent().trim();

                Assert.assertTrue(prospect.ProfileName().first().isVisible(), "Profile Name should be visible");
                String profileName = prospect.ProfileName().first().textContent().trim();

                Assert.assertTrue(prospect.ProfileLocation().first().isVisible(), "Profile Location should be visible");
                String profileLocation = prospect.ProfileLocation().first().textContent().trim();

                Assert.assertTrue(prospect.SkillsSection().first().isVisible(), "Skills section should be visible");
                String skills = prospect.SkillsSection().first().textContent().trim();

                Assert.assertTrue(prospect.Source().first().isVisible(), "Source should be visible");
                String source = prospect.Source().first().textContent().trim();

                Assert.assertTrue(prospect.MeetingDate().first().isVisible(), "Meeting Date should be visible");
                String meetingDate = prospect.MeetingDate().first().textContent().trim();

                Assert.assertTrue(prospect.MeetingTime().first().isVisible(), "Meeting Time should be visible");
                String meetingTime = prospect.MeetingTime().first().textContent().trim();

                Assert.assertTrue(prospect.CandidateDetails().first().isVisible(), "Candidate Details should be visible");
                String candidateDetails = prospect.CandidateDetails().first().textContent().trim();

                Assert.assertTrue(prospect.UserProfile().first().isVisible(), "User Profile should be visible");
                String userProfile = prospect.UserProfile().first().textContent().trim();

                Assert.assertTrue(prospect.Update().first().isVisible(), "Update should be visible");
                String update = prospect.Update().first().textContent().trim();

                prospect.uploadedResume().first().click();
                Thread.sleep(3000);

                Locator resumePreviewHeader = page.locator("//h5[text()='Resume Preview']");
                resumePreviewHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                Assert.assertTrue(resumePreviewHeader.isVisible(), "'Resume Preview' header should be visible");
                page.locator("//button[@class='btn-close']").click();

                prospect.viewProfile().first().click();
                Locator profileHeading = page.locator("//h5[text()='Profile ']");
                profileHeading.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                Assert.assertTrue(profileHeading.isVisible(), "'Profile' heading should be visible after clicking View Profile");
                page.locator("//button[@class='btn-close']").click();

            } catch (Exception e) {
                test.fail("❌ Test failed for row " + i + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
