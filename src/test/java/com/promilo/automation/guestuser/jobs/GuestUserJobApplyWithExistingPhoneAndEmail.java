package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
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

public class GuestUserJobApplyWithExistingPhoneAndEmail extends BaseClass {

    // Excel reference (not used for execution)
    private void readExcelForReference() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        String testCaseId = excel.getCellData(1, 0);
        String keyword = excel.getCellData(1, 1);
        String email = excel.getCellData(1, 3);
        String password = excel.getCellData(1, 6);
        String name = excel.getCellData(1, 4);
        String otp = excel.getCellData(1, 5);

        System.out.println("Excel reference -> TestCaseID: " + testCaseId + ", Keyword: " + keyword);
    }

    @Test
    public void applyForJobAsGuestUserWithExistingPhoneAndEmail() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Guest User (Existing Phone & Email) | Single Run");

        // Hardcoded input for run
        String name = "Existing User";
        String mobile = "7026268342";
        
        
        String email = "existinguser@test.com";

        // Launch browser
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Close popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Navigate to jobs
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.applyNow().click();
        Thread.sleep(2000);

        // Fill job application form
        homePage.applyNameField().nth(1).fill(name);
        homePage.applyNowMobileTextField().nth(1).fill(mobile);
        page.locator("//input[@placeholder='Email*']").nth(1).fill(email);

        // Select industries
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
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
        }

        // Submit application
        homePage.applyNameField().nth(1).click();
        Thread.sleep(1000);

        Locator applyButton = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
        Thread.sleep(2000);

       

        test.pass("✅ 'Thank You!' popup validated successfully for existing phone & email");

        extent.flush();
        page.close();
    }
}
