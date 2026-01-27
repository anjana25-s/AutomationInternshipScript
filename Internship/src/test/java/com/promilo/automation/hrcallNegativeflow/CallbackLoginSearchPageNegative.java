package com.promilo.automation.hrcallNegativeflow;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class CallbackLoginSearchPageNegative extends Baseclass {
	

    @Test
    public void CallbackLoginSearchPage() throws InterruptedException {

        // -------------------------
        // Close Initial Popup
        // -------------------------
        page.locator("//button[text()='May be later!']").click();
        
        // Close popup
        Locator closePopup = page.locator("img[src*='closeMiliIcon']");

        closePopup.click();
        Assert.assertTrue(closePopup.isHidden(), "Popup not closed");

        // -------------------------
        // Navigation Flow
        // -------------------------
        HomePage homePage = new HomePage(page);
        
        homePage.clickSearchBar();
        homePage.clickInternshipsTab();
        
        String mobile=SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp(); 
        String invalidOtp = SignUpUtility.generateInvalidOtp();
        
        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();
        callback.clickLogin();
        callback.enterEmailId(mobile);
        
        GetHRcallDataValidation data = new GetHRcallDataValidation(page);
        
        Assert.assertEquals(
        		data.signUpSideContentHeader().textContent().trim(),
                "Why Should You Sign In?");
        
        Assert.assertEquals(
        		data.SignUpSideContentFirstDescription().textContent().trim(),
                "Stay Ahead in Your Career: Access the latest internship opportunities tailored to your skills, industry trends, and career aspirations.");
        
        Assert.assertEquals(
        		data.SignUpSideContentSecondDescription().textContent().trim(),
                "Instant Notifications: Be the first to know when new internships matching your profile are posted on Promilo.");
        
        Assert.assertEquals(
        		data.SignUpSideContentThirdDescription().textContent().trim(),
                "Get Trusted Insights: Discover authentic reviews about potential companies and insights shared by peers and professionals.");
        
        Assert.assertEquals(
        		data.SignUpSideContentFourthDescription().textContent().trim(),
                "Guaranteed Privacy: Your information is safe with us. We ensure no unsolicited third-party communications.");
                
        callback.clickOnSendVerificationButton();
        callback.enterEmailId("9000025134");
        callback.clickOnSendVerificationButton();
        callback.enterNumber(invalidOtp);
        
        // ===================== OTP PAGE VALIDATION =====================
        Locator otpHeader = data.otpVerificationHeader();
        String otpPageDescription = data.otpPageDescription().textContent().trim();
        String expectedOtpPageDescription = "Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.";
        assertEquals(otpPageDescription, expectedOtpPageDescription);
        
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
        callback.resendClick();
        callback.enterNumber(otp);
        callback.clickSubmitButton();
       
        // ===================== LANGUAGE SELECTION VALIDATION =====================
        callback.clickLanguage();
     
        Locator languageText = data.chooseLanguageText();
        String chooseLanguageText =data.chooseLanguageText().textContent().trim();
        assertTrue( chooseLanguageText.contains("Please Select your preferred language with"), "Choose language text format is incorrect");
        String campaignName1 =chooseLanguageText.replace("Please Select your preferred language with", "").replace("This will make it easier for you and HR", "") .replace(".", "").trim();
        assertFalse( chooseLanguageText.isEmpty(),
            "Campaign name is missing in Choose Language text");
        
      
        callback.clickSubmit();
        callback.clickOnSubmit();
        
        // ===================== SCREENING PAGE & THANK YOU VALIDATION =====================
        Locator screeningQuestion = data.takeMomentSideText();
        String submitPageText = data.takeMomentSideText().textContent().trim();
        String expectedSubmitPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
        assertEquals(submitPageText, expectedSubmitPageText);
        String takeMomentText = data.takeMomentText().textContent().trim();
        String expectedTakeMomentText="Please take a moment to answer the below questions.";
        assertEquals(takeMomentText, expectedTakeMomentText);
   
        callback.clickOncheckBox();
        callback.clickOnSubmit();
    }

}

    

