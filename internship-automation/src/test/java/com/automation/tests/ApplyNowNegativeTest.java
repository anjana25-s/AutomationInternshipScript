package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.ApplyNowPage;
import com.automation.pages.ApplyNowErrorMessagesPage;
import com.automation.pages.HomepagePage;
import com.automation.utils.HelperUtility;
import com.microsoft.playwright.Locator;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ApplyNowNegativeTest extends BaseClass {

    @Test
    public void fullNegativeFlow() {

        HomepagePage home = new HomepagePage(page);
        ApplyNowPage apply = new ApplyNowPage(page);
        ApplyNowErrorMessagesPage popup = new ApplyNowErrorMessagesPage(page);
        HelperUtility helper = new HelperUtility(page);

        String INTERNSHIP = "Designer1";

        Reporter.log("==== APPLY NOW FULL NEGATIVE FLOW TEST STARTED ====", true);

        // -------------------- NAVIGATION --------------------
        Reporter.log("[STEP] Navigating to site...", true);
        page.navigate("https://stage.promilo.com/");
        if (home.getMaybeLaterBtn().isVisible()) {
            home.getMaybeLaterBtn().click();
        }

        // -------------------- OPEN INTERNSHIPS --------------------
        helper.safeClick(home.getInternshipsTab(), "Open Internships");

        Locator card = home.getInternshipCard(INTERNSHIP);
        helper.waitForVisible(card, "Internship Card Visible");
        helper.scrollAndClick(card, "Open Internship");

        // -------------------- OPEN APPLY NOW POPUP --------------------
        helper.safeClick(apply.getApplyNowButton(), "Click Apply Now");

        if (apply.getAskUsApplyNowButton().isVisible()) {
            helper.safeClick(apply.getAskUsApplyNowButton(), "Click Popup Apply Now");
        }

        // -------------------- SUBMIT EMPTY FORM --------------------
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Blank Form");

        // REAL ASSERTIONS
        popup.validateRequiredErrors();

        // -------------------- NAME MIN --------------------
        apply.getNameField().fill("ab");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submitting Min Name");
        popup.validateNameMin();

        // -------------------- NAME INVALID --------------------
        apply.getNameField().fill("Megh@");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submitting Invalid Name");
        popup.validateNameInvalid();

        // -------------------- NAME MAX --------------------
        apply.getNameField().fill("A".repeat(51));
        helper.safeClick(apply.getAskUsApplyNowButton(), "Submitting Max Name");
        popup.validateNameMax();

        // -------------------- MOBILE INVALID --------------------
        apply.getPhoneField().fill("98765");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Invalid Mobile Submit");
        popup.validateInvalidMobile();

        // -------------------- EMAIL INVALID --------------------
        apply.getEmailField().fill("megh@");
        helper.safeClick(apply.getAskUsApplyNowButton(), "Invalid Email Submit");
        popup.validateInvalidEmail();

        // -------------------- FIX DATA & SUBMIT --------------------
        apply.getNameField().fill("Meghana");
        apply.getPhoneField().fill("9876543210");
        apply.getEmailField().fill("meghana@gmail.com");

        helper.safeClick(apply.getIndustryDropdown(), "Open Industry");

        Locator boxes = apply.getAllIndustryCheckboxes();
        int total = boxes.count();
        Assert.assertTrue(total >= 3, "❌ Less than 3 industry checkboxes!");

        helper.safeClick(boxes.nth(1), "Select Checkbox 1");
        helper.safeClick(boxes.nth(3), "Select Checkbox 2");
        helper.safeClick(boxes.nth(5), "Select Checkbox 3");

        helper.safeClick(apply.getIndustryDropdown(), "Close Industry");

        helper.safeClick(apply.getAskUsApplyNowButton(), "Submit Valid Data");
        Reporter.log("---- REACHED OTP SCREEN ----", true);

        // ====================================================================
        //                      OTP NEGATIVE SCENARIOS (REAL ASSERTIONS)
        // ====================================================================

        // -------------------- BLANK OTP --------------------
        Reporter.log("[CHECK] Blank OTP → Verify button must be disabled", true);
        popup.validateVerifyBtnDisabled();

        // -------------------- PARTIAL OTP --------------------
        apply.getOtpInputField(1).fill("1");
        popup.validateVerifyBtnDisabled();

        apply.getOtpInputField(2).fill("2");
        popup.validateVerifyBtnDisabled();

        apply.getOtpInputField(3).fill("3");
        popup.validateVerifyBtnDisabled();

        // -------------------- FULL WRONG OTP --------------------
        apply.getOtpInputField(4).fill("4");
        popup.validateVerifyBtnEnabled();

        helper.safeClick(apply.getVerifyAndProceedButton(), "Submit Wrong OTP");

        assertOtpError();  // REAL A-level assertion

       

        Reporter.log("==== APPLY NOW FULL NEGATIVE FLOW TEST COMPLETED SUCCESSFULLY ====", true);
    }


    // -------------------- REAL OTP ASSERTION --------------------
    private void assertOtpError() {
        Locator otpError = page.locator("//div[@role='status' and contains(text(),'Invalid OTP')]");
        otpError.waitFor();
        Assert.assertTrue(otpError.isVisible(), "❌ Expected 'Invalid OTP.' message NOT shown!");
        Reporter.log("[OTP VALIDATION ✓] Invalid OTP displayed", true);
    }
}



