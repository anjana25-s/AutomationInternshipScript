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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class CourseVisitsPlannedFilter extends BaseClass {

    @Test
    public void verifyFilterFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 CourseFilterFunctionality  | Data Driven");

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

            test.info("🔐 Executing test case: " + testCaseId + " | Email: " + email);

            Page page = initializePlaywright();
            test.info("🌐 Initialized Playwright and launched browser.");

            page.navigate(prop.getProperty("stageUrl"));
            test.info("🌍 Navigated to Stage URL: " + prop.getProperty("stageUrl"));

            page.setViewportSize(1000, 768);
            test.info("🖥 Set viewport size to 1000x768.");

            Thread.sleep(3000);

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content not visible.");
            test.info("✅ Sign-in content is visible.");

            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To Expert should be visible");
            test.info("✅ Talk To Expert content is visible.");

            login.loginMailField().fill("adv@yopmail.com");
            test.info("✉ Entered email for login.");

            login.loginPasswordField().fill("adv@1234");
            test.info("🔑 Entered password for login.");

            login.signInButton().click();
            test.info("🔓 Clicked Sign In button.");

            
           
            
            AdvertiserHomepage home = new AdvertiserHomepage(page);
            home.hamburger().click();
            home.myAccount().click();
            page.locator("//span[text()='Visits Planned']").click();
            page.locator("//a[text()='All']").click();
            AdvertiserProspects prospects = new AdvertiserProspects(page);
            test.info("💼 Clicked Course tab.");

            // Filters to test
            String[] filters = {
                "All", "Accepted", "Expired",
                "Rejected", "Cancelled", "Reschedule", "Pending"
            };

            for (String filter : filters) {
                prospects.AllDropdown().click();
                test.info("🔽 Opened filter dropdown.");

                switch (filter) {
                    case "All":
                    	page.locator("//div[text()='All']").click();
                        break;
                    case "Accepted":
                    	page.locator("//div[text()='Accepted']").click();
					break;
                    case "Rejected":
                    	page.locator("//div[text()='Rejected']").click();
                        break;
                    case "Expired":
                	page.locator("//div[text()='Expired']").click();
                        break;
                    case "Cancelled":
                    	page.locator("//div[text()='Cancelled']").click();
                        break;
                    case "Reschedule":
                    	page.locator("//div[text()='Reschedule']").click();
                        break;
                    case "Pending":
                    	page.locator("//div[text()='Pending']").click();
                        break;
                    default:
                        test.warning("⚠️ Unknown filter: " + filter);
                        continue;
                }

                test.info("✅ Selected filter: " + filter);
                Thread.sleep(2000); // Optionally replace with explicit wait

                Locator results = page.locator(
                        "[class='mb-3 font-white my-acceptance-card visit-planned-card card']"
                );

                // Get total matched elements
                int count = results.count();

                // Assert at least one element exists
                Assert.assertTrue(count > 0,
                        "❌ No filter results found for: " + filter);

                // Optional: assert all are visible
                for (int i1 = 0; i1 < count; i1++) {
                    Assert.assertTrue(
                            results.nth(i1).isVisible(),
                            "❌ Result card not visible at index: " + i1
                    );
                }

                System.out.println("Total filter results for '" + filter + "': " + count);
                test.info("📊 Total filter results for '" + filter + "': " + count);

            }

            
            
            
            page.locator("[class='mb-3 font-white my-acceptance-card visit-planned-card card']").count();
            test.pass("✅ Filter functionality passed for: " + email);
        }

        extent.flush();
        test.info("🧹 Flushed Extent report and test completed.");
    }
}
