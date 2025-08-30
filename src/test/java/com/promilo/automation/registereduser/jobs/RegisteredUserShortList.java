package com.promilo.automation.registereduser.jobs;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class RegisteredUserShortList extends Baseclass {

    private static final Logger logger = LogManager.getLogger(RegisteredUserShortList.class);

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty()) break;
            rowCount++;
        }

        Object[][] data = new Object[rowCount][8];
        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1); // Keyword
            data[i - 1][2] = excel.getCellData(i, 3); // InputValue
            data[i - 1][3] = excel.getCellData(i, 6); // Password
            data[i - 1][4] = excel.getCellData(i, 7); // Name
            data[i - 1][5] = excel.getCellData(i, 5); // OTP
            data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
            data[i - 1][7] = i;                       // RowIndex
        }
        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobTestFromExcel(
            String testCaseId,
            String keyword,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone,
            int rowIndex
    ) throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

        if (!(keyword.equalsIgnoreCase("RegisteredUserJobShortList") ||
        	      keyword.equalsIgnoreCase("RegisteredUserJobShortListWithSignup") ||
        	      keyword.equalsIgnoreCase("Registered user shortlist") ||
        	      keyword.equalsIgnoreCase("Registered user shortlist with signup"))) {
        	    logger.info("[{}] Skipped: keyword mismatch.", testCaseId);
        	    return;
        	}


        if (keyword.equalsIgnoreCase("RegisteredUserJobShortListWithSignup")) {
            SignUpLogoutUtil signupUtil = new SignUpLogoutUtil();
            String[] generatedCreds = signupUtil.createAccountAndLoginFromExcel(
                    new ExcelUtil(Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString(), "PromiloTestData"),
                    rowIndex);
            inputvalue = generatedCreds[0];
            password = generatedCreds[1];

            // Logout after signup
            Page tempPage = initializePlaywright();
            tempPage.navigate(prop.getProperty("url"));
            tempPage.setViewportSize(1366, 768);
            JobListingPage homePage = new JobListingPage(tempPage);
            
            tempPage.close();
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

        extent.flush();
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp, String mailphone) throws Exception {
        LandingPage landingPage = new LandingPage(page);
        try { landingPage.getPopup().click(); } catch (Exception ignored) {}
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        applyJobDetailsFlow(page, name, otp, mailphone);
    }

    private void applyJobDetailsFlow(Page page, String name, String otp, String mailphone) throws Exception {
        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        page.waitForTimeout(2000);
        homePage.jobShortlist1().click();
        page.waitForTimeout(2000);
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userName']").fill(name);
     // ✅ Generate random mobile number starting with 90000
        Random random = new Random();
        String randomMobile = "90000" + String.format("%05d", random.nextInt(100000));

        // ✅ Fill into the form field
        page.locator("//div[@class='ask-us-popup-form-side']//input[@id='userMobile']").fill(randomMobile);

      

        
        


Thread.sleep(4000);
homePage.jobShortList().click();


if (otp == null || otp.length() < 4) {
    throw new IllegalArgumentException("OTP provided is less than 4 characters: " + otp);
}

for (int i = 0; i < 4; i++) {
    String otpChar = Character.toString(otp.charAt(i));
    Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

    otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

    boolean filled = false;
    int attempts = 0;

    while (!filled && attempts < 3) {
        attempts++;
        otpField.click(); // force focus
        otpField.fill(""); // clear previous
        otpField.fill(otpChar);

        // Validate the field actually has the entered digit
        String currentValue = otpField.evaluate("el => el.value").toString().trim();
        if (currentValue.equals(otpChar)) {
            filled = true;
        } else {
            page.waitForTimeout(500); // wait before retry
        }
    }

    if (!filled) {
        throw new RuntimeException("Failed to enter OTP digit " + (i + 1) + " correctly after retries.");
    }
}



Locator verifyButton = page.locator("//button[text()='Verify & Proceed']");
verifyButton.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
verifyButton.click();

        
        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000).setState(WaitForSelectorState.VISIBLE));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You' popup was not displayed.");
        
        

        try {
            String filePath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            File file = new File(filePath);
            if (file.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Cannot open file. Either it doesn't exist or Desktop is not supported.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
