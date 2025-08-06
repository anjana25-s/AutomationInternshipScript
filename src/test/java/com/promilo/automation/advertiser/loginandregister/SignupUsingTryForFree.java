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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class SignupUsingTryForFree extends Baseclass {

    private ExtentReports extent;
    private ExtentTest test;

    @Test
    public void loginWithTryForFree() {
        try {
            extent = ExtentManager.getInstance();
            test = extent.createTest("🚀 Signup Using Try For Free (Data Driven)");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            Thread.sleep(4000);

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
                String keyword = excel.getCellData(i, 1);
                if (!"SignupTryForFree".equalsIgnoreCase(keyword)) {
                    continue;
                }

                // Read data from Excel
                String firstName = excel.getCellData(i, 2);
                String lastName = excel.getCellData(i, 3);
                String jobTitle = excel.getCellData(i, 4);
                String businessType = excel.getCellData(i, 5);
                String email = excel.getCellData(i, 6);
                String phone = excel.getCellData(i, 7);
                String otp = excel.getCellData(i, 8);
                String companyName = excel.getCellData(i, 9);
                String website = excel.getCellData(i, 10);
                String industry = excel.getCellData(i, 11);
                String employees = excel.getCellData(i, 12);
                String pincode = excel.getCellData(i, 13);
                String password = excel.getCellData(i, 14);

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

                Locator businessDropdown = register.typeofBuisiness();
                businessDropdown.click();
                page.keyboard().type(businessType);
                page.keyboard().press("Enter");

                register.mailTextfield().fill(email);
                register.phoneNumbertextfield().fill(phone);
                register.SendotpButton().click();
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

                Thread.sleep(2000);

                Locator errorToaster = page.locator("xpath=//div[@role='status']").nth(1);
                errorToaster.waitFor(new Locator.WaitForOptions().setTimeout(5000));
                Assert.assertTrue(errorToaster.isVisible(), "Success Toaster is visible");

                test.pass("✅ Signup executed successfully with email: " + email);
            }

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
