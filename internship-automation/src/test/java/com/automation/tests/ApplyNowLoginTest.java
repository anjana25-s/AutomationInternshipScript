package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.automation.utils.LoginUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ApplyNowLoginTest extends BaseClass {

    private HomepagePage home;
    private ApplyNowPage apply;
    private LoginUtility login;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String OTP = "9999";
    private static final String INTERNSHIP_NAME = "Tester 2";

    // ================= INIT =================
    @BeforeMethod(alwaysRun = true)
    public void init() {

        home   = new HomepagePage(page);
        apply  = new ApplyNowPage(page);
        login  = new LoginUtility(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    // ================= TEST =================
    @Test
    public void verifyApplyNowViaLogin() {

        try {

            helper.step("LOGIN USING SAVED SIGNUP ACCOUNT");

            // ---------- LOGIN ----------
            login.loginUsingSignupAccount();

            Assert.assertTrue(
                    home.getInternshipsTab().isVisible(),
                    "❌ Login failed — Internships tab not visible"
            );

            // ---------- OPEN INTERNSHIPS ----------
            helper.step("OPEN INTERNSHIPS");
            helper.waitForVisible(home.getInternshipsTab(), "Internships Tab");
            helper.safeClick(home.getInternshipsTab(), "Internships");

            // ---------- OPEN INTERNSHIP CARD ----------
            helper.step("OPEN INTERNSHIP CARD");
            Locator internshipCard = home.getInternshipCard(INTERNSHIP_NAME);
            helper.waitForVisible(internshipCard, "Internship Card");
            helper.scrollAndClick(internshipCard, "Internship Card");

            // ---------- APPLY NOW ----------
            helper.step("CLICK APPLY NOW");
            helper.waitForVisible(apply.getApplyNowButton(), "Apply Now Button");
            helper.safeClick(apply.getApplyNowButton(), "Apply Now");

            // ---------- OTP (OPTIONAL) ----------
            helper.step("HANDLE OTP IF PRESENT");
            if (apply.getOtpInputField(1).count() > 0 &&
                apply.getOtpInputField(1).isVisible()) {

                for (int i = 1; i <= 4; i++) {
                    apply.getOtpInputField(i)
                            .fill(String.valueOf(OTP.charAt(i - 1)));
                }

                helper.safeClick(
                        apply.getVerifyAndProceedButton(),
                        "Verify OTP"
                );
            }

            // ---------- LANGUAGE ----------
            helper.step("SELECT LANGUAGE");
            helper.waitForVisible(
                    apply.getLanguageCard("English"),
                    "Language Card"
            );
            helper.safeClick(
                    apply.getLanguageCard("English"),
                    "English"
            );

            // ---------- DATE & TIME ----------
            helper.step("SELECT DATE & TIME");
            helper.safeClick(
                    apply.getFirstActiveDate(),
                    "Select Date"
            );
            helper.safeClick(
                    apply.getFirstActiveTimeSlot(),
                    "Select Time"
            );

            // ---------- SCREENING (OPTIONAL) ----------
            helper.step("HANDLE SCREENING IF PRESENT");
            if (apply.getNextButton().isVisible()) {

                helper.safeClick(
                        apply.getNextButton(),
                        "Next"
                );

                Locator questions = apply.getScreeningQuestions();
                for (int i = 0; i < questions.count(); i++) {

                    Locator q = questions.nth(i);

                    if (q.locator("input").count() > 0) {
                        helper.safeClick(
                                q.locator("input").first(),
                                "Select Option"
                        );
                    }

                    if (q.locator("textarea").count() > 0) {
                        helper.safeFill(
                                q.locator("textarea").first(),
                                "Automated Answer",
                                "Screening Answer"
                        );
                    }
                }

                helper.safeClick(
                        apply.getScreeningSubmitButton(),
                        "Submit Screening"
                );

            } else if (apply.getCalendarSubmitButton().isVisible()) {

                helper.safeClick(
                        apply.getCalendarSubmitButton(),
                        "Submit Calendar"
                );
            }

            // ---------- THANK YOU ----------
            helper.step("VERIFY THANK YOU & NAVIGATE TO MY INTEREST");
            helper.waitForVisible(
                    apply.getThankYouHeader(),
                    "Thank You Header"
            );

            helper.safeClick(
                    apply.getThankYouMyInterestLink(),
                    "My Interest"
            );

            page.waitForURL("**/myinterest**");

            Assert.assertTrue(
                    page.url().contains("myinterest"),
                    "❌ My Interest page not opened"
            );

            helper.pass("🎉 APPLY NOW VIA LOGIN FLOW PASSED");

        } catch (Exception e) {
            helper.fail("❌ APPLY NOW VIA LOGIN FLOW FAILED");
            throw e;
        }
    }
}

