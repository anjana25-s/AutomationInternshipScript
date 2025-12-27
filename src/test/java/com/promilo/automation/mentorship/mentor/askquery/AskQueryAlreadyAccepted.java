package com.promilo.automation.mentorship.mentor.askquery;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.MouseButton;
import com.promilo.automation.courses.intrestspages.ViewedIntrestPage;
import com.promilo.automation.mentor.myacceptance.MyAcceptance;
import com.promilo.automation.mentorship.mentor.MentorIntrestPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class AskQueryAlreadyAccepted extends BaseClass {

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance();
    }

    @AfterClass
    public void tearDownExtent() {
        if (extent != null) extent.flush();
    }

    @Test()
    public void AcceptVideoServiceRequestTest() throws Exception {

        test = extent.createTest("✅ AskQueryAlreadyAccepted - Data Driven Test");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {
            // HANDLE OPTIONAL POPUP
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

            test.info("✅ Logged in with credentials");

            // NAVIGATE TO MY ACCEPTANCE
            Hamburger hb = new Hamburger(page);
            hb.Mystuff().click();
            hb.MyAccount().click();

            MyAcceptance acceptRequest = new MyAcceptance(page);
            acceptRequest.myAcceptance().click();

            // VALIDATION BLOCK
            String actualMenteeName = acceptRequest.askQueryMenteeName().textContent().trim();
            assertEquals(actualMenteeName, "December");

            String actualCampaignName = acceptRequest.askQueryCampaignName().textContent().trim();
            assertEquals(actualCampaignName, "December Automation");

            String actaulHighlightText = acceptRequest.askQueryHighlightText().textContent().trim();
            assertEquals(actaulHighlightText, "dxgfchvjbng vbnm");

            String actualBillingAmount = acceptRequest.askQueryMoney().textContent().trim();
            assertEquals(actualBillingAmount, "₹ 1,950");

            String actualDuration = acceptRequest.askQueryTime().textContent().trim();
            assertEquals(actualDuration, "22 Days");

            // SEND FIRST MESSAGE
            acceptRequest.viewQuery().nth(2).click();

            page.waitForTimeout(3000);
            acceptRequest.messageTextfield().fill("something");
            acceptRequest.messageSendbutton().click();
            acceptRequest.chatExitIcon().click();
            page.waitForTimeout(3000);

            // SEND SECOND MESSAGE USING CONTINUE
            acceptRequest.askquerycontinueButton().first().click();
            page.waitForTimeout(3000);
            acceptRequest.messageTextfield().fill("Something");
            acceptRequest.messageSendbutton().click();

            // MAILOSAUR VERIFICATION
            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

            MailsaurCredentials mailsaur = new MailsaurCredentials(page);
            mailsaur.MialsaurMail();
            mailsaur.MailsaurContinue();
            mailsaur.MailsaurPassword();
            mailsaur.MailsaurLogin();

            Locator successMsg = page.locator("//p[contains(text(),'You’ve Successfully Answered')]").first();
            successMsg.click();

            // VIEW IN NEW PAGE
            Page newPage = page.waitForPopup(() -> 
                page.locator("//span[contains(text(),'View')]").click()
            );
            newPage.waitForLoadState();

            test.info("🌐 New page URL: " + newPage.url());
            System.out.println(newPage.url());

            // VALIDATION ON NEW PAGE
            MentorIntrestPage validation = new MentorIntrestPage(newPage);

            String preferenceHeader = validation.getPreferenceHeaderText().textContent().trim();
            String cardDetail = validation.getCardDetailValue().textContent().trim();
            String categoryText = validation.getCategoryTextInterestCard().textContent().trim();

            System.out.println(preferenceHeader);
            System.out.println(cardDetail);
            System.out.println(categoryText);

            Assert.assertEquals(preferenceHeader, "December", "Preference Header text mismatch");
            Assert.assertEquals(cardDetail, "₹ 1,950", "Card Detail Value mismatch");
            Assert.assertEquals(categoryText, "dxgfchvjbng vbnm", "Category Text mismatch");

            newPage.waitForTimeout(3000);

            Locator viewQuery = newPage.locator("//span[text()='View Query']");
            viewQuery.scrollIntoViewIfNeeded();
            viewQuery.click(new Locator.ClickOptions().setForce(true));

            newPage.waitForTimeout(3000);

            Locator exitClick = newPage.getByRole(AriaRole.IMG,
                    new Page.GetByRoleOptions().setName("close chat popup"));
            exitClick.scrollIntoViewIfNeeded();
            exitClick.click(new Locator.ClickOptions().setForce(true));

            newPage.waitForTimeout(3000);

            Locator continueButton = newPage.locator("//span[text()='Continue']");
            continueButton.scrollIntoViewIfNeeded();
            continueButton.click(new Locator.ClickOptions().setForce(true));

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // No variable `i`, so no row failure reporting
        }
    }
}
