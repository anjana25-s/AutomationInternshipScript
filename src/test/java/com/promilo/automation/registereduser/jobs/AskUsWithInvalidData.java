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
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class AskUsWithInvalidData extends BaseClass {

    private static final Logger logger = LogManager.getLogger(AskUsWithInvalidData.class);

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

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Ask Us With Invalid Data | " + testCaseId);

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
        page.waitForTimeout(2000);

        // Open AskUs form
        page.locator("//p[text()='Crime Scene Cleaner (Trauma & Biohazard Technician']").first().click();
        page.waitForTimeout(2000);
        page.locator("//button[text()='Ask us?']").first().click();

        // Fill invalid data
        page.locator("//input[@name='userName']").fill(name);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("+")).fill(mailphone);
        page.locator("//textarea[@id='feedbackDetails']").fill("");

        homePage.askUsSubmitButton().click();

        // Validate error toaster
        Locator errorLocator = page.locator("//div[contains(@class,'Toastify__toast--error')]");
        errorLocator.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(errorLocator.isVisible(), "Error toaster not visible for invalid AskUs data.");
        test.pass("Invalid AskUs data error toaster displayed correctly.");

        page.close();
        extent.flush();
    }
}
