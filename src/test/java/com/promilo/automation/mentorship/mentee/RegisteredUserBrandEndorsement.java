package com.promilo.automation.mentorship.mentee;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
<<<<<<< HEAD
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.pagepbjects.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserBrandEndorsement extends Baseclass{

	
	
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | Hardcoded Test");

        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
        
        
        
     // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
        Thread.sleep(2000);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("karthik");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);
        

        // Navigate to mentor details
        DescriptionPage GetMentorCall = new DescriptionPage(page);
        GetMentorCall.allLink().click();
        GetMentorCall.brandEndorsement().click();
        GetMentorCall.bookEnquiry().nth(1).click();
        

        // -------------------- Fill Brand Endorsement Form --------------------
        MentorshipFormComponents fillForm = new MentorshipFormComponents(page);
page.locator("//input[@name='userName']").fill("karthik");
        // Generate random 5-digit number
        int randomNum = 10000 + new Random().nextInt(90000);

        // Generate random phone number
        String randomPhone = "90000" + randomNum;


        // Fill phone and email fields
page.locator("//input[@name='userMobile']").fill(randomPhone);
        // Store in Baseclass
        Baseclass.generatedPhone = randomPhone;
=======
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserBrandEndorsement extends BaseClass{

	
	
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }

    @Test
    public void applyForJobWithInvalidData() throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup credentials not found for suite.");
        }

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("❌ Apply for Job Invalid OTP | Hardcoded Test");

        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        LandingPage landingPage = new LandingPage(page);
        try { landingPage.getPopup().click(); } catch (Exception ignored) {}
        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " + registeredEmail);
        
        
        
        
     // -------------------- Mentorship Module --------------------
        DashboardPage dashboard = new DashboardPage(page);
        Thread.sleep(2000);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("karthik");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);
        

        // Navigate to mentor details
        DescriptionPage GetMentorCall = new DescriptionPage(page);
        GetMentorCall.allLink().click();
        GetMentorCall.brandEndorsement().click();
        GetMentorCall.bookEnquiry().nth(1).click();
        

        // -------------------- Fill Brand Endorsement Form --------------------
        MentorshipFormComponents fillForm = new MentorshipFormComponents(page);
page.locator("//input[@name='userName']").fill("karthik");
        // Generate random 5-digit number
        int randomNum = 10000 + new Random().nextInt(90000);

        // Generate random phone number
        String randomPhone = "90000" + randomNum;


        // Fill phone and email fields
page.locator("//input[@name='userMobile']").fill(randomPhone);
        // Store in Baseclass
        BaseClass.generatedPhone = randomPhone;
>>>>>>> refs/remotes/origin/mentorship-Automation-on-Mentorship-Automation

        // Print the randomly generated email and phone
        System.out.println("✅ Randomly generated phone: " + randomPhone);

        // Submit enquiry
        GetMentorCall.bookAnEnquiry().click();

        // -------------------- OTP Handling --------------------
        String otp = "9999";
        if (otp.length() < 4) {
            throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
        }

        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator(
                    "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                    .setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));

            boolean filled = false;
            for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(otpChar);

                String currentValue = otpField.evaluate("el => el.value").toString().trim();
                if (currentValue.equals(otpChar)) {
                    filled = true;
                } else {
                    page.waitForTimeout(500);
                }
            }

            if (!filled) {
                throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
            }
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        // -------------------- Brand Endorsement Details --------------------
        page.locator("//span[text()='Type of brand endorsement']").click();
        page.locator("(//label)").first().click();
        page.locator("//textarea[@placeholder='Type your message here...']").fill("Hello");
        page.locator("//button[text()='Submit']").nth(1).click();

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

        MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
        ThankYouPopup thankYou = new ThankYouPopup(page);


        thankYou.myPreference().click();
        
        
        
       page.navigate("https://mailosaur.com/app/servers/8mgfvj1x/messages/inbox");
       
       MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
       mailsaurCredenatinals.MialsaurMail();
       mailsaurCredenatinals.MailsaurContinue();
       mailsaurCredenatinals.MailsaurPassword();
       mailsaurCredenatinals.MailsaurLogin();
       
       
       page.locator("//p[contains(text(),'has shown interest in working with you for a Brand Endorsement ')]").first().click();
       page.locator("//span[@class='tinyMce-placeholder']").textContent();
       page.locator("//p[contains(text(),'Good news!')]").textContent();
       page.locator("//button[normalize-space()='Pending']").textContent();
       page.locator("//span[contains(text(),'Accept')]").click();
       
       page.waitForTimeout(4000);
       
       
       page.bringToFront();
       
       page.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']//*[name()='path' and contains(@fill,'currentCol')]").click();
       page.locator("//p[contains(text(),'Thank you for taking the time to fill out the form for brand endorsement')]").first().click();
       page.locator("//span[@class='tinyMce-placeholder']").textContent();
       System.out.println(page.locator("//p[contains(text(),'Thank you for taking the time')]").textContent());
       page.locator("(//tbody)[15]").textContent();
       
       page.locator("//button[contains(text(),'View')]").click();
       
       

}
    
    
}
