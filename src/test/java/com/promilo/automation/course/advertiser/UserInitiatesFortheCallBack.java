package com.promilo.automation.course.advertiser;

import java.io.IOException;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

class UserInitiatesFortheCallBack extends BaseClass{
	
	

    @Test(
		      dependsOnMethods = {
				        "com.promilo.automation.guestuser.courses.interest.TalkToExperts.TaltoExpertIntrest"
				      } 
				    )
    public void CallbackOrTalktoExpertApprovefunctionalityTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📊 Callback/Talk to Expert Approve Functionality");
        
        
        Page page = initializePlaywright();
        test.info("🌐 Playwright initialized and browser launched.");


        page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");


        MailsaurCredentials approveMail = new MailsaurCredentials(page);
        approveMail.MialsaurMail();
        approveMail.MailsaurContinue();
        approveMail.MailsaurPassword();
        approveMail.MailsaurLogin();
        
        
        page.locator("//p[contains(text(),'Exciting News: Prospects Are Reaching Out via Talk to Expert for ')]").first().click();
        
        page.locator("//span[contains(text(),'Dear ')]").textContent();
        page.locator("//p[contains(text(),'This is regarding your')]").textContent();
        page.locator("//p[contains(text(),'We have exciting news! Your ad')]").textContent();
        page.locator("//p[contains(text(),'You have received interest')]").textContent();
    Locator table=    page.locator("//p[contains(text(),'Click the link below to view')]");
    table.textContent();      
        page.locator("//p[contains(text(),'regards')]").textContent();
        
        
        page.locator("//p[contains(text(),'Promilo Team')]").textContent();
        
        
        page.locator("//span[contains(text(),'Prospect')]").click();


    }

}
