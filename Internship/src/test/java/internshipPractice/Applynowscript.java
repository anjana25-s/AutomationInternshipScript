package internshipPractice;
import com.microsoft.playwright.Page;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.LoginPage;

import basetest.Baseclass;

import org.testng.Assert;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public class Applynowscript extends Baseclass{
    @Test
    public void applyNowTest() {
        
        LoginPage loginPage = new LoginPage(page);
        loginPage.clickMaybeLater();
        loginPage.clickLoginButtonOnHome();
        loginPage.enterEmail("pavi12@yopmail.com");
        loginPage.enterPassword("pavi1234");
        loginPage.clickLoginSubmit();
       System.out.println("Login completed");

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");
        page.waitForTimeout(5000);
       
    InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();
        internshipPage.clickApplyNow();
        internshipPage.clickLanguage();
        internshipPage.clickDate();
        internshipPage.clickTime();
        internshipPage.clickBookSlot();
        System.out.println("Slot booked");
        page.screenshot(new Page.ScreenshotOptions()
        	    .setPath(Paths.get("slot-booked4.png")));

        internshipPage.clickMyInterestTab();
        System.out.println("Navigated to My Interest");
        
        page.waitForTimeout(5000);
        
       Assert.assertTrue(page.locator("//div[text()='You already shown interest on this campaign.']").isVisible(), 
        		"Toaster displayed");
        
        page.screenshot(new Page.ScreenshotOptions()
        	    .setPath(Paths.get("slot-booked1.png")));

    }}   