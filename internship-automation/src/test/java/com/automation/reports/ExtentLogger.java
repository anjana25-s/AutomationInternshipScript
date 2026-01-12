package com.automation.reports;

import com.automation.listeners.ExtentTestListener;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class ExtentLogger {

    private static ExtentTest getTest() {
        return ExtentTestListener.getTest();
    }

    public static void info(String message) {
        if (getTest() != null) {
            getTest().info(message);
        }
    }

    public static void pass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    public static void fail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        }
    }

    public static void fail(Throwable t) {
        if (getTest() != null) {
            getTest().fail(t);
        }
    }

    // ✅ Screenshot support
    public static void failWithScreenshot(String message, String screenshotPath) {
        if (getTest() != null && screenshotPath != null) {
            try {
                getTest().fail(
                        message,
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build()
                );
            } catch (Exception e) {
                getTest().fail(message + " (Screenshot attach failed)");
            }
        }
    }
}
