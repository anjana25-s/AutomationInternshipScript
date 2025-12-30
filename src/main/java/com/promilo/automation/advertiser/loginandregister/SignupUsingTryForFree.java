package com.promilo.automation.advertiser.loginandregister;

import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserRegisterPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class SignupUsingTryForFree extends BaseClass {

    private ExtentReports extent;
    private ExtentTest test;

    @Test
    public void SignupUsingTryForFreeTest() {
        try {
            extent = ExtentManager.getInstance();
            test = extent.createTest("🚀 Signup Using Try For Free (Dynamic Mailosaur Email)");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(4000);

            // Excel initialized (not used in this flow)
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            test.info("📘 Excel Utility Initialized (Using hardcoded values instead)");

            // ✅ Hardcoded test data
            String firstName = "promilo";
            String lastName = "Account1";
            String jobTitle = "Purchase/Material Mgmt";
            String businessType = "college";

            // ✅ Dynamically generate unique Mailosaur email
            String mailosaurServerId = "qtvjnqv9"; 
            String emailPrefix = "TestAutomation" + System.currentTimeMillis();
            String email = emailPrefix + "@" + mailosaurServerId + ".mailosaur.net";

            String phone = "90000" + (int)(Math.random() * 100000);
            String otp = "9999"; // You can replace this with actual OTP via Mailosaur API
            String companyName = "promilobangalore" + System.currentTimeMillis();
            String website = "https://promilo.com";
            String industry = "Advertising & Marketing";
            String employees = "50-100";
            String pincode = "560001";
            String password = "John@12345";

            AdvertiserLoginPage login = new AdvertiserLoginPage(page);

            Assert.assertTrue(login.signInContent().isVisible(), "❌ Sign-in content is not visible.");
            Assert.assertTrue(login.talkToAnExpert().isVisible(), "Talk To expert content is visible");

            login.notACustomerSignInForFree().click();

            AdvertiserRegisterPage register = new AdvertiserRegisterPage(page);
            register.FirstnameInput().fill(firstName);
            register.LastnameInput().fill(lastName);

            Locator jobRole = register.JobTittle();
            jobRole.click();
            page.locator("//div[text()='" + jobTitle + "']").first().click();
            page.keyboard().press("Enter");

            Locator businessDropdown = register.typeofBuisiness();
            businessDropdown.click();
            page.keyboard().type(businessType);
            page.keyboard().press("Enter");

            register.mailTextfield().fill(email);
            register.phoneNumbertextfield().fill(phone);
            register.SendotpButton().first().click();


            register.otpfield().fill(otp);

            Locator companyDropdown = register.companyName();
            companyDropdown.click();
            page.keyboard().type(companyName);
            page.keyboard().press("Enter");
            


            register.Companywebsite().fill(website);

            Locator industryDropdown = register.IndustryDropdown();
            industryDropdown.click();
            page.keyboard().type(industry);
            page.keyboard().press("Enter");

            Locator employeeDropdown = register.EmployeesDropdown();
            employeeDropdown.click();
            page.keyboard().type(employees);
            page.keyboard().press("Enter");

            register.Pincode().fill(pincode);
            register.CreatePassword().fill(password);
            register.Submitbutton().click();
            
            Thread.sleep(3000);


            Locator errorToaster = page.locator("xpath=//div[@role='status']");
            errorToaster.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            Assert.assertTrue(errorToaster.isVisible(), "Success Toaster is visible");

            test.pass("✅ Signup executed successfully with email: " + email);
            
        }         catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
        extent.flush();

    }
}
