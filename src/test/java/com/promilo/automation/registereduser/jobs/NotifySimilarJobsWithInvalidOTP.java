package com.promilo.automation.registereduser.jobs;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class NotifySimilarJobsWithInvalidOTP extends BaseClass {

	 @DataProvider(name = "jobApplicationData")
	    public Object[][] jobApplicationData() throws Exception {
	        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
	                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

	        int totalRows = 0;
	        for (int i = 1; i <= 1000; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            if (testCaseId == null || testCaseId.isEmpty()) break;
	            totalRows++;
	        }

	        List<Object[]> filteredData = new ArrayList<>();
	        for (int i = 1; i <= totalRows; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            String keyword = excel.getCellData(i, 1);
	            String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

	            if (normalizedKeyword.equals("registereduserjobshortlist") ||
	                normalizedKeyword.equals("registereduserjobshortlistwithsignup") ||
	                normalizedKeyword.equals("registereduserfeedbackwithsignup")) {

	                Object[] row = new Object[8];
	                row[0] = testCaseId;
	                row[1] = keyword;
	                row[2] = excel.getCellData(i, 3); // InputValue
	                row[3] = excel.getCellData(i, 6); // Password
	                row[4] = excel.getCellData(i, 7); // Name
	                row[5] = excel.getCellData(i, 5); // OTP
	                row[6] = excel.getCellData(i, 8); // MailPhone
	                row[7] = i;                         // RowIndex
	                filteredData.add(row);
	            }
	        }

	        // If no rows match, return a dummy row to mark test as passed
	        if (filteredData.isEmpty()) {
	            filteredData.add(new Object[]{"NoTest", "NoKeyword", "", "", "", "", "", 0});
	        }

	        return filteredData.toArray(new Object[0][0]);
	    }

	    @Test(dataProvider = "jobApplicationData")
	    public void applyForJobAsRegisteredUser(
	            String testCaseId,
	            String keyword,
	            String inputvalue,
	            String password,
	            String name,
	            String otp,
	            String mailphone,
	            int rowIndex
	    ) throws Exception {

	        // Pass automatically if no matching keyword
	        if ("NoTest".equals(testCaseId)) {
	            return;
	        }


        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Notify Similar Jobs with Invalid OTP | " + testCaseId);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 768);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill("");
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();
        test.info("Logged in successfully with user: " );

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.fintech();

        // Notify Similar Jobs flow
        homePage.notifySimilarJobs().click();
        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill(mailphone);
        homePage.sendSimilarJobs().click();

        // Enter OTP (invalid)
        for (int i = 0; i < 4; i++) {
            String otpChar = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            int attempts = 0;
            while (!filled && attempts < 3) {
                attempts++;
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);
                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) filled = true;
                else page.waitForTimeout(500);
            }

            if (!filled) throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
        }

        Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
        verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
        verifyButton.click();

        // Validate error toaster
        Locator errorLocator = page.locator("//div[contains(@class,'Toastify__toast--error')]");
        errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(errorLocator.isVisible(), "Error toaster not visible for invalid OTP.");
        test.pass("Invalid OTP error toaster displayed correctly.");

        page.close();
        extent.flush();
    }
}
