package com.promilo.automation.hrcallNegativeflow;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;
import basetest.Baseclass;

public class CallbackSignupDescPageNegative extends Baseclass {
	

    @Test
    public void CallbackSignupDescPage() throws InterruptedException {

        // -------------------------
        // Close Initial Popup
        // -------------------------
        page.locator("//button[text()='May be later!']").click();

        // -------------------------
        // Navigation Flow
        // -------------------------
       HomePage homePage = new HomePage(page);
       homePage.clickInternships();

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

      
        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();
        callback.clickLogin();
        callback.clickSignup();
      
       
         // Generate a random email & OTP
        String email = SignUpUtility.generateRandomEmail();
        String mobileNumber = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp(); 
        String invalidOtp = SignUpUtility.generateInvalidOtp();
        
      
        callback.enterNameInTextfield("Anjana");
        callback.studentMobileNumber(mobileNumber);
        callback.enterEmail("pavi12@yopmail.com");
        callback.industryDropdown();
        callback.clickOnValue();
        callback.selectValue();
        callback.closeDropdown();
        page.keyboard().press("Enter");
        callback.enterPassword("abc@12345");
        callback.clickOnIcon();
        callback.clickCloseIcon();
        callback.clickOnRegButton();
        
        callback.enterNameInTextfield("An");
        callback.studentMobileNumber("anjdi");
        callback.enterEmail("paviyopmail.com");
        callback.enterNameInTextfield("9000");
        callback.studentMobileNumber("9000027677787");
        callback.enterEmail("5534535");
        callback.enterNameInTextfield("@%^");
        callback.studentMobileNumber("%$^");
        callback.enterEmail("^%&$");
        callback.enterPassword("@#@");
        
        // ===================== HR CALL POPUP VALIDATION =====================
        GetHRcallDataValidation data = new GetHRcallDataValidation(page);
        
        callback.enterNameInTextfield("Anjana");
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
                
        callback.studentMobileNumber("9000025134");
        callback.enterEmail("gshsj@hshs.jdjej");
        callback.enterPassword("abc@12345");
        callback.clickOnRegButton();
        
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

        System.out.println("✅ Invalid OTP validation completed");  
        
        
                callback.enterNumber(invalidOtp);
                callback.clickSubmitButton();
        		callback.resendClick();
        		callback.enterNumber(otp);
        		callback.clickSubmitButton();
        		
        		  // -------------------- NEGATIVE FLOW — SCREENING QUESTION --------------------
                callback.clickLanguage();
                callback.selectLanguage();
                
                Locator languageText = data.chooseLanguageText();
                String nextPageText = data.nextPageInfoText().first().textContent().trim();
                String expectedNextPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
                assertEquals(nextPageText, expectedNextPageText);
                page.waitForTimeout(2000);
                String chooseLangaugeText = data.chooseLanguageText().textContent().trim();
                String expectedChooseLangaugeText="Please Select your preferred language with UST Global. This will make it easier for you and HR to connect as you choose. ";
                assertEquals(chooseLangaugeText, expectedChooseLangaugeText);
                
                callback.clickOnLangTwo();
                callback.clickOnLangThree();
                callback.clickSubmit();
                callback.clickOnSubmit();
                
                Locator screeningQuestion = data.takeMomentSideText();
                String submitPageText = data.takeMomentSideText().textContent().trim();
                String expectedSubmitPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
                assertEquals(submitPageText, expectedSubmitPageText);
                String takeMomentText = data.takeMomentText().textContent().trim();
                String expectedTakeMomentText="Please take a moment to answer the below questions.";
                assertEquals(takeMomentText, expectedTakeMomentText);
                callback.clickOncheckBox();
                callback.clickOnSubmit();


                System.out.println("✅ Screening question validation completed");

            }
        }





