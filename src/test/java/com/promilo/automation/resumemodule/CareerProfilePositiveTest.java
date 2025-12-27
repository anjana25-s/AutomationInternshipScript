package com.promilo.automation.resumemodule;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.promilo.automation.pageobjects.myresume.CareerProfilePage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CareerProfilePositiveTest extends BaseClass {

    @Test
    public void CareerProfilePositiveTest() throws IOException, InterruptedException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Career Profile Positive Test | Data Driven");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);
        test.info("🌐 Navigated to application URL and set viewport.");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String password = excel.getCellData(i, 6);
            String email = excel.getCellData(i, 7);
            String industry = excel.getCellData(i, 8);
            String department = excel.getCellData(i, 9);
            String role = excel.getCellData(i, 10);
            String preferredLocation = excel.getCellData(i, 11);
            String salary = excel.getCellData(i, 12);

            if (!"AddCareerProfile".equalsIgnoreCase(keyword)) continue;

            page.navigate(prop.getProperty("url"));
            test.info("🔄 Navigated back to URL for new test iteration (TestCaseID: " + testCaseId + ")");

            // Handle potential popup
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                test.info("✅ Closed popup successfully.");
            } catch (PlaywrightException e) {
                test.info("ℹ️ No popup displayed.");
            }

            mayBeLaterPopUp.clickLoginButton();
            test.info("🔑 Clicked on Login button.");

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("testautomation@gmail.com");
            test.info("📧 Entered email: testautomation@gmail.com");

            loginPage.passwordField().fill("Karthik@88");
            test.info("🔒 Entered password.");

            loginPage.loginButton().click();
            test.info("✅ Clicked on Login button.");

            // Navigate to Resume > Career Profile
            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            test.info("📂 Clicked on 'My Stuff'.");

            hamburger.MyAccount().click();
            test.info("👤 Clicked on 'My Account'.");

            hamburger.MyResume().click();
            test.info("📄 Clicked on 'My Resume'.");

            hamburger.AddCareerProfile().click();
            test.info("➕ Clicked on 'Add Career Profile'.");

            CareerProfilePage careerProfilePage = new CareerProfilePage(page);
            Thread.sleep(2000);

            // Fill Career Profile Fields
            careerProfilePage.careerProfileCurrentIndustryDropdown().click();
            careerProfilePage.careerProfileCurrentIndustryDropdown().fill(industry);
            page.keyboard().press("ArrowDown");
            page.keyboard().press("Enter");
            test.info("🏢 Selected Industry: " + industry);

            careerProfilePage.careerProfileDepartmentDropdown().click();
            careerProfilePage.careerProfileDepartmentDropdown().fill(department);
            page.keyboard().press("ArrowDown");
            page.keyboard().press("Enter");
            test.info("💼 Selected Department: " + department);

            careerProfilePage.careerProfileJobRoleDropdown().click();
            careerProfilePage.careerProfileJobRoleDropdown().fill(role);
            page.keyboard().press("ArrowDown");
            page.keyboard().press("Enter");
            test.info("👔 Selected Role: " + role);

            careerProfilePage.careerProfilePermanentCheckbox().click();
            test.info("✔️ Checked Permanent Job option.");

            careerProfilePage.careerProfileFullTimeCheckbox().click();
            test.info("✔️ Checked Full-Time option.");

            careerProfilePage.careerProfileShiftPreferredDay().check();
            test.info("🌞 Selected Day Shift preference.");

            careerProfilePage.careerProfilePreferredLocationsDropdown().click();
            page.keyboard().type(preferredLocation);
            page.keyboard().press("Enter");
            test.info("📍 Selected Preferred Location: " + preferredLocation);

            careerProfilePage.careerProfileSelectSalaryDropdown().click();
            page.keyboard().type(salary);
            page.keyboard().press("Enter");
            test.info("💰 Selected Expected Salary: " + salary);

            // Save
            careerProfilePage.careerProfileSaveButton().click();
            test.info("💾 Clicked Save button.");
            Thread.sleep(3000);

            // Assertion
            SoftAssert softAssert = new SoftAssert();
            boolean isToastVisible = page.locator("text=Career Profile added successfully").isVisible();

            if (isToastVisible) {
                test.pass("✅ Career Profile added successfully (TestCaseID: " + testCaseId + ")");
            } else {
                test.fail("❌ Career Profile toast not visible (TestCaseID: " + testCaseId + ")");
            }

            softAssert.assertTrue(
                isToastVisible,
                "❌ Toast not visible for successful career profile addition"
            );
            softAssert.assertAll();
        }

        extent.flush();
    }
}
