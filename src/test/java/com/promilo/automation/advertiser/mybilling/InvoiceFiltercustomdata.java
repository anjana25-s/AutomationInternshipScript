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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class InvoiceFiltercustomdata extends Baseclass{
	
	ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

    @Test
    public void runAddFundsTest() {
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
            login.loginPasswordField().fill("Abcd12345");
            login.signInButton().click();
            test.info("✅ Logged in successfully as vikas78@yopmail.com");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.info("✅ Navigated to My Billing section");

            // Click download icon
            Billing download = new Billing(page);
            download.StartDate().click();
            
         // Click previous year button (start date)
            page.locator("(//button[@aria-label='Last year (Control + left)'])[1]").click();
            test.info("🔘 Clicked Previous Year button on start date picker");

            // Click previous month button (start date)
            page.locator("(//button[@aria-label='Previous month (PageUp)'])[1]").click();
            test.info("🔘 Clicked Previous Month button on start date picker");

            // Select specific start date
            page.locator("//td[@title='2024-07-13']").click();
            test.info("✅ Selected start date: 2024-07-13");

            Thread.sleep(5000); // wait for UI to update/load data

            // Click End Date input to open date picker
            download.EndDate().click();
            test.info("🔘 Clicked End Date field");

            // Click next year button on end date picker
            page.locator("(//button[@aria-label='Next year (Control + right)'])[2]").click();
            test.info("🔘 Clicked Next Year button on end date picker");

            // Optionally, you can click next month if needed:
            // page.locator("(//button[@aria-label='Next month (PageDown)'])[2]").click();
            // test.info("🔘 Clicked Next Month button on end date picker");

            // Select specific end date
            page.locator("(//td[@title='2025-08-08'])[2]").click();
            test.info("✅ Selected end date: 2025-08-08");

            Thread.sleep(5000); // wait for UI to update/load data

            // Now get and log filtered results same way you do for filters:
            Locator rows = page.locator("table.mt-1.table tbody tr");
            int rowCount = rows.count();
            test.info("ℹ️ Number of rows found for custom date range: " + rowCount);
            System.out.println("Results for custom date range:");

            for (int i = 0; i < rowCount; i++) {
                Locator row = rows.nth(i);
                if (!row.isVisible()) continue;

                List<String> columnsText = row.locator("td").allTextContents();
                if (columnsText.isEmpty() || columnsText.stream().allMatch(String::isEmpty)) continue;

                StringBuilder rowText = new StringBuilder();
                for (int j = 0; j < columnsText.size(); j++) {
                    rowText.append(columnsText.get(j));
                    if (j < columnsText.size() - 1) rowText.append(" | ");
                }

                System.out.println(rowText);
                test.info("📄 Row " + (i + 1) + ": " + rowText);
            }
            test.info("----- End of results for custom date range -----");
            System.out.println("----- End of results for custom date range -----\n");


}        catch (Exception e) {
    test.fail("❌ Test failed: " + e.getMessage());
    e.printStackTrace();
} finally {
    extent.flush();
}}  }
