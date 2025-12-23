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
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.BaseClass;

public class BuyResourcesFunctionality extends BaseClass {
    private static final Logger log = LogManager.getLogger(BuyResourcesFunctionality.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship Buy Resources Test =====");

        // -------------------- Initialize Playwright --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page --------------------
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
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

        DescriptionPage descriptionValidation = new DescriptionPage(page);
        Locator buyResources = descriptionValidation.buyResources().first();
        descriptionValidation.allLink().click();
        buyResources.scrollIntoViewIfNeeded();
        buyResources.click();

        // -------------------- Fill form details --------------------
        MentorshipFormComponents form = new MentorshipFormComponents(page);
        form.name().fill("karthik");

        // Generate phone number starting with 90000 + 5 random digits
        String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
        form.MobileTextField().fill(randomPhone);

        String serverId = "8mgfvj1x";
        int randomNum = 10000 + new Random().nextInt(90000);
        String randomEmail = "DownloadResource" + randomNum + "@" + serverId + ".mailosaur.net";
        page.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);


        form.downloadResource().click();

        // -------------------- OTP Handling --------------------
        String otp = "9999";
        for (int j = 0; j < 4; j++) {
            String otpChar = String.valueOf(otp.charAt(j));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            otpField.fill("");
            otpField.fill(otpChar);

            String currentValue = otpField.evaluate("el => el.value").toString().trim();
            if (!currentValue.equals(otpChar)) {
                throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
            }
        }

        page.locator("//button[text()='Verify & Proceed']").click();
        log.info("Resource Name: " + descriptionValidation.resourceName().textContent());
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
        log.info("Download tab opened with URL: " + downloadTab.url());
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

        // ✅ cardValidation
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
