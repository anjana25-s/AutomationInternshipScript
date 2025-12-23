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

    private final String BASE_URL = "https://stage.promilo.com/";
    private final String OTP = "9999";
    private final String INTERNSHIP = "Tester 1";

    @BeforeMethod(alwaysRun = true)
    public void init() {

        home   = new HomepagePage(page);
        apply  = new ApplyNowPage(page);
        login  = new LoginUtility(page);
        helper = new HelperUtility(page);

        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Preference Popup");
        }
    }

    @Test
    public void verifyApplyNowViaLogin() {

        // ---------- LOGIN ----------
        helper.log("Logging in using saved signup account");
        login.loginWithSavedAccount();
        Assert.assertTrue(
                login.isLoginSuccessful(),
                "❌ Login failed — no success indicator found"
        );


        // ---------- OPEN INTERNSHIPS ----------
        helper.waitForVisible(home.getInternshipsTab(), "Internships Tab");
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        // ---------- INTERNSHIP LIST ----------
        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        // ---------- APPLY NOW ----------
        helper.waitForVisible(apply.getApplyNowButton(), "Apply Now Button");
        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");

        // ---------- OTP (OPTIONAL FOR LOGIN FLOW) ----------
        if (apply.getOtpInputField(1).isVisible()) {

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
        helper.waitForVisible(
                apply.getLanguageCard("English"),
                "Language Selection"
        );
        helper.safeClick(
                apply.getLanguageCard("English"),
                "Select English"
        );

        // ---------- CALENDAR ----------
        helper.safeClick(
                apply.getFirstActiveDate(),
                "Select Date"
        );
        helper.safeClick(
                apply.getFirstActiveTimeSlot(),
                "Select Time"
        );

        // ---------- SCREENING (OPTIONAL) ----------
        if (apply.getNextButton().isVisible()) {

            helper.safeClick(
                    apply.getNextButton(),
                    "Go To Screening"
            );

            Locator questions = apply.getScreeningQuestions();
            for (int i = 0; i < questions.count(); i++) {

                Locator q = questions.nth(i);

                if (q.locator("input[type='checkbox'], input[type='radio']")
                        .count() > 0) {
                    helper.safeClick(
                            q.locator("input").first(),
                            "Select Option"
                    );
                }

                if (q.locator("textarea").count() > 0) {
                    helper.safeFill(
                            q.locator("textarea").first(),
                            "Automated Answer",
                            "Answer"
                    );
                }
            }

            helper.safeClick(
                    apply.getScreeningSubmitButton(),
                    "Submit Screening"
            );

        } else {
            helper.safeClick(
                    apply.getCalendarSubmitButton(),
                    "Submit Calendar"
            );
        }

        // ---------- THANK YOU ----------
        helper.waitForVisible(
                apply.getThankYouHeader(),
                "Thank You Popup"
        );
        helper.safeClick(
                apply.getThankYouMyInterestLink(),
                "Go To My Interest"
        );

        page.waitForURL("**/myinterest**");
        Assert.assertTrue(
                page.url().contains("myinterest"),
                "❌ My Interest page not opened"
        );

        helper.log("🎉 APPLY NOW LOGIN FLOW PASSED");
    }
}
