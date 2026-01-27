package com.promilo.automation.internship.getHRcall;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.MyBillingPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;
import com.promilo.automation.internship.pageobjects.MyProspectCardValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class GetHRCallFromSearchPage extends Baseclass {
	

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
        
       // Generate test data
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp();

        System.out.println("Generated Email: " + email);
        System.out.println("Generated Mobile: " + mobile);

        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();
        callback.enterStudentName("Diya");
        
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
        
        callback.studentMobileNumber(mobile);
        callback.enterMailId(email);
        callback.industryDropdown();
        callback.selectValue();
        callback.selectValue();
        callback.selectValue();
        callback.clickOnValue();
        callback.clickOnValue();
        callback.clickOnValue();
        callback.closeDropdown();
        callback.closeValueClick();
        callback.toggleClick();
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
       // callback.selectLanguage();
        
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
        
     // ===================== SCREENING PAGE & THANK YOU VALIDATION =====================
        callback.clickOncheckBox();
        callback.typeAnswer("yes");
        
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

     // ===================== Internship Role (DYNAMIC) =====================
        String actualRole =
                myPreference.internshipRole().innerText().trim();

        Assert.assertFalse(
                actualRole.isEmpty(),
                "❌ Internship role is missing in My Preference card"
        );


        // ===================== Duration (DYNAMIC) =====================
        String actualDuration =
                myPreference.duration().innerText().trim();

        Assert.assertFalse(
                actualDuration.isEmpty(),
                 "❌ Duration mismatch"
        );

        // ===================== Brand Name (DYNAMIC) =====================
        String actualBrandName =
                myPreference.brandName().innerText().trim();

        Assert.assertFalse(
                actualBrandName.isEmpty(),
                "❌ Brand name is missing in My Preference card"
        );

        // ===================== Service Name (SEMI-DYNAMIC) =====================
        String actualServiceName =
                myPreference.serviceName().innerText().trim();

        Assert.assertTrue(
                actualServiceName.contains("Get HR Call"),
                "❌ Service name format is incorrect"
        );

        // ===================== Stipend (DYNAMIC FORMAT) =====================
        String actualStipend =
                myPreference.stipend().innerText().trim();

        Assert.assertFalse(
                actualStipend.isEmpty(),
                "❌ Stipend is missing in My Preference card"
        );

        // ===================== Location (DYNAMIC FORMAT) =====================
        String actualLocation =
                myPreference.location().innerText().trim();

        Assert.assertFalse(
                actualLocation.isEmpty(),
                "❌ Location is missing in My Preference card"
        );

        System.out.println("✅ My Preference card data validated successfully");
        
         callback.clickApplyNow();
         
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
        advertiser.clickDone();
        
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
        String prospectCampaignName = prospectCard.getCampaignName();

        Assert.assertFalse(
                prospectCampaignName.isEmpty(),
                "❌ Campaign name is empty in prospect card"
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

      
        
  

}}