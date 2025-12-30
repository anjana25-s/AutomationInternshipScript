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

public class InvoiceFilterbaseOndays extends BaseClass{
	
	ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser InvoiceFilterbaseOndays Test | Data-Driven");

    @Test
    public void InvoiceFilterbaseOndaysTest() {
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
            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
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
            String[] filters = {
            	    "Last 7 Days",
            	    "Last 14 Days",
            	    "Last 30 Days",
            	    "Last 90 Days"
            	};

            	for (String filter : filters) {
            	    test.info("🔘 Selecting filter: " + filter);
            	    
            	    
                    download.StartDate().click();


            	    // Click on the filter li by exact text XPath
            	    page.locator("//li[text()='" + filter + "']").click();

            	    // Wait for table rows to update - wait for first row or timeout (replace fixed wait)
            	    Locator firstRow = page.locator("table.mt-1.table tbody tr").first();
            	    firstRow.waitFor(new Locator.WaitForOptions().setTimeout(5000)); // 5s timeout max

            	    // Get all visible rows after filter
            	    Locator rows = page.locator("table.mt-1.table tbody tr");
            	    int rowCount = rows.count();

            	    test.info("ℹ️ Number of rows found after filter '" + filter + "': " + rowCount);
            	    System.out.println("Results for filter: " + filter);

            	    for (int i = 0; i < rowCount; i++) {
            	        Locator row = rows.nth(i);
            	        if (!row.isVisible()) continue;  // skip hidden rows

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

            	    System.out.println("----- End of results for filter " + filter + " -----\n");
            	    test.info("----- End of results for filter " + filter + " -----");

            	}


}
        catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }}  }