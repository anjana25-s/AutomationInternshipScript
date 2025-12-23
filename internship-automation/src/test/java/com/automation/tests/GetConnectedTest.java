package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.GetConnectedPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetConnectedTest extends BaseClass {

    private HomepagePage home;
    private GetConnectedPage connect;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";
    private static final String INTERNSHIP = "Tester 1";
    private static final String OTP = "9999";

    @BeforeMethod(alwaysRun = true)
    public void openBase() {

        home    = new HomepagePage(page);
        connect = new GetConnectedPage(page);
        helper  = new HelperUtility(page);

        helper.log("Navigating to Promilo");
        page.navigate(BASE_URL);
        page.waitForLoadState();

        Assert.assertTrue(page.url().contains("promilo"),
                "❌ URL did not load correctly");

        if (home.getMaybeLaterBtn().isVisible()) {
            helper.safeClick(home.getMaybeLaterBtn(),
                    "Close Maybe Later Popup");
        }
    }

    @Test
    public void verifyGetConnectedFlow() {

        // ------------------------------------------------------------
        // TEST DATA
        // ------------------------------------------------------------
        String name     = helper.generateRandomName();
        String phone    = helper.generateRandomPhone();
        String email    = helper.generateEmailFromName(name);
        String password = "Test@" + phone.substring(6);

        helper.log("Name = " + name);
        helper.log("Phone = " + phone);
        helper.log("Email = " + email);

        // ------------------------------------------------------------
        // OPEN INTERNSHIP
        // ------------------------------------------------------------
        helper.safeClick(home.getInternshipsTab(),
                "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card,
                "Internship Card");

        helper.scrollAndClick(card,
                "Open Internship");

        // ------------------------------------------------------------
        // GET CONNECTED
        // ------------------------------------------------------------
        helper.waitForVisible(connect.getGetConnectedBtn(),
                "Get Connected Button");
        helper.safeClick(connect.getGetConnectedBtn(),
                "Click Get Connected");

        // ------------------------------------------------------------
        // FILL FORM
        // ------------------------------------------------------------
        helper.safeFill(connect.getNameField(),
                name, "Name");
        helper.safeFill(connect.getMobileField(),
                phone, "Mobile");
        helper.safeFill(connect.getEmailField(),
                email, "Email");

        // ------------------------------------------------------------
        // INDUSTRY DROPDOWN
        // ------------------------------------------------------------
        helper.safeClick(connect.getIndustryDropdown(),
                "Open Industry Dropdown");

        Locator industries = connect.getIndustryCheckboxes();
        Assert.assertTrue(industries.count() >= 3,
                "❌ Less than 3 industry options");

        helper.safeClick(industries.nth(1),
                "Select Industry 1");
        helper.safeClick(industries.nth(2),
                "Select Industry 2");
        helper.safeClick(industries.nth(3),
                "Select Industry 3");

        helper.safeClick(connect.getIndustryDropdown(),
                "Close Industry Dropdown");

        helper.safeFill(connect.getPasswordField(),
                password, "Password");

        // ------------------------------------------------------------
        // REGISTER
        // ------------------------------------------------------------
        helper.safeClick(connect.getRegisterBtn(),
                "Click Register");

        // ------------------------------------------------------------
        // OTP
        // ------------------------------------------------------------
        helper.waitForVisible(connect.getOtpInput(1),
                "OTP Screen");

        for (int i = 1; i <= 4; i++) {
            helper.safeFill(
                    connect.getOtpInput(i),
                    OTP.substring(i - 1, i),
                    "OTP Digit " + i
            );
        }

        Assert.assertFalse(connect.getVerifyOtpBtn().isDisabled(),
                "❌ Verify button should be enabled");

        helper.safeClick(connect.getVerifyOtpBtn(),
                "Verify OTP");

        helper.log("🎉 GET CONNECTED FLOW PASSED!");
    }
}




