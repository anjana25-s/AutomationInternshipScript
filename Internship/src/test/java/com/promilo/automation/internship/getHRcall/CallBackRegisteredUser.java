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
         
         // ===================== HR CALL POPUP VALIDATION =====================
         GetHRcallDataValidation data = new GetHRcallDataValidation(page);

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

         }}