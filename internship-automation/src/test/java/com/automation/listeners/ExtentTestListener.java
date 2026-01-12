package com.automation.listeners;

import com.automation.base.BaseClass;
import com.automation.reports.ExtentLogger;
import com.automation.reports.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;

public class ExtentTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentLogger.pass("✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        Object testClass = result.getInstance();

        if (testClass instanceof BaseClass) {

            BaseClass base = (BaseClass) testClass;

            String screenshotPath =
                    base.getHelper().takeScreenshot(
                            result.getMethod().getMethodName()
                    );

            ExtentLogger.failWithScreenshot(
                    "❌ Test Failed",
                    screenshotPath
            );
        }

        ExtentLogger.fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentLogger.info("⚠️ Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}

