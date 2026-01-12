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

public class ApplyNowMobileTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private ApplyNowPage apply;
    private MyInterestPage myInterest;
    private MyMeetingsPage myMeetings;
    private HelperUtility helper;

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
        myMeetings = new MyMeetingsPage(page);
        helper = new HelperUtility(page);

        

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }
    // ================= TEST =================
    @Test
    public void verifyApplyNow_Mobile_FullEndToEndValidation() {

        Page userPage = page;

        try {

            helper.step("START – Apply Now Mobile → Advertiser → My Interest → Prospect → My Meetings");

            // ================= SIGN UP (MOBILE) =================
            BaseClass.candidateName = helper.generateRandomName();
            String mobile = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(BaseClass.candidateName);

            helper.safeClick(signup.getInitialSignupButton(), "Sign Up");
            helper.safeFill(signup.getEmailOrPhoneInput(), mobile, "Mobile");
            helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");
            helper.safeFill(signup.getOtpInput(), OTP, "OTP");
            helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
            helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

            // ================= INTERNSHIP =================
            helper.safeClick(home.getInternshipsTab(), "Internships");
            helper.safeClick(home.getInternshipCard(INTERNSHIP_NAME), "Internship Card");

            BaseClass.companyName =
                    home.getCompanyNameOnDescription().innerText().trim();
            BaseClass.location =
                    home.getLocationOnDescription().innerText().trim();

            helper.safeClick(apply.getApplyNowButton(), "Apply Now");

            // ================= APPLY POPUP =================
            helper.assertTrue(
                    apply.getApplyHeader().innerText()
                            .startsWith(ApplyNowExpectedTexts.APPLY_HEADER_PREFIX),
                    "Apply header prefix"
            );
         

            // Capture company name from Apply popup
            String applyPopupCompanyName =
                    apply.getApplyHeaderCompanyName().innerText().trim();

            // Validate company name matches internship description
            helper.assertEquals(
                    applyPopupCompanyName,
                    BaseClass.companyName,
                    "Apply popup company name"
            );


            helper.assertEquals(
                    apply.getWhyRegisterHeader().innerText().trim(),
                    ApplyNowExpectedTexts.WHY_REGISTER_HEADER,
                    "Why Register header"
            );

            for (int i = 0; i < ApplyNowExpectedTexts.WHY_REGISTER_POINTS.length; i++) {
                helper.assertEquals(
                        apply.getWhyRegisterBullets().nth(i).innerText().trim(),
                        ApplyNowExpectedTexts.WHY_REGISTER_POINTS[i],
                        "Why Register bullet " + (i + 1)
                );
            }

            helper.assertEquals(
                    apply.getWhatsappLabel().innerText().trim(),
                    ApplyNowExpectedTexts.WHATSAPP_LABEL,
                    "Whatsapp label"
            );

            apply.getNameField().fill(BaseClass.candidateName);

            helper.assertTrue(
                    !apply.getPhoneField().inputValue().isEmpty(),
                    "Mobile prefilled"
            );

            if (apply.getEmailField().isEnabled()) {
                apply.getEmailField().fill(email);
            }

            helper.safeClick(apply.getIndustryDropdown(), "Industry");
            helper.safeClick(apply.getAllIndustryCheckboxes().nth(1), "Industry Option");
            helper.safeClick(apply.getIndustryDropdown(), "Close Industry");
            helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Apply");

            // ================= VIDEO INTERVIEW =================
            helper.safeClick(apply.getLanguageCard("English"), "Language");

            helper.assertEquals(
                    helper.normalizeUiText(apply.getVideoInterviewTitle().innerText()),
                    ApplyNowExpectedTexts.VIDEO_INTERVIEW_TITLE,
                    "Video Interview title"
            );

            helper.assertEquals(
                    helper.normalizeUiText(apply.getVideoInterviewDesc().innerText()),
                    ApplyNowExpectedTexts.VIDEO_INTERVIEW_DESC,
                    "Video Interview description"
            );

            // ================= DATE =================
            Locator date = apply.getFirstActiveDate();
            helper.safeClick(date, "Select Date");

            int day = Integer.parseInt(date.innerText().trim().split("\\s+")[0]);
            LocalDate selectedDate = LocalDate.now().withDayOfMonth(day);
            if (selectedDate.isBefore(LocalDate.now())) {
                selectedDate = selectedDate.plusMonths(1);
            }
            BaseClass.selectedDate = selectedDate.format(DATE_FORMAT);

            // ================= TIME =================
            Locator time = apply.getFirstActiveTimeSlot();
            helper.safeClick(time, "Select Time");
            BaseClass.selectedTime = helper.normalizeTime(time.innerText());

            // ================= SCREENING =================
            if (apply.getScreeningQuestions().count() > 0) {

                helper.safeClick(apply.getNextButton(), "Next");

                for (Locator q : apply.getScreeningQuestions().all()) {
                    if (q.locator("input").count() > 0)
                        helper.safeClick(q.locator("input").first(), "Option");

                    if (q.locator("textarea").count() > 0)
                        helper.safeFill(q.locator("textarea").first(),
                                "Automated Answer", "Screening");
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

            // ================= MY INTEREST CARD =================
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
                    helper.normalizeTime(
                            myInterest.getMeetingTime(INTERNSHIP_NAME).innerText()
                    ),
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

            // ================= ADVERTISER – PROSPECT =================
            Page advertiserPage = context.newPage();
            advertiserPage.navigate(ADVERTISER_URL);

            AdvertiserPage advertiser = new AdvertiserPage(advertiserPage);
            AdvertiserProspectCardPage prospect =
                    new AdvertiserProspectCardPage(advertiserPage);

            helper.safeFill(advertiser.getEmailInput(), ADVERTISER_EMAIL, "Advertiser Email");
            helper.safeFill(advertiser.getPasswordInput(), ADVERTISER_PASSWORD, "Advertiser Password");
            helper.safeClick(advertiser.getSignInButton(), "Sign In");

            helper.safeClick(advertiser.getMyAccountTab(), "My Account");
            helper.safeClick(advertiser.getMyProspectTab(), "My Prospect");
            helper.safeClick(advertiser.getInternshipsLink(), "Internships");

            helper.step("Validate Advertiser Prospect Card");

            prospect.waitForProspectCard(BaseClass.candidateName);

            helper.assertEquals(
                    prospect.getMeetingStatus(BaseClass.candidateName).innerText().trim(),
                    "Pending",
                    "Meeting status Pending"
            );

            helper.assertEquals(
                    helper.normalizeAdvertiserDate(
                            prospect.getMeetingDate(BaseClass.candidateName).innerText()
                    ),
                    BaseClass.selectedDate,
                    "Meeting Date"
            );

            helper.assertEquals(
                    helper.normalizeTime(
                            prospect.getMeetingTime(BaseClass.candidateName).innerText()
                    ),
                    BaseClass.selectedTime,
                    "Meeting Time"
            );

         // ================= EMAIL VERIFICATION (AUTO-DETECT) =================

            if (prospect.isEmailVerified(BaseClass.candidateName).isVisible()) {
                helper.log("✅ Email is VERIFIED");
            } else if (prospect.isEmailNotVerified(BaseClass.candidateName).isVisible()) {
                helper.log("ℹ️ Email is NOT VERIFIED");
            } else {
                helper.fail("❌ Email verification status not found");
            }

            if (prospect.isPhoneVerified(BaseClass.candidateName).isVisible()) {
                helper.log("Phone is verified");
            } else {
                helper.log("Phone is NOT verified");
            }

            // ================= APPROVE =================
            helper.safeClick(advertiser.getApproveButtonOnCard(), "Approve");

            // ================= MY MEETINGS =================
            userPage.bringToFront();
            userPage.reload();

            helper.safeClick(myMeetings.getMyMeetingsTab(), "My Meetings");

            helper.step("Validate My Meetings Card");

            helper.assertEquals(
                    myMeetings.getStatusTag(INTERNSHIP_NAME).innerText().trim(),
                    "Accepted",
                    "Meeting Accepted"
            );

            helper.assertEquals(
                    myMeetings.getMeetingDate(INTERNSHIP_NAME).innerText().trim(),
                    BaseClass.selectedDate,
                    "Meeting Date"
            );

            helper.assertEquals(
                    helper.normalizeTime(
                            myMeetings.getMeetingTime(INTERNSHIP_NAME).innerText()
                    ),
                    BaseClass.selectedTime,
                    "Meeting Time"
            );

            helper.assertEquals(
                    myMeetings.getInterviewType(INTERNSHIP_NAME).innerText().trim(),
                    "Online Interview",
                    "Interview Type"
            );

            helper.assertTrue(
                    myMeetings.getCancelButton(INTERNSHIP_NAME).isVisible(),
                    "Cancel button visible"
            );

            helper.assertTrue(
                    myMeetings.getJoinNowButton(INTERNSHIP_NAME).isDisabled(),
                    "Join Now disabled"
            );

            helper.pass("🎉 FULL APPLY NOW MOBILE FLOW PASSED");

        } catch (Exception e) {
            helper.fail("❌ FULL APPLY NOW MOBILE FLOW FAILED");
            throw e;
        }
    }
}


