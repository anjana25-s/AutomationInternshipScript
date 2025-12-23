package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.GetConnectedErrorMessagesPage;
import com.automation.pages.GetConnectedPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetConnectedNegativeTest extends BaseClass {

    private HomepagePage home;
    private GetConnectedPage conn;
    private GetConnectedErrorMessagesPage errors;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP = "Tester 1";
    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void openBase() {

        home   = new HomepagePage(page);
        conn   = new GetConnectedPage(page);
        errors = new GetConnectedErrorMessagesPage(page);
        helper = new HelperUtility(page);

        helper.log("Navigating to Promilo");
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }
    }

    @Test
    public void fullGetConnectedNegativeFlow() {

        helper.log("==== GET CONNECTED FULL NEGATIVE FLOW STARTED ====");

        // ------------------------------------------------------------
        // OPEN INTERNSHIP
        // ------------------------------------------------------------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card");
        helper.scrollAndClick(card, "Open Internship");

        helper.safeClick(conn.getGetConnectedBtn(),
                "Open Get Connected Form");

        // ------------------------------------------------------------
        // EMPTY FORM VALIDATION
        // ------------------------------------------------------------
        helper.safeClick(conn.getRegisterBtn(),
                "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(), "Name required");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required");
        helper.assertVisible(errors.emailRequiredError(), "Email required");
        helper.assertVisible(errors.industryRequiredError(), "Industry required");
        helper.assertVisible(errors.passwordRequiredError(), "Password required");

        // ------------------------------------------------------------
        // NAME NEGATIVES
        // ------------------------------------------------------------
        conn.getNameField().fill("ab");
        helper.safeClick(conn.getRegisterBtn(), "Short Name");
        helper.assertVisible(errors.nameMinError(), "Name min error");

        conn.getNameField().fill("Me@");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Name");
        helper.assertVisible(errors.nameInvalidError(), "Name invalid error");

        conn.getNameField().fill("A".repeat(51));
        helper.safeClick(conn.getRegisterBtn(), "Long Name");
        helper.assertVisible(errors.nameMaxError(), "Name max error");

        // ------------------------------------------------------------
        // MOBILE NEGATIVE
        // ------------------------------------------------------------
        conn.getMobileField().fill("12345");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Mobile");
        helper.assertVisible(errors.mobileInvalidError(), "Mobile invalid error");

        // ------------------------------------------------------------
        // EMAIL NEGATIVE
        // ------------------------------------------------------------
        conn.getEmailField().fill("abc@");
        helper.safeClick(conn.getRegisterBtn(), "Invalid Email");
        helper.assertVisible(errors.emailInvalidError(), "Email invalid error");

        // ------------------------------------------------------------
        // PASSWORD NEGATIVE
        // ------------------------------------------------------------
        conn.getPasswordField().fill("12345");
        helper.safeClick(conn.getRegisterBtn(), "Short Password");
        helper.assertVisible(errors.passwordMinError(), "Password min error");

        conn.getPasswordField().fill("A".repeat(16));
        helper.safeClick(conn.getRegisterBtn(), "Long Password");
        helper.assertVisible(errors.passwordMaxError(), "Password max error");

        // ------------------------------------------------------------
        // VALID DATA
        // ------------------------------------------------------------
        String name  = helper.generateRandomName();
        String email = helper.generateEmailFromName(name);
        String phone = helper.generateRandomPhone();
        String pass  = "Test@123";

        helper.log("Valid Data:");
        helper.log(name);
        helper.log(email);
        helper.log(phone);

        conn.getNameField().fill(name);
        conn.getMobileField().fill(phone);
        conn.getEmailField().fill(email);
        conn.getPasswordField().fill(pass);

        // ------------------------------------------------------------
        // INDUSTRY
        // ------------------------------------------------------------
        helper.safeClick(conn.getIndustryDropdown(), "Open Industry");

        Locator boxes = conn.getIndustryCheckboxes();
        helper.assertTrue(boxes.count() >= 3,
                "Industry options available");

        helper.safeClick(boxes.nth(1), "Select Industry");
        helper.safeClick(conn.getIndustryDropdown(), "Close Industry");

        // ------------------------------------------------------------
        // SUBMIT → OTP
        // ------------------------------------------------------------
        helper.safeClick(conn.getRegisterBtn(),
                "Submit Valid Details");

        helper.log("---- OTP NEGATIVE TEST ----");

        for (int i = 1; i <= 4; i++) {
            conn.getOtpInput(i).fill("1");
        }

        helper.safeClick(conn.getVerifyOtpBtn(),
                "Submit Wrong OTP");

        helper.assertToastAppeared(
                errors.invalidOtpToast(),
                "Invalid OTP toast"
        );

        // ------------------------------------------------------------
        // OTP POSITIVE
        // ------------------------------------------------------------
        helper.log("---- OTP POSITIVE TEST ----");

        for (int i = 1; i <= 4; i++) {
            conn.getOtpInput(i).fill("");
            conn.getOtpInput(i).fill("9");
        }

        helper.safeClick(conn.getVerifyOtpBtn(),
                "Submit Correct OTP");

        helper.log("OTP Verified Successfully");

        // ------------------------------------------------------------
        // ALREADY REGISTERED EMAIL
        // ------------------------------------------------------------
        helper.log("---- ALREADY REGISTERED EMAIL TEST ----");

        BrowserContext oldCtx = page.context();
        oldCtx.close();

        page = browser.newContext().newPage();

        home   = new HomepagePage(page);
        conn   = new GetConnectedPage(page);
        errors = new GetConnectedErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate(BASE_URL);

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");
        }

        helper.safeClick(home.getInternshipsTab(),
                "Open Internships Again");

        Locator card2 = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card2, "Internship Card");
        helper.scrollAndClick(card2, "Open Internship Again");

        helper.safeClick(conn.getGetConnectedBtn(),
                "Open Get Connected Again");

        conn.getNameField().fill(name);
        conn.getMobileField().fill(helper.generateRandomPhone());
        conn.getEmailField().fill(email);
        conn.getPasswordField().fill(pass);

        helper.safeClick(conn.getIndustryDropdown(), "Open Industry");
        helper.safeClick(conn.getIndustryCheckboxes().nth(1),
                "Select Industry");
        helper.safeClick(conn.getIndustryDropdown(), "Close Industry");

        helper.safeClick(conn.getRegisterBtn(),
                "Submit Already Registered");

        helper.assertToastAppeared(
                errors.emailAlreadyRegisteredToast(),
                "Already registered email toast"
        );

        helper.log("🎉 GET CONNECTED NEGATIVE FLOW PASSED!");
    }
}
