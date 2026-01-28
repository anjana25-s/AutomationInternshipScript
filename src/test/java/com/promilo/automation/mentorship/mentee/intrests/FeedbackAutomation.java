package com.promilo.automation.mentorship.mentee.intrests;

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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FeedbackAutomation extends BaseClass {

    private static final Logger log = LogManager.getLogger(FeedbackAutomation.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

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

            log.info("===== Starting Data-Driven Mentorship Feedback Test =====");

            // INITIALIZE PLAYWRIGHT
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.setViewportSize(1080, 720);

            // -------------------- Close Initial Popup --------------------
         // Landing popup
            MayBeLaterPopUp later = new MayBeLaterPopUp(page);
            later.getPopup().click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(2000);

            // -------------------- Mentorship Module --------------------
            HomePage home = new HomePage(page);
            home.mentorships().click(new Locator.ClickOptions().setForce(true));

            // -------------------- Search Mentor --------------------
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");

            page.waitForTimeout(2000);
            page.waitForTimeout(15000);

            // -------------------- Feedback Form --------------------
            DescriptionPage descPage = new DescriptionPage(page);
            descPage.feedbackTextfield().fill("something");
            descPage.submitButton().click();

            // Fill User Details
            MentorshipFormComponents form = new MentorshipFormComponents(page);

            // ===== Random email & phone =====
            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String randomEmail = "Automation" + new Random().nextInt(99999) + "@qtvjnqv9.mailosaur.net";
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            System.out.println("Generated Email: " + randomEmail);
            System.out.println("Generated Phone: " + randomPhone);
            form.name().fill(mentorName);
            form.MobileTextField().fill(randomPhone);
            form.emailTextfield().fill(randomEmail);

            page.getByRole(AriaRole.DIALOG)
                    .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Submit"))
                    .click();

            // -------------------- OTP Handling --------------------
            if (otp == null || otp.length() != 4) {
                throw new RuntimeException("OTP must be exactly 4 digits.");
            }

            for (int j = 0; j < 4; j++) {
                String digit = String.valueOf(otp.charAt(j));
                Locator otpField = page.locator(
                        "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(5000));
                otpField.click();
                otpField.fill(digit);
            }

            form.verifyAndProceed().click();
            
            page.waitForTimeout(5000);
            // -------------------- Validation --------------------
            Locator thankYouPopup = page.locator(
                    "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

            Assert.assertTrue(thankYouPopup.isVisible(), "❌ 'Thank You!' message not displayed.");
            

            log.info("✔ Feedback flow passed for: " + mentorName);
        }
    }
}
