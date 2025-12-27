package com.promilo.automation.myassignments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.*;

public class MyassignmentUsingUpload extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test;

    private static final Logger logger =
            LogManager.getLogger(MyassignmentUsingUpload.class);

    private static String registeredEmail;
    private static String registeredPassword;

    // Assignment data (previously missing)
    private static final String uploadText = "Sample assignment answer";
    private static final String uploadFilePath =
            "C:\\Users\\Admin\\Downloads\\Dcq.pdf";

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signup = new SignupWithMailosaurUI();
        String[] creds = signup.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];
    }

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {

        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloJob");

        Object[][] data = new Object[1][8];
        data[0][0] = excel.getCellData(1, 0);
        data[0][1] = excel.getCellData(1, 1);
        data[0][2] = excel.getCellData(1, 4);
        data[0][3] = excel.getCellData(1, 6);
        data[0][4] = excel.getCellData(1, 7);
        data[0][5] = excel.getCellData(1, 5);
        data[0][6] = excel.getCellData(1, 8);
        data[0][7] = 1;

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
            int rowIndex) throws Exception {

        test = extent.createTest("Apply Job + Assignment | " + testCaseId);

        inputvalue = registeredEmail;
        password = registeredPassword;

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

        test.pass("✅ Test completed successfully");
        extent.flush();
    }

    public void applyForJobAsRegisteredUser(
            Page page,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone) throws Exception {

        MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
        try { popup.getPopup().click(); } catch (Exception ignored) {}

        popup.clickLoginButton();

        LoginPage login = new LoginPage(page);
        login.loginMailPhone().fill(inputvalue);
        login.passwordField().fill(password);
        login.loginButton().click();

        JobListingPage jobPage = new JobListingPage(page);
        jobPage.homepageJobs().click();

        page.locator("//h3[text()='Developer']").first().click();
        page.waitForTimeout(3000);
        page.locator("//button[text()='Apply Now']").first().click();

        jobPage.applyNameField().fill("karthik");

        String mobile = (mailphone != null && !mailphone.isEmpty())
                ? mailphone
                : "90000" + new Random().nextInt(99999);

        jobPage.applyNowMobileTextField().fill(mobile);

        jobPage.selectIndustryDropdown().click();
        List<String> industries = Arrays.asList(
                "Telecom / ISP", "Advertising & Marketing", "Education");

        Locator options =
                page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");

        for (String industry : industries) {
            for (int i = 0; i < options.count(); i++) {
                if (options.nth(i).innerText().equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    break;
                }
            }
        }

        page.locator("//button[contains(@class,'submit-btm-askUs')]").click();

        for (int i = 0; i < 4; i++) {
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE));
            otpField.fill(Character.toString(otp.charAt(i)));
        }

        page.locator("//button[text()='Verify & Proceed']").click();
        page.locator("span.flatpickr-day[aria-current='date']").click();
        page.locator("li.time-slot-box.list-group-item").first().click();
        page.locator("//button[text()='Submit']").nth(1).click();

        // Assignment
        Hamburger menu = new Hamburger(page);
        menu.Mystuff().click();
        menu.MyAccount().click();
        page.locator("//a[text()='My Assignment']").click();

        page.locator("//span[text()='Start Assignment']").click();
        page.locator("//span[text()='Submit Assignment']").click();

        Locator editor =
                page.locator("div[aria-label='Editor editing area: main']");
        editor.click();
        page.keyboard().type(uploadText);

        page.onFileChooser(fc ->
                fc.setFiles(Paths.get(uploadFilePath)));

        page.locator("//div[text()='Upload New File']").click();
        page.locator("//span[text()='Submit']").click();

        Locator toast = page.locator("//div[@role='status']");
        toast.waitFor();

        Assert.assertTrue(toast.isVisible(),
                "Toast should be visible after submission");
    }
}
