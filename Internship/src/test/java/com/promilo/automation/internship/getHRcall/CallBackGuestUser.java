package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CallBackGuestUser extends Baseclass {

    @Test
    public void CallBackGuestUser() {

        // ===================== INITIAL SETUP =====================
        page.locator("//button[text()='May be later!']").click();

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();

        // Generate test data
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        System.out.println("Generated Email: " + email);
        System.out.println("Generated Mobile: " + mobile);

        // ===================== HR CALL FLOW =====================
        CallbackPage callback = new CallbackPage(page);
        callback.clickInternshipsTab();
        callback.clickAutomationTesterCard();
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
        assertEquals(getHrCallfooterText, expectedGetHrCallfooterText);

        // ===================== SUBMIT FORM =====================
        callback.enterStudentName("Diya");
        callback.studentMobileNumber(mobile);
        callback.enterMailId(email);
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();

        // ===================== OTP PAGE VALIDATION =====================
        Locator otpHeader = data.otpVerificationHeader();
        String otpPageDescription = data.otpPageDescription().textContent().trim();
        String expectedOtpPageDescription = "Start Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextStart Your Career JourneyStart your career with access to exclusive internships opportunities and personalized support.Tailored Internship MatchesReceive customized internship recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.";
        assertEquals(otpPageDescription, expectedOtpPageDescription);

        // Enter OTP
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

        callback.myPreference();

       
     // ===================== My Preference Card Validation =====================

        MyPreferenceCardValidation myPreference =
                new MyPreferenceCardValidation(page);

        // Expected values
        String expectedRole = "Artificial Intelligence";
        String expectedBrandName = "UST Global";
        String expectedDuration = "6 Months";
        String expectedLocation = "Bihar - Other";
        String expectedServiceName = "Get HR Call";
        String expectedStipend = "25K - 35K";

        // ===================== Internship Role =====================
        String actualRole =
                myPreference.internshipRole().innerText().trim();

        Assert.assertEquals(
                actualRole,
                expectedRole,
                "❌ Internship role mismatch"
        );

        // ===================== Duration =====================
        String actualDuration =
                myPreference.duration().innerText().trim();

        Assert.assertEquals(
                actualDuration,
                expectedDuration,
                "❌ Duration mismatch"
        );

        // ===================== Brand Name =====================
        String actualBrandName =
                myPreference.brandName().innerText().trim();

        Assert.assertEquals(
                actualBrandName,
                expectedBrandName,
                "❌ Brand name mismatch"
        );

        // ===================== Service Name =====================
        String actualServiceName =
                myPreference.serviceName().innerText().trim();

        Assert.assertEquals(
                actualServiceName,
                expectedServiceName,
                "❌ Service name mismatch"
        );

        // ===================== Stipend =====================
        String actualStipend =
                myPreference.stipend().innerText().trim();

        Assert.assertEquals(
                actualStipend,
                expectedStipend,
                "❌ Stipend mismatch"
        );

        // ===================== Location =====================
        String actualLocation =
                myPreference.location().innerText().trim();

        Assert.assertEquals(
                actualLocation,
                expectedLocation,
                "❌ Location mismatch"
        );

        System.out.println("✅ My Preference card data validated successfully");
}}