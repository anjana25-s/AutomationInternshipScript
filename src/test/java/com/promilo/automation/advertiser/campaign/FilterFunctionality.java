package com.promilo.automation.advertiser.campaign;

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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class FilterFunctionality extends Baseclass {

    @Test
    public void verifyFilterFunctionalityDataDriven() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Filter Functionality | Data Driven");

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

            if (!"FilterFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");

            login.loginMailField().fill(email);
            login.loginPasswordField().fill(password);
            login.signInButton().click();

            // Navigation

            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            
            homepage.myAccount().click();

            AdverstiserMyaccount myAccount = new AdverstiserMyaccount(page);
            myAccount.myProspect().click();

            AdvertiserProspects prospects = new AdvertiserProspects(page);
            prospects.Jobs().click();

            // Filters to test
            String[] filters = {
                "All", "Approved", "Expired", "NewLeads",
                "Rejected", "Completed", "Cancelled", "ResceduleRequests", "Pendings"
            };

            for (String filter : filters) {
                prospects.AllDropdown().click();
                switch (filter) {
                    case "All":
                        prospects.All().click();
                        break;
                    case "Approved":
                        prospects.Approved().click();
                        break;
                    case "Expired":
                        prospects.Expired().click();
                        break;
                    case "NewLeads":
                        prospects.NewLeads().click();
                        break;
                    case "Rejected":
                        prospects.Rejected().click();
                        break;
                    case "Completed":
                        prospects.Completed().click();
                        break;
                    case "Cancelled":
                        prospects.Cancelled().click();
                        break;
                    case "ResceduleRequests":
                        prospects.ResceduleRequests().click();
                        break;
                    case "Pendings":
                        prospects.Pendings().click();
                        break;
                    default:
                        test.warning("⚠️ Unknown filter: " + filter);
                        continue;
                }

                Thread.sleep(2000); // Optionally replace with explicit wait

                Locator result = prospects.FilterResult();
                Assert.assertTrue(result.isVisible(), "❌ Filter result should be visible for: " + filter);
                String text = result.textContent().trim();
                System.out.println("Filter Result (" + filter + "): " + text);
                test.info("✅ Filter '" + filter + "' result: " + text);
            }

            test.pass("✅ Filter functionality passed for: " + email);
        }
    }
}
