package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.ApplyNowExpectedTexts;
import com.automation.pages.*;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RescheduleFunctionalityRejectTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private ApplyNowPage apply;
    private MyInterestPage myInterest;
    private RescheduleFunctionalityPage reschedule;
    private HelperUtility helper;

    // ================= TEST DATA =================
    private static final String BASE_URL = "https://stage.promilo.com/";
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
    public void verifyApplyNow_Reschedule_AdvertiserReject_FullFlow() {

        Page userPage = page;

        try {

            helper.step("START – Apply → Reschedule → Advertiser Reject");

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
            BaseClass.companyName =
                    home.getCompanyNameOnDescription().innerText().trim();
            BaseClass.location =
                    home.getLocationOnDescription().innerText().trim();
            helper.safeClick(apply.getApplyNowButton(), "Apply Now");

            apply.getNameField().fill(BaseClass.candidateName);
            apply.getPhoneField().fill(phone);

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
            BaseClass.selectedTime =
                    time.innerText().trim().replaceFirst("^0", "");

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

            helper.step("Validate My Interest Card");

            helper.assertEquals(
                    myInterest.getStatusTag(INTERNSHIP_NAME).innerText().trim(),
                    "Pending",
                    "Status Pending"
            );

            helper.assertEquals(
                    myInterest.getInternshipTitle(INTERNSHIP_NAME).innerText().trim(),
                    INTERNSHIP_NAME,
                    "Internship title"
            );

            helper.assertEquals(
                    myInterest.getCompanyName(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.companyName,
                    "Company name"
            );

            helper.assertEquals(
                    myInterest.getLocation(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.location,
                    "Location"
            );

            helper.assertEquals(
                    myInterest.getMeetingDate(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.selectedDate,
                    "Meeting Date"
            );

            helper.assertEquals(
                    myInterest.getMeetingTime(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.selectedTime,
                    "Meeting Time"
            );

            helper.assertEquals(
                    myInterest.getInterviewMode(INTERNSHIP_NAME).innerText().trim(),
                    "Online Interview",
                    "Interview mode"
            );

            helper.assertTrue(
                    myInterest.getCalendarIcon(INTERNSHIP_NAME).isVisible(),
                    "Reschedule icon visible"
            );

            helper.assertTrue(
                    myInterest.getCancelButton(INTERNSHIP_NAME).isVisible(),
                    "Cancel button visible"
            );

            helper.assertTrue(
                    myInterest.getSendReminderButton(INTERNSHIP_NAME).isVisible(),
                    "Send Reminder button visible"
            );

            // ================= RESCHEDULE =================
            helper.step("Candidate Reschedules Meeting");

            helper.safeClick(
                    myInterest.getCalendarIcon(INTERNSHIP_NAME),
                    "Reschedule Icon"
            );

            // ----- DATE -----
            Locator rescheduleDate = reschedule.getFirstActiveDate();
            rescheduleDate.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            String rawDateLabel = rescheduleDate.getAttribute("aria-label");
            DateTimeFormatter uiFormat =
                    DateTimeFormatter.ofPattern("MMMM d, yyyy");

            BaseClass.rescheduledDate =
                    LocalDate.parse(rawDateLabel, uiFormat)
                            .format(DATE_FORMAT);

            // JS click for flatpickr
            rescheduleDate.scrollIntoViewIfNeeded();
            page.evaluate(
                    "el => el.click()",
                    rescheduleDate.elementHandle()
            );

            // ----- TIME -----
            Locator rescheduleTime = reschedule.getFirstAvailableTimeSlot();
            rescheduleTime.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            BaseClass.rescheduledTime =
                    helper.normalizeTime(rescheduleTime.innerText().trim());

            helper.safeClick(rescheduleTime, "Reschedule Time");
            helper.safeClick(reschedule.getContinueButton(), "Confirm Reschedule");

            helper.log("📅 Rescheduled Date: " + BaseClass.rescheduledDate);
            helper.log("⏰ Rescheduled Time: " + BaseClass.rescheduledTime);

            // ================= ADVERTISER =================
            helper.step("Advertiser Rejects Reschedule");

            Page advertiserPage = context.newPage();
            advertiserPage.navigate(ADVERTISER_URL);
            advertiserPage.waitForLoadState();

            AdvertiserPage advertiser = new AdvertiserPage(advertiserPage);

            helper.safeFill(advertiser.getEmailInput(), ADVERTISER_EMAIL, "Advertiser Email");
            helper.safeFill(advertiser.getPasswordInput(), ADVERTISER_PASSWORD, "Advertiser Password");
            helper.safeClick(advertiser.getSignInButton(), "Sign In");

            helper.safeClick(advertiser.getMyAccountTab(), "My Account");
            helper.safeClick(advertiser.getMyProspectTab(), "My Prospect");
            helper.safeClick(advertiser.getInternshipsLink(), "Internships");

            helper.safeClick(advertiser.getRescheduleRequest(), "Open Reschedule");

            // ===== VALIDATE POPUP SLOT =====
            advertiser.getRescheduledSlotText()
                    .waitFor(new Locator.WaitForOptions().setTimeout(15000));

            String slotText =
                    advertiser.getRescheduledSlotText().innerText().trim();
            // example: "10/01/2026 6:15 AM - 6:30 AM"

            String advDate = slotText.split(" ")[0];
            String advTime =
                    slotText.split(" ", 2)[1]
                            .split("-")[0]
                            .trim();

            helper.assertEquals(
                    advDate,
                    BaseClass.rescheduledDate,
                    "Advertiser reschedule date"
            );

            helper.assertEquals(
                    helper.normalizeTime(advTime),
                    BaseClass.rescheduledTime,
                    "Advertiser reschedule time"
            );

            // ================= REJECT =================
            helper.safeClick(advertiser.getCancelRequestButton(), "Cancel Request");
            helper.safeClick(advertiser.getRejectRescheduleButton(), "Reject Reschedule");

            // ================= BACK TO USER =================
            userPage.bringToFront();
            userPage.reload();
            myInterest.open();
            
            
          
            helper.assertEquals(
                    myInterest.getStatusTag(INTERNSHIP_NAME).innerText().trim(),
                    "Rejected",
                    "User status updated to Rejected"
            );

            helper.pass("🎉 APPLY → RESCHEDULE → ADVERTISER REJECT FLOW PASSED");

        } catch (Exception e) {
            helper.fail("❌ RESCHEDULE REJECT FLOW FAILED");
            throw e;
        }
    }
}
