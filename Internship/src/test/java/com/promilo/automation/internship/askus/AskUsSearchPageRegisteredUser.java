package com.promilo.automation.internship.askus;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.LoginUtility;
import basetest.Baseclass;

public class AskUsSearchPageRegisteredUser extends Baseclass{
	
	
@Test
	public void AskUs3Test() throws InterruptedException{
		
     
 // ---------- LOGIN USING UTILITY ----------
    LoginUtility loginUtil = new LoginUtility(page);
    loginUtil.loginWithSavedUser();  

    // ------------------------
    // NAVIGATE TO INTERNSHIP
    // ------------------------
    HomePage homePage = new HomePage(page);
    
    homePage.clickSearchBar();
    homePage.clickInternshipsTab();
    
    AskUsPage askUsPage=new AskUsPage(page);
    askUsPage.clickAskUs();
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
