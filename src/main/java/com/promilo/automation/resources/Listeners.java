package com.promilo.automation.resources;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;

public class Listeners extends Baseclass implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("✅ Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest currentTest = test.get();
        currentTest.fail(result.getThrowable());

        try {
            Page page = Baseclass.getPage();
            if (page != null) {
                byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                String base64Screenshot = java.util.Base64.getEncoder().encodeToString(screenshotBytes);

                currentTest.fail(
                        "❌ Test Failed - Screenshot Attached",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, "Failure Screenshot").build()
                );
            } else {
                currentTest.fail("❗ Page is null, unable to capture screenshot.");
            }
        } catch (Exception e) {
            currentTest.fail("❗ Exception while capturing screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest currentTest = test.get();
        if (result.getThrowable() != null) {
            currentTest.skip("⚠️ Test Skipped: " + result.getThrowable());
        } else {
            currentTest.skip("⚠️ Test Skipped: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        test.remove(); // Cleanup ThreadLocal
    }
}
