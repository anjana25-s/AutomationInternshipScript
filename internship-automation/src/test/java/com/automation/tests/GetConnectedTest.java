package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.constants.GetConnectedExpectedTexts;
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
    private static final String OTP = "9999";
    private static final String INTERNSHIP_NAME = "Tester 1";

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        home = new HomepagePage(page);
        connect = new GetConnectedPage(page);
        helper = new HelperUtility(page);

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
    }

    @Test
    public void verifyGetConnectedCompleteFlow_AssertTrueOnly() {

        try {

            helper.step("START – Get Connected → Full Validation Flow");

            // =====================================================
            // OPEN INTERNSHIP DESCRIPTION
            // =====================================================
            helper.step("Open Internship Description");

            helper.safeClick(home.getInternshipsTab(), "Internships");

            Locator card = home.getInternshipCard(INTERNSHIP_NAME);
            helper.waitForVisible(card, "Internship Card");
            helper.scrollAndClick(card, "Open Internship");

            // =====================================================
            // GET CONNECTED ENTRY POINT
            // =====================================================
            helper.step("Open Get Connected Modal");

            helper.waitForVisible(
                    connect.getGetConnectedBtn(),
                    "Get Connected button"
            );

            Assert.assertTrue(
                    connect.getGetConnectedBtn().isEnabled(),
                    "❌ Get Connected button not enabled"
            );

            helper.safeClick(
                    connect.getGetConnectedBtn(),
                    "Open Get Connected Modal"
            );

            // =====================================================
            // GET CONNECTED MODAL – BANNER
            // =====================================================
            helper.step("Validate Get Connected Banner");

            helper.waitForVisible(
                    connect.getConnectBannerTitle(),
                    "Get Connected banner title"
            );

            Assert.assertTrue(connect.getConnectBannerDesc().isVisible());

            // =====================================================
            // REGISTER MODAL – CONTENT
            // =====================================================
            helper.step("Validate Register Modal Content");

            Assert.assertTrue(connect.getRegisterTitle().isVisible());
            Assert.assertTrue(connect.getRegisterSubtitle().isVisible());

            Assert.assertTrue(page.content().contains(GetConnectedExpectedTexts.WHY_SIGN_IN_TITLE));
            Assert.assertTrue(page.content().contains(GetConnectedExpectedTexts.SIGN_IN_POINT_1));
            Assert.assertTrue(page.content().contains(GetConnectedExpectedTexts.SIGN_IN_POINT_2));
            Assert.assertTrue(page.content().contains(GetConnectedExpectedTexts.SIGN_IN_POINT_3));
            Assert.assertTrue(page.content().contains(GetConnectedExpectedTexts.SIGN_IN_POINT_4));

            // =====================================================
            // TEST DATA
            // =====================================================
            String name = helper.generateRandomName();
            String phone = helper.generateRandomPhone();
            String email = helper.generateEmailFromName(name);
            String password = "Test@" + phone.substring(6);

            // =====================================================
            // REGISTER FORM
            // =====================================================
            helper.step("Fill Register Form");

            helper.safeFill(connect.getNameField(), name, "Name");
            helper.safeFill(connect.getMobileField(), phone, "Mobile");
            helper.safeFill(connect.getEmailField(), email, "Email");

            helper.safeClick(connect.getIndustryDropdown(), "Open Industry");
            helper.safeClick(connect.getIndustryCheckboxes().first(), "Select Industry");
            helper.safeClick(connect.getIndustryDropdown(), "Close Industry");

            helper.safeFill(connect.getPasswordField(), password, "Password");

            Assert.assertTrue(connect.getWhatsappToggleLabel().isVisible());
            Assert.assertTrue(connect.getRegisterBtn().isEnabled());

            helper.safeClick(connect.getRegisterBtn(), "Register");

            // =====================================================
            // OTP SCREEN
            // =====================================================
            helper.step("OTP Verification");

            helper.waitForVisible(connect.getOtpHeader(), "OTP Header");

            Assert.assertTrue(connect.getOtpThankYouText().isVisible());
            Assert.assertTrue(
                    connect.getOtpInstructionText().innerText()
                            .contains(GetConnectedExpectedTexts.OTP_INSTRUCTION_PREFIX)
            );

            Assert.assertTrue(connect.getOtpStillCantFindText().isVisible());
            Assert.assertTrue(connect.getResendOtpText().isVisible());

            Assert.assertTrue(connect.getBanner1Title().isVisible());
            Assert.assertTrue(connect.getBanner2Title().isVisible());
            Assert.assertTrue(connect.getBanner3Title().isVisible());

            Assert.assertTrue(connect.getVerifyOtpBtn().isDisabled());

            for (int i = 1; i <= 4; i++) {
                helper.safeFill(
                        connect.getOtpInput(i),
                        OTP.substring(i - 1, i),
                        "OTP digit " + i
                );
            }

            Assert.assertTrue(connect.getVerifyOtpBtn().isEnabled());
            helper.safeClick(connect.getVerifyOtpBtn(), "Verify OTP");

            // =====================================================
            // FINAL THANK YOU
            // =====================================================
            helper.step("Final Thank You Validation");

            helper.waitForVisible(
                    connect.getFinalThankYouTitle(),
                    "Final Thank You title"
            );

            Assert.assertTrue(
                    connect.getFinalThankYouMessage().innerText().contains("Congratulations")
            );

            Assert.assertTrue(connect.getFinalThankYouSubMessage().isVisible());

            helper.log("✅ END – Get Connected Full Flow PASSED");

        } catch (Exception e) {

            helper.log("❌ FAIL – Get Connected Full Flow FAILED");
            throw e; // Important: rethrow so TestNG marks it failed
        }
    }
}