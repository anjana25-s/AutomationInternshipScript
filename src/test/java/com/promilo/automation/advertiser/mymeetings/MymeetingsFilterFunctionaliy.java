package com.promilo.automation.advertiser.mymeetings;

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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MymeetingsFilterFunctionaliy extends BaseClass {

    @Test
    public void verifyMyMeetingsFiltersDataDriven() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📊 MyMeetings Filter Functionality | Data-Driven Test");

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
            String filter = excel.getCellData(i, 11); // Add 'FilterType' values in Excel

            if (!"MyMeetingsFilter".equalsIgnoreCase(keyword)) continue;

            test.info("🔍 Executing TestCaseID: " + testCaseId + " | Filter: " + filter);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "❌ Talk to expert not visible.");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            page.locator("//a[@class='nav-menu-main menu-toggle hidden-xs is-active nav-link']").click();

            AdvertiserHomepage myaccount = new AdvertiserHomepage(page);
            myaccount.myAccount().click();

            AdverstiserMyaccount prospect = new AdverstiserMyaccount(page);
            prospect.myProspect().click();

            AdvertiserProspects approveFunctionality = new AdvertiserProspects(page);
            approveFunctionality.Jobs().click();

            approveFunctionality.AllDropdown().click();

            switch (filter.toLowerCase()) {
                case "all":
                    approveFunctionality.All().click();
                    break;
                case "approved":
                    approveFunctionality.Approved().click();
                    break;
                case "expired":
                    approveFunctionality.Expired().click();
                    break;
                case "new leads":
                    approveFunctionality.NewLeads().click();
                    break;
                case "rejected":
                    approveFunctionality.Rejected().click();
                    break;
                case "completed":
                    approveFunctionality.Completed().click();
                    break;
                case "cancelled":
                    approveFunctionality.Cancelled().click();
                    break;
                case "reschedule requests":
                    approveFunctionality.ResceduleRequests().click();
                    break;
                case "pendings":
                    approveFunctionality.Pendings().click();
                    break;
                default:
                    test.warning("⚠️ Unknown filter type: " + filter);
                    continue;
            }

            waitForFilterUpdate(approveFunctionality, filter);
        }
    }

    private void waitForFilterUpdate(AdvertiserProspects approveFunctionality, String filterName) {
        Locator filterResult = approveFunctionality.FilterResult();
        Assert.assertTrue(filterResult.isVisible(), "❌ Filter result not visible for: " + filterName);
        String filterText = filterResult.textContent().trim();
        System.out.println("✅ Filter Result (" + filterName + "): " + filterText);
    }
}
