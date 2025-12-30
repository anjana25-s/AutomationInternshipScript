package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.pageobjects.myresume.*;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class BasicDetailsFunctionality extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void BasicDetailsFunctionalityTest() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void basicDetailsLoginTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Basic Details Functionality | Data Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int row = 40; // fixed row index
        String inputValue = excel.getCellData(row, 3);
        String password = excel.getCellData(row, 6);
        String testCaseId = excel.getCellData(row, 0);     
        String keyword = excel.getCellData(row, 1);        
        String whitePaperTitle = excel.getCellData(row, 11); 
        String url = excel.getCellData(row, 12);           
        String year = excel.getCellData(row, 13);          
        String month = excel.getCellData(row, 14);         
        String description = excel.getCellData(row, 15);   

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        test.info("🌐 Navigated to application URL and set viewport.");

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try {
            mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
            test.info("✅ Popup closed successfully.");
        } catch (PlaywrightException e) {
            test.info("ℹ️ No popup displayed.");
        }

        mayBeLaterPopUp.clickLoginButton();
        test.info("🔑 Clicked on Login button.");

        // 🔑 Login using Mailosaur-generated creds
        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not available. Aborting test.");
            return;
        }

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        test.info("📧 Entered registered email: " + registeredEmail);

        loginPage.passwordField().fill(registeredPassword);
        test.info("🔒 Entered password.");

        loginPage.loginButton().click();
        test.info("✅ Clicked on Login button.");

        // Resume Navigation
        Hamburger resumePage = new Hamburger(page);
        resumePage.Mystuff().click();
        test.info("📂 Navigated to 'My Stuff'.");

        resumePage.MyAccount().click();
        test.info("👤 Navigated to 'My Account'.");

        resumePage.MyResume().click();
        test.info("📄 Navigated to 'My Resume'.");

        resumePage.EditOption().click();
        test.info("✏️ Clicked on 'Edit Resume' option.");

        // Basic Details
        BasicDetailsPage basicDetails = new BasicDetailsPage(page);

        basicDetails.BasicDetailsFirstName().clear();
        basicDetails.BasicDetailsFirstName().fill("Karthik");
        test.info("📝 Entered First Name: Karthik");

        basicDetails.BasicDetailsLastName().clear();
        basicDetails.BasicDetailsLastName().fill("U");
        test.info("📝 Entered Last Name: U");

        basicDetails.locationDropdDrown().fill("Bangalore");
        basicDetails.locationDropdDrown().press("Enter");
        test.info("📍 Entered Location: Bangalore");

        basicDetails.MobileNumber().fill("9000047723");
        test.info("📱 Entered Mobile Number: 9000047723");

        page.locator("//button[text()='Send OTP']").click();
        test.info("📨 Clicked on 'Send OTP'.");

        page.locator("//input[@placeholder='Enter OTP']").fill("9999");
        page.locator("//button[text()='Verify']").click();
        test.info("🔑 Entered OTP and clicked Verify.");

        Locator verifiedSpan = page.locator("//span[text()='Verified']").nth(1);
        		if (verifiedSpan.isVisible()) {
            test.pass("✅ OTP verified successfully.");
        } else {
            test.warning("⚠️ OTP verification label not visible.");
        }

        basicDetails.AddEmployment().click();
        test.info("➕ Clicked on 'Add Employment'.");

        // Employment Details
        AddEmploymentDetails employmentDetails = new AddEmploymentDetails(page);
        employmentDetails.YesRadioBox().click();
        test.info("✔️ Selected 'Currently Working'.");
        
        employmentDetails.FullTimeButton().click();
        test.info("✔️ Selected 'Full Time' option.");

        page.locator(".my-2 > div > div > .custom-select > .react-select__control > .react-select__value-container > .react-select__input-container").first().click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("PRO TECH 1234").setExact(true)).click();
        test.info("🏢 Selected Employer: PRO TECH 1234");

        page.locator(".my-2 > div > .custom-select > .react-select__control > .react-select__value-container > .react-select__input-container").click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Software Development - ALL")).click();
        test.info("💻 Selected Department: Software Development - ALL");

        employmentDetails.currentSalary().click();
        employmentDetails.currentSalaryoption().click();
        test.info("💰 Selected Current Salary.");

        employmentDetails.NoticePeriod().click();
        employmentDetails.noticePeriodoption().click();
        test.info("📆 Selected Notice Period.");

        employmentDetails.description().fill(description != null ? description : "Automation Employment Description");
        test.info("📝 Entered Job Description.");

        employmentDetails.JoiningYear().click();
        employmentDetails.joiningYearoption().click();
        test.info("📅 Selected Joining Year.");

        employmentDetails.JoiningMonth().click();
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("January")).click();
        test.info("📅 Selected Joining Month: January");

        employmentDetails.SkillsUsed().click();
        employmentDetails.skillsOption().click();
        test.info("🛠️ Selected Skills Used.");

        employmentDetails.SaveButton().click();
        test.info("💾 Clicked on Save button.");

        test.pass("✅ Basic details & employment submitted successfully for: " + inputValue);

        extent.flush();
    }
}
