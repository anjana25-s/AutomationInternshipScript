package com.promilo.automation.resources;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;

import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Listener class for TestNG suite.
 * Sends a single emailable report email after entire suite finishes.
 */
public class Listeners extends Baseclass implements ITestListener, ISuiteListener, IAnnotationTransformer {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    // -------------------- TestNG Test Listener --------------------
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
        ExtentTest currentTest = test.get();
        currentTest.pass("✅ Test Passed: " + result.getMethod().getMethodName());
        attachScreenshot(currentTest, "Pass Screenshot");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest currentTest = test.get();
        currentTest.fail(result.getThrowable());
        attachScreenshot(currentTest, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest currentTest = test.get();
        if (result.getThrowable() != null) {
            currentTest.skip("⚠️ Test Skipped: " + result.getThrowable());
        } else {
            currentTest.skip("⚠️ Test Skipped: " + result.getMethod().getMethodName());
        }
        attachScreenshot(currentTest, "Skipped Screenshot");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush ExtentReports at the end of each <test> tag
        extent.flush();
        test.remove();
    }

    // -------------------- ISuiteListener --------------------
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("📧 Sending consolidated TestNG emailable report for entire suite...");
        // Flush ExtentReports just in case
        extent.flush();

        // Send email with static TestNG report
        try {
            EmailReportUtility.sendReport();
        } catch (Exception e) {
            System.err.println("❌ Failed to send TestNG report: " + e.getMessage());
        }
    }

    @Override
    public void onStart(ISuite suite) {
        // Can initialize something at suite start if needed
    }

    // -------------------- Retry Analyzer for test annotations --------------------
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        if (annotation.getRetryAnalyzerClass() == null) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }

    // -------------------- Screenshot capture for Extent --------------------
    private void attachScreenshot(ExtentTest currentTest, String title) {
        try {
            Page page = Baseclass.getPage();
            if (page != null) {
                byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                String base64Screenshot = java.util.Base64.getEncoder().encodeToString(screenshotBytes);
                currentTest.info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot, title).build());
            } else {
                currentTest.info("❗ Page is null, unable to capture screenshot.");
            }
        } catch (Exception e) {
            currentTest.info("❗ Exception while capturing screenshot: " + e.getMessage());
        }
    }
}
