package com.promilo.automation.mentorship.mentor.askquery;

import static org.testng.Assert.assertTrue;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.promilo.automation.courses.intrestspages.ViewedIntrestPage;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentor.mybilling.MentorMyBilling;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class AskQueryAnswerQuerFunctionality extends BaseClass {

    String emailToLogin = BaseClass.generatedEmail;
    String phoneToLogin = BaseClass.generatedPhone;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        System.out.println("Setting up ExtentReports...");
        extent = ExtentManager.getInstance();
    }

    @AfterClass
    public void tearDownExtent() {
        System.out.println("Flushing ExtentReports...");
        if (extent != null) extent.flush();
    }

    @Test(dependsOnMethods = "com.promilo.automation.mentorship.mentee.intrests.MentorshipAskQueryPaid.mentorshipShortListFunctionalityTest")
    public void AcceptVideoServiceRequestTest() throws Exception {

        System.out.println("Starting AcceptVideoServiceRequestTest...");
        test = extent.createTest("✅ Accept video service - Positive Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {

            // POPUP
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try {
                popup.getPopup().click();
                test.info("✅ Popup closed");
            } catch (Exception ignored) {}

            popup.clickLoginButton();

            // LOGIN
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill("812de0aa@qtvjnqv9.mailosaur.net");
            loginPage.passwordField().fill("Karthik@88");
            loginPage.loginButton().click();
            test.info("✅ Logged in");

            // MENU → Account
            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();
            hamburger.MyAccount().click();

            MyAcceptance acceptRequest = new MyAcceptance(page);
            acceptRequest.myAcceptance().click();
            acceptRequest.answerQuery().first().click();
            

         // CHAT BUBBLE TEXT VALIDATION
            String uiText = page.locator("(//div[@class='chat-bubble-container left '])[1]")
                    .textContent()
                    .trim();

            // Normalize both strings (remove extra spaces, ignore case)
            String actualNormalized = uiText.replaceAll("\\s+", " ").toLowerCase();
            String expectedNormalized = BaseClass.askYourQuestionText.trim().toLowerCase();

            // PASS if expected text appears anywhere inside actual text
            Assert.assertTrue(
                    actualNormalized.contains(expectedNormalized),
                    "❌ Chat bubble text mismatch!\nExpected (contains): " + expectedNormalized +
                    "\nActual: " + actualNormalized
            );
            
            
            
            // ANSWER QUERY
            
            acceptRequest.messageTextfield().fill("something");
            acceptRequest.messageSendbutton().click();
            page.waitForTimeout(3000);
            Locator chatExitButton=acceptRequest.chatExitIcon();
            chatExitButton.scrollIntoViewIfNeeded();
            chatExitButton.click();


            

            

           
            // NAVIGATE TO BILLING
            Hamburger hamburger2 = new Hamburger(page);
            hamburger2.Mystuff().click();
            hamburger2.MyAccount().click();

            MentorMyBilling billingValidation = new MentorMyBilling(page);
            billingValidation.myBillingButton().click();

            // Extract table text
            String tableHead = billingValidation.billingtableHead().textContent();
            String billingRow = billingValidation.billingData().textContent();

            System.out.println("Billing table head: " + tableHead);
            System.out.println("Billing data: " + billingRow);
            
            
            page.pause();

                        // MAILOSAUR
            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

            MailsaurCredentials mailsaur = new MailsaurCredentials(page);
            mailsaur.MialsaurMail();
            mailsaur.MailsaurContinue();
            mailsaur.MailsaurPassword();
            mailsaur.MailsaurLogin();

            page.locator("//p[contains(text(),'You’ve Successfully Answered ')]").first().click();
            System.out.println("TinyMCE: " + page.locator("//span[@class='tinyMce-placeholder']").textContent());

            Page newPage = page.waitForPopup(() -> {
                page.locator("//span[contains(text(),'View')]").click();
            });

            newPage.waitForLoadState();
            System.out.println("New page URL: " + newPage.url());

            page.bringToFront();
            page.locator("//a[@class='_btn_klaxo_2 _secondary_klaxo_2 _btnRound_klaxo_2 _iconNoChildren_klaxo_2']//div//*[name()='svg']").click();

            page.locator("//p[contains(text(),'Congratulations on completing')]").first().click();

            // LOGIN AS MENTOR
            Browser actualBrowser = browser.get();
            BrowserContext advertiserContext = actualBrowser.newContext();
            Page mentorPage = advertiserContext.newPage();
            mentorPage.navigate(prop.getProperty("url"));

            MayBeLaterPopUp login = new MayBeLaterPopUp(mentorPage);
            login.getPopup().click();
            login.clickLoginButton();

            LoginPage loginPage1 = new LoginPage(mentorPage);
            loginPage1.loginMailPhone().fill(BaseClass.generatedPhone);
            loginPage1.loginWithOtp().click();
            loginPage1.otpField().fill("9999");
            loginPage1.loginButton().click();

            ViewedIntrestPage cardValidation = new ViewedIntrestPage(mentorPage);
            mentorPage.waitForTimeout(3000);

            cardValidation.myInterestTab().click();

            mentorPage.waitForTimeout(4000);

            Locator intrestCard = cardValidation.mentorshipCard();

            PlaywrightAssertions.assertThat(intrestCard).isVisible();

            System.out.println(intrestCard);

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
