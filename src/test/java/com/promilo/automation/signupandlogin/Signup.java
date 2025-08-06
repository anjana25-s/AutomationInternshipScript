package com.promilo.automation.signupandlogin;

import com.aventstack.extentreports.*;
import com.promilo.automation.resources.*;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class Signup extends Baseclass {

    @Test
    public void signup() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Promilo Signup Test - Using Signup Utility");

        // Excel path setup
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("📋 Loaded " + rowCount + " rows from Excel.");

        Set<String> signupKeywords = Collections.singleton("UserSignup");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);

            if (!signupKeywords.contains(keyword)) continue;

            try {
                SignUpLogoutUtil util = new SignUpLogoutUtil();
                String[] creds = util.createAccountAndLoginFromExcel(excel, i);
                String email = creds[0];
                String password = creds[1];

                test.pass("✅ Signup successful for TestCaseID: " + testCaseId + " using email: " + email);

            } catch (Exception e) {
                test.fail("❌ Signup failed for TestCaseID: " + testCaseId + ". Reason: " + e.getMessage());
                throw e;
            }
        }

        extent.flush();
    }
}
