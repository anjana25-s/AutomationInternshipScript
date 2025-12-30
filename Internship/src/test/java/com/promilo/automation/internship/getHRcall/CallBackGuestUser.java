package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.SignUpUtility;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;

import basetest.Baseclass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CallBackGuestUser extends Baseclass {

    @Test
    public void CallBackGuestUser() {

        // ===================== INITIAL SETUP =====================
        page.locator("//button[text()='May be later!']").click();

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();

        // Generate test data
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        System.out.println("Generated Email: " + email);
        System.out.println("Generated Mobile: " + mobile);

        // ===================== HR CALL FLOW =====================
        CallbackPage callback = new CallbackPage(page);
        callback.clickInternshipsTab();
        callback.clickAutomationTesterCard();
        callback.clickGetHRCall();

        // ===================== HR CALL POPUP VALIDATION =====================
        GetHRcallDataValidation data = new GetHRcallDataValidation(page);

        Locator header = data.getHrCallHeaderText();
        header.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(header.isVisible(), "❌ HR Call header text missing");
        Assert.assertTrue(data.getHrCallPopupDescription().isVisible(), "❌ HR Call description text missing");
        Assert.assertTrue(data.enableWhatsappNotification().isVisible(), "❌ WhatsApp notification toggle missing");
        Assert.assertTrue(data.getHrCallFooterText().isVisible(), "❌ HR Call footer text missing");

        System.out.println("✅ HR Call popup static texts validated successfully.");

        // ===================== SUBMIT FORM =====================
        callback.enterStudentName("Diya");
        callback.studentMobileNumber(mobile);
        callback.enterMailId(email);
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();

        // ===================== OTP PAGE VALIDATION =====================
        Locator otpHeader = data.otpVerificationHeader();
        otpHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(otpHeader.isVisible(), "❌ OTP Verification header missing");
        Assert.assertTrue(data.otpPageDescription().isVisible(), "❌ OTP page description missing");
        Assert.assertTrue(data.otpInstructionText().isVisible(), "❌ OTP instruction text missing");
        Assert.assertTrue(data.otpStillCantFind().isVisible(), "❌ 'Still can’t find OTP' text missing");

        System.out.println("✅ OTP page static texts validated successfully.");

        // Enter OTP
        callback.enterNumber(otp);
        // Wait for thanks message after OTP submission
        Locator otpThanks = data.otpThanksForInformation();
        otpThanks.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(otpThanks.isVisible(), "❌ OTP success text missing");
        System.out.println("✅ OTP submission validation passed successfully.");
         callback.clickSubmitButton();

       
        // ===================== LANGUAGE SELECTION VALIDATION =====================
        callback.clickLanguage();
        Locator languageText = data.chooseLanguageText();
        languageText.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(languageText.isVisible(), "❌ Language selection text missing");
       
        System.out.println("✅ Language selection texts validated successfully.");
        callback.clickSubmit();

       
        // ===================== SCREENING PAGE & THANK YOU VALIDATION =====================
        callback.clickOncheckBox();
        Locator screeningQuestion = data.takeMomentText();
        screeningQuestion.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(data.takeMomentText().isVisible(), "❌ 'Take a moment' text missing");
        System.out.println("✅ screening question validated successfully.");
        callback.clickOnSubmit();
        
        page.waitForLoadState();
        Locator thankYou = data.thankYouText();
        thankYou.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(20000));
       
        String actualThankYouMessage = thankYou.textContent().trim();
        System.out.println("✅ Thank You message displayed: " + actualThankYouMessage);

        Assert.assertTrue(
                actualThankYouMessage.toLowerCase().contains("thank you"),
                "❌ Thank You message validation failed!"
        );

        callback.myPreference();

       
     // ===================== My Preference Card Validation =====================

        MyPreferenceCardValidation myPreference =
                new MyPreferenceCardValidation(page);

        // Expected values
        String expectedRole = "Artificial Intelligence";
        String expectedBrandName = "UST Global";
        String expectedDuration = "6 Months";
        String expectedLocation = "Bihar - Other";
        String expectedServiceName = "Get HR Call";
        String expectedStipend = "25K - 35K";

        // ===================== Internship Role =====================
        String actualRole =
                myPreference.internshipRole().innerText().trim();

        Assert.assertEquals(
                actualRole,
                expectedRole,
                "❌ Internship role mismatch"
        );

        // ===================== Duration =====================
        String actualDuration =
                myPreference.duration().innerText().trim();

        Assert.assertEquals(
                actualDuration,
                expectedDuration,
                "❌ Duration mismatch"
        );

        // ===================== Brand Name =====================
        String actualBrandName =
                myPreference.brandName().innerText().trim();

        Assert.assertEquals(
                actualBrandName,
                expectedBrandName,
                "❌ Brand name mismatch"
        );

        // ===================== Service Name =====================
        String actualServiceName =
                myPreference.serviceName().innerText().trim();

        Assert.assertEquals(
                actualServiceName,
                expectedServiceName,
                "❌ Service name mismatch"
        );

        // ===================== Stipend =====================
        String actualStipend =
                myPreference.stipend().innerText().trim();

        Assert.assertEquals(
                actualStipend,
                expectedStipend,
                "❌ Stipend mismatch"
        );

        // ===================== Location =====================
        String actualLocation =
                myPreference.location().innerText().trim();

        Assert.assertEquals(
                actualLocation,
                expectedLocation,
                "❌ Location mismatch"
        );

        System.out.println("✅ My Preference card data validated successfully");
}}