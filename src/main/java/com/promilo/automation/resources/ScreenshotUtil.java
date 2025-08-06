package com.promilo.automation.resources;


import com.microsoft.playwright.Page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtil {

    /**
     * Takes a screenshot with Playwright, saves it under /screenshots with timestamp, and prints its path.
     */
    public static void takeScreenshot(String testName, Page page) throws IOException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        String filePath = screenshotDir + fileName;

        // Create directories if they do not exist
        File dest = new File(filePath);
        dest.getParentFile().mkdirs();

        // Capture screenshot as bytes
        byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));

        // Write to file
        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(screenshotBytes);
        }

        System.out.println("📸 Screenshot saved at: " + filePath);
    }

    /**
     * Captures a screenshot with Playwright and returns it as a Base64 string for ExtentReports.
     */
    public static String captureScreenshotAsBase64(Page page) {
        byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return Base64.getEncoder().encodeToString(screenshotBytes);
    }
}

