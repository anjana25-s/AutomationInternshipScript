package com.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            extent = new ExtentReports();

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter("test-output/ExtentReport.html");

            reporter.config().setReportName("Promilo Automation Report");
            reporter.config().setDocumentTitle("Test Execution Report");

            extent.attachReporter(reporter);
        }
        return extent;
    }
}
