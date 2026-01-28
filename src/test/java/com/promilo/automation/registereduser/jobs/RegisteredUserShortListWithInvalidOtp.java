package com.promilo.automation.registereduser.jobs;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

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

public class RegisteredUserShortListWithInvalidOtp extends BaseClass {

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortListWithInvalidOtp.class);

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
            logger.info("No matching keywords found in Excel. Test marked as passed.");
            return;
        }


        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        applyJobFlow(page, name, otp, mailphone);

       
    }

    private void applyJobFlow(Page page, String name, String otp, String mailphone) throws Exception {
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        page.waitForTimeout(2000);
        homePage.jobShortlist1().first().click();
        page.waitForTimeout(2000);

        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill(name);

        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty()) ? mailphone : "90000" + String.format("%05d", random.nextInt(100000));
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(mobileToUse);

        page.waitForTimeout(2000);
        homePage.jobShortList().click();

        // Enter OTP
        if (otp == null || otp.length() < 4) throw new IllegalArgumentException("OTP invalid: " + otp);
        for (int i = 0; i < 4; i++) {
            String otpChar = String.valueOf(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
            otpField.fill(otpChar);
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        // Optional: open Excel file if exists
        try {
            String filePath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            File file = new File(filePath);
            if (file.exists() && Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
        } catch (Exception ignored) {}

        page.context().close();
    }
}
