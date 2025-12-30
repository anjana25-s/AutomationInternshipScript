package com.promilo.automation.guestuser.jobs;

import java.nio.file.Paths;

import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.*;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserJobApplyWithInvalidOtp extends BaseClass {

    private static Playwright playwright;
    private static Browser browser;

    @BeforeClass
    public void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(100)
        );
        System.out.println("🚀 Browser launched for GuestUserJobApplyWithInvalidOtp tests.");
    }

    @AfterClass
    public void tearDownBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
        System.out.println("✅ Browser closed for GuestUserJobApplyWithInvalidOtp tests.");
    }

    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                        "PromiloAutomationTestData_Updated_With_OTP (2).xlsx")
                .toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int totalRows = excel.getRowCount();
        // Count only rows with TestCaseID
        int validRowCount = 0;
        for (int i = 1; i <= totalRows; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId != null && !testCaseId.trim().isEmpty()) validRowCount++;
        }

        Object[][] data = new Object[validRowCount][9];
        int index = 0;
        for (int i = 1; i <= totalRows; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) continue;

            data[index][0] = testCaseId;
            data[index][1] = excel.getCellData(i, 1); // Keyword
            data[index][2] = excel.getCellData(i, 2); // FieldType
            data[index][3] = excel.getCellData(i, 3); // InputValue
            data[index][4] = excel.getCellData(i, 4); // ExpectedResult
            data[index][5] = excel.getCellData(i, 5); // OTP
            data[index][6] = excel.getCellData(i, 6); // Password
            data[index][7] = excel.getCellData(i, 7); // Name
            data[index][8] = excel.getCellData(i, 8); // ApplyPhone
            index++;
        }
        return data;
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String fieldType,
            String inputValue,
            String expectedResult,
            String otp,
            String password,
            String name,
            String applyPhone
    ) throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        if (!keyword.equalsIgnoreCase("MailJobInvalidOtp")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

    }
}
