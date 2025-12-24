package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MybillingaddFunds extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

    @Test
    public void runAddFundsTest() {
        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
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
            test.info("✅ Logged in successfully");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.info("✅ Navigated to My Billing");

            // Billing page interactions
            Billing billing = new Billing(page);
            billing.AddFunds().click();
            billing.amountTextfield().fill("25000");
            test.info("✅ Add Funds clicked and amount entered");
            
            
            billing.payButton().click();
            test.info("✅ Pay button clicked");
            
            
           

                
                
                
            page.locator("iframe").contentFrame().getByTestId("contactNumber").click();
            page.locator("iframe").contentFrame().getByTestId("contactNumber").fill("9000086844");
            page.locator("iframe").contentFrame().getByTestId("Wallet").click();
            
            test.info("✅ wallet button clicked");
            
            
           
  
          
           Page page8 = page.waitForPopup(() -> {
               page.locator("iframe").contentFrame().getByTestId("screen-container").locator("div").filter(new Locator.FilterOptions().setHasText("PhonePe")).nth(2).click();
               
               
           });
           page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
           
           
           
           
           

           
           Locator successMessage = page.getByText("Payment Successfully");

           // Wait until it's visible
           successMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

           // Now assert it is visible
           Assert.assertTrue(successMessage.isVisible(), "✅ Payment success message is visible");
           test.info("✅ Payment success message is visible");
           
           test.info("Add fund functionality test pass");

           

         


                
        

            
            

               

                
                
               
                
           

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
