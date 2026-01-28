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
 * Sends a single e-mailable report email after entire suite finishes.
 */
public class Listeners extends BaseClass implements ITestListener, ISuiteListener, IAnnotationTransformer {

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

        // No screenshot for passed tests
        test.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest currentTest = test.get();
        currentTest.fail(result.getThrowable());

        // Screenshot ONLY for failed tests
        attachScreenshot(currentTest, "Failure Screenshot");

        test.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest currentTest = test.get();

        if (result.getThrowable() != null) {
            currentTest.skip("⚠️ Test Skipped: " + result.getThrowable());
        } else {
            currentTest.skip("⚠️ Test Skipped: " + result.getMethod().getMethodName());
        }

        // No screenshot for skipped tests
        test.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        // Do NOT flush here → only flush at suite level
        test.remove();
    }

    // -------------------- ISuiteListener --------------------
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("📧 Sending consolidated TestNG emailable report for entire suite...");

        // Final flush at suite end
        extent.flush();

        try {
            EmailReportUtility.sendReport();
        } catch (Exception e) {
            System.err.println("❌ Failed to send TestNG report: " + e.getMessage());
        }
    }

    @Override
    public void onStart(ISuite suite) {
        System.out.println("🚀 Test Suite Started: " + suite.getName());
    }

    // -------------------- Retry Analyzer --------------------
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
            Page page = BaseClass.getPage();

            if (page == null) {
                currentTest.info("❗ Page is null, unable to capture screenshot.");
                return;
            }

            if (page.isClosed()) {
                currentTest.info("❗ Page is already closed. Screenshot skipped.");
                return;
            }

            // Take screenshot
            byte[] screenshotBytes = page.screenshot(
                    new Page.ScreenshotOptions().setFullPage(true)
            );
            String base64 = java.util.Base64.getEncoder().encodeToString(screenshotBytes);

                        // Inline embedded base64 image
            String htmlImage =
                    "<b>" + title + "</b><br>" +
                    "<img src='data:image/png;base64," + base64 + "' height='450' />";


        } catch (Exception e) {
            currentTest.info("❗ Error capturing screenshot: " + e.toString());
        }
    }
}
