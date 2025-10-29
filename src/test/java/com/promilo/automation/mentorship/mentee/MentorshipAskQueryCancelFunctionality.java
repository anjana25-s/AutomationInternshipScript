package com.promilo.automation.mentorship.mentee;

import java.io.IOException;

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
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;

public class MentorshipAskQueryCancelFunctionality extends Baseclass {

    private static final Logger log = LogManager.getLogger(MentorshipAskQueryCancelFunctionality.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {
        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        LandingPage landingPage = new LandingPage(page);
        if (landingPage.getPopup().isVisible()) {
            landingPage.getPopup().click(new Locator.ClickOptions().setForce(true));
            log.info("Closed landing page popup");
        }
        page.waitForTimeout(2000);

        // -------------------- Mentorship Module --------------------
        DashboardPage dashboard = new DashboardPage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("karthik");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // -------------------- Mentor Description --------------------
        DescriptionPage descriptionValidation = new DescriptionPage(page);
        page.waitForTimeout(3000);
        descriptionValidation.askQuery().click();
        page.waitForTimeout(2000);

        descriptionValidation.askYourQuery().nth(1).click();
        page.waitForTimeout(2000);

        // -------------------- Ask Query Form --------------------
        MentorshipFormComponents askQuery = new MentorshipFormComponents(page);
        askQuery.name().fill("karthik Ask Query paid");

        // Generate random 5-digit number
        int randomNum = (int) (Math.random() * 90000) + 10000;

        String phoneNumber = "90000" + randomNum;
        String email = "promilo" + randomNum + "@gmail.com";

        askQuery.MobileTextField().fill(phoneNumber);
        askQuery.emailTextfield().fill(email);
        page.locator("//button[normalize-space()='Ask Your Query']").nth(2)
        .click(new Locator.ClickOptions().setForce(true));
        // -------------------- OTP Handling --------------------
        String otp = "9999";
        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator(
                "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

            otpField.waitFor(new Locator.WaitForOptions()
                .setTimeout(10000)
                .setState(WaitForSelectorState.VISIBLE));

            otpField.fill(otpChar);
        }

        page.locator("//button[normalize-space()='Verify & Proceed']").click();

        page.getByRole(AriaRole.TEXTBOX,
                new Page.GetByRoleOptions().setName("Type your questions here..."))
            .fill("Ask Your Questions Here");

        askQuery.nextButton().click();

        // -------------------- Invoice Form --------------------
        askQuery.InvoiceNameField().fill("Karthik");
        askQuery.StreetAdress1().fill("Dasarahalli");
        askQuery.StreetAdress2().fill("Bangalore");
        askQuery.pinCode().fill("560057");
        askQuery.yesRadrioBox().click();
        askQuery.gstNumber().fill("22AAAAA9999A1Z5");
        askQuery.panNumber().fill("AAAAA9999A");
        page.getByRole(AriaRole.CHECKBOX, 
            new Page.GetByRoleOptions().setName("By checking this box, I")).check();
        page.getByRole(AriaRole.BUTTON, 
            new Page.GetByRoleOptions().setName("Save")).click();

        // -------------------- Payment --------------------
        MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
        paymentFunctionality.payOnline().click();
        paymentFunctionality.payButton().click();

        page.waitForTimeout(5000);

        // Switch to iframe for payment
        FrameLocator frame = page.frameLocator("iframe");
        frame.getByTestId("contactNumber").fill("9000098765");

        frame.getByTestId("nav-sidebar")
            .locator("div")
            .filter(new Locator.FilterOptions().setHasText("Wallet"))
            .nth(2)
            .click();

        page.waitForTimeout(3000);

        // Handle new popup (PhonePe page)
        Page phonePePage = page.waitForPopup(() -> {
            frame.getByTestId("screen-container")
                .locator("div")
                .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                .nth(2)
                .click();
        });

        phonePePage.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Success"))
            .click();

        page.waitForTimeout(8000);

        // -------------------- Validation --------------------
        Locator thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        Assert.assertTrue(thankYouPopup.isVisible(), "'Thank You!' popup not displayed.");

        page.getByText("Thank you for registering and").click();
        page.getByText("Easy Access to Your").click();

        // Click My Interest
        page.getByRole(AriaRole.DIALOG)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
            .click();
        
        
        page.waitForTimeout(4000);

        
        // Ensure correct page object is used with current page
        MentorshipMyintrest interestValidation = new MentorshipMyintrest(page);
        page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName("campaign_profile_image")).click();
        Locator cardValidations =page.locator("//div[@class='my-preferance-card-body card-body']");
        cardValidations.isVisible();
        System.out.println(cardValidations.textContent()); 
        interestValidation.cancelButton().click();
        
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Yes")).click();
        
        System.out.println(page.getByRole(AriaRole.DIALOG).locator("div").nth(1).textContent());
        System.out.println( page.locator("//div[@class='text-center top-img-wrapper w-100']").textContent());

        
        
    }
}
