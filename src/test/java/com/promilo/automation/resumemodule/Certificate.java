package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.promilo.automation.pageobjects.myresume.AccomplishmentsPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class Certificate extends BaseClass {

    @Test
    public void basicDetailsLoginTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🏅 Certificate Functionality | Data Driven Test");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);
            String certName = excel.getCellData(i, 8);
            String certUrl = excel.getCellData(i, 9);
            String certId = excel.getCellData(i, 10);

            if (!"Certificate".equalsIgnoreCase(keyword)) {
                continue;
            }

            Page page = initializePlaywright();
            try {
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1000, 768);
                test.info("🌐 Navigated to application URL and set viewport.");

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup.");
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup to close.");
                }

                mayBeLaterPopUp.clickLoginButton();
                test.info("🔑 Clicked Login button.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("testautomation@gmail.com");
                test.info("📧 Entered email: testautomation@gmail.com");

                loginPage.passwordField().fill("Karthik@88");
                test.info("🔒 Entered password.");

                loginPage.loginButton().click();
                test.info("✅ Clicked on Login.");

                Hamburger resumePage = new Hamburger(page);
                resumePage.Mystuff().click();
                test.info("📂 Clicked 'My Stuff'.");

                Assert.assertTrue(resumePage.Mystuff().isVisible(), "Mystuff menu should be visible after login");
                test.info("✅ Verified 'My Stuff' menu is visible.");

                resumePage.MyAccount().click();
                test.info("👤 Clicked 'My Account'.");

                resumePage.MyResume().click();
                test.info("📄 Clicked 'My Resume'.");

                resumePage.AddCertificate().click();
                test.info("➕ Clicked 'Add Certificate'.");

                AccomplishmentsPage certificate = new AccomplishmentsPage(page);
                certificate.certificationsNameTextField().fill("devops");
                test.info("🏅 Entered certificate name: devops");

                certificate.certificationsCompletionIdField().fill("devops123");
                test.info("🆔 Entered certificate completion ID: devops123");

                certificate.certificationsUrlField().fill("https://promilo.com/");
                test.info("🔗 Entered certificate URL: https://promilo.com/");

                certificate.certificationsValidityFromYear().first().click();
                page.keyboard().type("2020");
                page.keyboard().press("Enter");
                test.info("📅 Selected From Year: 2020");

                certificate.durationFromMonthDropDown().first().click();
                page.keyboard().type("jan");
                page.keyboard().press("Enter");
                test.info("📅 Selected From Month: January");

                page.locator("(//div[contains(@class,'react-select__control')])[3]").click();
                page.keyboard().type("2022");
                page.keyboard().press("Enter");
                test.info("📅 Selected To Year: 2022");

                page.locator("(//div[contains(@class,'col-sm-3')])[4]//div[contains(@class,'react-select__control')]").click();
                page.keyboard().type("feb");
                page.keyboard().press("Enter");
                test.info("📅 Selected To Month: February");

                page.locator("//button[text()='Save']").click();
                test.info("💾 Clicked Save button.");

                Thread.sleep(2000);
                Locator toast = page.locator("//div[@role='status']");
                toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));

                if (!toast.isVisible()) {
                    throw new AssertionError("❌ Toast with role='status' was not displayed.");
                }

                test.pass("✅ Certificate added successfully and toast verified.");

            } catch (Exception e) {
                test.fail("❌ Test failed with exception: " + e.getMessage());
                throw e;
            } finally {
                page.context().close();
                test.info("🛑 Closed browser context.");
            }
        }

        extent.flush();
    }
}
