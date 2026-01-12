package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.RegisterWithUsExpectedTexts;
import com.automation.pages.HomepagePage;
import com.automation.pages.RegisterWithUsPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterWithUsTest extends BaseClass {

    private HomepagePage home;
    private RegisterWithUsPage register;
    private HelperUtility helper;

    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        home = new HomepagePage(page);
        register = new RegisterWithUsPage(page);
        helper = new HelperUtility(page);
    }

    @Test
    public void verifyRegisterWithUsFlow() {

        helper.step("START – Register With Us Flow");

        // ================= TEST DATA =================
        String name     = helper.generateRandomName();
        String phone    = helper.generateRandomPhone();
        String email    = helper.generateEmailFromName(name);
        String password = "Test@" + phone.substring(phone.length() - 3);

        // ================= OPEN REGISTER =================
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        // ================= BASIC DETAILS =================
        helper.safeFill(register.getNameField(), name, "Name");
        helper.safeFill(register.getMobileField(), phone, "Mobile");
        helper.safeFill(register.getEmailField(), email, "Email");
        helper.safeFill(register.getPasswordField(), password, "Password");

        // ================= LOCATION =================
        helper.safeClick(
                register.getPreferredLocationDropdown(),
                "Open Preferred Location"
        );
        register.getPreferredLocationDropdown().type("Ban");

        Locator suggestions = register.getLocationSuggestions();
        helper.waitForVisible(suggestions.first(), "Location Suggestions");
        helper.safeClick(suggestions.first(), "Select Location");

        // ================= INDUSTRY =================
        helper.safeClick(register.getIndustryDropdown(), "Open Industry");

        Locator industries = register.getIndustryCheckboxes();
        helper.waitForVisible(industries.first(), "Industry Options");

        helper.safeClick(industries.nth(0), "Industry 1");
        helper.safeClick(industries.nth(1), "Industry 2");

        helper.safeClick(register.getIndustryDropdown(), "Close Industry");

        // ================= REGISTER =================
        helper.safeClick(register.getRegisterNowButton(), "Register Now");

        
     // ================= OTP =================
        Locator otpInputs = register.getOtpInputs();
        helper.waitForVisible(otpInputs.first(), "OTP Inputs");

        for (int i = 0; i < OTP.length(); i++) {
            helper.safeFill(
                    otpInputs.nth(i),
                    OTP.substring(i, i + 1),
                    "OTP digit " + (i + 1)
            );
        }

        helper.safeClick(register.getVerifyOtpButton(), "Verify OTP");

        // ================= THANK YOU =================
        helper.waitForVisible(register.getThankYouPopup(), "Thank You Popup");

        helper.assertEquals(
                register.getFinalThankYouTitle().innerText().trim(),
                RegisterWithUsExpectedTexts.FINAL_THANK_YOU_TITLE,
                "Thank You title"
        );

        helper.assertEquals(
                register.getFinalThankYouMessage().innerText().trim(),
                RegisterWithUsExpectedTexts.FINAL_THANK_YOU_MSG,
                "Thank You message"
        );

        helper.assertEquals(
                register.getFinalThankYouSubMessage().innerText().trim(),
                RegisterWithUsExpectedTexts.FINAL_THANK_YOU_SUB_MSG,
                "Thank You sub message"
        );

        helper.safeClick(
                register.getThankYouCloseButton(),
                "Close Thank You Popup"
        );

        helper.pass("🎉 REGISTER WITH US FLOW PASSED");
    }
}
