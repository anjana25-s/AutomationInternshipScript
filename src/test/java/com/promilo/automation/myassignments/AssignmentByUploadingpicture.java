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
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AssignmentByUploadingpicture extends BaseClass {

    @Test
    public void uploadAssignmentWithImageDataDriven() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📄 Assignment Upload Test | Data Driven");

        // Load Excel file
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
            String textArea = excel.getCellData(i, 10); // Assignment content

            if (!"AssignmentUpload".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔁 Running test for TestCaseID: " + testCaseId);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1000, 768);

            // Landing page interaction
            LandingPage landingPage = new LandingPage(page);
            landingPage.getPopup().click();
            landingPage.clickLoginButton();

            // Login interaction
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(email);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // Navigate to MyResume > My Assignment
            MyResumePage resumePage = new MyResumePage(page);
            resumePage.Mystuff().click();
            resumePage.MyAccount().click();

            page.locator("//a[text()='My Assignment']").click();

            // Print salary and experience
            Locator salaryLocator = page.locator("span[class='text-truncate']").first();
            System.out.println("Salary: " + salaryLocator.textContent().trim());

            Locator experienceLocator = page.locator("(//div[@class='card_detail-label'])[position()=1]");
            System.out.println("Experience: " + experienceLocator.textContent().trim());

            page.locator("//span[text()='Start Assignment']").click();
            Thread.sleep(2000);

            // Open text editor and type assignment
            page.locator("div[aria-label='Editor editing area: main']").click();
            page.keyboard().type(textArea != null ? textArea : "Default assignment text.");

            // Upload image
            page.onFileChooser(fileChooser -> {
                try {
                    fileChooser.setFiles(Paths.get("C:\\Users\\Admin\\Downloads\\pexels-moh-adbelghaffar-771742.jpg"));
                } catch (Exception e) {
                    throw new RuntimeException("❌ Failed to set file in FileChooser", e);
                }
            });

            page.locator("//button[@data-cke-tooltip-text='Insert image']").click();

            // Submit assignment
            Locator submitBtn = page.locator("//span[text()='Submit']");
            submitBtn.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            submitBtn.click();

            // Verify toaster
            Locator toast = page.locator("//div[@role='status']");
            toast.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            if (!toast.isVisible()) {
                throw new AssertionError("❌ Toast was not displayed after submitting the assignment.");
            }

            System.out.println("✅ Assignment submitted successfully for: " + email);
        }
    }
}
