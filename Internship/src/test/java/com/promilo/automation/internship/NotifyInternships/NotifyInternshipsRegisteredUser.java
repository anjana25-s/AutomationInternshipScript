package com.promilo.automation.internship.NotifyInternships;

import org.testng.Assert;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.internship.assignment.BusinessPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.LoginPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.utilities.LoginUtility;

import basetest.Baseclass;

public class NotifyInternshipsRegisteredUser extends Baseclass {

    @Test
    public void NotifyInternshipsRU() throws InterruptedException {
       
       // ---------- LOGIN USING UTILITY ----------
        LoginUtility loginUtil = new LoginUtility(page);
        loginUtil.loginWithSavedUser();  

       
       System.out.println("Login completed");
       
       HomePage homePage = new HomePage(page);
       homePage.clickInternships();
       System.out.println("Clicked Internships tab");
      
       NotifyInternshipsPage notify=new NotifyInternshipsPage(page);
        notify.clickBankManagerCard();
        notify.clickOnNotify();
       
        
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualMessage = thankYou.successPopup().textContent().trim();
         System.out.println("Success message displayed: " + actualMessage);
        Assert.assertEquals(actualMessage, "Thank You!", "Success message validation failed!");
      
        
        
     }}