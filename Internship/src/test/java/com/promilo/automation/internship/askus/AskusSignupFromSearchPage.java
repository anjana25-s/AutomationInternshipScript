package com.promilo.automation.internship.askus;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class AskusSignupFromSearchPage extends Baseclass {
	

    @Test
    public void LoginAskUsTest() throws InterruptedException {

        // -------------------------
        // Close Initial Popup
        // -------------------------
        page.locator("//button[text()='May be later!']").click();

        // -------------------------
        // Navigation Flow
        // -------------------------
        HomePage homePage = new HomePage(page);
        
        homePage.clickSearchBar();
        homePage.clickInternshipsTab();
      
        AskUsPage askUsPage = new AskUsPage(page);
        askUsPage.clickAskUs();
        askUsPage.clickLogin();
        askUsPage.clickSignup();
        
        // Generate a random email & OTP
        String email = SignUpUtility.generateRandomEmail();
        String mobileNumber = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp(); 
        
        // AskUs Data Validation
        AskusDataValidation validation = new AskusDataValidation(page);
        
        askUsPage.enterNameInTextfield("Anjana");
        Assert.assertEquals(
                validation.signUpSideContentHeader().textContent().trim(),
                "Why Should You Sign In?");
        
        Assert.assertEquals(
                validation.SignUpSideContentFirstDescription().textContent().trim(),
                "Stay Ahead in Your Career: Access the latest internship opportunities tailored to your skills, industry trends, and career aspirations.");
        
        Assert.assertEquals(
                validation.SignUpSideContentSecondDescription().textContent().trim(),
                "Instant Notifications: Be the first to know when new internships matching your profile are posted on Promilo.");
        
        Assert.assertEquals(
                validation.SignUpSideContentThirdDescription().textContent().trim(),
                "Get Trusted Insights: Discover authentic reviews about potential companies and insights shared by peers and professionals.");
        
        Assert.assertEquals(
                validation.SignUpSideContentFourthDescription().textContent().trim(),
                "Guaranteed Privacy: Your information is safe with us. We ensure no unsolicited third-party communications.");
                
        askUsPage.enterMobileNumber(mobileNumber);
        askUsPage.enterEmail(email);
        askUsPage.industryDropdown();
        askUsPage.clickOnValue();
        askUsPage.selectValue();
        askUsPage.closeDropdown();
        page.keyboard().press("Enter");
        askUsPage.enterPassword("abc@12345");
        askUsPage.clickOnIcon();
        askUsPage.clickCloseIcon();
        askUsPage.toggleClick();
        askUsPage.clickOnRegButton();
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


        askUsPage.enterOtp(otp); 
        askUsPage.clickVerify();
        askUsPage.enterQuery("Helloo");
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
