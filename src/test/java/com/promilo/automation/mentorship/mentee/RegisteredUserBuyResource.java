package com.promilo.automation.mentorship.mentee;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class RegisteredUserBuyResource extends BaseClass {

	
	
	
	
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
	        
	        
	        
	        
	        DescriptionPage descriptionValidation = new DescriptionPage(page);
	        Locator buyResources = descriptionValidation.buyResources().first();
	        descriptionValidation.allLink().click();
	        buyResources.scrollIntoViewIfNeeded();
	        buyResources.click();

	        // -------------------- Fill form details --------------------
	        MentorshipFormComponents form = new MentorshipFormComponents(page);
	        page.locator("//input[@name='userName']").fill("karthik");

	        // Generate phone number starting with 90000 + 5 random digits
	        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
	        page.locator("//input[@name='userMobile']").fill(randomPhone);

	        

	        form.downloadResource().click();
	        
	        String otp= "9999";

	        if (otp == null || otp.length() < 4) {
	            throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
	        }

	        for (int i = 0; i < 4; i++) {
	            String otpChar = String.valueOf(otp.charAt(i));
	            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
	            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

	            int attempts = 0;
	            boolean filled = false;
	            while (!filled && attempts < 3) {
	                attempts++;
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
	                throw new RuntimeException("Failed to enter OTP digit " + (i + 1));
	            }
	            test.info("Entered OTP digit: " + otpChar);
	        }
	        
	        page.locator("//button[text()='Verify & Proceed']").click();
	        form.nextButton().click();

	        // -------------------- Invoice --------------------
	        form.InvoiceNameField().fill("Karthik");
	        form.StreetAdress1().fill("Dasarahalli");
	        form.StreetAdress2().fill("Bangalore");
	        form.pinCode().fill("560057");
	        form.yesRadrioBox().click();
	        form.gstNumber().fill("22AAAAA9999A1Z5");
	        form.panNumber().fill("AAAAA9999A");

	        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
	        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

	        // -------------------- Payment --------------------
	        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
	        paymentFunctionality.payOnline().click();
	        paymentFunctionality.payButton().click();

	        page.waitForTimeout(5000);

	        // Switch to iframe for payment
	        FrameLocator frame = page.frameLocator("iframe");
	        frame.getByTestId("contactNumber").fill("9000098765");

	        // Select "Wallet" option
	        frame.getByTestId("nav-sidebar").locator("div")
	                .filter(new Locator.FilterOptions().setHasText("Wallet"))
	                .nth(2)
	                .click();

	        // -------------------- Handle PhonePe Popup --------------------
	        Page page8 = page.waitForPopup(() -> {
	            page.locator("iframe").contentFrame()
	                .getByTestId("screen-container")
	                .locator("div")
	                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
	                .nth(2)
	                .click();
	        });

	        // Click Success in popup
	        page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();

	     // -------------------- Handle Download Tab --------------------
	        Page downloadTab = page.waitForPopup(() -> {
	            // This will auto-trigger when success is clicked
	        });
	        downloadTab.waitForLoadState();
	        logger.info("Download tab opened with URL: " + downloadTab.url());
	        downloadTab.close(); // ✅ close the tab safely

	        // Switch back to main page
	        page.bringToFront();

	        // -------------------- Validation --------------------
	        Locator thankYouPopup = page.locator(
	                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
	        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
	        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
	        

	        ThankYouPopup popupValidation = new ThankYouPopup(page);
	        popupValidation.downloadManually().click();
	        page.bringToFront();
	        popupValidation.myDownloadsButton().click();

	        // ✅ Use main page object for card validation instead of page8
	        MentorshipMyintrest cardValidation = new MentorshipMyintrest(page);
	        System.out.println(cardValidation.mentorName().textContent()); 
	        System.out.println(cardValidation.statusTag().textContent()); 
	        System.out.println(cardValidation.locationString().textContent());
	        System.out.println(cardValidation.locationValue().textContent());
	        System.out.println(cardValidation.experianceString().textContent());
	        System.out.println(cardValidation.experianceValue().textContent());
	        cardValidation.serviceIcon().isVisible();
	        System.out.println(cardValidation.serviceName().textContent());
	        cardValidation.viewProfile().click();
	        
	        

	    
}
}
