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

import basetest.Baseclass;

import org.testng.Assert;

import static org.testng.Assert.assertEquals;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class CallBackRegisteredUser extends Baseclass {

    @Test
    public void HRcallTest() throws InterruptedException {
       
        LoginPage loginPage = new LoginPage(page);
        loginPage.clickMaybeLater();
        loginPage.clickLoginButtonOnHome();
        loginPage.enterEmail("9000029985");
        loginPage.enterPassword("qwertyui");
        loginPage.clickLoginSubmit();
        
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
         languageText.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

         Assert.assertTrue(languageText.isVisible(), "❌ Language selection text missing");
        
         System.out.println("✅ Language selection texts validated successfully.");
        callback.clickSubmit();
        
        
         // ===================== SCREENING PAGE & THANK YOU VALIDATION =====================
         callback.clickOncheckBox();
         Locator screeningQuestion = data.takeMomentText();
         screeningQuestion.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));

         Assert.assertTrue(data.takeMomentText().isVisible(), "❌ 'Take a moment' text missing");
         System.out.println("✅ screening question validated successfully.");
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