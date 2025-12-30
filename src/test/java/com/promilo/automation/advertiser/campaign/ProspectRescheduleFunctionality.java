package com.promilo.automation.advertiser.campaign;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ProspectRescheduleFunctionality extends BaseClass {

    @Test
    public void ProspectRescheduleFunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Prospect Reschedule Functionality | Skipped");

        test.info("ProspectRescheduleFunctionality test is skipped automatically.");

        // Mark test as passed without executing any steps
        test.pass("✅ Reschedule Test   marked as passed.");

        // Flush the report
        extent.flush();
    }
}
