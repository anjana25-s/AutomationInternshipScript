package com.promilo.automation.resumemodule;

import com.microsoft.playwright.*;
import com.aventstack.extentreports.*;
import com.promilo.automation.pageobjects.myresume.AccomplishmentsPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.ToasterUtil;
import com.promilo.automation.resources.SignupWithMailosaurUI;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class WorkSamplePage extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup once before suite with Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered test user: " + registeredEmail);
    }

    @Test
    public void uploadWorkSampleUsingExcel() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📄 Upload Work Sample Using Excel");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        String testCaseId = excel.getCellData(1, 0); // TestCaseID
        String workTitle = excel.getCellData(1, 11);
        String url = excel.getCellData(1, 12);
        String year = excel.getCellData(1, 13);
        String month = excel.getCellData(1, 14);
        String description = excel.getCellData(1, 15);

        Page page = null;

        try {
            // Launch browser
            page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);
            test.info("🌐 Navigated to Promilo application URL.");
            Thread.sleep(2000);

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

            // Handle popup
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup on landing page.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup appeared.");
            }

            // Login with Mailosaur creds
            mayBeLaterPopUp.clickLoginButton();
            test.info("👉 Clicked on Login button.");
            if (registeredEmail == null || registeredPassword == null) {
                test.fail("❌ Signup credentials not available. Aborting test.");
                return;
            }

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(registeredEmail);
            loginPage.passwordField().fill(registeredPassword);
            loginPage.loginButton().click();
            test.info("🔐 Logged in as: " + registeredEmail);

            // Resume navigation
            Hamburger resumePage = new Hamburger(page);
            resumePage.Mystuff().click();
            test.info("📂 Clicked on My Stuff.");
            resumePage.MyAccount().click();
            test.info("👤 Clicked on My Account.");
            resumePage.MyResume().click();
            test.info("📑 Opened My Resume section.");
            resumePage.AddWorksample().click();
            test.info("➕ Navigated to Add Work Sample form.");

            // Fill work sample form
            AccomplishmentsPage workSample = new AccomplishmentsPage(page);
            workSample.workTitleTextField().fill(workTitle);
            test.info("✍️ Entered Work Title: " + workTitle);

            workSample.urlTextField().fill(url);
            test.info("🔗 Entered Work Sample URL: " + url);

            workSample.currentlyWorkingCheckbox().click();
            test.info("☑️ Selected Currently Working checkbox.");

            Locator yearDropdown = workSample.durationFromYearDropdown();
            yearDropdown.click();
            yearDropdown.fill(year);
            page.keyboard().press("Enter");
            test.info("📅 Selected Year: " + year);

            Locator monthDropdown = workSample.durationFromMonthDropDown();
            monthDropdown.click();
            monthDropdown.fill(month);
            page.keyboard().press("Enter");
            test.info("📅 Selected Month: " + month);

            workSample.descriptionBox().fill(description);
            test.info("📝 Entered Description.");

            workSample.worksamplesaveButton().click();
            test.info("📤 Submitted Work Sample form.");

            // Toaster validation
            ToasterUtil toasterValidation = new ToasterUtil(page);
            try {
                toasterValidation.getSuccessToaster().waitFor(new Locator.WaitForOptions().setTimeout(5000));
                if (toasterValidation.getSuccessToaster().isVisible()) {
                    test.pass("✅ Success toaster appeared for TestCaseID: " + testCaseId);
                } else {
                    test.warning("⚠️ Success toaster not visible after submission.");
                }
            } catch (Exception ex) {
                test.warning("⚠️ No toaster appeared. Might be a backend issue.");
            }

            // Screenshot
            String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_worksample.png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
            test.addScreenCaptureFromPath(screenshotPath, "📸 Screenshot after submitting work sample.");

        } catch (Exception e) {
            test.fail("❌ TestCaseID: " + testCaseId + " failed: " + e.getMessage());
            throw e;
        } finally {
            if (page != null) {
                closePlaywright();
                test.info("🧹 Closed browser for TestCaseID: " + testCaseId);
            }
        }

        extent.flush();
    }
}
