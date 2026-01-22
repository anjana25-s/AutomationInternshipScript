package com.promilo.automation.internship.askus;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.SignupPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.SignUpUtility;
import com.promilo.automation.internship.utilities.TestAccountStore;

import basetest.Baseclass;

public class AskUsSearchPageSignupwithMobile extends Baseclass {

    @Test
    public void AskUs2Test() throws InterruptedException {

        SignupPage signup = new SignupPage(page);

        signup.clickMaybeLater();
        signup.clickInitialSignupButton();

        // Step 3: Generate random mobile
        String mobileNumber = SignUpUtility.generateRandomMobile();
        System.out.println("Generated Mobile: " + mobileNumber);
        String password=SignUpUtility.generateRandomPassword();
        String otp = SignUpUtility.getFixedOtp();  // → always 9999
        String email = SignUpUtility.generateRandomEmail();

        signup.enterEmailOrPhone(mobileNumber);
        signup.clickVerificationCode();
        signup.enterMobileOtp(otp);
        signup.enterPassword(password);
        signup.clickFinalSignupButton();
        signup.isSignupSuccess();
        
        TestAccountStore.save(mobileNumber,password);

        
        HomePage homePage = new HomePage(page);
        homePage.clickSearchBar();
        homePage.clickInternshipsTab();
      
        AskUsPage askUsPage = new AskUsPage(page);
        askUsPage.clickAskUs();
        askUsPage.enterName("Ammu");
        askUsPage.enterMail(email);
        askUsPage.enterQuery("Good evening");
        AskusDataValidation validation = new AskusDataValidation(page);
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
