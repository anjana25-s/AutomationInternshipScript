package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;

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

public class InvoiceInfo extends BaseClass {
	
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
            
            page.locator("//img[@class='pointer']").click();
            
            Thread.sleep(3000);
            
            
            Billing invoiceInfo= new Billing(page);
            invoiceInfo.buisinessName().fill("education");
            invoiceInfo.adress1().fill("T dasarahalli");
            invoiceInfo.adress2().fill("Bangalore");
            invoiceInfo.pinCode().fill("560057");
            
            invoiceInfo.yesOption().click();
            invoiceInfo.gstNumber().fill("29ABCDE1234F2Z5");
            invoiceInfo.panNumber().fill("ABCDE1234F");
            
            invoiceInfo.checkBox().click();
            
            invoiceInfo.saveButton().click();
            
            
            
            Locator successMessage = page.locator("//div[text()='Updated successfully']");
            String messageText = successMessage.textContent(); // or use .innerText() if you prefer

            System.out.println("✅ Message Text: " + messageText);
            test.pass("✅ Success message displayed: " + messageText);
            
            
            
            


}catch (Exception e) {
    test.fail("❌ Test failed: " + e.getMessage());
    e.printStackTrace();
} finally {
    extent.flush();
}
}
}
