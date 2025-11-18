package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.HomepagePage;
import com.automation.pages.RegisterWithUsPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterWithUsTest extends BaseClass {

    private HomepagePage home;
    private RegisterWithUsPage register;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String OTP = "9999";

    @BeforeClass
    public void initPages() {
        home = new HomepagePage(page);
        register = new RegisterWithUsPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openUrl() {
        helper.log("[Step 1] Navigating to " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();
        page.waitForSelector("body");

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close Maybe Later Popup");
        }
    }

    @Test
    public void verifyRegisterWithUsFlow() {
        try {
            // Generate random user
            String name = helper.generateRandomName();
            String phone = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(name);
            String password = "Test@" + phone.substring(phone.length() - 3);

            helper.log("[DATA] Name=" + name + " | Phone=" + phone + " | Email=" + email);

            helper.safeClick(home.getInternshipsTab(), "Open Internships Tab");

            helper.safeFill(register.getNameField(), name, "Name");
            helper.safeFill(register.getMobileField(), phone, "Mobile");
            helper.safeFill(register.getEmailField(), email, "Email");
            helper.safeFill(register.getPasswordField(), password, "Password");

            // ---------- LOCATION ----------
            helper.safeClick(register.getPreferredLocationDropdown(), "Open Location Dropdown");
            helper.safeClick(register.getFirstLocationOption(), "Select FIRST Location Option");

            // ---------- INDUSTRY ----------
            helper.safeClick(register.getIndustryDropdown(), "Open Industry Dropdown");

            Locator checkboxes = register.getIndustryCheckboxes();
            int total = checkboxes.count();

            Assert.assertTrue(total > 5, "❌ Industry checkboxes not loaded!");

            helper.safeClick(checkboxes.nth(0), "Select Checkbox 1");
            helper.safeClick(checkboxes.nth(1), "Select Checkbox 2");

            helper.safeClick(register.getIndustryDropdown(), "Close Industry Dropdown");

            // ---------- REGISTER ----------
            helper.safeClick(register.getRegisterNowButton(), "Register Now");

            // ---------- OTP ----------
            Locator otp = register.getOtpInputs();
            otp.nth(0).fill("9");
            otp.nth(1).fill("9");
            otp.nth(2).fill("9");
            otp.nth(3).fill("9");

            helper.safeClick(register.getVerifyOtpButton(), "Verify & Proceed");

            // ---------- THANK YOU ----------
            register.getThankYouPopup().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));

            Assert.assertTrue(register.getThankYouPopup().isVisible(),
                    "❌ THANK YOU POPUP NOT VISIBLE!");

            helper.safeClick(register.getThankYouCloseButton(), "Close Thank You Popup");

            helper.log("===== ✅ REGISTER WITH US FLOW PASSED =====");

        } catch (Exception e) {
            helper.takeScreenshot("RegisterWithUs_Failed");
            Assert.fail("❌ TEST FAILED: " + e.getMessage());
        }
    }
}
