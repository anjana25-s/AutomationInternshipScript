package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.*;

public class ApplyNowNoSignupTest extends BaseClass {

    private HomepagePage home;
    private ApplyNowPage apply;
    private HelperUtility helper;

    private final String BASE_URL = "https://stage.promilo.com/";
    private final String INTERNSHIP = "Designer1";
    private final String OTP = "9999";

    @BeforeClass
    public void initPages() {
        home = new HomepagePage(page);
        apply = new ApplyNowPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openBase() {
        helper.log("Navigating to " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void verifyApplyNowWithoutSignup() {

        // ---------------- TEST DATA ----------------
        String name = helper.generateRandomName();
        String phone = helper.generateRandomPhone();
        String email = helper.generateEmailFromName(name);

        helper.log("Generated Name = " + name);
        helper.log("Generated Email = " + email);
        helper.log("Generated Phone = " + phone);

        // ---------------- INTERNSHIP ----------------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");

        Assert.assertTrue(card.isVisible(), "❌ Internship card not visible!");

        helper.scrollAndClick(card, "Open Internship");

        // ---------------- APPLY NOW ----------------
        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");

        Assert.assertTrue(apply.getNameField().isVisible(), "❌ Apply Now popup did not open!");

        // Fill user details
        helper.safeFill(apply.getNameField(), name, "Name");
        helper.safeFill(apply.getPhoneField(), phone, "Phone");
        helper.safeFill(apply.getEmailField(), email, "Email");

        // ---------- SELECT CHECKBOXES ----------
        helper.safeClick(apply.getIndustryDropdown(), "Open Industry");

        Locator boxes = apply.getAllIndustryCheckboxes();
        int total = boxes.count();
        Assert.assertTrue(total >= 3, "❌ Less than 3 industry checkboxes!");

        helper.safeClick(boxes.nth(1), "Select Checkbox 1");
        helper.safeClick(boxes.nth(3), "Select Checkbox 2");
        helper.safeClick(boxes.nth(5), "Select Checkbox 3");

        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit ApplyNow Form");

        // ---------- OTP ----------
        for (int i = 1; i <= 4; i++) {
            helper.safeFill(apply.getOtpInputField(i), OTP.substring(i - 1, i), "OTP Digit " + i);
            Assert.assertEquals(apply.getOtpInputField(i).inputValue(), OTP.substring(i - 1, i),
                    "❌ OTP digit mismatch!");
        }

        helper.safeClick(apply.getVerifyAndProceedButton(), "Verify OTP");

        // ⭐⭐⭐ FIXED — WAIT FOR LANGUAGE CARD PROPERLY ⭐⭐⭐
        helper.waitForVisible(apply.getLanguageCard("English"), "Language Card");
        Assert.assertTrue(apply.getLanguageCard("English").isVisible(),
                "❌ Language selection not loaded!");

        // ---------- CALENDAR ----------
        helper.safeClick(apply.getLanguageCard("English"), "Select English");
        helper.safeClick(apply.getFirstActiveDate(), "Select Date");
        helper.safeClick(apply.getFirstActiveTimeSlot(), "Select Time");

        // ---------------- SCREENING ----------------
        if (apply.getNextButton().isVisible()) {

            helper.safeClick(apply.getNextButton(), "Go to Screening");

            Locator questions = apply.getScreeningQuestions();
            int count = questions.count();
            helper.log("Total Screening Questions = " + count);

            Assert.assertTrue(count > 0, "❌ No screening questions found!");

            for (int i = 0; i < count; i++) {

                Locator q = questions.nth(i);

                // Objective
                Locator options = q.locator("input[type='checkbox'], input[type='radio']");
                if (options.count() > 0) {
                    helper.safeClick(options.first(), "Select Objective Option");
                }

                // Subjective
                Locator textArea = q.locator("textarea");
                if (textArea.count() > 0) {
                    helper.safeFill(textArea.first(), "Automated Answer", "Subjective Answer");
                }
            }

            helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");

        } else {
            helper.safeClick(apply.getCalendarSubmitButton(), "Submit Calendar");
        }

        // ---------------- THANK YOU ----------------
        helper.waitForVisible(apply.getThankYouHeader(), "Thank You Popup");

        Assert.assertTrue(apply.getThankYouHeader().isVisible(),
                "❌ Thank You popup not visible!");

        helper.log("✔ Thank You popup verified!");

        // ---------------- CLICK MY INTEREST ----------------
        helper.safeClick(apply.getThankYouMyInterestLink(), "Click My Interest");

        page.waitForURL("**/myinterest**");
        Assert.assertTrue(page.url().contains("myinterest"), "❌ My Interest NOT opened!");

        helper.log("✔ Navigated to My Interest");

        // ---------------- ASSERT INTEREST CARD ----------------
        Locator interestCard = page.locator("//div[contains(@class,'my-interest-card-contianer')]").first();
        helper.waitForVisible(interestCard, "My Interest Card");

        Assert.assertTrue(interestCard.isVisible(), "❌ My Interest card not visible!");
        helper.log("✔ Interest Card Visible");

        // Status
        Locator status = interestCard.locator(".my-interest-status-tag");
        Assert.assertEquals(status.innerText().trim(), "Pending", "❌ Status mismatch!");

        helper.log("✔ Status = Pending");

        // Date
        String date = interestCard.locator("(//div[@class='card_detail-value'])[1]").innerText().trim();
        Assert.assertFalse(date.isEmpty(), "❌ Meeting Date empty!");

        // Time
        String time = interestCard.locator("(//div[@class='card_detail-value'])[2]").innerText().trim();
        Assert.assertFalse(time.isEmpty(), "❌ Meeting Time empty!");

        helper.log("✔ Meeting Date & Time Validated");
        helper.log("🎉 APPLY NOW — WITHOUT SIGNUP FLOW PASSED!");
    }
}




