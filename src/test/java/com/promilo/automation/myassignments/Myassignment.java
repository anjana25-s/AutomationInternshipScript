package com.promilo.automation.myassignments;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class Myassignment extends Baseclass {

    @Test
    public void myAssignmentTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📘 My Assignment Test | Data Driven");

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
            if (!"MyAssignment".equalsIgnoreCase(keyword)) continue;

            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);
            String assignmentDescription = excel.getCellData(i, 10);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            resumePage.MyAccount().click();
            page.locator("//a[text()='My Assignment']").click();

            page.locator("//span[text()='Start Assignment']").click();
            Thread.sleep(2000);

            Locator assignmentTitle = page.locator("(//div[@class='assignment-feedback-comment'])[1]");
            Locator assignmentTopic = page.locator("(//div[@class='assignment-feedback-comment'])[2]");

            System.out.println("Assignment Title: " + assignmentTitle.textContent().trim());
            System.out.println("Assignment Topic: " + assignmentTopic.textContent().trim());

            page.locator("//span[text()='Submit Assignment']").click();

            Locator assignmentTextarea = page.locator("div[aria-label='Editor editing area: main']");
            assignmentTextarea.click();
            page.keyboard().type(assignmentDescription);

            page.locator("//span[text()='Submit']").click();

            Locator toast = page.locator("//div[@role='status']");
            toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (!toast.isVisible()) {
                throw new AssertionError("❌ Toast was not displayed.");
            }

            System.out.println("✅ Toast displayed successfully.");
        }
    }
}
