package com.promilo.automation.askusNegativeFlow;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.negative.AskUsPopUp;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class AskusFromSearchPageNegative extends Baseclass {

    @Test
    public void AskUsTestFullFlow() throws InterruptedException {

        page.locator("//button[text()='May be later!']").click();

 HomePage homePage = new HomePage(page);
        
        homePage.clickSearchBar();
        homePage.clickInternshipsTab();
      
       
        AskUsPage askUsPage = new AskUsPage(page);
        askUsPage.clickAskUs();

        AskUsPopUp popup = new AskUsPopUp(page);

        //  NEGATIVE FLOW — Without data validation
        askUsPage.clickOnButton();
        
        // AskUs Data Validation
        AskusDataValidation validation = new AskusDataValidation(page);
     
        assertEquals(
        		validation.askUsDescription().textContent().trim(),
        		"Ask Us Anything for FreeGet "
        		+ "personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com."
        		);

        		assertEquals(
        				validation.askUsHeaderText().textContent().trim(),
        		"Share your query to get help!"
        		);

        		assertEquals(
        				validation.askUsFooterText().textContent().trim(),
        		"By proceeding ahead you expressly agree to the PromiloTerms & Conditions"
        		);

        popup.verifyEmptyNameField();
        popup.verifyEmptyPhoneField();
        popup.verifyEmptyEmailField();
        popup.verifyQuestionTextbox();
        System.out.println("Blank fields validation Passed!");

        //  NEGATIVE FLOW — Invalid short input validation
        askUsPage.typeUserName("An");
        askUsPage.typenumber("90000299");
        askUsPage.typeEmail("ammuu044yopmail.com");
        askUsPage.enterQuery("H");
        askUsPage.clickOnButton();
        System.out.println("Invalid length validation Passed!");
        
        askUsPage.typeUserName("2342");
        askUsPage.typenumber("90000299222222");
        askUsPage.typeEmail("233222");
        askUsPage.clickOnButton();
        askUsPage.typeUserName("@&*");
        askUsPage.typenumber("@#%");
        askUsPage.typenumber("abhhddd");
        askUsPage.typeEmail("@#$@");
        askUsPage.clickOnButton();
        System.out.println("Incorrect data validation Passed!");
        
         // Refresh & fill valid data
        page.reload();
        askUsPage.clickAskUs();

        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp(); 
        String invalidOtp = SignUpUtility.generateInvalidOtp();

        askUsPage.typeUserName("Ammu");
        askUsPage.typenumber(mobile);
        askUsPage.typeEmail("pavi12@yopmail.com");
        askUsPage.enterQuery("Good evening, I have a doubt");
        askUsPage.clickOnButton();
        askUsPage.typeEmail(email);
        askUsPage.clickOnButton();
        
        
      //  NEGATIVE FLOW — Invalid OTP
        askUsPage.enterOtp(invalidOtp);
        askUsPage.clickVerify();
        popup.invalidOtp();
        System.out.println("Invalid OTP validation Passed!");

        // POSITIVE FLOW — Valid OTP
        askUsPage.resendClick();
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


