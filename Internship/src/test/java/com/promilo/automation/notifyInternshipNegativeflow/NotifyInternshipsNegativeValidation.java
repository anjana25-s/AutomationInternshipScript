package com.promilo.automation.notifyInternshipNegativeflow;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.*;
import com.promilo.automation.internship.negative.NotifyInternshipPopup;
import com.promilo.automation.internship.utilities.SignUpUtility;

import basetest.Baseclass;

public class NotifyInternshipsNegativeValidation extends Baseclass {

    @Test
    public void NotifyInternshipFullNegativeFlow() throws InterruptedException {

        // Launch Home Page & navigate to Internships
        page.locator("//button[text()='May be later!']").click();

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();
        System.out.println("Clicked Internships tab");

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        NotifyInternshipsPage notify = new NotifyInternshipsPage(page);
     
        // Close Milli again (it reopens sometimes)
        closeMilliIfVisible();
        notify.clickOnNotify();
        notify.button();
        
     
        NotifyInternshipPopup popup = new NotifyInternshipPopup(page);
        popup.verifyEmptyNameField();
        popup.verifyEmptyPhoneField();
        popup.verifyEmptyEmailField();
        System.out.println("✅ Blank field validation passed!");

        // -------------------------------------------------------------------------------------
        //  NEGATIVE FLOW 2 — Invalid Input Data (Short Name, Short Phone, Invalid Email)
        // --------------------------------------------------------------------------------------
        page.reload();
        homePage.clickInternships();
        internshipPage.clickAutomationTesterCard();
        closeMilliIfVisible();
        notify.clickOnNotify();
        notify.typeUserName("Ka");
        notify.enterPhoneNumber("900002026");
        notify.typeemail("kamal8yopmail.com");
        notify.button();

        popup.nameTextFieldEnteringLessCharacters();
        popup.phoneTextFieldEnteringLessCharacters();
        popup.emailTextFieldEnteringLessCharacters();
        System.out.println("✅ Invalid input data validation passed!");

        // --------------------------------------------------------------------------------------
        // 🔹 NEGATIVE FLOW 3 — Invalid OTP Validation
        // --------------------------------------------------------------------------------------
        page.reload();
        homePage.clickInternships();
        internshipPage.clickAutomationTesterCard();
        closeMilliIfVisible();
        notify.clickOnNotify();
        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String invalidOtp = SignUpUtility.generateInvalidOtp();

        notify.typeUserName("Kamal");
        notify.enterPhoneNumber(mobile);
        notify.typeemail(email);
        notify.button();

        // Entering wrong OTP digits
        notify.enterOtp(invalidOtp);
        notify.clickSubmitButton();

        popup.invalidOtp();
        System.out.println("✅ Invalid OTP validation passed!");

        // --------------------------------------------------------------------------------------
        // ✅ Final confirmation
        // --------------------------------------------------------------------------------------
        System.out.println("🎯 Notify Internship Negative Flow validation completed successfully!");
    }
}
