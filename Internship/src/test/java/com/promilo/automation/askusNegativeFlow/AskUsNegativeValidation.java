package com.promilo.automation.askusNegativeFlow;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.promilo.automation.internship.assignment.AskUsPage;
import com.promilo.automation.internship.assignment.HomePage;
import com.promilo.automation.internship.assignment.InternshipPage;
import com.promilo.automation.internship.assignment.NotifyInternshipsPage;
import com.promilo.automation.internship.assignment.SignUpUtility;
import com.promilo.automation.internship.negative.AskUsPopUp;
import basetest.Baseclass;

public class AskUsNegativeValidation extends Baseclass {

    @Test
    public void AskUsTestFullFlow() throws InterruptedException {

        page.locator("//button[text()='May be later!']").click();

        HomePage homePage = new HomePage(page);
        homePage.clickInternships();

        InternshipPage internshipPage = new InternshipPage(page);
        internshipPage.clickAutomationTesterCard();

        AskUsPage askus = new AskUsPage(page);
        askus.clickAskUs();

        AskUsPopUp popup = new AskUsPopUp(page);

        //  NEGATIVE FLOW — Without data validation
        askus.clickOnButton();
        popup.verifyEmptyNameField();
        popup.verifyEmptyPhoneField();
        popup.verifyEmptyEmailField();
        popup.verifyQuestionTextbox();
        System.out.println("Blank fields validation Passed!");

        //  NEGATIVE FLOW — Invalid short input validation
        askus.typeUserName("An");
        askus.typenumber("90000299");
        askus.typeEmail("ammuu044yopmail.com");
        askus.enterQuery("H");
        askus.clickOnButton();
        System.out.println("Invalid length validation Passed!");

        // Refresh & fill valid data
        page.reload();
        askus.clickAskUs();

        String email = SignUpUtility.generateRandomEmail();
        String mobile = SignUpUtility.generateRandomMobile();
        String otp = SignUpUtility.getFixedOtp(); 
        String invalidOtp = SignUpUtility.generateInvalidOtp();

        askus.typeUserName("Ammu Tester");
        askus.typenumber(mobile);
        askus.typeEmail(email);
        askus.enterQuery("Good evening, I have a doubt");
        askus.clickOnButton();

        //  NEGATIVE FLOW — Invalid OTP
        askus.enterOtp(invalidOtp);
        askus.clickVerify();
        popup.invalidOtp();
        System.out.println("Invalid OTP validation Passed!");

        // POSITIVE FLOW — Valid OTP
        askus.enterOtp(otp);
        askus.clickVerify();

        // Success validation
        NotifyInternshipsPage thankYou = new NotifyInternshipsPage(page);
        String actualText = thankYou.successPopup().textContent().trim();
        Assert.assertEquals(actualText, "Thank You!", "Success popup not shown!");

        System.out.println("Ask Us full validation flow completed successfully!");
    }
}