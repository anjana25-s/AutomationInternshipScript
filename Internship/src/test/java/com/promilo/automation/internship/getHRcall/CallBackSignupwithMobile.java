package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.assignment.SignUpUtility;

import basetest.Baseclass;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CallBackSignupwithMobile extends Baseclass {

    @Test
    public void HRcallTest() throws InterruptedException {

        SignupPage signup = new SignupPage(page);

        // Close popup
        signup.clickMaybeLater();

        // Click Sign Up
        signup.clickInitialSignupButton();

        //  Generate random mobile number
        String mobileNumber = SignUpUtility.generateRandomMobile();
        System.out.println("Generated Mobile Number: " + mobileNumber);
        String otp = SignUpUtility.getFixedOtp(); 
        String email = SignUpUtility.generateRandomEmail();


        // Enter mobile number
        signup.enterEmailOrPhone(mobileNumber);

        // Click "Send Verification Code"
        signup.clickVerificationCode();

        // Enter fixed OTP
        signup.enterMobileOtp(otp);

        // Enter password
        signup.enterPassword("Testanju12");

        // Complete signup
        signup.clickFinalSignupButton();
        signup.isSignupSuccess();

        // Navigate to internships tab
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        // Click automation tester card
        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        // Fill HR callback form
        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();
        page.waitForTimeout(4000);
        // ===================== HR CALL POPUP VALIDATION =====================
        GetHRcallDataValidation data = new GetHRcallDataValidation(page);

        Locator header = data.getHrCallHeaderText();
        header.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

        Assert.assertTrue(header.isVisible(), "❌ HR Call header text missing");
        Assert.assertTrue(data.getHrCallPopupDescription().isVisible(), "❌ HR Call description text missing");
        Assert.assertTrue(data.enableWhatsappNotification().isVisible(), "❌ WhatsApp notification toggle missing");
        Assert.assertTrue(data.getHrCallFooterText().isVisible(), "❌ HR Call footer text missing");

        System.out.println("✅ HR Call popup static texts validated successfully.");

       
        callback.enterUserName("Anu");
        callback.emailIdField(email);
        callback.clickDropDown();
        callback.clickOnValue();
        page.keyboard().press("Enter");
        callback.clickButton();
       
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

       // Open business portal
        Page newPage = context.newPage();
        newPage.navigate("https://stagebusiness.promilo.com/");
        System.out.println("Navigated to business Promilo in new tab");

        // Business approval flow
        BusinessPage advertiser = new BusinessPage(newPage);
        advertiser.enterUserName("adv@yopmail.com");
        advertiser.enterPassword("adv@1234");
        advertiser.clickSignIn();
        advertiser.clickMyAccount();
        advertiser.clickMyProspect();
        advertiser.clickInternships();
        advertiser.clickCallBack();
        advertiser.clickReject();
        advertiser.confirmReject();

        // Validate rejection label
        BusinessPage MyProspect = new BusinessPage(newPage);
        String actualMessage1 = MyProspect.rejectTextMessage().textContent().trim();
        System.out.println(" :✅  " + actualMessage1);
        Assert.assertEquals(actualMessage1, "Rejected", "Rejected message validation failed!");
    }
}
