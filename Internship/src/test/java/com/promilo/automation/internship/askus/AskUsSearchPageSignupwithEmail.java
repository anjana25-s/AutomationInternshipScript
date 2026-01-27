package com.promilo.automation.internship.askus;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;
import com.promilo.automation.internship.utilities.TestAccountStore;
import basetest.Baseclass;

public class AskUsSearchPageSignupwithEmail extends Baseclass {

    @Test
    public void AskUs1Test() throws InterruptedException {

        // ------------------------
        // SIGNUP
        // ------------------------
        SignupPage signup = new SignupPage(page);
        signup.clickMaybeLater();
        signup.clickInitialSignupButton();
        

        // Generate a random email & OTP
        String email = SignUpUtility.generateRandomEmail();
        String otp = SignUpUtility.getFixedOtp();
        String password=SignUpUtility.generateRandomPassword();
        String mobile=SignUpUtility.generateRandomMobile();
       
         signup.enterEmailOrPhone(email);
        signup.clickVerificationCode();
        signup.enterEmailOtp(otp);
        signup.enterPassword(password);
        signup.clickFinalSignupButton();
        signup.isSignupSuccess();

        TestAccountStore.save(email, password);
         // ------------------------
        // NAVIGATE TO INTERNSHIP
        // ------------------------
        HomePage homePage = new HomePage(page);
        
        homePage.clickSearchBar();
        homePage.clickInternshipsTab();
      
        AskUsPage askUsPage = new AskUsPage(page);
        askUsPage.clickAskUs();
        askUsPage.enterName("Ammu");
        askUsPage.enterNumber(mobile);
        askUsPage.enterQuery("Good evening");
        AskusDataValidation validation = new AskusDataValidation(page);
        assertEquals(
        		validation.askUsDescription().textContent().trim(),
        		"Ask Us Anything for FreeGet personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com."
        		);

        		assertEquals(
        				validation.askUsHeaderText().textContent().trim(),
        		"Share your query to get help!"
        		);

        		assertEquals(
        				validation.askUsFooterText().textContent().trim(),
        		"By proceeding ahead you expressly agree to the PromiloTerms & Conditions"
        		);
        askUsPage.clickOnButton();
        // Enter OTP
        askUsPage.enterOtp(otp);
        
        assertEquals(
        		validation.otpPageDescription().textContent().trim(),
        		"Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams."
        		);
        assertEquals(
        		validation.otpSuccessText().textContent().trim(),
        		"Thanks for giving your Information!"
        		);

        		assertEquals(
        				validation.otpHeader().textContent().trim(),
        		"OTP Verification"
        		);

        		assertTrue(
        				validation.otpDescription().textContent().trim()
        		.contains("Enter the 4-digit verification code we just sent you to")
        		);

        		assertTrue(
        				validation.otpStillCantFind().textContent().trim()
        		.contains("Still can’t find the OTP")
        		);
        		
        askUsPage.clickVerify();
        
        Assert.assertEquals(
                validation.thankYouHeader().textContent().trim(),
                "Thank You!",
                "❌ Thank You popup header text mismatch"
        );
        askUsPage.closeThankyouPopup();
        askUsPage.notificationIcon();
           
        String notificationText =
                validation.inAppNotification().textContent().trim();

        Assert.assertTrue(
                notificationText.contains("We’ve got your question about"),
                "Notification prefix text is missing"
        );

        Assert.assertTrue(
                notificationText.contains("Sit tight—our team is preparing a detailed response"),
                "Notification response message is missing"
        );

        // -------------------- FINAL SUCCESS MESSAGE --------------------
        System.out.println("✅ Test data validation passed successfully");

        


       
}

}
