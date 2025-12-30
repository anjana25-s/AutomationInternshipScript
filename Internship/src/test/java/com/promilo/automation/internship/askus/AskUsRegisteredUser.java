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

import basetest.Baseclass;

public class AskUsRegisteredUser extends Baseclass{
	
	
@Test
	public void AskUs3Test() throws InterruptedException{
		
        LoginPage loginPage = new LoginPage(page);
        loginPage.clickMaybeLater();
        loginPage.clickLoginButtonOnHome();
        loginPage.enterEmail("mithra12@yopmail.com");
        loginPage.enterPassword("mithra1234");
        loginPage.clickLoginSubmit();
        
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");
        

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();
        
        AskUsPage askus=new AskUsPage(page);
        askus.clickAskUs();
        askus.enterQuery("Good evening");
        AskusDataValidation validation = new AskusDataValidation(page);

        // -------------------------
        // ASK US PAGE VALIDATIONS
        // -------------------------
        assertTrue(
                validation.askUsHeaderText().isVisible(),
                "❌ Ask Us header text is not visible"
        );

        assertEquals(
                validation.askUsDescription().textContent().trim(),
                "Ask Us Anything for FreeGet personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com.",
                "❌ Ask Us description text mismatch"
        );

        assertTrue(
                validation.askUsFooterText().isVisible(),
                "❌ Ask Us footer text is not visible"
        );
        askus.clickOnButton();
       
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        
        
        String actualMessage = thankYou.successPopup().textContent().trim();
         System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");

       
   	
   	
}}   
   