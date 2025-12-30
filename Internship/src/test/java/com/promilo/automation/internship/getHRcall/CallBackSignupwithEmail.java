package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.LoginPage;
import com.promilo.automation.internship.assignment.MyBillingPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.assignment.SignUpUtility;

import basetest.Baseclass;

import org.testng.Assert;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class CallBackSignupwithEmail extends Baseclass {

    @Test
    public void HRcallTest() throws InterruptedException {
    	
         //sign up
         SignupPage signup=new SignupPage(page);
         signup.clickMaybeLater();
         signup.clickInitialSignupButton();
         
      // Generate a random email & OTP
         String email = SignUpUtility.generateRandomEmail();
         String mobile=SignUpUtility.generateRandomMobile();
         String otp = SignUpUtility.getFixedOtp();  // → always 9999
         System.out.println("Generated Email: " + email);
         
         // Enter email
         signup.enterEmailOrPhone(email);
         
         // Request OTP
         signup.clickVerificationCode();
         
         // Enter fixed OTP 9999ext
         signup.enterEmailOtp(otp);
         
          // Enter Password
         signup.enterPassword("Testautomation123");
         
         // Final signup
         signup.clickFinalSignupButton();
         
         // Validate signup success
         signup.isSignupSuccess();
         
         // ------------------------
         // NAVIGATE TO INTERNSHIP
         // ------------------------
         HomePage homePage = new HomePage(page);
        homePage.clickInternships();
         System.out.println("Clicked Internships tab");
         

         InternshipPage internshipPage = new InternshipPage(page);
         internshipPage.clickAutomationTesterCard();
         
         // CALLBACK POPUP
         CallbackPage callback = new CallbackPage(page);
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

         callback.enterUserName("Anjali");
         callback.enterMobileNumber(mobile);
         callback.clickDropDown();
         callback.clickOnValue();
         page.keyboard().press("Enter");
         callback.clickButton();
       
         // ===================== OTP PAGE VALIDATION =====================
         Locator otpHeader = data.otpVerificationHeader();
         otpHeader.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

         Assert.assertTrue(otpHeader.isVisible(), "❌ OTP Verification header missing");
         Assert.assertTrue(data.otpPageDescription().isVisible(), "❌ OTP page description missing");
         Assert.assertTrue(data.otpInstructionText().isVisible(), "❌ OTP instruction text missing");
         Assert.assertTrue(data.otpStillCantFind().isVisible(), "❌ 'Still can’t find OTP' text missing");

         System.out.println("✅ OTP page static texts validated successfully.");

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
        
      
         Page newPage = context.newPage();  
         newPage.navigate("https://stagebusiness.promilo.com/");
         System.out.println("Navigated to business Promilo in new tab");

         BusinessPage advertiser=new BusinessPage(newPage);
         advertiser.enterUserName("adv@yopmail.com");
         advertiser.enterPassword("adv@1234");
         advertiser.clickSignIn();
         advertiser.clickMyAccount();
         advertiser.clickMyProspect();
         advertiser.clickInternships();
         advertiser.clickCallBack();
         advertiser.clickApprove();
         advertiser.clickProceed();
         
        
         MyBillingPage billingpage=new MyBillingPage(newPage);
         billingpage.clickMyBilling();
         billingpage.clicksendEmail();
         billingpage.clickSendInvoice();
         
           }
         
  }
