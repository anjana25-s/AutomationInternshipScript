package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.GetConnectedPage;
import com.automation.pages.GetConnectedErrorMessagesPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class GetConnectedNegativeTest extends BaseClass {

    @Test
    public void fullGetConnectedNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        GetConnectedPage conn = new GetConnectedPage(page);
        GetConnectedErrorMessagesPage errors = new GetConnectedErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        helper.log("==== GET CONNECTED FULL NEGATIVE FLOW STARTED ====");

        // ------------------------------------------------------------
        // NAVIGATION
        // ------------------------------------------------------------
        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard("Designer1");
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        helper.safeClick(conn.getGetConnectedBtn(), "Open Get Connected Form");

        // ------------------------------------------------------------
        // EMPTY FORM
        // ------------------------------------------------------------
        helper.safeClick(conn.getRegisterBtn(), "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(), "Name required");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required");
        helper.assertVisible(errors.emailRequiredError(), "Email required");
        helper.assertVisible(errors.industryRequiredError(), "Industry required");
        helper.assertVisible(errors.passwordRequiredError(), "Password required");

        // ------------------------------------------------------------
        // NAME NEGATIVES
        // ------------------------------------------------------------
        conn.getNameField().fill("ab");
        helper.safeClick(conn.getRegisterBtn(), "Short Name Submit");
        helper.assertVisible(errors.nameMinError(), "Name min error");

        conn.getNameField().fill("Me@");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Name Submit");
        helper.assertVisible(errors.nameInvalidError(), "Invalid name error");

        conn.getNameField().fill("A".repeat(51));
        helper.safeClick(conn.getRegisterBtn(), "Long Name Submit");
        helper.assertVisible(errors.nameMaxError(), "Name max error");

        // ------------------------------------------------------------
        // MOBILE INVALID
        // ------------------------------------------------------------
        conn.getMobileField().fill("12345");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Mobile Submit");
        helper.assertVisible(errors.mobileInvalidError(), "Invalid mobile error");

        // ------------------------------------------------------------
        // EMAIL INVALID
        // ------------------------------------------------------------
        conn.getEmailField().fill("abc@");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Email Submit");
        helper.assertVisible(errors.emailInvalidError(), "Invalid email error");

        // ------------------------------------------------------------
        // PASSWORD INVALID
        // ------------------------------------------------------------
        conn.getPasswordField().fill("12345");
        helper.safeClick(conn.getRegisterBtn(), "Short Password Submit");
        helper.assertVisible(errors.passwordMinError(), "Password min error");

        conn.getPasswordField().fill("A".repeat(16));
        helper.safeClick(conn.getRegisterBtn(), "Long Password Submit");
        helper.assertVisible(errors.passwordMaxError(), "Password max error");

        // ------------------------------------------------------------
        // VALID DATA
        // ------------------------------------------------------------
        String NAME = helper.generateRandomName();
        String EMAIL = helper.generateEmailFromName(NAME);
        String PHONE = helper.generateRandomPhone();
        String PASSWORD = "Test@123";

        helper.log("Random Data:");
        helper.log(NAME);
        helper.log(EMAIL);
        helper.log(PHONE);

        conn.getNameField().fill(NAME);
        conn.getMobileField().fill(PHONE);
        conn.getEmailField().fill(EMAIL);
        conn.getPasswordField().fill(PASSWORD);

        // ------------------------------------------------------------
        // INDUSTRY
        // ------------------------------------------------------------
        helper.safeClick(conn.getIndustryDropdown(), "Open Industry");
        Locator boxes = conn.getIndustryCheckboxes();

        helper.assertTrue(boxes.count() >= 3, "Industry checkboxes present");

        helper.safeClick(boxes.nth(1), "Select Industry");
        helper.safeClick(conn.getIndustryDropdown(), "Close Industry");

        // ------------------------------------------------------------
        // SUBMIT → OTP SCREEN
        // ------------------------------------------------------------
        helper.safeClick(conn.getRegisterBtn(), "Submit Valid Details");
        helper.log("---- REACHED OTP SCREEN ----");

     // ------------------------------------------------------------
     // OTP 
      // ------------------------------------------------------------
     helper.log("---- OTP NEGATIVE TEST ----");

     // enter wrong OTP
     for (int i = 1; i <= 4; i++) {
         conn.getOtpInput(i).fill("1");
     }

     // click verify
     helper.safeClick(conn.getVerifyOtpBtn(), "Submit WRONG OTP");

     // assert toast
     helper.assertToastAppeared(
             errors.invalidOtpToast(),
             "Invalid OTP toast appeared"
     );

     // ------------------------------------------------------------
     // OTP - CORRECT → Verify → Success
     // ------------------------------------------------------------
     helper.log("---- OTP POSITIVE TEST ----");

     // clear previous input
     for (int i = 1; i <= 4; i++) {
         conn.getOtpInput(i).fill("");
     }

     // enter correct OTP
     for (int i = 1; i <= 4; i++) {
         conn.getOtpInput(i).fill("9");
     }

     helper.safeClick(conn.getVerifyOtpBtn(), "Submit CORRECT OTP");

     helper.log("---- OTP VERIFIED SUCCESSFULLY ----");

        // ------------------------------------------------------------
        // ALREADY REGISTERED EMAIL TEST
        // ------------------------------------------------------------
        helper.log("---- TESTING ALREADY REGISTERED EMAIL ----");

        BrowserContext oldCtx = page.context();
        oldCtx.close();

        page = browser.newContext().newPage();
        home = new HomepagePage(page);
        conn = new GetConnectedPage(page);
        errors = new GetConnectedErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible())
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships Again");

        Locator card2 = home.getInternshipCard("Designer1");
        helper.waitForVisible(card2, "Internship Card");
        helper.scrollAndClick(card2, "Open Internship Again");

        helper.safeClick(conn.getGetConnectedBtn(), "Open Get Connected Again");

        // same EMAIL, new phone
        conn.getNameField().fill(NAME);
        conn.getMobileField().fill(helper.generateRandomPhone());
        conn.getEmailField().fill(EMAIL);
        conn.getPasswordField().fill(PASSWORD);

        helper.safeClick(conn.getIndustryDropdown(), "Open Industry");
        helper.safeClick(conn.getIndustryCheckboxes().nth(1), "Select Industry");
        helper.safeClick(conn.getIndustryDropdown(), "Close Industry");

        helper.safeClick(conn.getRegisterBtn(), "Submit Already Registered");

        helper.assertToastAppeared(errors.emailAlreadyRegisteredToast(),
                "Already registered toast visible");

        helper.log("==== GET CONNECTED FULL NEGATIVE FLOW COMPLETED ====");
    }
}