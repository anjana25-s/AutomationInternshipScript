package com.promilo.automation.internship.askus;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.LoginPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.pageobjects.AskusDataValidation;
import com.promilo.automation.internship.utilities.LoginUtility;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class AskUsRegisteredUser extends Baseclass{
	
	
@Test
	public void AskUs3Test() throws InterruptedException{
		
     
 // ---------- LOGIN USING UTILITY ----------
    LoginUtility loginUtil = new LoginUtility(page);
    loginUtil.loginWithSavedUser();  

	
	  HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");
        

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();
        
        AskUsPage askus=new AskUsPage(page);
        askus.clickAskUs();
        askus.enterQuery("Good evening");
        
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

        askus.clickOnButton();
       
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        
        
        String actualMessage = thankYou.successPopup().textContent().trim();
         System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");

       
   	
   	
}}   
   