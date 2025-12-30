package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
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

public class MentorshipAskQueryCancelFunctionality extends BaseClass {

    private static final Logger log = LogManager.getLogger(MentorshipAskQueryCancelFunctionality.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship Ask Query Cancel Functionality Test =====");

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
           

        

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        if (mayBeLaterPopUp.getPopup().isVisible()) {
            mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
            log.info("Closed landing page popup");
        }
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
        page.waitForTimeout(12000);

        // -------------------- Mentor Description --------------------
        DescriptionPage descriptionValidation = new DescriptionPage(page);
        page.waitForTimeout(3000);
        descriptionValidation.askQuery().click();
        page.waitForTimeout(2000);

        descriptionValidation.askYourQuery().nth(1).click();
        page.waitForTimeout(2000);

        
        
        // -------------------- Ask Query Form --------------------
        MentorshipFormComponents askQuery = new MentorshipFormComponents(page);
        askQuery.name().nth(1).fill(name);
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String randomEmail = "Automation" + new Random().nextInt(99999) + "@qtvjnqv9.mailosaur.net";
        BaseClass.generatedEmail = randomEmail;
        BaseClass.generatedPhone = randomPhone;

        System.out.println("Generated Email: " + randomEmail);
        System.out.println("Generated Phone: " + randomPhone);


        askQuery.MobileTextField().fill(randomPhone);
        askQuery.emailTextfield().fill(randomEmail);
        page.locator("//button[normalize-space()='Ask Your Query']").nth(2)
            .click(new Locator.ClickOptions().setForce(true));

     // -------------------- ENTER OTP --------------------
        if (otp.length() != 4)
            throw new RuntimeException("Invalid OTP in Excel!");

        for (int j = 0; j < 4; j++) {
            String digit = otp.substring(j, j + 1);
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(8000)
                    .setState(WaitForSelectorState.VISIBLE));
            otpField.fill(digit);
        }// -------------------- ENTER OTP --------------------
        if (otp.length() != 4)
            throw new RuntimeException("Invalid OTP in Excel!");

        for (int j = 0; j < 4; j++) {
            String digit = otp.substring(j, j + 1);
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(8000)
                    .setState(WaitForSelectorState.VISIBLE));
            otpField.fill(digit);
        }

        page.locator("//button[normalize-space()='Verify & Proceed']").click();

        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Type your questions here..."))
            .fill("Type your questions here");

        askQuery.nextButton().click();

        // -------------------- Invoice Form --------------------
        askQuery.InvoiceNameField().fill(invoiceName);
        askQuery.StreetAdress1().fill(street1);
        askQuery.StreetAdress2().fill(street2);
        askQuery.pinCode().fill(pincode);
        askQuery.yesRadrioBox().click();
        askQuery.gstNumber().fill(gst);
        askQuery.panNumber().fill(pan);
        page.getByRole(AriaRole.CHECKBOX, 
            new Page.GetByRoleOptions().setName("By checking this box, I")).check();
        page.getByRole(AriaRole.BUTTON, 
            new Page.GetByRoleOptions().setName("Save")).click();

        // -------------------- Payment --------------------
        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
        paymentFunctionality.payOnline().click();
        paymentFunctionality.payButton().click();

        page.waitForTimeout(5000);

        // Switch to iframe for payment
        FrameLocator frame = page.frameLocator("iframe");
        frame.getByTestId("contactNumber").fill(contactNumber);

        frame.getByTestId("nav-sidebar")
            .locator("div")
            .filter(new Locator.FilterOptions().setHasText("Wallet"))
            .nth(2)
            .click();

        page.waitForTimeout(3000);

        // Handle new popup (PhonePe page)
        Page phonePePage = page.waitForPopup(() -> {
            frame.getByTestId("screen-container")
                .locator("div")
                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                .nth(2)
                .click();
        });

        phonePePage.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Success"))
            .click();

        page.waitForTimeout(8000);

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
        
        

        page.getByText("Thank you for registering and").click();
        page.getByText("Easy Access to Your").click();

        // Click My Interest
        page.getByRole(AriaRole.DIALOG)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
            .click();

     // --------------------  Card Validation --------------------
        MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
        page.waitForTimeout(5000);
        assertEquals(myintrest.askQueryMentorName().innerText().trim(), "December Automation");
        assertEquals(myintrest.askQueryMentorData().innerText().trim(), "dxgfchvjbng vbnm");
        assertEquals(myintrest.askQueryDuration().innerText().trim(), "22 Days");
        assertEquals(myintrest.askQueryServiceName().innerText().trim(), "Ask Query");
           }
    }
}
