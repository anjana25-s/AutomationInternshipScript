package com.promilo.automation.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;
    private static final Object lock = new Object();

    
    
    
    
    public static ExtentReports getInstance() {
        if (extent == null) {
            synchronized (lock) {
                if (extent == null)
                
                
                
                
                {
                    // Ensure /reports directory exists
                    String reportDir = System.getProperty("user.dir") + "/reports";
                    new File(reportDir).mkdirs();

                    // Define report path under /reports folder
                    String reportPath = reportDir + "/ExtentReport.html";
                    ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

                    sparkReporter.config().setDocumentTitle("Test Execution Results");
                    sparkReporter.config().setReportName("Regression Suite - Automation Report");
                    sparkReporter.config().setTheme(Theme.DARK);

                    extent = new ExtentReports();
                    extent.attachReporter(sparkReporter);

                    // Adding system information
                    extent.setSystemInfo("Tester", "Karthik U");
                    extent.setSystemInfo("Environment", "QA");
                    extent.setSystemInfo("OS", System.getProperty("os.name"));
                    extent.setSystemInfo("Tool", "Playwright");
                    extent.setSystemInfo("Build", "v1.0.0");
                }
            }
        }
        return extent;
    }
}
