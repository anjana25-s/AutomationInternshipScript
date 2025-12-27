package com.promilo.automation.mentorship.mentee.intrests;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipShortListFunctionality extends BaseClass {

    private static final Logger log = LogManager.getLogger(MentorshipShortListFunctionality.class);


    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException {
    	
    	
    	
    	ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

     // ======== Load Excel ========
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Mentee Interest");
            System.out.println("✅ Excel loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Failed to load Excel: " + e.getMessage());
            test.fail("Failed to load Excel: " + e.getMessage());
            Assert.fail("Excel loading failed");
            return;
        }

        // ======== Map headers to column index ========
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ======== Count data rows (skip header) ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("✅ Loaded " + rowCount + " data rows from Excel.");

        if (rowCount == 0) {
            System.out.println("❌ No data rows found");
            test.fail("No data rows in Excel");
            Assert.fail("No data rows in Excel");
            return;
        }

        // ======== Process rows ========
        Set<String> targetKeywords = Collections.singleton("MentorshipShortListFunctionality");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();

            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data using header mapping
            String mentorName     = excel.getCellData(i, colMap.get("MentorName"));
            String otp            = excel.getCellData(i, colMap.get("otp"));
            String invoiceName    = excel.getCellData(i, colMap.get("invoiceName"));
            String name           = excel.getCellData(i, colMap.get("MentorName"));
            String street1        = excel.getCellData(i, colMap.get("street1"));
            String street2        = excel.getCellData(i, colMap.get("street2"));
            String pincode        = excel.getCellData(i, colMap.get("pincode"));
            String gst            = excel.getCellData(i, colMap.get("gst"));
            String pan            = excel.getCellData(i, colMap.get("pan"));
            String contactNumber  = excel.getCellData(i, colMap.get("contactNumber"));
            String password       = excel.getCellData(i, colMap.get("password"));
        


        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);
        log.info("===== Starting Mentorship ShortList Functionality Test =====");


        // -------------------- Landing Page --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill(mentorName);
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // Shortlist mentor
        DescriptionPage description = new DescriptionPage(page);
        description.shortlist().click();
        log.info("Clicked on shortlist button");

      
     // -------------------- Fill form details --------------------
        MentorshipFormComponents form = new MentorshipFormComponents(page);
        form.name().nth(1).fill(name);

        // Generate phone number starting with 90000 + 5 random digits
        String randomPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));
        form.MobileTextField().fill(randomPhone);

        // Generate email using Promilo prefix + 5-character random string + mailsaur domain
        String randomString = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        String dynamicEmail = "promilo" + randomString + "@mailosaur.net";
        form.emailTextfield().fill(dynamicEmail);

       

        form.shortListButton().click();
        log.info("Filled shortlist form details");

        // -------------------- OTP Handling --------------------
        if (otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
        }

        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
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
                throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
            }
        }

        page.waitForTimeout(2000);
        page.locator("//button[text()='Verify & Proceed']").click();
        page.waitForTimeout(3000);
        
        

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

        ThankYouPopup thankYou = new ThankYouPopup(page);
        // Verify that the Thank You popup messages are displayed
        Assert.assertTrue(thankYou.ThankYouMessage().isVisible(), "❌ Thank You message is not displayed");
        Assert.assertTrue(thankYou.thankYouFooter().isVisible(), "❌ Thank You footer is not displayed");

        thankYou.myPreference().click();
        
        log.info("✅ Shortlist functionality completed successfully.");
        
        page.waitForTimeout(3000);
        


     // ========================= SECTION: Validate Mentor Interest Card =========================
        MentorshipMyintrest myintrest = new MentorshipMyintrest(page);

        // Expected Values – Temporary hardcoded
        String expectedMentorName = "December Automation";
        String expectedMentorData = "dxgfchvjbng vbnm";
        String expectedExperience = "2 Years";
        String expectedLocation = "Anantapur";
        
        String expectedServiceName = "Shortlisted";


        // 2. Mentor name
        String actualMentorName = myintrest.mentorName().innerText().trim();
        Assert.assertEquals(actualMentorName, expectedMentorName);

        // 3. Mentor Data
        String actualMentorData = myintrest.mentorData().innerText().trim();
        Assert.assertEquals(actualMentorData, expectedMentorData);

        // 4. Experience label
        Assert.assertTrue(myintrest.experianceString().isVisible());

        // 5. Experience value
        String actualExperience = myintrest.experianceValue().innerText().trim();
        Assert.assertEquals(actualExperience, expectedExperience);

        // 6. Location value
        String actualLocation = myintrest.locationValue().innerText().trim();
        Assert.assertEquals(actualLocation, expectedLocation);

        // 7. Service Name
        String actualServiceName = myintrest.serviceName().innerText().trim();
        Assert.assertEquals(actualServiceName, expectedServiceName);

        // Get the current URL
        String currentUrl = page.url();
        System.out.println("Current URL after clicking View Profile:" + currentUrl);
        log.info("Current URL after clicking View Profile: " + currentUrl);
        

        
        
        

        

        // Cleanup
        page.close();
    }
}
    
}
