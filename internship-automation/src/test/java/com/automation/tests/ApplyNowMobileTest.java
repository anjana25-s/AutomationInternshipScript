package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.HomepagePage;
import com.automation.pages.SignUpPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.*;

public class ApplyNowMobileTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private ApplyNowPage apply;
    private HelperUtility helper;

    private final String BASE_URL = "https://stage.promilo.com/";
    private final String OTP = "9999";
    private final String PASSWORD = "Test@123";
    private final String INTERNSHIP = "Designer1";

    @BeforeClass
    public void initPages() {
        home = new HomepagePage(page);
        signup = new SignUpPage(page);
        apply = new ApplyNowPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openBase() {
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void verifyApplyNowViaMobile() {

        // ---------- Test Data ----------
        String name = helper.generateRandomName();
        String mobile = helper.generateRandomPhone();
        String email = helper.generateEmailFromName(name);

        helper.log("[Generated Name] " + name);
        helper.log("[Generated Mobile] " + mobile);
        helper.log("[Generated Email] " + email);

        // ---------- SIGNUP USING MOBILE ----------
        helper.safeClick(signup.getInitialSignupButton(), "Click SignUp");
        helper.safeFill(signup.getEmailOrPhoneInput(), mobile, "Enter Mobile Number");
        helper.safeClick(signup.getSendVerificationCodeButton(), "Send Verification Code");

        helper.safeFill(signup.getOtpInput(), OTP, "Enter OTP");
        helper.safeFill(signup.getPasswordInput(), PASSWORD, "Enter Password");
        helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

        // ---------- INTERNSHIP ----------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ---------- APPLY NOW POPUP ----------
        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");

        // name: empty → fill
        helper.safeFill(apply.getNameField(), name, "Fill Name");

        // mobile: already prefilled → skip, OR assert that it is prefilled
        Assert.assertFalse(apply.getPhoneField().inputValue().isEmpty(), "❌ Mobile number should be prefilled!");
        helper.log("✔ Mobile number is prefilled.");

        // email is empty (only in mobile) → fill
        if (apply.getEmailField().isEnabled()) {
            helper.safeFill(apply.getEmailField(), email, "Fill Email");
        }

        // ---------- SELECT INDUSTRIES ----------
        helper.safeClick(apply.getIndustryDropdown(), "Open Industry");

        Locator boxes = apply.getAllIndustryCheckboxes();
        int total = boxes.count();
        Assert.assertTrue(total >= 3, "❌ Less than 3 industry checkboxes!");

        helper.safeClick(boxes.nth(1), "Select Checkbox 1");
        helper.safeClick(boxes.nth(3), "Select Checkbox 2");
        helper.safeClick(boxes.nth(5), "Select Checkbox 3");

        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        // ---------- MOBILE → NO OTP ----------
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Apply Now");

        // ---------- CALENDAR ----------
        helper.safeClick(apply.getLanguageCard("English"), "Select English");
        helper.safeClick(apply.getFirstActiveDate(), "Select Date");
        helper.safeClick(apply.getFirstActiveTimeSlot(), "Select Time");

        // ---------- SCREENING FLOW ----------
        if (apply.getNextButton().isVisible()) {

            helper.safeClick(apply.getNextButton(), "Go to Screening");

            Locator questions = apply.getScreeningQuestions();
            int count = questions.count();

            helper.log("Total Screening Questions = " + count);

            for (int i = 0; i < count; i++) {

                Locator q = questions.nth(i);

                Locator options = q.locator("input[type='checkbox'], input[type='radio']");
                if (options.count() > 0) {
                    helper.safeClick(options.first(), "Select Objective Option");
                }

                Locator textArea = q.locator("textarea");
                if (textArea.count() > 0) {
                    helper.safeFill(textArea.first(), "Automated Answer", "Fill Subjective Answer");
                }
            }

            helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");

        } else {
            helper.safeClick(apply.getCalendarSubmitButton(), "Submit Calendar");
        }

        // ---------- ASSERT THANK YOU POPUP ----------
        helper.waitForVisible(apply.getThankYouHeader(), "Thank You Popup");
        Assert.assertTrue(apply.getThankYouHeader().isVisible(), "❌ Thank You popup NOT visible!");

        helper.log("✔ Thank You popup verified!");

        // ---------- CLICK MY INTEREST ----------
        helper.safeClick(apply.getThankYouMyInterestLink(), "Click My Interest");

        page.waitForURL("**/myinterest**");
        Assert.assertTrue(page.url().contains("myinterest"), "❌ My Interest NOT opened!");

        helper.log("✔ Navigated to My Interest");

        // ---------- ASSERT MY INTEREST CARD ----------
        Locator interestCard = page.locator("//div[contains(@class,'my-interest-card-contianer')]").first();
        helper.waitForVisible(interestCard, "My Interest Card");
        Assert.assertTrue(interestCard.isVisible(), "❌ My Interest Card NOT visible!");

        helper.log("✔ My Interest Card Visible");

        // Status
        Locator status = interestCard.locator(".my-interest-status-tag");
        Assert.assertEquals(status.innerText().trim(), "Pending", "❌ Status incorrect!");

        helper.log("✔ Status = Pending");

        // Meeting Date
        String date = interestCard.locator("(//div[@class='card_detail-value'])[1]").innerText().trim();
        Assert.assertFalse(date.isEmpty(), "❌ Meeting Date is empty!");

        // Meeting Time
        String time = interestCard.locator("(//div[@class='card_detail-value'])[2]").innerText().trim();
        Assert.assertFalse(time.isEmpty(), "❌ Meeting Time is empty!");

        helper.log("✔ Meeting Date & Time Validated");
        helper.log("🎉 APPLY NOW MOBILE FLOW PASSED!");
    }
}


