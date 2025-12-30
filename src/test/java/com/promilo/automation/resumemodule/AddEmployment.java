package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.AddEmploymentDetails;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class AddEmployment extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void addEmploymentPositiveTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

        if (registeredEmail == null || registeredPassword == null) {
            test.fail("❌ Signup credentials not found. Aborting test.");
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        test.info("🌐 Navigated to application URL.");

        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (!"AddEmployment".equalsIgnoreCase(keyword)) continue;

            String inputValue = excel.getCellData(i, 3);
            String description = excel.getCellData(i, 10);

            try {
                test.info("➡️ Starting execution for row " + i + " with input: " + inputValue);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                mayBeLaterPopUp.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(registeredEmail);
                loginPage.passwordField().fill(registeredPassword);
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials: " + registeredEmail);

                // Navigate to My Resume
                Hamburger resumePage = new Hamburger(page);
                resumePage.Mystuff().click();
                resumePage.MyAccount().click();
                resumePage.MyResume().click();
                test.info("📄 Navigated to My Resume section.");

                page.locator("//span[@class='pointer edit-option ms-1 ']").click();
                test.info("✏️ Opened Resume Edit options.");

                Thread.sleep(3000);

                // Fill Employment Details
                AddEmploymentDetails emp = new AddEmploymentDetails(page);
                emp.YesRadioBox().click();
                emp.FullTimeButton().click();
                test.info("✅ Selected Employment Type: Full Time.");

                emp.CurrentCompanyDropdown().click();
                emp.currentCompanyoption().first().click();
                test.info("🏢 Selected Current Company.");

                Thread.sleep(2000);

                emp.Currentdesignatipondropdown().nth(1).click();
                emp.currentDesignationoption().first().click();
                test.info("👔 Selected Current Designation.");

                emp.JoiningMonth().click();
                emp.joiningMonthoption().click();
                emp.JoiningYear().click();
                emp.joiningYearoption().click();
                test.info("📅 Selected Joining Month and Year.");

                emp.SkillsUsed().click();
                emp.skillsOption().click();
                test.info("💡 Selected Skills Used.");

                emp.currentSalary().click();
                emp.currentSalaryoption().click();
                test.info("💰 Selected Current Salary.");

                emp.NoticePeriod().click();
                emp.noticePeriodoption().click();
                test.info("📆 Selected Notice Period.");

                emp.description().fill(description != null ? description : "Automation Added Employment");
                test.info("📝 Filled Employment Description.");

                emp.SaveButton().click();
                test.info("💾 Clicked Save button for Employment details.");

                Thread.sleep(5000);

                // Validate success toast
                Locator successToast = page.locator("//div[contains(text(),'Employment added successfully')]");
                boolean isToastVisible = successToast.isVisible();

                if (isToastVisible) {
                    test.pass("✅ Employment added successfully for: " + inputValue);
                } else {
                    test.warning("⚠️ Employment success toast not visible for: " + inputValue);
                }

            } catch (Exception e) {
                test.fail("❌ Exception occurred for row " + i + " → " + e.getMessage());
            }
        }

        extent.flush();
    }
}
