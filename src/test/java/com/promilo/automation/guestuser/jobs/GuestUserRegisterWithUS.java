package com.promilo.automation.guestuser.jobs;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GuestUserRegisterWithUS extends BaseClass{
    private static final Logger logger = LogManager.getLogger(GuestUserRegisterWithUS.class);

	
	 /**
     * DataProvider to fetch job application data from Excel for test execution.
     */
    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        // Construct the Excel path dynamically
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        // Initialize data array with 8 columns based on expected test data
        Object[][] data = new Object[rowCount - 1][8];

        // Populate data array with required columns
        for (int i = 1; i < rowCount; i++) {
            data[i - 1][0] = excel.getCellData(i, 0);  // TestCaseID
            data[i - 1][1] = excel.getCellData(i, 1);  // Keyword
            data[i - 1][2] = excel.getCellData(i, 3);  // InputValue (Email)
            data[i - 1][3] = excel.getCellData(i, 6);  // Password
            data[i - 1][4] = excel.getCellData(i, 7);  // Name
            data[i - 1][5] = excel.getCellData(i, 5);  // OTP
            data[i - 1][6] = excel.getCellData(i, 8);  // MailPhone
            data[i - 1][7] = excel.getCellData(i, 10); // TextArea
        }

        return data;
    }

    /**
     * Test to apply for a job as a registered user on the 'Ask Us' job section.
     */
    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(String testCaseId, String keyword, String email, String password, String name, String otp, String mailphone, String TextArea) throws Exception {

        // Initialize Extent Report for structured reporting
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🚀 Apply for Job as Registered User | " + testCaseId);

        // Skip test execution if the keyword does not match 'Askus?'
        if (!keyword.equalsIgnoreCase("RegisterWithUS")) {
            test.info("⏭️ Skipping TestCaseID: " + testCaseId + " due to keyword: " + keyword);
            return;
        }

        // Launch browser using Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);
        
        
        LandingPage landiPage= new LandingPage(page);
        landiPage.getPopup().click();
        
        
        JobListingPage registerwithus= new JobListingPage(page);
        registerwithus.homepageJobs().click();
        
        registerwithus.RegisteredWithUsname().fill("karthik");
        Thread.sleep(3000);

        registerwithus.RgeisteredwithUsphone().fill("9000011864");
        Thread.sleep(3000);

        registerwithus.RegisteredWithUsmail().fill("testuser0010karthik@gmail.com");
        registerwithus.Rgeisteredwithuslocation().click();
        Thread.sleep(3000);

        page.locator("//label[text()='Bengaluru/Bangalore']").click();
        registerwithus.Rgeisteredwithuslocation().click();
        Thread.sleep(3000);

        registerwithus.RegisteredwithusPassword().fill("Karthik@88");
        
        Thread.sleep(3000);

        List<String> industries = Arrays.asList(
                "Animation & VFX"
        );

        // Open the dropdown
        page.locator("//div[@id='industry-dropdown']").click();
        page.waitForTimeout(500);

        Locator optionElements = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");

        for (String industry : industries) {
            boolean found = false;
            int count = optionElements.count();
            for (int i = 0; i < count; i++) {
                String text = optionElements.nth(i).innerText().trim();
                if (text.equalsIgnoreCase(industry)) {
                    optionElements.nth(i).click();
                    test.info("✅ Selected industry: " + industry);
                    found = true;
                    break;
                }
            }
            if (!found) {
                test.warning("⚠️ Industry not found: " + industry);
            }
            if (!industry.equals(industries.get(industries.size() - 1))) {
                page.locator("//div[@id='industry-dropdown']").click();
                page.waitForTimeout(300);
            }
        }

        registerwithus.Registeredwithusbutton().click();
        
        
        Thread.sleep(5000);
        
        
        if (otp == null || otp.length() < 4) {
            throw new IllegalArgumentException("OTP provided is less than 4 characters: " + otp);
        }

        for (int i = 0; i < 4; i++) {
            String otpChar = Character.toString(otp.charAt(i));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            int attempts = 0;

            while (!filled && attempts < 3) {
                attempts++;
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("Failed to enter OTP digit " + (i + 1) + " correctly after retries.");
            }
        }

        Locator verifyButton = page.locator("//button[@class='submit-btm-askUs  btn btn-primary']");
        verifyButton.click();
        
        Thread.sleep(5000);

  
     // Relaxed locator
        Locator thankYouMessage = page.locator("//div[@class='modal-content']");
        thankYouMessage.waitFor(new Locator.WaitForOptions().setTimeout(10000));

        // Optionally wait a bit for dynamic rendering
        page.waitForTimeout(500);

        String actualMessage = thankYouMessage.textContent().trim().toLowerCase();
        logger.info("[{}] Thank You message displayed: {}", testCaseId, actualMessage);

        // Case-insensitive partial match
        if (actualMessage.contains("thank you") && actualMessage.contains("congratulations")) {
            logger.info("[{}] Thank You message verified successfully.", testCaseId);
            test.pass("✅ Thank You message verified: " + actualMessage);
        } else {
            logger.error("[{}] Thank You message does not match expected partial text.", testCaseId);
            test.fail("❌ Thank You message mismatch. Actual: " + actualMessage);
        }

        extent.flush();
    
}
    
}

