package com.promilo.automation.mentorship.mentee;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.pagepbjects.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.pagepbjects.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.MailsaurCredentials;

public class BrandEndorsement extends Baseclass {

    private static final Logger log = LogManager.getLogger(BrandEndorsement.class);

    @Test
    public void mentorshipBrandEndorsement() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship Brand Endorsement Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");

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
        fillForm.name().fill("karthik");

        // Generate random 5-digit number
        int randomNum = 10000 + new Random().nextInt(90000);

        // Generate random phone number
        String randomPhone = "90000" + randomNum;

        // Generate random Mailosaur email
        String serverId = "8mgfvj1x";
        String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

        // Fill phone and email fields
        fillForm.MobileTextField().fill(randomPhone);
        fillForm.emailTextfield().fill(randomEmail);

        // Store in Baseclass
        Baseclass.generatedEmail = randomEmail;
        Baseclass.generatedPhone = randomPhone;

        // Print the randomly generated email and phone
        System.out.println("✅ Randomly generated email: " + randomEmail);
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
        

        // Print confirmation message in the thank you pop-up
        System.out.println(page.getByText("Thank you for registering and").textContent());
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
