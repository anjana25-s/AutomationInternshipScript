package com.promilo.automation.mentorship.mentee.intrests;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipRegisterWithUS extends BaseClass{

	
	private static final Logger log = LogManager.getLogger(MentorFilterTest.class);

    @Test
    public void mentorshipRegisterWithUSTest() throws IOException {

        log.info("===== Starting Mentor Filter Test =====");
        
        
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

        // LOAD EXCEL
        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentee Interest");

        int totalRows = excel.getRowCount();
        if (totalRows < 2) {
            test.fail("❌ No data found in Excel.");
            Assert.fail("No data in Excel");
        }

        // BUILD HEADER MAP (normalize header names to avoid NPE)
        int totalCols = excel.getColumnCount();
        Map<String, Integer> headerMap = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                String key = header.trim().replace("\u00A0","").toLowerCase(); // remove non-breaking space
                headerMap.put(key, c);
            }
        }

        // Debug: print headers

        // FIND ROW WHERE KEYWORD = MentorCampaignDownload
        int matchRow = -1;
        for (int i = 1; i < totalRows; i++) {
            String keyword = excel.getCellData(i, headerMap.get("keyword"));
            if ("MentorshipRegisterWithUS".equalsIgnoreCase(keyword)) {
                matchRow = i;
                break;
            }
        }

        if (matchRow == 0) {
            test.fail("❌ No row found with keyword MentorCampaignDownload");
            Assert.fail("No keyword match");
        }

        test.info("📘 Executing with Excel Row: " + matchRow);

        // FETCH REQUIRED DATA FROM EXCEL
        String inputValue = excel.getCellData(matchRow, headerMap.get("inputvalue"));
        String password   = excel.getCellData(matchRow, headerMap.get("password"));
        String userName   = excel.getCellData(matchRow, headerMap.get("name"));
        String otp   = excel.getCellData(matchRow, headerMap.get("otp"));


        
        // -------------------- Initialize Page --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);
        page.waitForTimeout(10000);


        // -------------------- Landing Page Popup --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);
        
     // Generate random data
        String  randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String emailPrefix= "Automation";
		String mailServerId= "qtvjnqv9";
		String randomEmail = emailPrefix + new Random().nextInt(99999) + "@" + mailServerId + ".mailosaur.net";


        
        
        MeetupsListingPage registerWithUs= new MeetupsListingPage(page);
        HomePage mentorshipClick = new HomePage(page);
        mentorshipClick.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");
        page.waitForTimeout(2000);
        registerWithUs.nameField().fill(userName);
        registerWithUs.typeofMentor().click();
        registerWithUs.Registermentortype().click();
        registerWithUs.mobileNumber().fill(randomPhone);
        registerWithUs.emailTextfield().fill(randomEmail);
        registerWithUs.password().fill("Kaethik@12399");
        registerWithUs.registerButton().click();
        
        
        page.waitForTimeout(2000);
        
        
     // -------------------- ENTER OTP (EXCEL → 9999) --------------------
        if (otp.length() != 4)
            throw new RuntimeException("Invalid OTP in Excel!");

        for (int j = 0; j < 4; j++) {
            String digit = otp.substring(j, j + 1);

            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(8000)
                    .setState(WaitForSelectorState.VISIBLE));

            otpField.fill(digit);
        }


        page.locator("//button[text()='Verify & Proceed']").click();

     

       
        
        
        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");

        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        Assert.assertTrue(thankYouPopup.isVisible(),
                "'Thank You!' popup not displayed.");

        
        
        
    }
}
