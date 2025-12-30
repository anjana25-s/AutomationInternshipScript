package com.promilo.automation.internship.listener;


import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {
            String path = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(path);

            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Promilo Automation Execution");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Tester", "Anjana");
            extent.setSystemInfo("Environment", "Stage");
        }
        return extent;
    }
}


