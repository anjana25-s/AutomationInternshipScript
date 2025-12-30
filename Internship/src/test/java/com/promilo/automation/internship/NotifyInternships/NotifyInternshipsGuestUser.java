package com.promilo.automation.internship.NotifyInternships;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignUpUtility;
import com.promilo.automation.internship.pageobjects.NotifyInternshipsDataValidation;
import basetest.Baseclass;

public class NotifyInternshipsGuestUser extends Baseclass {

    @Test
    public void SimilarInternships() throws InterruptedException {

        // ===================== INITIAL SETUP =====================
        page.locator("//button[text()='May be later!']").click();

        // Navigate to internships and open notify popup
        NotifyInternshipsPage notify = new NotifyInternshipsPage(page);
        notify.clickInternshipsTab();
        notify.clickBankManagerCard();
        notify.clickOnNotify();

        // ===================== DATA VALIDATION: NOTIFY POPUP =====================
        NotifyInternshipsDataValidation data = new NotifyInternshipsDataValidation(page);

        // Wait for header to appear
        Locator header = data.headerText();
        header.waitFor();

        Assert.assertTrue(
                header.isVisible(),
                "❌ Notify popup header is missing"
        );
        System.out.println("✅ Header text visible: " + header.textContent());

        Assert.assertTrue(
                data.registerDescriptionText().isVisible(),
                "❌ Register description text is missing"
        );

        Assert.assertTrue(
                data.whatsappNotificationText().isVisible(),
                "❌ Whatsapp notification label is missing"
        );

        Assert.assertTrue(
                data.agreeText().isVisible(),
                "❌ Agreement text is missing"
        );

        System.out.println("✅ Notify popup static texts validated successfully.");

        // ===================== USER DATA ENTRY =====================
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        System.out.println("Generated Email: " + email);
        System.out.println("Generated Mobile: " + mobile);

        notify.typeUserName("Kamal");
        notify.enterPhoneNumber(mobile);
        notify.typeemail(email);
        notify.button();

        // ===================== DATA VALIDATION: OTP PAGE =====================
        Assert.assertTrue(
                data.otpPageDescription().isVisible(),
                "❌ OTP page description missing"
        );

        Assert.assertTrue(
                data.otpVerificationHeader().isVisible(),
                "❌ OTP verification header missing"
        );

        // Enter OTP
        notify.enterOtp(otp);
        notify.clickSubmitButton();

        // Wait for OTP Thanks message
        Locator thanksText = data.otpThanksText();
        thanksText.waitFor();

        Assert.assertTrue(
                thanksText.textContent().contains("Thanks"),
                "❌ OTP success text mismatch"
        );

        System.out.println("✅ OTP validation passed successfully.");

        // ===================== THANK YOU POPUP VALIDATION =====================
        Locator thankYouPopup = data.thankYouPopup();
        thankYouPopup.waitFor();

        Assert.assertTrue(
                thankYouPopup.isVisible(),
                "❌ Thank You popup not visible"
        );

        Assert.assertTrue(
                data.thankYouMessageText().isVisible(),
                "❌ Thank You message text missing"
        );

        String actualMessage = data.thankYouMessageText().textContent().trim();
        System.out.println("✅ Success message displayed: " + actualMessage);

        Assert.assertTrue(
            actualMessage.toLowerCase().contains("thank you"),
            "❌ Success message validation failed! Expected text to contain 'Thank you'"
        );

        System.out.println("Notify Internships data validations passed successfully!");
    }
}
