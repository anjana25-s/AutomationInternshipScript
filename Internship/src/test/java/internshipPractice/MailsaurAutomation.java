package internshipPractice;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;


import basetest.Baseclass;
import practice.Mailosaur;

public class MailsaurAutomation extends Baseclass {
	@Test
	public void MailsaurAutomation() throws InterruptedException {
       
        Mailosaur mp=new Mailosaur(page);
        mp.mailId().fill("karthiku7026@gmail.com");
       
        mp.continueButton().click();
        page.waitForTimeout(2000);
        mp.password().fill("Karthik@88");
        page.waitForTimeout(2000);
        mp.loginButton().click();
        page.waitForTimeout(2000);
       
       
        
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("screenshot2.png")));
                
       

        System.out.println("Screenshot saved successfully.");
    }
}

	

