package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.MyBillingPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;
import com.promilo.automation.internship.pageobjects.MyProspectCardValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

import static org.testng.Assert.assertEquals;

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

        callback.enterUserName("Anu");
        callback.emailIdField(email);
        callback.clickDropDown();
        callback.clickOnValue();
        page.keyboard().press("Enter");
        callback.clickButton();
       
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
        
     // Initialize Prospect Card Validation
        MyProspectCardValidation prospectCard = new MyProspectCardValidation(newPage);

        // Wait until prospect card is visible
        prospectCard.waitForProspectCard();

        /* ---------------- USER NAME ---------------- */
        String userName = prospectCard.getUserName();
        Assert.assertFalse(
                userName.isEmpty(),
                "❌ User name is empty in prospect card"
        );

        /* ---------------- CAMPAIGN NAME ---------------- */
        String campaignName = prospectCard.getCampaignName();
        Assert.assertTrue(
                campaignName.toUpperCase().contains("B2C"),
                "❌ Campaign name does not contain B2C"
        );

        /* ---------------- INTEREST SHOWN DATE ---------------- */
        String interestShownDate = prospectCard.getInterestShownDate();
        Assert.assertFalse(
                interestShownDate.isEmpty(),
                "❌ Interest shown date is empty"
        );

        /* ---------------- MEETING STATUS ---------------- */
        String meetingStatus = prospectCard.getMeetingStatus();
        Assert.assertTrue(
                meetingStatus.equalsIgnoreCase("Pending")
                || meetingStatus.equalsIgnoreCase("Completed")
                || meetingStatus.equalsIgnoreCase("Rejected"),
                "❌ Invalid meeting status: " + meetingStatus
        );

        /* ---------------- PREFERRED LANGUAGE ---------------- */
        String preferredLanguage = prospectCard.getPreferredLanguage();
        Assert.assertFalse(
                preferredLanguage.isEmpty(),
                "❌ Preferred language is empty"
        );


        System.out.println("✅ My Prospect card data validated successfully.");

        
          // ===================== USER SIDE REJECTED STATUS VALIDATION =====================

     // Switch back to user page (original page)
     page.bringToFront();
     page.waitForLoadState();

     // Navigate to My Preferences 
     CallbackPage callback1 = new CallbackPage(page);
     callback1.myPreference();
     
             // Initialize preference card validation
     MyPreferenceCardValidation preference = new MyPreferenceCardValidation(page);

     // Wait for rejected tag
     Locator rejectedTag = preference.rejectedStatusTag();
     rejectedTag.waitFor(new Locator.WaitForOptions()
             .setState(WaitForSelectorState.VISIBLE)
             .setTimeout(15000));

     // Assertions
     Assert.assertTrue(
             rejectedTag.isVisible(),
             "❌ Rejected status tag not visible on user side"
     );

     String rejectedText = rejectedTag.textContent().trim();
     Assert.assertEquals(
             rejectedText,
             "Rejected",
             "❌ Rejected status text mismatch on user side"
     );

     System.out.println("✅ User side rejected status validated successfully");

}
}