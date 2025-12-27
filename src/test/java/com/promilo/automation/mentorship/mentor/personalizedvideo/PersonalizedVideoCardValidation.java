package com.promilo.automation.mentorship.mentor.personalizedvideo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class PersonalizedVideoCardValidation extends BaseClass {

    ExtentReports extent;
    ExtentTest test;

    // Login using previously generated credentials
    String emailToLogin = BaseClass.generatedEmail;
    String phoneToLogin = BaseClass.generatedPhone;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance();
    }

    @AfterClass
    public void tearDownExtent() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Test(
        dependsOnMethods = {
            "com.promilo.automation.mentorship.mentee.intrests.RequestVideoTest.mentorshipShortListFunctionalityTest"
        }
    )
    public void AcceptVideoServiceRequestTest() throws Exception {

        ExtentTest test = extent.createTest("✅ Accept Video Service - Card Validation Test");

     // ========================= SECTION: Excel Initialization =========================
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

        // ========================= SECTION: Excel Header Mapping =========================
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();

        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ========================= SECTION: Count Excel Data Rows =========================
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

        // ========================= SECTION: Row Processing =========================
        Set<String> targetKeywords = Collections.singleton("GetMentorCall");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch all values from Excel
            String mentorName = excel.getCellData(i, colMap.get("MentorName"));
            String otp = excel.getCellData(i, colMap.get("otp"));
            String invoiceName = excel.getCellData(i, colMap.get("invoiceName"));
            String name = excel.getCellData(i, colMap.get("MentorName"));
            String street1 = excel.getCellData(i, colMap.get("street1"));
            String street2 = excel.getCellData(i, colMap.get("street2"));
            String pincode = excel.getCellData(i, colMap.get("pincode"));
            String gst = excel.getCellData(i, colMap.get("gst"));
            String pan = excel.getCellData(i, colMap.get("pan"));
            String contactNumber = excel.getCellData(i, colMap.get("contactNumber"));
            String password = excel.getCellData(i, colMap.get("password"));
            String Description=excel.getCellData(i, colMap.get("feedbackText"));


            
            
            // ========================= SECTION: Playwright Initialization =========================
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);


            // --- Login flow ---
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);

            try {
                popup.getPopup().click();
                test.info("Popup closed.");
            } catch (Exception ignored) {
                test.info("No popup displayed.");
            }

            popup.clickLoginButton();

            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("812de0aa@qtvjnqv9.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();

            test.info("Logged in successfully.");

            // --- Navigate to My Acceptance ---
            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();

            MyAcceptance acceptRequest = new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            
            

            test.info("📌 Navigated to My Acceptance section.");

            MyAcceptance acceptVideoRequest= new MyAcceptance(page);
            
            
            
            String statusTag=acceptVideoRequest.videoCallstatusTag().first().textContent().trim();
            assertEquals(statusTag, "Pending");
            
            String  menteeName= acceptVideoRequest.videoServiceMenteeName().first().textContent().trim();
            assertEquals(menteeName, "December");
            
            String campaignName=acceptVideoRequest.personalizedVideoCampaignName().first().textContent().trim();
            assertEquals(campaignName, "December Automation");
            
            String highlightText=acceptVideoRequest.videoServiceHighlight().first().textContent().trim();
            assertEquals(highlightText, "dxgfchvjbng vbnm");
            
            
            String amountValidation=acceptRequest.personalizedVideoAmount().textContent().trim();
            assertEquals(amountValidation, "₹ 6,500");
 
            
            
            
            
            
            
            
            
            
                        
                        
            String contentType=acceptRequest.typeOfContent().first().textContent().trim();
            assertEquals(contentType, "Type:Birthday Wishes");
            
            
            String videoDurationValidation= acceptRequest.videoDurationAndTime().textContent().trim();
            assertEquals(videoDurationValidation, "5 Mins | 3 Days"); 
            
            page.locator("//span[text()='View Content']").first().click();
            String actualContent=acceptRequest.modalContent().textContent();
            assertEquals(actualContent, Description);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            test.pass("✔ Personalized Video Acceptance validated successfully.");
        }
    }
}
