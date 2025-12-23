package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.ApplyNowErrorMessagesPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class ApplyNowNegativeTest extends BaseClass {

    @Test
    public void fullApplyNowNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        ApplyNowPage apply = new ApplyNowPage(page);
        ApplyNowErrorMessagesPage errors = new ApplyNowErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        helper.log("==== APPLY NOW FULL NEGATIVE FLOW STARTED ====");

        // ------------------------------------------------------------
        // NAVIGATION
        // ------------------------------------------------------------
        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard("Tester 1");
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");
        if (apply.getAskUsApplyNowButton().isVisible())
            helper.safeClick(apply.getAskUsApplyNowButton(), "Popup Apply Now");

        // ------------------------------------------------------------
        // EMPTY FORM
        // ------------------------------------------------------------
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(), "Name required");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required");
        helper.assertVisible(errors.emailRequiredError(), "Email required");
        helper.assertVisible(errors.industryRequiredError(), "Industry required");

        // ------------------------------------------------------------
        // NAME NEGATIVES
        // ------------------------------------------------------------
        apply.getNameField().fill("ab");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Short Name Submit");
        helper.assertVisible(errors.nameMinError(), "Name min error");

        apply.getNameField().fill("Me@");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Invalid Name Submit");
        helper.assertVisible(errors.nameInvalidError(), "Invalid name error");

        apply.getNameField().fill("A".repeat(51));
        helper.safeClick(apply.getAskUsApplyNowButton(), "Long Name Submit");
        helper.assertVisible(errors.nameMaxError(), "Name max error");

        // ------------------------------------------------------------
        // MOBILE INVALID
        // ------------------------------------------------------------
        apply.getPhoneField().fill("12345");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Invalid Mobile Submit");
        helper.assertVisible(errors.mobileInvalidError(), "Invalid mobile error");

        // ------------------------------------------------------------
        // EMAIL INVALID
        // ------------------------------------------------------------
        apply.getEmailField().fill("abc@");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Invalid Email Submit");
        helper.assertVisible(errors.emailInvalidError(), "Invalid email error");

        // ------------------------------------------------------------
        // VALID FORM DATA
        // ------------------------------------------------------------
        String NAME = helper.generateRandomName();
        String EMAIL = helper.generateEmailFromName(NAME);
        String PHONE = helper.generateRandomPhone();

        helper.log("Random Data:");
        helper.log(NAME);
        helper.log(EMAIL);
        helper.log(PHONE);

        apply.getNameField().fill(NAME);
        apply.getPhoneField().fill(PHONE);
        apply.getEmailField().fill(EMAIL);

        // ------------------------------------------------------------
        // INDUSTRY
        // ------------------------------------------------------------
        helper.safeClick(apply.getIndustryDropdown(), "Open Industry");

        Locator boxes = apply.getAllIndustryCheckboxes();
        helper.assertTrue(boxes.count() >= 3, "Industry checkboxes present");

        helper.safeClick(boxes.nth(1), "Industry 1");
        helper.safeClick(boxes.nth(3), "Industry 2");
        helper.safeClick(boxes.nth(5), "Industry 3");

        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        // ------------------------------------------------------------
        // SUBMIT → OTP SCREEN
        // ------------------------------------------------------------
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Valid Data");
        helper.log("---- REACHED OTP SCREEN ----");

     // ------------------------------------------------------------
     // OTP NEGATIVE → Enter wrong OTP → Verify → Toast
     // ------------------------------------------------------------
     helper.log("---- OTP NEGATIVE TEST ----");

     for (int i = 1; i <= 4; i++) {
         apply.getOtpInputField(i).fill("1");   // wrong OTP
     }

     helper.safeClick(apply.getVerifyAndProceedButton(), "Submit WRONG OTP");
     helper.assertToastAppeared(errors.invalidOtpError(), "Invalid OTP toast appeared");


     // ------------------------------------------------------------
     // OTP POSITIVE → Enter correct OTP → Verify
     // ------------------------------------------------------------
     helper.log("---- OTP POSITIVE TEST ----");

     // clear previous values
     for (int i = 1; i <= 4; i++) {
         apply.getOtpInputField(i).fill("");
     }

     // enter correct OTP
     for (int i = 1; i <= 4; i++) {
         apply.getOtpInputField(i).fill("9");
     }

     helper.safeClick(apply.getVerifyAndProceedButton(), "Submit CORRECT OTP");
     helper.log("---- OTP VERIFIED SUCCESSFULLY ----");



     // LANGUAGE
     helper.safeClick(apply.getLanguageCard("English"), "Select English");

     // CALENDAR
     helper.safeClick(apply.getFirstActiveDate(), "Select Date");
     helper.safeClick(apply.getFirstActiveTimeSlot(), "Select Time");

     // SCREENING
     if (apply.getNextButton().isVisible()) {
         helper.safeClick(apply.getNextButton(), "Go To Screening");

         Locator questions = apply.getScreeningQuestions();
         for (int i = 0; i < questions.count(); i++) {
             Locator q = questions.nth(i);

             if (q.locator("input[type='checkbox'], input[type='radio']").count() > 0)
                 helper.safeClick(q.locator("input").first(), "Select Option");

             if (q.locator("textarea").count() > 0)
                 helper.safeFill(q.locator("textarea").first(), "Automated Answer", "Answer");
         }

         helper.safeClick(apply.getScreeningSubmitButton(), "Submit Screening");
     } else {
         helper.safeClick(apply.getCalendarSubmitButton(), "Submit Calendar");
     }

     // THANK YOU
     helper.waitForVisible(apply.getThankYouHeader(), "Thank You Popup");


        // ------------------------------------------------------------
        // ALREADY REGISTERED → REOPEN NEW PAGE SAFELY
        // ------------------------------------------------------------
        helper.log("---- TESTING ALREADY REGISTERED EMAIL ----");

        BrowserContext oldCtx = page.context();
        oldCtx.close();

        page = browser.newContext().newPage();
        home = new HomepagePage(page);
        apply = new ApplyNowPage(page);
        errors = new ApplyNowErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships Again");

        Locator card2 = home.getInternshipCard("Tester 1");
        helper.waitForVisible(card2, "Internship Card");
        helper.scrollAndClick(card2, "Open Internship Again");

        helper.safeClick(apply.getApplyNowButton(), "Apply Again");
        if (apply.getAskUsApplyNowButton().isVisible())
            helper.safeClick(apply.getAskUsApplyNowButton(), "Popup Apply Again");

        // Same email, new phone
        apply.getNameField().fill(NAME);
        apply.getPhoneField().fill(helper.generateRandomPhone());
        apply.getEmailField().fill(EMAIL);

        helper.safeClick(apply.getIndustryDropdown(), "Open Industry");
        helper.safeClick(apply.getAllIndustryCheckboxes().nth(1), "Industry 1");
        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Already Registered");

        helper.assertToastAppeared(
                errors.emailAlreadyRegisteredToast(),
                "Already registered toast visible"
        );

        helper.log("==== APPLY NOW FULL NEGATIVE FLOW COMPLETED ====");
    }
}



