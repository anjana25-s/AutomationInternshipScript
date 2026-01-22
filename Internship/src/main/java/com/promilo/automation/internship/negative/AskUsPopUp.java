package com.promilo.automation.internship.negative;
import org.testng.Assert;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AskUsPopUp {
    private Page page;
    
 public AskUsPopUp(Page page) {
 	   this.page=page;
    }
 	   // Validate empty name field
       public void verifyEmptyNameField() {
           Locator nameError = page.locator("//div[text()='Name is required']");
           nameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = nameError.textContent().trim();
           boolean isVisible = nameError.isVisible();

           System.out.println("👉 Name Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Name error message is not visible!");
           Assert.assertEquals(actual, "Name is required", "❌ Incorrect name error message!");
           System.out.println("✅ Name validation passed successfully.\n");
       }

       // Validate empty mobile number field
       public void verifyEmptyPhoneField() {
           Locator phoneError = page.locator("//div[text()='Mobile number is required']");
           phoneError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = phoneError.textContent().trim();
           boolean isVisible = phoneError.isVisible();

           System.out.println("👉 Phone Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Mobile number error message is not visible!");
           Assert.assertEquals(actual, "Mobile number is required", "❌ Incorrect mobile number error message!");
           System.out.println("✅ Mobile number validation passed successfully.\n");
       }

       // Validate empty email field
       public void verifyEmptyEmailField() {
           Locator emailError = page.locator("//div[text()='Email is required']");
           emailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = emailError.textContent().trim();
           boolean isVisible = emailError.isVisible();

           System.out.println("👉 Email Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Email error message is not visible!");
           Assert.assertEquals(actual, "Email is required", "❌ Incorrect email error message!");
           System.out.println("✅ Email validation passed successfully.\n");
       }
       
       public void verifyQuestionTextbox() {
    	   Locator queryError=page.locator("//div[text()='Question is required']");
    	   queryError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    	  
    	   String actual = queryError.textContent().trim();
           boolean isVisible = queryError.isVisible();

           System.out.println("👉 Query Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ query error message is not visible!");
           Assert.assertEquals(actual, "Question is required", "❌ Incorrect query error message!");
           System.out.println("✅ query validation passed successfully.\n");
       }
    	   
    	     public void nameTextFieldEnteringLessCharacters() {
           Locator nameError = page.locator("(//div[text()='Must be 3 characters or greater'])[1]");
           nameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = nameError.textContent().trim();
           boolean isVisible = nameError.isVisible();

           System.out.println("👉 Name Length Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Name length error message not visible!");
           Assert.assertEquals(actual, "Must be 3 characters or greater", "❌ Incorrect name length error message!");
           System.out.println("✅ Name length validation passed.\n");
       }

       
    public void phoneTextFieldEnteringLessCharacters() {
           Locator phoneError = page.locator("//div[text()='Invalid Mobile number, must be exactly 10 digits']");
           phoneError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = phoneError.textContent().trim();
           boolean isVisible = phoneError.isVisible();

           System.out.println("👉 Phone Length Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Mobile number length error message not visible!");
           Assert.assertEquals(actual, "Invalid Mobile number, must be exactly 10 digits", "❌ Incorrect mobile number error message!");
           System.out.println("✅ Mobile number length validation passed.\n");
       }

       public void emailTextFieldEnteringLessCharacters() {
           Locator emailError = page.locator("//div[text()='Invalid email address']");
           emailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = emailError.textContent().trim();
           boolean isVisible = emailError.isVisible();

           System.out.println("👉 Email Format Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Email error message not visible!");
           Assert.assertEquals(actual, "Invalid email address", "❌ Incorrect email error message!");
           System.out.println("✅ Email validation passed.\n");
       }
       
       
        public void QueryTextBoxEnteringLessCharacters() {
    	   Locator queryError=page.locator("(//div[text()='Must be 3 characters or greater'])[2]");
    	   
    	   String actual = queryError.textContent().trim();
           boolean isVisible = queryError.isVisible();

           System.out.println("👉 Query Format Error Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ query error message not visible!");
           Assert.assertEquals(actual, "Must be 3 characters or greater", "❌ Incorrect query error message!");
           System.out.println("✅ query validation passed.\n");
       }
       
       public void invalidOtp() {
           Locator otpError = page.locator("//div[text()='Invalid OTP.']");
           otpError.waitFor(new Locator.WaitForOptions().setTimeout(5000));

           String actual = otpError.textContent().trim();
           boolean isVisible = otpError.isVisible();

           System.out.println("👉 Invalid OTP Message Visible: " + isVisible);
           System.out.println("📝 Visible Text: " + actual);

           Assert.assertTrue(isVisible, "❌ Invalid OTP message is not visible!");
           Assert.assertEquals(actual, "Invalid OTP.", "❌ Incorrect OTP error message!");
           System.out.println("✅ Invalid OTP validation passed.\n");
       }
       
       
      
       
       
   }
       
       
       
       
       
       
       
       
       
       
       
       
       
    
