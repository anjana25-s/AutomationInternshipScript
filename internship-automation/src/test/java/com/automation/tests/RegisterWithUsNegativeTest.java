package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.HomepagePage;
import com.automation.pages.RegisterWithUsPage;
import com.automation.pages.RegisterWithUsErrorMessagesPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import org.testng.annotations.Test;

public class RegisterWithUsNegativeTest extends BaseClass {

    @Test
    public void fullRegisterWithUsNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        RegisterWithUsPage reg = new RegisterWithUsPage(page);
        RegisterWithUsErrorMessagesPage errors = new RegisterWithUsErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        helper.log("==== REGISTER WITH US FULL NEGATIVE FLOW STARTED ====");

        // ------------------------------------------------------------
        // NAVIGATION
        // ------------------------------------------------------------
        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible()) helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships");


        // ------------------------------------------------------------
        // EMPTY FORM VALIDATION
        // ------------------------------------------------------------
        helper.safeClick(reg.getRegisterNowButton(), "Submit Blank Form");

        helper.assertVisible(errors.nameRequiredError(), "Name required");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required");
        helper.assertVisible(errors.emailRequiredError(), "Email required");
        helper.assertVisible(errors.preferredLocationRequiredError(), "Preferred location required");
        helper.assertVisible(errors.industryRequiredError(), "Industry required");
        helper.assertVisible(errors.passwordRequiredError(), "Password required");


        // ------------------------------------------------------------
        // INVALID NAME
        // ------------------------------------------------------------
        reg.getNameField().fill("12@@");
        helper.safeClick(reg.getRegisterNowButton(), "Invalid Name Submit");
        helper.assertVisible(errors.invalidNameError(), "Invalid name error");


        // ------------------------------------------------------------
        // INVALID MOBILE
        // ------------------------------------------------------------
        reg.getMobileField().fill("12345");
        helper.safeClick(reg.getRegisterNowButton(), "Invalid Mobile Submit");
        helper.assertVisible(errors.invalidMobileError(), "Invalid mobile error");


        // ------------------------------------------------------------
        // INVALID EMAIL
        // ------------------------------------------------------------
        reg.getEmailField().fill("test@");
        helper.safeClick(reg.getRegisterNowButton(), "Invalid Email Submit");
        helper.assertVisible(errors.invalidEmailError(), "Invalid email error");


        // ------------------------------------------------------------
        // PASSWORD INVALIDS
        // ------------------------------------------------------------
        reg.getPasswordField().fill("12345");
        helper.safeClick(reg.getRegisterNowButton(), "Password too short");
        helper.assertVisible(errors.passwordMinError(), "Password min error");

        reg.getPasswordField().fill("A".repeat(16));
        helper.safeClick(reg.getRegisterNowButton(), "Password too long");
        helper.assertVisible(errors.passwordMaxError(), "Password max error");


        // ------------------------------------------------------------
        // VALID RANDOM DATA
        // ------------------------------------------------------------
        String NAME = helper.generateRandomName();
        String EMAIL = helper.generateEmailFromName(NAME);
        String PHONE = helper.generateRandomPhone();
        String PASSWORD = "Test@123";

        helper.log("Random Data:");
        helper.log(NAME);
        helper.log(EMAIL);
        helper.log(PHONE);

        reg.getNameField().fill(NAME);
        reg.getMobileField().fill(PHONE);
        reg.getEmailField().fill(EMAIL);
        reg.getPasswordField().fill(PASSWORD);


        // ------------------------------------------------------------
        // LOCATION + INDUSTRY
        // ------------------------------------------------------------
        helper.safeClick(reg.getPreferredLocationDropdown(), "Open Preferred Location");
        helper.safeClick(reg.getFirstLocationOption(), "Select Location");

        helper.safeClick(reg.getIndustryDropdown(), "Open Industry");
        Locator boxes = reg.getIndustryCheckboxes();
        helper.assertTrue(boxes.count() > 0, "Industry checkboxes present");

        helper.safeClick(boxes.nth(0), "Select Industry");
        helper.safeClick(reg.getIndustryDropdown(), "Close Industry");


        // ------------------------------------------------------------
        // SUBMIT VALID DATA → OTP SCREEN
        // ------------------------------------------------------------
        helper.safeClick(reg.getRegisterNowButton(), "Submit Valid Details");
        helper.log("---- REACHED OTP SCREEN ----");


        // ------------------------------------------------------------
        // OTP NEGATIVE (compact incremental validation)
        // ------------------------------------------------------------
        String[] wrongDigits = {"1", "2", "3", "9"};
        for (int i = 0; i < wrongDigits.length; i++) {
            reg.getOtpInputs().nth(i).fill(wrongDigits[i]);

            boolean shouldEnable = (i == wrongDigits.length - 1);
            boolean isEnabled = !errors.verifyBtn().isDisabled();

            helper.assertTrue(isEnabled == shouldEnable,
                    "OTP digit " + (i + 1) + " → verifyEnabled=" + isEnabled);
        }

        helper.safeClick(reg.getVerifyOtpButton(), "Submit WRONG OTP");

        helper.assertToastAppeared(errors.invalidOtpToast(), "Invalid OTP toast appeared");


        // ------------------------------------------------------------
        // ENTER CORRECT OTP
        // ------------------------------------------------------------
        for (int i = 0; i < 4; i++) reg.getOtpInputs().nth(i).fill("");

        for (int i = 0; i < 4; i++) {
            reg.getOtpInputs().nth(i).fill("9");
            helper.assertEquals(reg.getOtpInputs().nth(i).inputValue(), "9", "OTP digit correct");
        }

        helper.safeClick(reg.getVerifyOtpButton(), "Submit CORRECT OTP");
        helper.log("---- OTP VERIFIED SUCCESSFULLY ----");


        // Close Thank You popup if visible
        if (reg.getThankYouPopup().isVisible()) {
            helper.safeClick(reg.getThankYouCloseButton(), "Close Thank You Popup");
        }


        // ------------------------------------------------------------
        // ALREADY REGISTERED VALIDATION
        // ------------------------------------------------------------
        helper.log("---- TESTING ALREADY REGISTERED EMAIL ----");

        BrowserContext old = page.context();
        old.close();

        page = browser.newContext().newPage();
        home = new HomepagePage(page);
        reg = new RegisterWithUsPage(page);
        errors = new RegisterWithUsErrorMessagesPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible()) helper.safeClick(home.getMaybeLaterBtn(), "Close Popup");

        helper.safeClick(home.getInternshipsTab(), "Open Internships Again");

        // SAME EMAIL, NEW PHONE
        reg.getNameField().fill(NAME);
        reg.getMobileField().fill(helper.generateRandomPhone());
        reg.getEmailField().fill(EMAIL);
        reg.getPasswordField().fill(PASSWORD);

        helper.safeClick(reg.getPreferredLocationDropdown(), "Open Preferred Location");
        helper.safeClick(reg.getFirstLocationOption(), "Select Location");

        helper.safeClick(reg.getIndustryDropdown(), "Open Industry");
        helper.safeClick(reg.getIndustryCheckboxes().nth(0), "Select Industry");
        helper.safeClick(reg.getIndustryDropdown(), "Close Industry");

        helper.safeClick(reg.getRegisterNowButton(), "Submit Already Registered");

        helper.assertToastAppeared(errors.emailAlreadyRegisteredToast(),
                "Already registered toast visible");

        helper.log("==== REGISTER WITH US FULL NEGATIVE FLOW COMPLETED ====");
    }
}

