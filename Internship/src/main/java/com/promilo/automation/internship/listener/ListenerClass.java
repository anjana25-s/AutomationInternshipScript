package com.promilo.automation.internship.listener;

import java.io.File;
import java.nio.file.Paths;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import basetest.Baseclass;

public class ListenerClass implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

  
    //  Initialize Extent Report
   
    public static ExtentReports createInstance() {

        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setReportName("Promilo Automation Report");
        spark.config().setDocumentTitle("Automation Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Tester", "Anjana");
        extent.setSystemInfo("Project", "Promilo Internship");
        extent.setSystemInfo("OS", System.getProperty("os.name"));

        return extent;
    }

    
    //  When Test Suite Starts
    @Override
    public void onStart(ITestContext context) {
        if (extent == null) {
            extent = createInstance();
        }
        System.out.println("====== Test Suite Started ======");
    }

   
    // 3️ On Test Start
   @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testReport.set(test);
    }

    
    // On Test Success
     @Override
    public void onTestSuccess(ITestResult result) {
        testReport.get().log(Status.PASS, "Test Passed");
    }

   
    // On Test Failure + Screenshot
  @Override
     public void onTestFailure(ITestResult result) {

    	    ExtentTest test = testReport.get();
    	    test.log(Status.FAIL, result.getThrowable());

    	    try {
    	        Page page = (Page) result.getTestContext().getAttribute("page");

    	        if (page == null) {
    	            test.warning("⚠ Page object is NULL → Browser may have closed before capturing screenshot.");
    	            return;
    	        }

    	        String screenshotDir = System.getProperty("user.dir") + "/test-output/screenshots/";
    	        new File(screenshotDir).mkdirs();

    	        String screenshotName = result.getMethod().getMethodName() + ".png";
    	        String screenshotPath = screenshotDir + screenshotName;

    	        // TAKE SCREENSHOT
    	        page.screenshot(new Page.ScreenshotOptions()
    	                .setPath(Paths.get(screenshotPath))
    	                .setFullPage(true)
    	        );

    	        // Attach to Extent
    	        test.fail("Screenshot:",
    	                MediaEntityBuilder.createScreenCaptureFromPath("screenshots/" + screenshotName).build()
    	        );

    	    } catch (PlaywrightException e) {
    	        test.fail("❌ Failed to capture screenshot: " + e.getMessage());
    	    } catch (Exception ex) {
    	        ex.printStackTrace();
    	        
    	    }
    	}

   
  // On Test Skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        testReport.get().log(Status.SKIP, "Test Skipped");
    }

   
    // On Test Suite End
  @Override
    public void onFinish(ITestContext context) {
        System.out.println("====== Test Suite Finished ======");
        extent.flush();
    }
  
  

}
