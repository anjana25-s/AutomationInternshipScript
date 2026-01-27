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
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;
import com.promilo.automation.internship.pageobjects.MyProspectCardValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;
import com.promilo.automation.internship.utilities.TestAccountStore;

import basetest.Baseclass;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
         String password=SignUpUtility.generateRandomPassword();
         String otp = SignUpUtility.getFixedOtp();  // → always 9999
         System.out.println("Generated Email: " + email);
         
         signup.enterEmailOrPhone(email);
         signup.clickVerificationCode();
         signup.enterEmailOtp(otp);
         signup.enterPassword(password);
         signup.clickFinalSignupButton();
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

         
         String getHrCallPopUpDescription = data.getHrCallPopupDescription().textContent().trim();
         String expectedGetHrCallPopUpDescription = "Why Register to Get an HR Callback for Your First Internship?Take Charge of Your Career: Connect with recruiters and apply for internships that match your aspirations.Stay Updated: Receive real-time notifications about internship openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored internship alerts ensure you don't miss the right openings.Exclusive Resources: Unlock premium tools and tips for acing interviews and securing your dream internship.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.Take Charge of Your Career: Connect with recruiters and apply for internships that match your aspirations.Stay Updated: Receive real-time notifications about internship openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored internship alerts ensure you don't miss the right openings.Exclusive Resources: Unlock premium tools and tips for acing interviews and securing your dream internship.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.PreviousNext";
         assertEquals(getHrCallPopUpDescription, expectedGetHrCallPopUpDescription);
         String getHrCallHeaderText = data.getHrCallHeaderText().textContent().trim();
         String expectedGetHrCallHeaderText="Get an HR Call from UST Global!";
         assertEquals(getHrCallHeaderText, expectedGetHrCallHeaderText);
         String enableWhatssappNotification = data.enableWhatsappNotification().textContent().trim();
         String expectedEnableWhatssappNotification="Enable updates & important information on Whatsapp";
         assertEquals(enableWhatssappNotification, expectedEnableWhatssappNotification);
         String getHrCallfooterText = data.getHrCallFooterText().textContent().trim();
         String expectedGetHrCallfooterText="By proceeding ahead you expressly agree to the PromiloTerms & Conditions";
         assertEquals(getHrCallfooterText, expectedGetHrCallfooterText);;

         callback.enterUserName("Anjali");
         callback.enterMobileNumber(mobile);
         callback.clickDropDown();
         callback.clickOnValue();
         page.keyboard().press("Enter");
         callback.clickButton();
       
         // ===================== OTP PAGE VALIDATION =====================
         Locator otpHeader = data.otpVerificationHeader();
         String otpPageDescription = data.otpPageDescription().textContent().trim();
         String expectedOtpPageDescription = "Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.";
         assertEquals(otpPageDescription, expectedOtpPageDescription);

         System.out.println("✅ OTP page static texts validated successfully.");

        callback.enterNumber(otp);
        
        // Wait for thanks message after OTP submission
        Locator otpThanks = data.otpThanksForInformation();
        String text3 = data.otpThanksForInformation().textContent().trim();
        String expectedResult3="Thanks for giving your Information!";
        assertEquals(text3, expectedResult3);


        String text4 = data.otpVerificationHeader().textContent().trim();
        String expectedResult4="OTP Verification";

        assertEquals(text4, expectedResult4);
        String text5 = data.otpInstructionText().textContent().trim();
        String expectedResult5="Enter the 4-digit verification code we just sent you to";
        assertTrue(text5.contains(expectedResult5));

        String text6 = data.otpStillCantFind().textContent().trim();
        String expectedResult6="Still can’t find the OTP";
        assertTrue(text6.contains(expectedResult6));
        callback.clickSubmitButton();
        
        // ===================== LANGUAGE SELECTION VALIDATION =====================
        callback.clickLanguage();
        Locator languageText = data.chooseLanguageText();
        String nextPageText = data.nextPageInfoText().first().textContent().trim();
        String expectedNextPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
        assertEquals(nextPageText, expectedNextPageText);
        page.waitForTimeout(2000);
        String chooseLangaugeText = data.chooseLanguageText().textContent().trim();
        String expectedChooseLangaugeText="Please Select your preferred language with UST Global. This will make it easier for you and HR to connect as you choose. ";
        assertEquals(chooseLangaugeText, expectedChooseLangaugeText);
        callback.clickSubmit();


        // ===================== SCREENING PAGE & THANK YOU VALIDATION =====================
        callback.clickOncheckBox();
        Locator screeningQuestion = data.takeMomentSideText();
        String submitPageText = data.takeMomentSideText().textContent().trim();
        String expectedSubmitPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
        assertEquals(submitPageText, expectedSubmitPageText);
        String takeMomentText = data.takeMomentText().textContent().trim();
        String expectedTakeMomentText="Please take a moment to answer the below questions.";
        assertEquals(takeMomentText, expectedTakeMomentText);
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
        
        Page businessPage = context.newPage();
        businessPage.navigate("https://stagebusiness.promilo.com/");
        businessPage.waitForLoadState();

         BusinessPage advertiser=new BusinessPage(businessPage);
         advertiser.enterUserName("adv@yopmail.com");
         advertiser.enterPassword("adv@1234");
         advertiser.clickSignIn();
         advertiser.clickMyAccount();
         advertiser.clickMyProspect();
         advertiser.clickInternships();
         advertiser.clickCallBack();
         advertiser.clickApprove();
         advertiser.clickProceed();
         advertiser.clickDone();
         
         // ===================== USER SIDE COMPLETED STATUS VALIDATION =====================

      // Switch back to user portal
      page.bringToFront();
      page.waitForLoadState();

      // Navigate to My Preferences
      CallbackPage callback1 = new CallbackPage(page);
      callback1.myPreference();      
      
      // Initialize preference card validation
      MyPreferenceCardValidation preference = new MyPreferenceCardValidation(page);

      // Wait for Completed tag
      Locator completedTag = preference.completedStatusTag();
      completedTag.waitFor(new Locator.WaitForOptions()
              .setState(WaitForSelectorState.VISIBLE)
              .setTimeout(20000));

      // Assertions
      Assert.assertTrue(
              completedTag.isVisible(),
              "❌ Completed status tag not visible on user side"
      );

      String completedText = completedTag.textContent().trim();

      Assert.assertEquals(
              completedText,
              "Completed",
              "❌ Completed status text mismatch on user side"
      );

      System.out.println("✅ User side completed status validated successfully");

      // -------------------------
      // Navigation Flow
      // -------------------------
      HomePage homePage1 = new HomePage(page);
      homePage.clickHomeTab();
      homePage.clickSearchBar();
      homePage.clickInternshipsTab();
      
      CallbackPage callback2 = new CallbackPage(page);
      callback.clickGetHRCall();
      callback.clickLanguage();
      callback.clickSubmit();
      callback.clickOncheckBox();
      callback.typeAnswer("yes");
      callback.clickOnSubmit();
      callback.buildResume();
      callback.notificationIcon();
      
      String notificationText1 =
              data.latestInAppNotification().textContent().trim();

      Assert.assertTrue(
              notificationText1.contains(
                  "HR Call Request Pending... Your HR call request is currently in pending status. " +
                  "Stay tuned! Our team will be in touch soon." ),
              "HR Call Pending notification text is incorrect or missing"
      );

      businessPage.bringToFront();
      businessPage.waitForLoadState();
      
      advertiser.clickMyProspect();
      advertiser.clickInternships();
      advertiser.clickCallBack();
      advertiser.clickApprove();
      advertiser.clickProceed();
      advertiser.clickDone();

  }}
