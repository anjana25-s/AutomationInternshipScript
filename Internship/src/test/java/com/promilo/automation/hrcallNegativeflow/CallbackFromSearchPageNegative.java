package com.promilo.automation.hrcallNegativeflow;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.negative.GetHRcallPopup;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class CallbackFromSearchPageNegative extends Baseclass {
	

    @Test
    public void GetHRCallSearchPage() throws InterruptedException {

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
        
        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();

        GetHRcallPopup popup = new GetHRcallPopup(page);

        // -------------------- NEGATIVE FLOW — EMPTY FIELDS --------------------
        callback.clickButton();

        popup.verifyEmptyNameField();
        popup.verifyEmptyPhoneField();
        popup.verifyEmptyEmailField();
        popup.verifyIndustryDropdown();

        System.out.println("✅ Empty field validation completed");

        // -------------------- NEGATIVE FLOW — INVALID INPUT DATA --------------------
        callback.enterStudentName("Di");
        
        // ===================== HR CALL POPUP VALIDATION =====================
        GetHRcallDataValidation data = new GetHRcallDataValidation(page);

        String getHrCallPopUpDescription = data.getHrCallPopupDescription().textContent().trim();
        String expectedGetHrCallPopUpDescription = "Why Register to Get an HR Callback for Your First Internship?Take Charge of Your Career: Connect with recruiters and apply for internships that match your aspirations.Stay Updated: Receive real-time notifications about internship openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored internship alerts ensure you don't miss the right openings.Exclusive Resources: Unlock premium tools and tips for acing interviews and securing your dream internship.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.Take Charge of Your Career: Connect with recruiters and apply for internships that match your aspirations.Stay Updated: Receive real-time notifications about internship openings tailored to your profile.Direct HR Access: Ensure your application reaches the right recruiter for prompt callback opportunities.Personalized Opportunities: Tailored internship alerts ensure you don't miss the right openings.Exclusive Resources: Unlock premium tools and tips for acing interviews and securing your dream internship.Privacy Guaranteed: Your data is safe—no unauthorized communication or spam.PreviousNext";
        assertEquals(getHrCallPopUpDescription, expectedGetHrCallPopUpDescription);
        
        String getHrCallHeaderText =data.getHrCallHeaderText().textContent().trim();
        assertTrue(getHrCallHeaderText.contains("Get an HR Call from"),"HR Call header format is incorrect"  );
        
        String campaignName = getHrCallHeaderText.replace("Get an HR Call from", "").replace("!", "").trim();
        assertFalse( campaignName.isEmpty(), "Campaign name is missing in HR Call header" );

        String enableWhatssappNotification = data.enableWhatsappNotification().textContent().trim();
        String expectedEnableWhatssappNotification="Enable updates & important information on Whatsapp";
        assertEquals(enableWhatssappNotification, expectedEnableWhatssappNotification);
        String getHrCallfooterText = data.getHrCallFooterText().textContent().trim();
        String expectedGetHrCallfooterText="By proceeding ahead you expressly agree to the PromiloTerms & Conditions";
        assertEquals(getHrCallfooterText, expectedGetHrCallfooterText);
        
        callback.studentMobileNumber("90000987");
        callback.enterMailId("diya33yopmail.com");
        callback.clickButton();

        callback.enterStudentName("94444333");
        callback.studentMobileNumber("90000987333333");
        callback.enterMailId("3442322");
        callback.clickButton();

        callback.enterStudentName("@#$");
        callback.studentMobileNumber("$#@");
        callback.enterMailId("#@#");
        callback.clickButton();

      System.out.println("✅ Invalid input validation completed");

        // -------------------- NEGATIVE FLOW — INVALID OTP --------------------
        page.reload();
        callback.clickGetHRCall();

        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String invalidOtp = SignUpUtility.generateInvalidOtp();
        String otp = SignUpUtility.getFixedOtp();

        callback.enterStudentName("Diya");
        callback.studentMobileNumber(mobile);
        callback.enterMailId("pavi12@yopmail.com");
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();
        callback.studentMobileNumber("9000025134");
        callback.enterMailId("gshsj@hshs.jdjej");
        callback.clickButton();

        callback.enterNumber(invalidOtp);
        callback.clickSubmitButton();
        callback.resendClick();
        
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
        
        callback.enterNumber(otp);
        callback.clickSubmitButton();
        
        // -------------------- NEGATIVE FLOW — SCREENING QUESTION --------------------
        callback.clickLanguage();
      
        
        Locator languageText = data.chooseLanguageText();
        String nextPageText = data.nextPageInfoText().first().textContent().trim();
        String expectedNextPageText="Get Selected Faster!Your answers will help the Recruiter select you faster to schedule an interview.";
        assertEquals(nextPageText, expectedNextPageText);
        page.waitForTimeout(2000);
        String chooseLanguageText =data.chooseLanguageText().textContent().trim();
        assertTrue( chooseLanguageText.contains("Please Select your preferred language with"), "Choose language text format is incorrect");
        String campaignName1 =chooseLanguageText.replace("Please Select your preferred language with", "").replace("This will make it easier for you and HR", "") .replace(".", "").trim();
        assertFalse( campaignName.isEmpty(),
            "Campaign name is missing in Choose Language text");
      
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



