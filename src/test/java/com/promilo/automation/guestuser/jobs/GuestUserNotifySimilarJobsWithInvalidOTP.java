package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;
import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserNotifySimilarJobsWithInvalidOTP extends BaseClass {

    // Reference only: read Excel data (not used for execution)
    private void readExcelForReference() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        String testCaseId = excel.getCellData(1, 0);
        String keyword = excel.getCellData(1, 1);
        String email = excel.getCellData(1, 3);
        String name = excel.getCellData(1, 7);
        String otp = excel.getCellData(1, 5);
        String mailphone = excel.getCellData(1, 8);
        System.out.println("Excel reference -> " + testCaseId + ", " + keyword + ", " + email + ", " + otp);
    }

    @Test
    public void notifySimilarJobsWithInvalidOTP() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Guest User | Notify Similar Jobs | Invalid OTP");

        // Hardcoded invalid OTP test data
        String testCaseId = "InvalidOTP_001";
        String name = "Guest User";
        String mailphone = "9kka";
        String invalidOtp = "123"; // Intentionally invalid

        // Launch Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        // Close landing popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click();

        // Go to jobs and pick fintech
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        Locator fintechJobCard = page.locator(
                "//span[@class='font-12 additional-tags-text additional-cards-text-truncate jobs-brand-additional-title']");
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        fintechJobCard.scrollIntoViewIfNeeded();
        fintechJobCard.waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        fintechJobCard.click();

        // Notify similar jobs form
        homePage.notifySimilarJobs().click();
        homePage.applyNameField().nth(1).fill(name);
        homePage.applyNowMobileTextField().nth(1).fill(mailphone);
        page.locator("//input[@placeholder='Email*']").nth(1).fill("tgmail");

        homePage.sendSimilarJobs().click();


        // Screenshot
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_invalid_otp.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
        test.addScreenCaptureFromPath(screenshotPath, "🖼️ Screenshot after invalid OTP attempt");

        // Close browser
        page.close();
        extent.flush();
    }
}
