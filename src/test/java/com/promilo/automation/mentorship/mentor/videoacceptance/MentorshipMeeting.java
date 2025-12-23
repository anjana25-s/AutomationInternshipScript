package com.promilo.automation.mentorship.mentor.videoacceptance;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.Mymeetings.MymeetingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipMeeting extends Baseclass{
	
	
	@Test
    public void JoinmeetingFunctionalityTest() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📅 Join Meeting Functionality | Hardcoded Data Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366,1000 );
        test.info("🌐 Navigated to URL: " + prop.getProperty("url"));
        System.out.println("🌐 Navigated to URL: " + prop.getProperty("url"));

        String[][] credentials = {
                {"testuser08622@gmail.com", "9999"},
        };

        for (String[] creds : credentials) {
            String email = creds[0];
            String password = creds[1];

            try {
                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

                // Popup
                mayBeLaterPopUp.getPopup().click();
                test.info("✅ Popup closed.");
                System.out.println("✅ Popup closed.");

                // Login
                mayBeLaterPopUp.clickLoginButton();
                test.info("➡️ Clicked Login button.");
                System.out.println("➡️ Clicked Login button.");

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(email);
                test.info("✍️ Entered Email: " + email);
                System.out.println("✍️ Entered Email: " + email);

                loginPage.loginWithOtp().click();
                test.info("📩 Clicked on Login with OTP.");
                System.out.println("📩 Clicked on Login with OTP.");

                loginPage.otpField().fill(password);
                test.info("🔑 Entered OTP: " + password);
                System.out.println("🔑 Entered OTP: " + password);

                loginPage.loginButton().click();
                test.info("🔓 Clicked Login button.");
                System.out.println("🔓 Clicked Login button.");

                Thread.sleep(3000);

                // Dialog listener
                page.onDialog((Dialog dialog) -> {
                    System.out.println("⚠️ Dialog message: " + dialog.message());
                    test.info("⚠️ Dialog appeared: " + dialog.message());
                    dialog.accept();
                });

                // Navigate to meetings
                page.locator("//span[normalize-space()='My Meetings']").click();
                test.info("📅 Clicked My Meetings tab.");
                System.out.println("📅 Clicked My Meetings tab.");

                page.locator("//div[text()='All']").click();
                test.info("📌 Clicked All meetings.");
                System.out.println("📌 Clicked All meetings.");

                page.locator("//span[text()='Join Now']").click();
                test.info("▶️ Clicked Join Now.");
                System.out.println("▶️ Clicked Join Now.");

                page.locator("//button[text()='Join now']").click();
                test.info("🔗 Clicked Join Now confirmation.");
                System.out.println("🔗 Clicked Join Now confirmation.");

                
                
                // Meeting page actions
                MymeetingPage mymeeting = new MymeetingPage(page);

               // mymeeting.audioMuteButton().click();
                page.locator(".flex.items-center.justify-center.rounded-lg.bg-white > .cursor-pointer").first().click();

                test.info("🔇 Muted Audio.");
                System.out.println("🔇 Muted Audio.");

                mymeeting.videoTogglebutton().first().click();
                test.info("📷 Toggled Video.");
                System.out.println("📷 Toggled Video.");

               
Thread.sleep(3000);
                mymeeting.RisehandButton().first().click();
                test.info("✋ Clicked Raise Hand button.");
                System.out.println("✋ Clicked Raise Hand button.");
                
                
                
                
               // mymeeting.chatButton().click();
                
                page.locator("//div[@style='opacity: 1; transform: scale(1); transition: 200ms linear;']").nth(5).click();

                test.info("💬 Opened Chat window.");
                System.out.println("💬 Opened Chat window.");

                mymeeting.chatTextfield().fill("Hi");
                test.info("✍️ Entered Chat message: Hi");
                System.out.println("✍️ Entered Chat message: Hi");

                mymeeting.chatSendbutton().click();
                test.info("📤 Sent Chat message.");
                System.out.println("📤 Sent Chat message.");

                mymeeting.chatExitbutton().click();
                test.info("🚪 Exited Chat window.");
                System.out.println("🚪 Exited Chat window.");

                test.pass("✅ Meeting joined and actions done for: " + email);
                System.out.println("✅ Meeting joined and actions done for: " + email);
                
                
                
                
                
                page.navigate("https://stage.promilo.com/navigationbar/mystuff/myaccount/mymeeting");
                
                
                
                page.locator("//div[text()='All']").click();
                test.info("📌 Clicked All meetings.");
                System.out.println("📌 Clicked All meetings.");

                page.locator("//span[text()='Join Now']").click();
                test.info("▶️ Clicked Join Now.");
                System.out.println("▶️ Clicked Join Now.");

                page.locator("//button[text()='Join now']").click();
                test.info("🔗 Clicked Join Now confirmation.");
                System.out.println("🔗 Clicked Join Now confirmation.");
                
                
                
             // Meeting page actions
                MymeetingPage mymeeting2 = new MymeetingPage(page);

               // mymeeting.audioMuteButton().click();
                page.locator(".flex.items-center.justify-center.rounded-lg.bg-white > .cursor-pointer").first().click();

                test.info("🔇 Muted Audio.");
                System.out.println("🔇 Muted Audio.");

                mymeeting2.videoTogglebutton().first().click();
                test.info("📷 Toggled Video.");
                System.out.println("📷 Toggled Video.");
                
                
                mymeeting2.chatExitbutton().click();
                test.info("🚪 Exited Chat window.");
                System.out.println("🚪 Exited Chat window.");
                
                

            } catch (Exception e) {
                test.fail("❌ Exception while joining meeting for " + email + ": " + e.toString());
                System.out.println("❌ Exception while joining meeting for " + email + ": " + e.toString());
                e.printStackTrace();

           
            
            
            
            
            }
            }
        
	
	}
}
