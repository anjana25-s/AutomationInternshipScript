package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.courses.intrestspages.ConnectNowPage;
import com.promilo.automation.courses.intrestspages.TalkToExpertPage;
import com.promilo.automation.courses.intrestspages.ViewedIntrestPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MyPreferenceViewedCardValidation extends BaseClass {
	
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

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
        Set<String> targetKeywords = Collections.singleton("GetMentorCall");

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


        

        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
        
        
        // Step 1: Click Courses
        page.locator("//a[text()='Courses']").click();
        test.info("📚 Clicked on 'Courses'.");

        




            

            page.waitForTimeout(3000);
            page.locator("//span[text()='View Details']").first().click();
            
            
           
            
            
                        
            
            
            
            
            //Card Validation
            
            ViewedIntrestPage cardValidation= new ViewedIntrestPage(page);
            
            
            cardValidation.myInterestTab().click();
            page.waitForTimeout(3000);
            cardValidation.myPreferenceTab().scrollIntoViewIfNeeded();
            page.waitForTimeout(1000);
            cardValidation.myPreferenceTab().click();
            
            page.waitForTimeout(2000);
            page.locator("//div[text()='Viewed']").click();
            
            
            
            page.waitForTimeout(5000);
         // Campaign Name
            Assert.assertTrue(cardValidation.campaignName().isVisible(), "Campaign Name is not visible!");
            String campaignName = cardValidation.campaignName().textContent().trim();
            System.out.println("Campaign Name: " + campaignName);
            Assert.assertFalse(campaignName.isEmpty(), "Campaign Name text is empty!");


            // Location
            Assert.assertTrue(cardValidation.location().isVisible(), "Location is not visible!");
            String location = cardValidation.location().textContent().trim();
            System.out.println("Location: " + location);
            Assert.assertFalse(location.isEmpty(), "Location text is empty!");
            Assert.assertEquals(location, "Bengaluru/Bangalore");


            // Product Title
            Assert.assertTrue(cardValidation.productTitle().isVisible(), "Product Title is not visible!");
            String productTitle = cardValidation.productTitle().textContent().trim();
            System.out.println("Product Title: " + productTitle);
            Assert.assertFalse(productTitle.isEmpty(), "Product Title text is empty!");
            Assert.assertEquals(productTitle, "Mobile test application title");


            // Duration
            Assert.assertTrue(cardValidation.duration().isVisible(), "Duration is not visible!");
            String duration = cardValidation.duration().textContent().trim();
            System.out.println("Duration: " + duration);
            Assert.assertFalse(duration.isEmpty(), "Duration text is empty!");
            Assert.assertEquals(duration, "6 Months");


            // Fee
            Assert.assertTrue(cardValidation.fee().isVisible(), "Fee is not visible!");
            String fee = cardValidation.fee().textContent().trim();
            System.out.println("Fee: " + fee);
            Assert.assertFalse(fee.isEmpty(), "Fee text is empty!");
            Assert.assertEquals(fee, "0.01 Lakhs");

            page.waitForTimeout(3000);
            String serviceName=cardValidation.serviceName().textContent().trim();
            assertEquals(serviceName, "Viewed");
            
            
            cardValidation.serviceIcon().isVisible();
            
            
            cardValidation.cardPicture().isVisible();
            
            
         // Click 1
            cardValidation.myPreferenceTalkToExpertButton().click();
            page.waitForURL("**");   // wait for navigation
            String url1 = page.url();
            System.out.println("Talk to Expert URL: " + url1);

            // Click 2
            page.bringToFront();
            cardValidation.myPreferenceFreeVideoCounselling().click();
            page.waitForURL("**");   // wait for navigation
            String url2 = page.url();
            System.out.println("Free Video Counselling URL: " + url2);

            
            
            
            
            
            
            
            
            
            
            
            
        }}

}
