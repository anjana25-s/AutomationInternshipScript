package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.ApplyNowExpectedTexts;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.HomepagePage;
import com.automation.pages.MyInterestPage;
import com.automation.pages.SignUpPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ApplyNowEmailTest extends BaseClass {

    private HomepagePage home;
    private SignUpPage signup;
    private ApplyNowPage apply;
    private MyInterestPage myInterest;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String OTP = "9999";
    private static final String PASSWORD = "Test@123";
    private static final String INTERNSHIP = "Tester 1";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeMethod(alwaysRun = true)
    public void init() {

        home = new HomepagePage(page);
        signup = new SignUpPage(page);
        apply = new ApplyNowPage(page);
        myInterest = new MyInterestPage(page);
        helper = new HelperUtility(page);

        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Preference Popup");
        }

        BaseClass.screeningAnswers.clear();
    }

    @Test
    public void verifyApplyNowViaEmail_fullStrictValidation() {

        // ================= TEST DATA =================
        String name = helper.generateRandomName();
        String email = helper.generateEmailFromName(name);
        String phone = helper.generateRandomPhone();

        helper.log("Name: " + name);
        helper.log("Email: " + email);
        helper.log("Phone: " + phone);

        // ================= SIGN UP =================
        helper.safeClick(signup.getInitialSignupButton(), "Sign Up");
        helper.safeFill(signup.getEmailOrPhoneInput(), email, "Email");
        helper.safeClick(signup.getSendVerificationCodeButton(), "Send OTP");
        helper.safeFill(signup.getOtpInput(), OTP, "OTP");
        helper.safeFill(signup.getPasswordInput(), PASSWORD, "Password");
        helper.safeClick(signup.getFinalSignupButton(), "Complete Signup");

        // ================= OPEN INTERNSHIP =================
        helper.safeClick(home.getInternshipsTab(), "Internships");
        helper.scrollAndClick(home.getInternshipCard(INTERNSHIP), "Internship Card");

        // ================= APPLY NOW =================
        helper.safeClick(apply.getApplyNowButton(), "Apply Now");

        // ================= APPLY POPUP TEXTS =================
        String applyHeader = apply.getApplyHeader().innerText().trim();
        helper.log("Apply Header: " + applyHeader);

        Assert.assertTrue(
                applyHeader.startsWith(ApplyNowExpectedTexts.APPLY_HEADER_PREFIX)
        );

        BaseClass.campaignName = applyHeader
                .replace(ApplyNowExpectedTexts.APPLY_HEADER_PREFIX, "")
                .replace("!", "")
                .trim();

        Assert.assertEquals(
                apply.getWhyRegisterHeader().innerText().trim(),
                ApplyNowExpectedTexts.WHY_REGISTER_HEADER
        );

        Locator bullets = apply.getWhyRegisterBullets();
        Assert.assertEquals(bullets.count(),
                ApplyNowExpectedTexts.WHY_REGISTER_POINTS.length);

        for (int i = 0; i < bullets.count(); i++) {
            String bullet = bullets.nth(i).innerText().trim();
            helper.log("Why Register Bullet " + (i + 1) + ": " + bullet);

            Assert.assertEquals(
                    bullet,
                    ApplyNowExpectedTexts.WHY_REGISTER_POINTS[i]
            );
        }

        Assert.assertEquals(
                apply.getWhatsappLabel().innerText().trim(),
                ApplyNowExpectedTexts.WHATSAPP_LABEL
        );

        // ================= APPLY FORM =================
        apply.getNameField().fill(name);
        apply.getPhoneField().fill(phone);
        Assert.assertFalse(apply.getEmailField().isEnabled());

        helper.safeClick(apply.getIndustryDropdown(), "Industry");
        Locator industries = apply.getAllIndustryCheckboxes();
        helper.safeClick(industries.nth(1), "Industry 1");
        helper.safeClick(industries.nth(3), "Industry 2");
        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit");

        // ================= OTP =================
        for (int i = 1; i <= 4; i++) {
            apply.getOtpInputField(i)
                    .fill(String.valueOf(OTP.charAt(i - 1)));
        }

        Assert.assertEquals(
                apply.getOtpThankYouText().innerText().trim(),
                ApplyNowExpectedTexts.OTP_THANK_YOU
        );

        Assert.assertEquals(
                apply.getOtpHeader().innerText().trim(),
                ApplyNowExpectedTexts.OTP_HEADER
        );

        Assert.assertTrue(
                apply.getOtpInstructionText().innerText()
                        .contains(ApplyNowExpectedTexts.OTP_INSTRUCTION)
        );

        Assert.assertEquals(
                apply.getOtpStillCantFindText().innerText().trim(),
                ApplyNowExpectedTexts.OTP_STILL_CANT_FIND
        );

        helper.safeClick(apply.getVerifyAndProceedButton(), "Verify OTP");

        // ================= VIDEO INTERVIEW =================
        Assert.assertEquals(
                helper.normalizeText(apply.getVideoInterviewTitle().innerText()),
                ApplyNowExpectedTexts.VIDEO_INTERVIEW_TITLE
        );

        Assert.assertEquals(
                helper.normalizeText(apply.getVideoInterviewDesc().innerText()),
                ApplyNowExpectedTexts.VIDEO_INTERVIEW_DESC
        );

        // ================= LANGUAGE =================
        helper.safeClick(apply.getLanguageCard("English"), "English");

        // ================= DATE =================
        Locator dateElement = apply.getFirstActiveDate();
        helper.safeClick(dateElement, "Date");

        int selectedDay = Integer.parseInt(
                dateElement.innerText().trim().split("\\s+")[0]
        );

        LocalDate today = LocalDate.now();
        LocalDate selectedDate =
                today.withDayOfMonth(selectedDay);

        if (selectedDate.isBefore(today)) {
            selectedDate = selectedDate.plusMonths(1);
        }

        BaseClass.selectedDate =
                selectedDate.format(DATE_FORMAT);

        helper.log("Selected Full Date Stored: " + BaseClass.selectedDate);

        // ================= TIME =================
        Locator timeElement = apply.getFirstActiveTimeSlot();
        helper.safeClick(timeElement, "Time");

        BaseClass.selectedTime =
                timeElement.innerText().trim().replaceFirst("^0", "");

        helper.log("Selected Time Stored: " + BaseClass.selectedTime);

        // ================= SCREENING =================
        Locator questions = apply.getScreeningQuestions();

        if (questions.count() > 0) {
            helper.safeClick(apply.getNextButton(), "Next");

            for (int i = 0; i < questions.count(); i++) {
                Locator q = questions.nth(i);
                String question = helper.normalizeText(q.innerText());

                String answer = "";

                if (q.locator("input").count() > 0) {
                    helper.safeClick(q.locator("input").first(), "Select Option");
                    answer = "Option Selected";
                }

                if (q.locator("textarea").count() > 0) {
                    helper.safeFill(q.locator("textarea").first(),
                            "Automated Answer", "Answer");
                    answer = "Automated Answer";
                }

                Assert.assertFalse(answer.isEmpty());
                BaseClass.screeningAnswers.put(question, answer);
            }

            helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");
        }

        // ================= THANK YOU =================
        Assert.assertEquals(
                apply.getThankYouHeader().innerText().trim(),
                ApplyNowExpectedTexts.THANK_YOU_HEADER
        );

        helper.safeClick(apply.getThankYouMyInterestLink(), "My Interest");

        // ================= MY INTEREST =================
        Assert.assertEquals(
                myInterest.getCardStatus().first().innerText().trim(),
                "Pending"
        );

        Assert.assertEquals(
                myInterest.getCardTitle().first().innerText().trim(),
                INTERNSHIP
        );

        Assert.assertEquals(
                myInterest.getCardCompany().first().innerText().trim(),
                BaseClass.campaignName
        );

        Assert.assertEquals(
                myInterest.getMeetingDate().innerText().trim(),
                BaseClass.selectedDate
        );

        Assert.assertEquals(
                myInterest.getMeetingTime().innerText().trim(),
                BaseClass.selectedTime
        );

        Assert.assertEquals(
                page.locator(".my_interest_footer-service-banner-wrapper span")
                        .first().innerText().trim(),
                "Online Interview"
        );

        helper.log("✅ APPLY NOW EMAIL – FULL STRICT VALIDATION PASSED");
    }
}


