package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.HomepagePage;
import com.automation.pages.RegisterWithUsPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterWithUsTest extends BaseClass {

    private HomepagePage home;
    private RegisterWithUsPage register;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void openBase() {

        home     = new HomepagePage(page);
        register = new RegisterWithUsPage(page);
        helper   = new HelperUtility(page);

        helper.log("[Step 1] Navigating to " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(),
                    "Close Maybe Later Popup");
        }
    }

    @Test
    public void verifyRegisterWithUsFlow() {

        // ------------------------------------------------------------
        // TEST DATA
        // ------------------------------------------------------------
        String name = helper.generateRandomName();
        String phone = helper.generateRandomPhone();
        String email = helper.generateEmailFromName(name);
        String password = "Test@" + phone.substring(phone.length() - 3);

        helper.log("[DATA] Name = " + name);
        helper.log("[DATA] Phone = " + phone);
        helper.log("[DATA] Email = " + email);

        // ------------------------------------------------------------
        // OPEN REGISTER WITH US (via homepage section)
        // ------------------------------------------------------------
        helper.safeClick(home.getInternshipsTab(),
                "Open Internships Tab");

        // ------------------------------------------------------------
        // FILL BASIC DETAILS
        // ------------------------------------------------------------
        helper.safeFill(register.getNameField(), name, "Name");
        helper.safeFill(register.getMobileField(), phone, "Mobile");
        helper.safeFill(register.getEmailField(), email, "Email");
        helper.safeFill(register.getPasswordField(), password, "Password");

        // ------------------------------------------------------------
        // LOCATION
        // ------------------------------------------------------------
        helper.safeClick(register.getPreferredLocationDropdown(),
                "Open Location Dropdown");

        helper.safeClick(register.getFirstLocationOption(),
                "Select First Location");

        // ------------------------------------------------------------
        // INDUSTRY
        // ------------------------------------------------------------
        helper.safeClick(register.getIndustryDropdown(),
                "Open Industry Dropdown");

        Locator boxes = register.getIndustryCheckboxes();
        Assert.assertTrue(boxes.count() > 2,
                "❌ Industry options not loaded");

        helper.safeClick(boxes.nth(0), "Select Industry 1");
        helper.safeClick(boxes.nth(1), "Select Industry 2");

        helper.safeClick(register.getIndustryDropdown(),
                "Close Industry Dropdown");

        // ------------------------------------------------------------
        // REGISTER
        // ------------------------------------------------------------
        helper.safeClick(register.getRegisterNowButton(),
                "Click Register Now");

        // ------------------------------------------------------------
        // OTP
        // ------------------------------------------------------------
        Locator otpInputs = register.getOtpInputs();
        helper.waitForVisible(otpInputs.first(),
                "OTP Inputs");

        for (int i = 0; i < 4; i++) {
            otpInputs.nth(i).fill("9");
        }

        helper.safeClick(register.getVerifyOtpButton(),
                "Verify & Proceed");

        // ------------------------------------------------------------
        // THANK YOU POPUP
        // ------------------------------------------------------------
        helper.waitForVisible(register.getThankYouPopup(),
                "Thank You Popup");

        Assert.assertTrue(register.getThankYouPopup().isVisible(),
                "❌ Thank You popup not visible");

        helper.safeClick(register.getThankYouCloseButton(),
                "Close Thank You Popup");

        helper.log("🎉 REGISTER WITH US FLOW PASSED SUCCESSFULLY!");
    }
}
