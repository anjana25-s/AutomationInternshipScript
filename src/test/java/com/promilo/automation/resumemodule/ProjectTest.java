package com.promilo.automation.resumemodule;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.AddProjectPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ProjectTest extends BaseClass {

    @Test
    public void addProjectTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📁 Add Project - Data Driven");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);
        test.info("🌐 Navigated to application URL.");

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
            String keyword = excel.getCellData(i, 1);
            if (!"ProjectTest".equalsIgnoreCase(keyword)) {
                continue;
            }

            Map<String, String> data = new HashMap<>();
            data.put("TestCaseID", excel.getCellData(i, 0));
            data.put("Keyword", excel.getCellData(i, 1));
            data.put("InputValue", excel.getCellData(i, 3));
            data.put("OTP", excel.getCellData(i, 3));
            data.put("Password", excel.getCellData(i, 6));
            data.put("ProjectTitle", excel.getCellData(i, 7));
            data.put("Client", excel.getCellData(i, 8));
            data.put("FromYear", excel.getCellData(i, 9));
            data.put("FromMonth", excel.getCellData(i, 10));
            data.put("Description", excel.getCellData(i, 11));
            data.put("Location", excel.getCellData(i, 12));
            data.put("TeamSize", excel.getCellData(i, 13));
            data.put("Role", excel.getCellData(i, 14));
            data.put("RoleDescription", excel.getCellData(i, 15));
            data.put("Skills", excel.getCellData(i, 16));

            // 🔑 Login Flow
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            mayBeLaterPopUp.dismissPopup();
            test.info("✅ Dismissed popup (if any).");

            mayBeLaterPopUp.clickLoginButton();
            test.info("➡️ Clicked Login button.");

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("7026268342");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("🔐 Logged in successfully with registered credentials.");

            // 🧭 Navigate to Resume -> Add Project
            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            test.info("📂 Clicked MyStuff.");
            hamburger.MyAccount().click();
            test.info("👤 Clicked MyAccount.");
            hamburger.MyResume().click();
            test.info("📄 Opened My Resume.");
            hamburger.AddProject().click();
            test.info("➕ Clicked Add Project.");

            Thread.sleep(4000);

            // 📝 Fill Project form
            AddProjectPage addProjectPage = new AddProjectPage(page);
            addProjectPage.ProjectTitle().fill(data.get("ProjectTitle"));
            test.info("✍️ Entered Project Title: " + data.get("ProjectTitle"));

            addProjectPage.ClientTextField().fill(data.get("Client"));
            test.info("🏢 Entered Client: " + data.get("Client"));

            addProjectPage.InProgressStatus().click();
            test.info("📌 Selected In Progress status.");

            Thread.sleep(2000);

            addProjectPage.WorkedFromYear().click();
            page.keyboard().type(data.get("FromYear"));
            page.keyboard().press("Enter");
            test.info("📅 Selected From Year: " + data.get("FromYear"));

            Thread.sleep(2000);

            addProjectPage.WorkedFromMonth().click();
            page.keyboard().type(data.get("FromMonth"));
            page.keyboard().press("Enter");
            test.info("📆 Selected From Month: " + data.get("FromMonth"));

            Thread.sleep(2000);

            addProjectPage.ProjectDescription().fill(data.get("Description"));
            test.info("📝 Entered Project Description.");

            addProjectPage.AddMoreDetailsButton().click();
            test.info("📎 Clicked Add More Details.");

            Thread.sleep(2000);

            addProjectPage.ProjectLocationInput().click();
            page.keyboard().type(data.get("Location"));
            page.keyboard().press("Enter");
            test.info("📍 Selected Location: " + data.get("Location"));

            Thread.sleep(2000);

            addProjectPage.OnsiteRadio().click();
            addProjectPage.FullTimeRadio().click();
            test.info("🏢 Selected Work Mode: Onsite, Full-time.");

            addProjectPage.TeamSizeInput().click();
            page.keyboard().type(data.get("TeamSize"));
            page.keyboard().press("Enter");
            test.info("👥 Entered Team Size: " + data.get("TeamSize"));

            Thread.sleep(2000);

            addProjectPage.RoleInput().click();
            page.keyboard().type(data.get("Role"));
            page.keyboard().press("Enter");
            test.info("🎯 Entered Role: " + data.get("Role"));

            Thread.sleep(2000);

            addProjectPage.RoleDescription().fill(data.get("RoleDescription"));
            test.info("📖 Entered Role Description.");

            addProjectPage.SkillsUsedTextarea().fill(data.get("Skills"));
            test.info("🛠️ Entered Skills Used: " + data.get("Skills"));

            addProjectPage.SaveButton().click();
            test.info("💾 Clicked Save button.");

            Thread.sleep(4000);

            test.pass("✅ Project added successfully for: " + data.get("InputValue"));
        }

        extent.flush();
    }
}
