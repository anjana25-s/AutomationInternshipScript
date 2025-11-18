package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.HomepagePage;
import com.automation.pages.GetConnectedPage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

public class GetConnectedTest extends BaseClass {

    private HomepagePage home;
    private GetConnectedPage connect;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP = "Designer1";
    private static final String OTP = "9999";

    @BeforeClass
    public void initPages() {
        home = new HomepagePage(page);
        connect = new GetConnectedPage(page);
        helper = new HelperUtility(page);
    }

    @BeforeMethod
    public void openUrl() {

        helper.log("[Step 1] Navigating to: " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        Assert.assertTrue(page.url().contains("promilo"), "❌ URL did not load correctly!");

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(), "Close 'Maybe later' popup");
        }
    }

    @Test
    public void verifyGetConnectedFlow() {

        try {
            // ------------------- GENERATE DATA -------------------
            String name = helper.generateRandomName();
            String phone = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(name);
            String password = "Test@" + phone.substring(6);

            helper.log("[DATA] Name: " + name);
            helper.log("[DATA] Phone: " + phone);
            helper.log("[DATA] Email: " + email);

            // ------------------- OPEN INTERNSHIPS -------------------
            helper.safeClick(home.getInternshipsTab(), "Open Internships");

            Locator card = home.getInternshipCard(INTERNSHIP);
            helper.waitForVisible(card, "Internship Card");

            helper.scrollAndClick(card, "Open Internship");

            // ------------------- CLICK GET CONNECTED -------------------
            helper.safeClick(connect.getGetConnectedBtn(), "Click Get Connected");

            // ------------------- FILL FORM -------------------
            helper.safeFill(connect.getNameField(), name, "Name");
            helper.safeFill(connect.getMobileField(), phone, "Mobile");
            helper.safeFill(connect.getEmailField(), email, "Email");

            // ------------------- INDUSTRY DROPDOWN -------------------
            helper.safeClick(connect.getIndustryDropdown(), "Open Industry");

            Locator boxes = connect.getIndustryCheckboxes();
            int total = boxes.count();

            Assert.assertTrue(total >= 3, "❌ Less than 3 industry checkboxes available!");

            helper.safeClick(boxes.nth(1), "Select Industry Checkbox 1");
            helper.safeClick(boxes.nth(2), "Select Industry Checkbox 2");
            helper.safeClick(boxes.nth(3), "Select Industry Checkbox 3");

            // Close dropdown (same locator used)
            helper.safeClick(connect.getIndustryDropdown(), "Close Industry Dropdown");

            helper.safeFill(connect.getPasswordField(), password, "Password");

            // ------------------- REGISTER -------------------
            helper.safeClick(connect.getRegisterBtn(), "Click Register");

            // ------------------- OTP SCREEN -------------------
            Locator otpBox = connect.getOtpInput(1);
            otpBox.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(15000));

            helper.log("OTP screen loaded!");

            // ------------------- ENTER OTP -------------------
            for (int i = 1; i <= 4; i++) {
                helper.safeFill(connect.getOtpInput(i),
                        OTP.substring(i - 1, i),
                        "OTP Digit " + i);
            }

            Assert.assertFalse(connect.getVerifyOtpBtn().isDisabled(),
                    "❌ Verify & Proceed button should be enabled!");

            // ------------------- VERIFY OTP -------------------
            helper.safeClick(connect.getVerifyOtpBtn(), "Verify OTP");

            helper.log("===== ✅ GET CONNECTED FLOW COMPLETED SUCCESSFULLY =====");

        } catch (Exception e) {
            Reporter.log("❌ TEST FAILED: " + e.getMessage(), true);
            helper.takeScreenshot("GetConnected_Failed");
            Assert.fail("❌ Test crashed: " + e.getMessage());
        }
    }
}


