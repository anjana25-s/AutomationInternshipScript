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
    private final String INTERNSHIP = "Tester 1";

    @BeforeMethod(alwaysRun = true)
    public void init() {

        home = new HomepagePage(page);
        signup = new SignUpPage(page);
        apply = new ApplyNowPage(page);
        helper = new HelperUtility(page);

        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void verifyApplyNowViaMobile() {

        // ---------- TEST DATA ----------
        String name = helper.generateRandomName();
        String mobile = helper.generateRandomPhone();
        String email = helper.generateEmailFromName(name);

        helper.log("Generated: " + name + " | " + mobile + " | " + email);

        // ---------- SIGNUP (MOBILE) ----------
        helper.safeClick(signup.getInitialSignupButton(), "Sign Up");

        helper.safeFill(signup.getEmailOrPhoneInput(), mobile, "Enter Mobile");
        helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");

        helper.safeFill(signup.getOtpInput(), OTP, "Enter OTP");
        helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
        helper.safeClick(signup.getFinalSignupButton(), "Complete SignUp");

        helper.waitForVisible(home.getInternshipsTab(), "Internships Tab");
        Assert.assertTrue(home.getInternshipsTab().isVisible(), "Signup failed!");


        // ---------- INTERNSHIP ----------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        helper.safeClick(apply.getApplyNowButton(), "Open Apply Now");

        // ---------- APPLY FORM ----------
        helper.safeFill(apply.getNameField(), name, "Name");

        // Mobile should be prefilled
        Assert.assertFalse(
                apply.getPhoneField().inputValue().isEmpty(),
                "Mobile number should be prefilled!"
        );

        // Email should be editable in mobile flow
        if (apply.getEmailField().isEnabled()) {
            helper.safeFill(apply.getEmailField(), email, "Email");
        }

        // ---------- INDUSTRY ----------
        helper.safeClick(apply.getIndustryDropdown(), "Industry");

        Locator boxes = apply.getAllIndustryCheckboxes();
        Assert.assertTrue(boxes.count() >= 3, "Less than 3 industries!");

        helper.safeClick(boxes.nth(1), "Industry 1");
        helper.safeClick(boxes.nth(3), "Industry 2");
        helper.safeClick(boxes.nth(5), "Industry 3");

        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Apply Form");

        // ---------- LANGUAGE ----------
        helper.safeClick(apply.getLanguageCard("English"), "Select English");

        // ---------- CALENDAR ----------
        helper.safeClick(apply.getFirstActiveDate(), "Select Date");
        helper.safeClick(apply.getFirstActiveTimeSlot(), "Select Time");

        // ---------- SCREENING ----------
        if (apply.getNextButton().isVisible()) {

            helper.safeClick(apply.getNextButton(), "Go To Screening");

            Locator questions = apply.getScreeningQuestions();
            for (int i = 0; i < questions.count(); i++) {

                Locator q = questions.nth(i);

                if (q.locator("input[type='checkbox'], input[type='radio']").count() > 0) {
                    helper.safeClick(q.locator("input").first(), "Select Option");
                }

                if (q.locator("textarea").count() > 0) {
                    helper.safeFill(
                            q.locator("textarea").first(),
                            "Automated Answer",
                            "Answer"
                    );
                }
            }

            helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");

        } else {
            helper.safeClick(apply.getCalendarSubmitButton(), "Submit Calendar");
        }

        // ---------- THANK YOU ----------
        helper.waitForVisible(apply.getThankYouHeader(), "Thank You Popup");
        helper.safeClick(apply.getThankYouMyInterestLink(), "My Interest");

        page.waitForURL("**/myinterest**");
        Assert.assertTrue(page.url().contains("myinterest"), "My Interest not opened!");

        helper.log("🎉 APPLY NOW MOBILE FLOW PASSED!");
    }
}
