package com.promilo.automation.myassignments;

import org.testng.Assert;
import org.testng.annotations.Test;
import java.nio.file.Paths;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.*;

public class MyassignmentUsingUpload extends Baseclass {

    @Test
    public void myAssignmentUploadTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📂 My Assignment Upload | Data-Driven Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 600);

        // Excel setup
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
            String uploadText = excel.getCellData(i, 10); // Optional: input text for textarea
            String uploadFilePath = excel.getCellData(i, 11); // Optional: file path to upload

            if (!"MyAssignmentUpload".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("✅ Running test for TestCaseID: " + testCaseId);

            // Landing page
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // MyResume nav
            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            Assert.assertTrue(resumePage.Mystuff().isVisible(), "Mystuff menu should be visible after login");
            resumePage.MyAccount().click();

            // My Assignment section
            page.locator("//a[text()='My Assignment']").click();

            // Salary & experience logs
            Locator salaryLocator = page.locator("span[class='text-truncate']");
            test.info("💸 Salary: " + salaryLocator.textContent().trim());

            Locator experienceLocator = page.locator("(//div[@class='card_detail-label'])[position()=1]");
            test.info("🕒 Experience: " + experienceLocator.textContent().trim());

            // Start assignment
            page.locator("//span[text()='Start Assignment']").click();
            page.locator("(//div[@class='assignment-feedback-comment'])[1]").waitFor();

            test.info("📝 Assignment Text: " + page.locator("(//div[@class='assignment-feedback-comment'])[1]").textContent().trim());
            test.info("📌 Assignment Topic: " + page.locator("(//div[@class='assignment-feedback-comment'])[2]").textContent().trim());

            // Fill assignment and upload file
            page.locator("//span[text()='Submit Assignment']").click();
            Locator textArea = page.locator("div[aria-label='Editor editing area: main']");
            textArea.click();
            page.keyboard().type(uploadText != null ? uploadText : "Sample assignment answer");

            // Handle file upload
            page.onFileChooser(fileChooser -> {
                try {
                    String path = uploadFilePath != null && !uploadFilePath.isEmpty()
                        ? uploadFilePath
                        : "C:\\Users\\Admin\\Downloads\\Dcq.pdf";
                    fileChooser.setFiles(Paths.get(path));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            page.locator("//div[text()='Upload New File']").click();

            Thread.sleep(2000);
            page.locator("//span[text()='Submit']").click();
            Thread.sleep(2000);

            // Toaster confirmation
            Locator toast = page.locator("//div[@role='status']");
            toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (!toast.isVisible()) {
                throw new AssertionError("❌ Toast with role='status' was not displayed.");
            }

            test.pass("✅ Toast displayed: " + toast.textContent().trim());
        }
    }
}
