package com.promilo.automation.resumemodule;

import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ITskills extends BaseClass {

    @Test
    public void addITSkillsDataDrivenTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🛠️ IT Skills Functionality Test");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📗 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 3);
            String password = excel.getCellData(i, 6);
            String skillName = excel.getCellData(i, 7); 
            String version = excel.getCellData(i, 8); 
            String yearUsed = excel.getCellData(i, 10); 
            String experienceYear = excel.getCellData(i, 8); 
            String experienceMonth = excel.getCellData(i, 9); 

            if (!"ITskills".equalsIgnoreCase(keyword)) continue;

            Page page = initializePlaywright();
            try {
                test.info("🌐 Navigating to application URL.");
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1000, 768);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup.");
                } catch (PlaywrightException e) {
                    test.info("ℹ️ No popup found.");
                }

                test.info("🔐 Attempting login with test credentials.");
                mayBeLaterPopUp.clickLoginButton();
                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("testuser1110@gmail.com");
                loginPage.passwordField().fill("Karthik@88");
                loginPage.loginButton().click();
                test.info("✅ Login successful.");

                Hamburger resumePage = new Hamburger(page);
                resumePage.Mystuff().click();
                resumePage.MyAccount().click();
                resumePage.MyResume().click();
                test.info("📄 Navigated to My Resume section.");

                resumePage.AddITskills().click();
                test.info("➕ Adding IT Skill.");

                page.locator("//input[@id='experience' and @placeholder='Enter skill/software name']").fill("devops");
                test.info("🖊️ Entered skill name: devops");

                page.locator("//input[@placeholder='Enter software version ']").fill("version1");
                test.info("🖊️ Entered version: version1");

                Locator yearDropdown = page.locator("#react-select-2-input");
                yearDropdown.click();
                page.keyboard().type("2022");
                page.keyboard().press("Enter");
                test.info("📅 Selected Year Used: 2022");

                Locator experYear = page.locator("(//div[contains(@class,'react-select__control')])[2]");
                experYear.click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("2 years").setExact(true)).click();
                test.info("📊 Selected Experience: 2 years");

                page.locator(".label-month > .custom-select > .react-select__control > .react-select__value-container > .react-select__input-container").click();
                page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("1 month").setExact(true)).click();
                test.info("📊 Selected Duration: 1 month");

                page.locator("//button[@class='save-resume-btn']").click();
                test.info("💾 Clicked Save button.");

                Locator statusDiv = page.locator("(//div[@role='status'])[1]");
                statusDiv.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

                Assert.assertTrue(statusDiv.isVisible(), "❌ Status message is NOT visible");
                test.pass("✅ IT Skill added successfully. Status message verified.");

            } catch (Exception e) {
                test.fail("❌ Test failed due to: " + e.getMessage());
                throw e;
            } finally {
                page.context().browser().close();
                test.info("🛑 Browser closed.");
            }
        }
    }
}
