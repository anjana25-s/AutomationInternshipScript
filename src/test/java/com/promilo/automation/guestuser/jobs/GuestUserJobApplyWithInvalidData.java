package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserJobApplyWithInvalidData extends BaseClass {

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        // Only return the first row where keyword = GetHRCall
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;

            String keyword = excel.getCellData(i, 1);
            if ("GetHRCall".equalsIgnoreCase(keyword)) {
                return new Object[][] {
                    {
                        testCaseId,
                        keyword,
                        excel.getCellData(i, 3), // Email
                        excel.getCellData(i, 6), // Password
                        excel.getCellData(i, 7), // Name
                        excel.getCellData(i, 5), // OTP
                        excel.getCellData(i, 8), // MailPhone
                        excel.getCellData(i, 4)  // ExpectedResult
                    }
                };
            }
        }
        // If no row found, return empty to avoid NullPointer
        return new Object[0][];
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String email,
            String password,
            String name,
            String otp,
            String mailphone,
            String expectedResult
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Navigate to jobs
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.applyNow().click();
        Thread.sleep(2000);

        // Fill job application form with invalid data
        homePage.applyNameField().nth(1).fill("123");
        homePage.applyNowMobileTextField().nth(1).fill("mail");
        page.locator("//input[@placeholder='Email*']").nth(1).fill("999");

        homePage.selectIndustryDropdown().nth(1).click();
        Thread.sleep(1000);

        List<String> industries = Arrays.asList(
                "Telecom / ISP",
                "Advertising & Marketing",
                "Animation & VFX",
                "Healthcare",
                "Education"
        );

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    break;
                }
            }
        }

        homePage.applyNameField().nth(1).click();
        Thread.sleep(2000);


        // Invalid Name assertion
        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor();
        Assert.assertEquals(invalidNameError.innerText().trim(),
                "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");


        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor();
        Assert.assertEquals(invalidEmailError.innerText().trim(), "Invalid email address");


      
    }
}
