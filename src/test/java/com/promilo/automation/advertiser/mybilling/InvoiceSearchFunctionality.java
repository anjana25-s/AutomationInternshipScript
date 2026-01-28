package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class InvoiceSearchFunctionality extends BaseClass {

	
	ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser InvoiceSearchFunctionality Test | Data-Driven");

    @Test
    public void InvoiceSearchFunctionalityTest() {
        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            test.info("✅ Navigated to: " + prop.getProperty("stageUrl"));

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("vikas78@yopmail.com");
            test.info("✅ Entered login email");
            login.loginPasswordField().fill("Abcd12345");
            test.info("✅ Entered login password");
            login.signInButton().click();
            test.info("✅ Clicked Sign In button and logged in successfully as vikas78@yopmail.com");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            test.info("✅ Clicked Hamburger menu");
            homepage.myAccount().click();
            test.info("✅ Clicked My Account");
            homepage.myBilling().click();
            test.info("✅ Clicked My Billing section");

            Thread.sleep(5000); // initial wait if needed
            test.info("⏳ Waited 5 seconds after navigating to Billing");

            Billing download = new Billing(page);
            String[] inputs = {"karthik", "GSTS/00183/07-2025"};

            for (String input : inputs) {
                // Fill the search field
                download.SearchField().fill(input);
                test.info("✅ Filled search input: " + input);

                // Wait for the table rows to update - better to wait for a specific condition
                page.waitForTimeout(5000);
                test.info("⏳ Waited 5 seconds for search results to load");

                Locator rows = page.locator("table.mt-1.table tbody tr");

                int rowCount = rows.count();
                test.info("ℹ️ Number of rows found for search input '" + input + "': " + rowCount);
                System.out.println("Results for search input: " + input);

                for (int i = 0; i < rowCount; i++) {
                    Locator row = rows.nth(i);

                    // Optional: Check if the row is visible/displayed
                    if (!row.isVisible()) {
                        test.info("⚠️ Skipping hidden row at index " + i);
                        continue;  // skip hidden rows
                    }

                    List<String> columnsText = row.locator("td").allTextContents();

                    // Skip empty rows
                    if (columnsText.isEmpty() || columnsText.stream().allMatch(String::isEmpty)) {
                        test.info("⚠️ Skipping empty row at index " + i);
                        continue;
                    }

                    StringBuilder rowText = new StringBuilder();
                    for (int j = 0; j < columnsText.size(); j++) {
                        rowText.append(columnsText.get(j));
                        if (j < columnsText.size() - 1) rowText.append(" | ");
                    }

                    test.info("📄 Row " + (i + 1) + " data: " + rowText.toString());

                    // Also print to console as per your original
                    System.out.println(rowText.toString());
                }

                System.out.println("----- End of results for " + input + " -----\n");
                test.info("----- End of results for " + input + " -----");
            }

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}   