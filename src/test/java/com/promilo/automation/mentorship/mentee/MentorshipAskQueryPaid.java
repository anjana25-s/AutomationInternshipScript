package com.promilo.automation.mentorship.mentee;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;

public class MentorshipAskQueryPaid extends Baseclass {

    private static final Logger log = LogManager.getLogger(MentorshipAskQueryPaid.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {
        log.info("===== Starting Mentorship ShortList Functionality Test =====");
        System.out.println("===== Starting Mentorship ShortList Functionality Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        System.out.println("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        if (mayBeLaterPopUp.getPopup().isVisible()) {
            mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
            log.info("Closed landing page popup");
            System.out.println("Closed landing page popup");
        }
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        HomePage dashboard = new HomePage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");
        System.out.println("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        System.out.println("Clicked search text field");
        searchPage.SearchTextField().fill("karthik");
        System.out.println("Entered mentor name: karthik");
        page.keyboard().press("Enter");
        System.out.println("Pressed Enter for mentor search");
        page.waitForTimeout(2000);
        
        // -------------------- Mentor Description --------------------
        DescriptionPage descriptionValidation = new DescriptionPage(page);
        page.waitForTimeout(3000);
        descriptionValidation.askQuery().click();
        log.info("Clicked on 'Ask Query'");
        System.out.println("Clicked on 'Ask Query'");
        page.waitForTimeout(2000);
        
       

        descriptionValidation.askYourQuery().nth(1).click();
        log.info("Selected 'Ask Your Query' option");
        System.out.println("Selected 'Ask Your Query' option");
        page.waitForTimeout(2000);

        // -------------------- Ask Query Form --------------------
        MentorshipFormComponents askQuery = new MentorshipFormComponents(page);
        askQuery.name().fill("karthik Ask Query paid");
        System.out.println("Filled name: karthik Ask Query paid");

     // Generate phone number starting with 90000 + 5 random digits
        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
        askQuery.MobileTextField().fill(randomPhone);

        String serverId = "8mgfvj1x";
        int randomNum = 10000 + new Random().nextInt(90000);
        String randomEmail = "GetMentorCall" + randomNum + "@" + serverId + ".mailosaur.net";
        page.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);
        Baseclass.generatedEmail = randomEmail;
        Baseclass.generatedPhone = randomPhone;
        System.out.println("Filled email field");

        page.locator("//button[normalize-space()='Ask Your Query']").nth(2)
            .click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked 'Ask Your Query' button");

        // -------------------- OTP Handling --------------------
        String otp = "9999";
        System.out.println("Entering OTP: " + otp);
        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
            otpField.fill(otpChar);
            System.out.println("Entered OTP digit " + otpChar + " in field " + (j + 1));
        }

        page.locator("//button[normalize-space()='Verify & Proceed']")
            .click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked 'Verify & Proceed'");

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type your questions here..."))
            .fill("Ask Your Questions Here");
        System.out.println("Filled question text box");

        askQuery.nextButton().click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked 'Next' button");

        // -------------------- Invoice Form --------------------
        askQuery.InvoiceNameField().fill("Karthik");
        System.out.println("Filled invoice name: Karthik");
        askQuery.StreetAdress1().fill("Dasarahalli");
        askQuery.StreetAdress2().fill("Bangalore");
        askQuery.pinCode().fill("560057");
        System.out.println("Filled address fields");
        askQuery.yesRadrioBox().click();
        askQuery.gstNumber().fill("22AAAAA9999A1Z5");
        askQuery.panNumber().fill("AAAAA9999A");
        System.out.println("Filled GST and PAN numbers");

        page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
        System.out.println("Checked terms checkbox");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"))
            .click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked Save button");

        // -------------------- Payment --------------------
        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
        paymentFunctionality.payOnline().click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked Pay Online");
        paymentFunctionality.payButton().click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked Pay button");

        page.waitForTimeout(5000);

        // Switch to iframe for payment
        FrameLocator frame = page.frameLocator("iframe");
        frame.getByTestId("contactNumber").fill("9000098765");
        System.out.println("Filled contact number in payment iframe");

        frame.getByTestId("nav-sidebar").locator("div")
            .filter(new Locator.FilterOptions().setHasText("Wallet"))
            .nth(2).click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked Wallet option in payment iframe");

        page.waitForTimeout(3000);

        // Handle new popup (PhonePe)
        Page phonePePage = page.waitForPopup(() -> {
            frame.getByTestId("screen-container")
                .locator("div")
                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                .nth(2).click(new Locator.ClickOptions().setForce(true));
        });
        System.out.println("Switched to PhonePe popup window");

        phonePePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
        System.out.println("Clicked Success on PhonePe page");

        page.waitForTimeout(8000);

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");
        System.out.println("✅ Thank You popup displayed successfully");

        page.getByText("Thank you for registering and").click();
        page.getByText("Easy Access to Your").click();
        System.out.println("Navigated through Thank You popup options");

        // Click My Interest
        page.getByRole(AriaRole.DIALOG)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
            .click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked My Interest link");

        page.waitForTimeout(4000);

        MentorshipMyintrest interestValidation = new MentorshipMyintrest(page);
        page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("campaign_profile_image"))
            .click(new Locator.ClickOptions().setForce(true));
        System.out.println("Opened campaign profile image");

        Locator cardValidations = page.locator("//div[@class='my-preferance-card-body card-body']");
        Assert.assertTrue(cardValidations.isVisible(), "Preference card not visible");
        System.out.println("Preference card visible: " + cardValidations.textContent());

        interestValidation.chatButton().click(new Locator.ClickOptions().setForce(true));
        System.out.println("Clicked Chat button");

        page.waitForTimeout(5000);
        System.out.println("===== Mentorship Ask Query Paid Test Completed Successfully =====");
    }
}
