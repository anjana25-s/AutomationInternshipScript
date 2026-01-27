package com.promilo.automation.internship.getHRcall;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.CallbackPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.LoginPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.pageobjects.GetHRcallDataValidation;
import com.promilo.automation.internship.pageobjects.MyPreferenceCardValidation;
import com.promilo.automation.internship.utilities.LoginUtility;

import basetest.Baseclass;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class CallBackRegisteredUser extends Baseclass {

    @Test
    public void HRcallTest() throws InterruptedException {
       
      // ---------- LOGIN USING UTILITY ----------
        LoginUtility loginUtil = new LoginUtility(page);
        loginUtil.loginWithSavedUser();  

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");
         
         CallbackPage callback = new CallbackPage(page);
         callback.clickInternshipsTab();
         callback.clickAutomationTesterCard();
         callback.clickGetHRCall();
         callback.clickDropDown();
         callback.selectValue();
         callback.selectValue();
         callback.selectValue();
         callback.clickOnValue();
         callback.clickOnValue();
         callback.clickOnValue();
         callback.clickondropdown();
         callback.closeValueClick();
         callback.toggleClick();
         callback.clickButton();
         
         // ===================== HR CALL POPUP VALIDATION =====================
         GetHRcallDataValidation data = new GetHRcallDataValidation(page);

      // ===================== LANGUAGE SELECTION VALIDATION =====================
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
         
         callback.notificationIcon();
         
         String notificationText =
                 data.latestInAppNotification().textContent().trim();

         Assert.assertTrue(
                 notificationText.contains(
                     "HR Call Request Pending... Your HR call request is currently in pending status. " +
                     "Stay tuned! Our team will be in touch soon." ),
                 "HR Call Pending notification text is incorrect or missing"
         );
         
         callback.closeNotification();
         callback.clickOnCard();
            
         Page businessPage = context.newPage();
         businessPage.navigate("https://stagebusiness.promilo.com/");
         businessPage.waitForLoadState();

         BusinessPage advertiser = new BusinessPage(businessPage);

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

            
            // Switch back to user portal
            page.bringToFront();
            page.waitForLoadState();
            
            // -------------------------
            // Navigation Flow
            // -------------------------
            HomePage homePage1 = new HomePage(page);
            homePage.clickHomeTab();
            homePage.clickSearchBar();
            homePage.clickInternshipsTab();
            
            CallbackPage callback1 = new CallbackPage(page);
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
                    notificationText.contains(
                        "HR Call Request Pending... Your HR call request is currently in pending status. " +
                        "Stay tuned! Our team will be in touch soon." ),
                    "HR Call Pending notification text is incorrect or missing"
            );

            businessPage.bringToFront();
            businessPage.waitForLoadState();
            
            //advertiser.clickMyProspect();
            //advertiser.clickInternships();
            //advertiser.clickCallBack();
            advertiser.clickApprove();
            advertiser.clickProceed();
            advertiser.clickDone();

            
         }}