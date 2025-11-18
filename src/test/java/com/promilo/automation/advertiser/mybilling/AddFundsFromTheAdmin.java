package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;
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

public class AddFundsFromTheAdmin extends Baseclass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

    @Test
    public void runAddFundsTest() {
        try {
            // Step 1: Load Excel Data
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
            test.info("📂 Loaded Excel data from: " + excelPath);

            // Step 2: Admin Login
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("adminUrl"));
            page.setViewportSize(1000, 768);
            test.info("✅ Navigated to: " + prop.getProperty("adminUrl"));

            page.locator("input[id='username']").fill("admin@promilo.com");
            page.locator("input[id='password']").fill("11111aA@");
            test.info("📝 Entered admin credentials");

            page.locator("//button[text()='Submit']").click();
            page.waitForSelector("//div[text()='Manage Company']");
            test.pass("🔓 Admin login successful");

            // Step 3: Select company & Add funds
            page.locator("//div[text()='Manage Company']").click();
            test.info("📂 Navigated to Manage Company");

            page.waitForSelector("//td[normalize-space(text())='indiapromilo']");
            page.locator("//td[normalize-space(text())='indiapromilo']/preceding-sibling::td//input[@type='checkbox']").click();
            test.info("✅ Selected company: indiapromilo");

            page.locator("//span[text()='Add fund']").click();
            page.waitForSelector("//label[text()='Postpaid']");
            page.locator("//label[text()='Postpaid']").click();
            page.locator("//input[@placeholder='Enter amount']").fill("35000");
            page.locator("//textarea[@placeholder='Write a remark with only 500 characters']").fill("promiloindia Funds Added");
            test.info("💰 Entered fund details: 35000, remark: promiloindia Funds Added");

            page.locator("//button[text()='Add Fund']").click();
            String successMessage = page.locator("//div[text()='Balance added  successfully.']").textContent();
            test.pass("🎉 Fund addition success message: " + successMessage);

            // Step 4: Navigate to Stage & validate balance
            Page stagePage = page.context().newPage();
            stagePage.navigate(prop.getProperty("stageUrl"));
            test.info("🌐 Navigated to Stage URL: " + prop.getProperty("stageUrl"));

            AdvertiserLoginPage login = new AdvertiserLoginPage(stagePage);
            login.loginMailField().fill("warm-apart@ofuk8kzb.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            test.info("📝 Entered advertiser credentials");

            login.signInButton().click();
            

            // Step 5: Go to My Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(stagePage);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            stagePage.waitForSelector("//table");
            test.info("📂 Navigated to My Billing section");
         // Step 6: Search for transaction entry directly
         // Step 6: Get the first matching row that contains "Promiloindia funds added"
            Locator matchingRow = stagePage
                .locator("//table//tr[td//div[contains(normalize-space(.), 'Promiloindia funds added')]]")
                .first();

            matchingRow.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (matchingRow.isVisible()) {
                String rowText = matchingRow.textContent().trim();
                System.out.println("✅ Found matching row: " + rowText);
                test.pass("✅ Found matching billing row: " + rowText);
            } else {
                System.out.println("❌ Matching billing row not found.");
                test.fail("❌ Matching billing row not found.");
            }


        } catch (Exception e) {
            test.fail("💥 Test failed due to exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
