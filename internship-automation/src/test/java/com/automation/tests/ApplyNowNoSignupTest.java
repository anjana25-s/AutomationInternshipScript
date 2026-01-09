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

public class ApplyNowNoSignupTest extends BaseClass {

    private HomepagePage home;
    private ApplyNowPage apply;
    private MyInterestPage myInterest;
    private HelperUtility helper;

    // ================= TEST DATA =================
    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String ADVERTISER_URL = "https://stagebusiness.promilo.com/login";

    private static final String INTERNSHIP_NAME = "Tester 1";
    private static final String OTP = "9999";

    private static final String ADVERTISER_EMAIL = "nidhiadvemailtesting@yopmail.com";
    private static final String ADVERTISER_PASSWORD = "promilo@123";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ================= INIT =================
    @BeforeMethod(alwaysRun = true)
    public void init() {

        home = new HomepagePage(page);
        apply = new ApplyNowPage(page);
        myInterest = new MyInterestPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
        }

    // ================= TEST =================
    @Test
    public void verifyApplyNow_NoSignup_AdvertiserReject_FullValidation() {

        Page userPage = page;

        try {

            helper.step("START – Apply Now (No Signup) → Prospect → Advertiser Reject");

            // ================= TEST DATA =================
            BaseClass.candidateName = helper.generateRandomName();
            String phone = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(BaseClass.candidateName);

            // ================= APPLY NOW =================
            helper.safeClick(home.getInternshipsTab(), "Internships");
            helper.safeClick(home.getInternshipCard(INTERNSHIP_NAME), "Internship Card");
            BaseClass.companyName =
                    home.getCompanyNameOnDescription().innerText().trim();
            BaseClass.location =
                    home.getLocationOnDescription().innerText().trim();

            helper.safeClick(apply.getApplyNowButton(), "Apply Now");

            // ================= APPLY POPUP VALIDATIONS =================
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
                    "WhatsApp label"
            );

            // ================= APPLY FORM =================
            helper.safeFill(apply.getNameField(), BaseClass.candidateName, "Name");
            helper.safeFill(apply.getPhoneField(), phone, "Phone");
            helper.safeFill(apply.getEmailField(), email, "Email");

            helper.safeClick(apply.getIndustryDropdown(), "Industry");
            helper.safeClick(apply.getAllIndustryCheckboxes().nth(1), "Industry Option");
            helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

            helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Apply");

            // ================= OTP =================
            helper.assertEquals(
                    apply.getOtpHeader().innerText().trim(),
                    ApplyNowExpectedTexts.OTP_HEADER,
                    "OTP Header"
            );

            for (int i = 1; i <= 4; i++) {
                apply.getOtpInputField(i)
                        .fill(String.valueOf(OTP.charAt(i - 1)));
            }

            helper.safeClick(apply.getVerifyAndProceedButton(), "Verify OTP");

            // ================= VIDEO INTERVIEW =================
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
                        helper.safeFill(
                                q.locator("textarea").first(),
                                "Automated Answer",
                                "Screening"
                        );
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

            // ================= MY INTEREST (FULL CARD) =================
            helper.step("Validate My Interest Card");

            helper.assertEquals(
                    myInterest.getStatusTag(INTERNSHIP_NAME).innerText().trim(),
                    "Pending",
                    "Status Pending"
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

            // ================= ADVERTISER – PROSPECT =================
            helper.step("Validate Advertiser Prospect Card (Before Reject)");

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


         // ================= REJECT =================
            helper.safeClick(advertiser.getRejectButtonOnCard(), "Reject");

            // confirm reject popup
            helper.safeClick(
                    advertiser.getRejectConfirmButton(),
                    "Confirm Reject"
            );


            // ================= BACK TO USER =================
            userPage.bringToFront();
            userPage.reload();

            myInterest.open();

            helper.assertEquals(
                    myInterest.getStatusTag(INTERNSHIP_NAME).innerText().trim(),
                    "Rejected",
                    "Final status Rejected"
            );

            helper.pass("🎉 APPLY NOW NO SIGNUP → ADVERTISER REJECT FLOW PASSED");

        } catch (Exception e) {
            helper.fail("❌ APPLY NOW NO SIGNUP FLOW FAILED");
            throw e;
        }
    }
}

