package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.ApplyNowExpectedTexts;
import com.automation.constants.AdvertiserPageExpectedTexts;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RescheduleFunctionalityAcceptTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private ApplyNowPage apply;
    private MyInterestPage myInterest;
    private RescheduleFunctionalityPage reschedule;
    private HelperUtility helper;

    private static final String ADVERTISER_URL = "https://stagebusiness.promilo.com/login";

    private static final String OTP = "9999";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP_NAME = "Tester 1";

    private static final String ADVERTISER_EMAIL = "nidhiadvemailtesting@yopmail.com";
    private static final String ADVERTISER_PASSWORD = "promilo@123";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ================= INIT =================
    @BeforeMethod(alwaysRun = true)
    public void init() {

        home = new HomepagePage(page);
        signup = new SignUpPage(page);
        apply = new ApplyNowPage(page);
        myInterest = new MyInterestPage(page);
        reschedule = new RescheduleFunctionalityPage(page);
        helper = new HelperUtility(page);
        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    // ================= TEST =================
    @Test
    public void verifyApplyNow_Reschedule_AdvertiserAccept_FullFlow() {

        Page userPage = page;

        try {

            helper.step("START – Apply → Reschedule → Advertiser Accept");

            // ================= SIGN UP =================
            BaseClass.candidateName = helper.generateRandomName();
            String email = helper.generateEmailFromName(BaseClass.candidateName);
            String phone = helper.generateRandomPhone();

            helper.safeClick(signup.getInitialSignupButton(), "Sign Up");
            helper.safeFill(signup.getEmailOrPhoneInput(), email, "Email");
            helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");
            helper.safeFill(signup.getOtpInput(), OTP, "OTP");
            helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
            helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

            // ================= APPLY =================
            helper.safeClick(home.getInternshipsTab(), "Internships");
            helper.safeClick(home.getInternshipCard(INTERNSHIP_NAME), "Internship Card");
            helper.safeClick(apply.getApplyNowButton(), "Apply Now");

            helper.safeFill(apply.getNameField(), BaseClass.candidateName, "Name");
            helper.safeFill(apply.getPhoneField(), phone, "Phone");

            helper.safeClick(apply.getIndustryDropdown(), "Industry");
            helper.safeClick(apply.getAllIndustryCheckboxes().nth(1), "Industry Option");
            helper.safeClick(apply.getIndustryDropdown(), "Close Industry");
            helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Apply");

            // ================= OTP =================
            for (int i = 1; i <= 4; i++) {
                apply.getOtpInputField(i)
                        .fill(String.valueOf(OTP.charAt(i - 1)));
            }
            helper.safeClick(apply.getVerifyAndProceedButton(), "Verify OTP");

            // ================= DATE & TIME =================
            Locator date = apply.getFirstActiveDate();
            helper.safeClick(date, "Select Date");

            int day = Integer.parseInt(date.innerText().trim().split("\\s+")[0]);
            LocalDate selectedDate = LocalDate.now().withDayOfMonth(day);
            if (selectedDate.isBefore(LocalDate.now())) {
                selectedDate = selectedDate.plusMonths(1);
            }

            BaseClass.selectedDate = selectedDate.format(DATE_FORMAT);

            Locator time = apply.getFirstActiveTimeSlot();
            helper.safeClick(time, "Select Time");
            BaseClass.selectedTime = helper.normalizeTime(time.innerText());

            // ================= SCREENING =================
            if (apply.getScreeningQuestions().count() > 0) {
                helper.safeClick(apply.getNextButton(), "Next");

                for (Locator q : apply.getScreeningQuestions().all()) {
                    if (q.locator("input").count() > 0) {
                        helper.safeClick(q.locator("input").first(), "Option");
                    }
                    if (q.locator("textarea").count() > 0) {
                        helper.safeFill(
                                q.locator("textarea").first(),
                                "Automated Answer",
                                "Screening"
                        );
                    }
                }
                helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");
            }

            // ================= THANK YOU =================
            helper.assertEquals(
                    apply.getThankYouHeader().innerText().trim(),
                    ApplyNowExpectedTexts.THANK_YOU_HEADER,
                    "Thank You header"
            );

            helper.safeClick(apply.getThankYouMyInterestLink(), "My Interest");

            // ================= MY INTEREST – INITIAL =================
            myInterest.open();

            helper.assertEquals(
                    myInterest.getMeetingDate(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.selectedDate,
                    "Initial meeting date"
            );

            // ================= RESCHEDULE =================
            helper.step("Candidate Reschedules Meeting");

            helper.safeClick(
                    myInterest.getCalendarIcon(INTERNSHIP_NAME),
                    "Reschedule Icon"
            );

            Locator rescheduleDate = reschedule.getFirstActiveDate();
            rescheduleDate.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            String rawDateLabel = rescheduleDate.getAttribute("aria-label");
            LocalDate parsedDate =
                    LocalDate.parse(rawDateLabel, DateTimeFormatter.ofPattern("MMMM d, yyyy"));

            BaseClass.rescheduledDate = parsedDate.format(DATE_FORMAT);

            page.evaluate("el => el.click()", rescheduleDate.elementHandle());

            Locator rescheduleTime = reschedule.getFirstAvailableTimeSlot();
            rescheduleTime.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            BaseClass.rescheduledTime =
                    helper.normalizeTime(rescheduleTime.innerText());

            helper.safeClick(rescheduleTime, "Reschedule Time");
            helper.safeClick(reschedule.getContinueButton(), "Confirm Reschedule");

            // ================= ADVERTISER =================
            helper.step("Advertiser Accepts Reschedule");

            Page advertiserPage = context.newPage();
            advertiserPage.navigate(ADVERTISER_URL);
            advertiserPage.waitForLoadState();

            AdvertiserPage advertiser = new AdvertiserPage(advertiserPage);

            helper.safeFill(advertiser.getEmailInput(), ADVERTISER_EMAIL, "Email");
            helper.safeFill(advertiser.getPasswordInput(), ADVERTISER_PASSWORD, "Password");
            helper.safeClick(advertiser.getSignInButton(), "Sign In");

            advertiserPage.waitForLoadState();

            helper.safeClick(advertiser.getMyAccountTab(), "My Account");
            helper.safeClick(advertiser.getMyProspectTab(), "My Prospect");
            helper.safeClick(advertiser.getInternshipsLink(), "Internships");

             helper.safeClick(advertiser.getRescheduleRequest(), "Open Reschedule");

            advertiser.waitForReschedulePopup();

            String slotText = advertiser.getRescheduledSlotText().innerText().trim();

            helper.assertEquals(
                    slotText.split(" ")[0],
                    BaseClass.rescheduledDate,
                    "Advertiser reschedule date"
            );

            helper.safeClick(advertiser.getAcceptRequestButton(), "Accept");

            helper.assertEquals(
                    advertiser.getPopupMessage().innerText().trim(),
                    AdvertiserPageExpectedTexts.RESCHEDULE_ACCEPT_SUCCESS_MESSAGE,
                    "Accept success popup"
            );

            helper.safeClick(advertiser.getDoneButton(), "Done");

            // ================= BACK TO USER =================
            userPage.bringToFront();
            userPage.reload();
            myInterest.open();

            helper.assertEquals(
                    myInterest.getMeetingDate(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.rescheduledDate,
                    "User updated date"
            );

            helper.pass("🎉 RESCHEDULE → ADVERTISER ACCEPT FLOW PASSED");
        
        } catch (Exception e) {
            helper.fail("❌ RESCHEDULE ACCEPT FLOW FAILED");
            throw e;
        }
    }
}



