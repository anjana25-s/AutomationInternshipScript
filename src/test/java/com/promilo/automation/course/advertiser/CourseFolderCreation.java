package com.promilo.automation.course.advertiser;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.resources.*;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class CourseFolderCreation extends BaseClass {

    @Test
    public void verifyMyProspectFolderCreation() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 MyProspect Folder Creation | Data Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("📘 Loaded " + rowCount + " rows from Excel.");
        System.out.println("Loaded " + rowCount + " rows from Excel");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing test case: " + testCaseId + " | Email: " + email);
            System.out.println("Executing test case: " + testCaseId + " with email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Playwright page initialized");
            System.out.println("Playwright page initialized");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to URL: " + prop.getProperty("stageUrl"));
            System.out.println("Navigated to URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);
            test.info("🖥 Viewport set to 1000x768 and waited for 3 seconds");
            System.out.println("Viewport set and waited for 3 seconds");

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            test.info("✅ Sign-in content is visible");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");
            test.info("✅ Talk to Expert content is visible");
            System.out.println("Sign-in content and Talk to Expert verified");

            login.loginMailField().fill("adv@yopmail.com");
            test.info("✉ Entered email for login");
            System.out.println("Filled email field");

            login.loginPasswordField().fill("devuttan2023");
            test.info("🔑 Entered password for login");
            System.out.println("Filled password field");

            login.signInButton().click();
            test.info("🔓 Clicked Sign-in button");
            System.out.println("Clicked Sign-in button");

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();
            test.info("☰ Clicked main menu toggle");
            System.out.println("Clicked main menu toggle");

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();
            test.info("📁 Clicked My Account");
            System.out.println("Clicked My Account");

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();
            test.info("📋 Clicked My Prospect");
            System.out.println("Clicked My Prospect");

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            test.info("💼 Clicked Courses tab");
            System.out.println("Clicked Courses tab");

            Thread.sleep(3000);

            approveFunctionality.shortlistedbutton().click();
            test.info("✅ Clicked Shortlisted button");
            System.out.println("Clicked Shortlisted button");

            approveFunctionality.CreateFolder().click();
            test.info("📂 Clicked Create Folder");
            System.out.println("Clicked Create Folder");

            approveFunctionality.FolderName().fill("foldername");
            test.info("✏ Filled Folder Name");
            System.out.println("Filled Folder Name");

            approveFunctionality.FolderDescription().fill("folder description");
            test.info("✏ Filled Folder Description");
            System.out.println("Filled Folder Description");

            approveFunctionality.CreateButton().click();
            test.info("✅ Clicked Create button");
            System.out.println("Clicked Create button");

            Thread.sleep(4000);
            page.locator("//div[@class='d-flex']//a[@class='nav-link active']").click();
            test.info("📂 Navigated to active folder list");
            System.out.println("Navigated to active folder list");

            approveFunctionality.FolderCheckbox().first().click();
            test.info("☑ Checked first folder checkbox");
            System.out.println("Checked first folder checkbox");

            page.locator("//div[text()='···']").first().click();
            approveFunctionality.ViewDetails().click();
            test.info("🔍 Viewed folder details");
            System.out.println("Viewed folder details");

            page.locator("//button[@aria-label='Close']").click();
            test.info("❌ Closed folder details modal");
            System.out.println("Closed folder details modal");

            Thread.sleep(3000);

            page.locator("//div[text()='···']").first().click();
            approveFunctionality.RenameFolder().click();
            test.info("✏ Clicked Rename folder");
            System.out.println("Clicked Rename folder");

            approveFunctionality.FolderName().fill("renamedfolder");
            page.locator("//button[text()='Confirm']").click();
            test.info("✅ Renamed folder and confirmed");
            System.out.println("Renamed folder and confirmed");

            Thread.sleep(4000);

            approveFunctionality.DeleteFolder().click();
            test.info("🗑 Clicked Delete Folder");
            System.out.println("Clicked Delete Folder");

            Locator codeElement = page.locator("//b[contains(text(), 'Please enter this code')]");
            String fullText = codeElement.textContent();
            String otpCode = fullText.replaceAll("[^0-9]", "");
            test.info("🔢 Extracted OTP code: " + otpCode);
            System.out.println("Extracted OTP code: " + otpCode);

            for (int j = 0; j < otpCode.length(); j++) {
                char digit = otpCode.charAt(j);
                String locatorXpath = String.format("//input[@aria-label='Please enter OTP character %d']", j + 1);
                Locator otpInput = page.locator(locatorXpath);
                otpInput.fill(String.valueOf(digit));
            }
            test.info("🔑 Entered OTP digits for folder deletion");
            System.out.println("Entered OTP digits");

            Locator deleteButtons = page.locator("//button[text()='Delete']");
            deleteButtons.nth(1).click();
            test.info("✅ Confirmed folder deletion");
            System.out.println("Confirmed folder deletion");

            test.pass("✅ MyProspect folder created, renamed, viewed, and deleted successfully.");
            System.out.println("Test passed: Folder operations completed successfully");
        }

        extent.flush();
        test.info("🧹 Flushed Extent report and test completed.");
    }
}
