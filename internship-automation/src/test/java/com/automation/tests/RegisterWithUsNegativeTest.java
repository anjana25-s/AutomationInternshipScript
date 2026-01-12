package com.automation.tests;

import com.automation.base.BaseClass;
import com.automation.pages.HomepagePage;
import com.automation.pages.RegisterWithUsPage;
import com.automation.pages.RegisterWithUsErrorMessagesPage;
import com.automation.utils.HelperUtility;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterWithUsNegativeTest extends BaseClass {

    private HomepagePage home;
    private RegisterWithUsPage register;
    private RegisterWithUsErrorMessagesPage errors;
    private HelperUtility helper;

    private static final String BASE_URL = "https://stage.promilo.com/";

    @BeforeMethod(alwaysRun = true)
    public void setup() {

        home     = new HomepagePage(page);
        register = new RegisterWithUsPage(page);
        errors   = new RegisterWithUsErrorMessagesPage(page);
        helper   = new HelperUtility(page);

        helper.log("Navigating to " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState();

        page.navigate("https://stage.promilo.com/");
        page.waitForLoadState();
        closeMilliIfPresent();
        closePreferenceModalIfPresent();

        helper.safeClick(home.getInternshipsTab(), "Open Internships Tab");
    }

    @Test
    public void verifyRegisterWithUsNegativeScenarios() {

        helper.step("START – Register With Us NEGATIVE FLOW");

        // =====================================================
        // 1️⃣ SUBMIT EMPTY FORM
        // =====================================================
        helper.safeClick(
                register.getRegisterNowButton(),
                "Submit Empty Register Form"
        );

        helper.assertVisible(errors.nameRequiredError(), "Name required error");
        helper.assertVisible(errors.mobileRequiredError(), "Mobile required error");
        helper.assertVisible(errors.emailRequiredError(), "Email required error");
        helper.assertVisible(errors.preferredLocationRequiredError(), "Location required error");
        helper.assertVisible(errors.industryRequiredError(), "Industry required error");
        helper.assertVisible(errors.passwordRequiredError(), "Password required error");

        // =====================================================
        // 2️⃣ INVALID NAME
        // =====================================================
        register.getNameField().fill("12@@");
        helper.safeClick(register.getRegisterNowButton(), "Submit Invalid Name");
        helper.assertVisible(errors.invalidNameError(), "Invalid name error");

        // =====================================================
        // 3️⃣ INVALID MOBILE
        // =====================================================
        register.getMobileField().fill("12345");
        helper.safeClick(register.getRegisterNowButton(), "Submit Invalid Mobile");
        helper.assertVisible(errors.invalidMobileError(), "Invalid mobile error");

        // =====================================================
        // 4️⃣ INVALID EMAIL
        // =====================================================
        register.getEmailField().fill("test@");
        helper.safeClick(register.getRegisterNowButton(), "Submit Invalid Email");
        helper.assertVisible(errors.invalidEmailError(), "Invalid email error");

        // =====================================================
        // 5️⃣ PASSWORD VALIDATIONS
        // =====================================================
        register.getPasswordField().fill("12345");
        helper.safeClick(register.getRegisterNowButton(), "Short Password");
        helper.assertVisible(errors.passwordMinError(), "Password min length error");

        register.getPasswordField().fill("A".repeat(16));
        helper.safeClick(register.getRegisterNowButton(), "Long Password");
        helper.assertVisible(errors.passwordMaxError(), "Password max length error");

        // =====================================================
        // 6️⃣ VALID FIELDS BUT NO LOCATION / INDUSTRY
        // =====================================================
        String name  = helper.generateRandomName();
        String phone = helper.generateRandomPhone();
        String email = helper.generateEmailFromName(name);

        register.getNameField().fill(name);
        register.getMobileField().fill(phone);
        register.getEmailField().fill(email);
        register.getPasswordField().fill("Test@123");

        helper.safeClick(
                register.getRegisterNowButton(),
                "Submit Without Location & Industry"
        );

        helper.assertVisible(errors.preferredLocationRequiredError(), "Location required");
        helper.assertVisible(errors.industryRequiredError(), "Industry required");

        // =====================================================
        // 7️⃣ VERIFY OTP SCREEN DOES NOT APPEAR
        // =====================================================
        helper.assertTrue(
                !register.getOtpInputs().first().isVisible(),
                "OTP screen should NOT appear for invalid form"
        );


        helper.pass("✅ REGISTER WITH US – NEGATIVE FLOW VERIFIED");
    }
}



