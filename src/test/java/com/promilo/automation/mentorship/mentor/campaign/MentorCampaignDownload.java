package com.promilo.automation.mentorship.mentor.campaign;

<<<<<<< HEAD
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import java.nio.file.Path;
import java.awt.Desktop;
import java.io.File;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignDownload extends Baseclass {

    @Test
    public void addEmploymentPositiveTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

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
                loginPage.loginMailPhone().fill("testradha68@yopmail.com");
                loginPage.passwordField().fill("Karthik@88");
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials.");

                // Navigate to My Resume
                Hamburger resumePage = new Hamburger(page);
=======
import java.awt.Desktop;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.MyResumePage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignDownload extends BaseClass {

    @Test
    public void addEmploymentPositiveTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("✅ Add Employment - Positive Test");

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

                LandingPage landingPage = new LandingPage(page);
                try {
                    landingPage.getPopup().click();
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                landingPage.clickLoginButton();
                test.info("🔑 Navigating to Login Page.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill("testradha68@yopmail.com");
                loginPage.passwordField().fill("Karthik@88");
                loginPage.loginButton().click();
                test.info("✅ Logged in with registered credentials.");

                // Navigate to My Resume
                MyResumePage resumePage = new MyResumePage(page);
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation
                resumePage.Mystuff().click();

                // Become a Mentor actions
                BecomeMentor becomeMentor = new BecomeMentor(page);
                becomeMentor.becomeMentorButton().click();
                becomeMentor.campaignList().click();

                CampaignlistPage downloadExcel = new CampaignlistPage(page);

                // Step 1: Select checkbox and trigger Excel download
                downloadExcel.checkBox().first().click();

                Download download = page.waitForDownload(() -> {
                    downloadExcel.excelDownload().click();
                });

                // Step 2: Save the downloaded file locally
                String downloadedFilePathStr = "downloads/" + download.suggestedFilename();
                Path downloadedFilePath = Path.of(downloadedFilePathStr);
                new File("downloads").mkdirs(); // Ensure folder exists
                download.saveAs(downloadedFilePath);

                System.out.println("✅ Excel downloaded at: " + downloadedFilePathStr);

                // Step 3: Open the downloaded Excel in desktop application
                try {
                    File fileToOpen = new File(downloadedFilePathStr);
                    if (fileToOpen.exists()) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(fileToOpen);
                            System.out.println("📂 Excel file opened in default application.");
                        } else {
                            System.out.println("⚠️ Desktop is not supported. Cannot open Excel automatically.");
                        }
                    } else {
                        System.out.println("❌ File does not exist: " + downloadedFilePathStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Step 4: Use ExcelUtil to read the downloaded Excel
                String sheetName = "Mentor-Campaigns"; // Sheet name
                ExcelUtil excel2 = new ExcelUtil(downloadedFilePathStr, sheetName);

                // Step 5: Define expected headers
                String[] expectedHeaders = {
                        "S.NO", "Campaign Name", "Mentor Type", "Domain", "Status", "Created Date",
                        "Start Date", "End Date", "Service Name", "Service Type", "Cost Per Service",
                        "Cost Type", "Clicks", "Interest Shown", "Acceptance", "Service Completed",
                        "Total Service Earning"
                };

                // Step 6: Validate each header
                for (int col = 0; col < expectedHeaders.length; col++) {
                    String actualHeader = excel2.getCellData(0, col);
                    System.out.println("📄 Header at column " + col + ": " + actualHeader);
                    Assert.assertEquals(actualHeader.trim(), expectedHeaders[col],
                            "Header mismatch at column " + col);
                }
                
                System.out.println("✅ All headers are displayed correctly in Excel.");

                // Step 7: Read all campaign data
                int totalRows = excel2.getRowCount();
                int totalCols = expectedHeaders.length;

                List<String[]> allCampaigns = new ArrayList<>();

                for (int rowIdx = 1; rowIdx <= totalRows; rowIdx++) { // Start from 1 to skip header
                    String[] rowData = new String[totalCols];
                    for (int colIdx = 0; colIdx < totalCols; colIdx++) {
                        rowData[colIdx] = excel2.getCellData(rowIdx, colIdx);
                    }
                    allCampaigns.add(rowData);
                }

                
                
                // Step 8: Print all campaign data in column-wise format
                for (int rowIdx = 0; rowIdx < allCampaigns.size(); rowIdx++) {
                    String[] campaign = allCampaigns.get(rowIdx);
                    System.out.println("📄 Campaign Row " + (rowIdx + 1) + ": ");
                    for (int colIdx = 0; colIdx < campaign.length; colIdx++) {
                        System.out.println("    " + expectedHeaders[colIdx] + ": " + campaign[colIdx]);
                    }
                    System.out.println("--------------------------------------------------");
                }

            } catch (Exception e) {
                test.fail("❌ Error in row " + i + ": " + e.getMessage());
                throw e;
            }
        }
    }
}
