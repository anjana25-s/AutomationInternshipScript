package com.promilo.automation.hrcallNegativeflow;

import org.testng.annotations.Test;
import basetest.Baseclass;
import com.promilo.automation.internship.assignment.*;
import com.promilo.automation.internship.negative.GetHRcallPopup;
import com.promilo.automation.internship.utilities.SignUpUtility;

public class CallbackNegativeValidation extends Baseclass {

    /**
     * Common navigation to Internship → Automation Tester → Get HR Call
     */
    private void navigateToHRCallForm() throws InterruptedException {
        page.locator("//button[text()='May be later!']").click();
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        CallbackPage callback = new CallbackPage(page);
        callback.clickGetHRCall();
    }

    /**
     * Test Case 1: Validation for submitting empty form fields
     */
    @Test(priority = 1)
    public void validateEmptyFields() throws InterruptedException {
        navigateToHRCallForm();

        CallbackPage callback = new CallbackPage(page);
        callback.clickButton();

        GetHRcallPopup popup = new GetHRcallPopup(page);
        popup.verifyEmptyNameField();
        popup.verifyEmptyPhoneField();
        popup.verifyEmptyEmailField();
        popup.verifyIndustryDropdown();

        System.out.println("✅ Empty field validation completed.");
    }

    /**
     * Test Case 2: Validation for invalid input data (short name, short phone, invalid email)
     */
    @Test(priority = 2)
    public void validateInvalidInputData() throws InterruptedException {
        navigateToHRCallForm();

        CallbackPage callback = new CallbackPage(page);
        callback.enterStudentName("Di");
        callback.studentMobileNumber("90000987");
        callback.enterMailId("diya33yopmail.com");
        callback.clickButton();

        GetHRcallPopup popup = new GetHRcallPopup(page);
        popup.nameTextFieldEnteringLessCharacters();
        popup.phoneTextFieldEnteringLessCharacters();
        popup.emailTextFieldEnteringLessCharacters();
        popup.verifyIndustryDropdown();

        System.out.println("✅ Invalid data validation completed.");
    }

    /**
     * Test Case 3: Validation for invalid OTP entry
     */
    @Test(priority = 3)
    public void validateInvalidOtp() throws InterruptedException {
        navigateToHRCallForm();

        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String invalidOtp = SignUpUtility.generateInvalidOtp();

        CallbackPage callback = new CallbackPage(page);
        callback.enterStudentName("Diya");
        callback.studentMobileNumber(mobile);
        callback.enterMailId(email);
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();

        // Entering wrong OTP digits
        callback.enterNumber(invalidOtp);
        callback.clickSubmitButton();

        GetHRcallPopup popup = new GetHRcallPopup(page);
        popup.invalidOtp();

        System.out.println("✅ Invalid OTP validation completed.");
    }

    /**
     * Test Case 4: Validation for screening question flow
     */
    @Test(priority = 4)
    public void validateScreeningQuestionFlow() throws InterruptedException {
        navigateToHRCallForm();

        CallbackPage callback = new CallbackPage(page);
        callback.enterStudentName("Pavi");
        callback.studentMobileNumber("9000020312");
        callback.enterMailId("pavi12@yopmail.com");
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();

        String otp = SignUpUtility.getFixedOtp();
        callback.enterNumber(otp);
        callback.clickSubmitButton();

        // Navigate through screening questions
        callback.clickLanguage();
        callback.clickSubmit();
        callback.clickOnSubmit();
        
        GetHRcallPopup popup = new GetHRcallPopup(page);
        popup.screeningQuestion();

        System.out.println("✅ Screening question validation completed.");
    }

    /**
     * Test Case 5: Validation for 'Already Shown Interest' scenario
     */
    @Test(priority = 5)
    public void validateAlreadyShownInterest() throws InterruptedException {
        page.locator("//button[text()='May be later!']").click();
        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        CallbackPage callback = new CallbackPage(page);
        callback.clickInternshipsTab();
        callback.clickAutomationTesterCard();
        callback.clickGetHRCall();

        callback.enterStudentName("Pavi");
        callback.studentMobileNumber("9000020312");
        callback.enterMailId("pavi12@yopmail.com");
        callback.industryDropdown();
        callback.selectValue();
        callback.closeDropdown();
        callback.clickButton();

        String otp = SignUpUtility.getFixedOtp();
        callback.enterNumber(otp);
        callback.clickSubmitButton();
        callback.clickLanguage();
        callback.clickSubmit();
        callback.clickOncheckBox();
        callback.clickOnSubmit();

        GetHRcallPopup popup = new GetHRcallPopup(page);
        popup.alreadyShownInterest();

        System.out.println("✅ Already shown interest validation completed.");
    }
}