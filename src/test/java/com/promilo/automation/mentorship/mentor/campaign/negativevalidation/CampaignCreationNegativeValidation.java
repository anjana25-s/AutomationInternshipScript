package com.promilo.automation.mentorship.mentor.campaign.negativevalidation;

import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.mentorship.mentee.AskUsWithInvalidData;
import com.promilo.automation.mentorship.mentee.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CreateProfile;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class CampaignCreationNegativeValidation extends BaseClass {

	private static final Logger log = LogManager.getLogger(AskUsWithInvalidData.class);

	private static String registeredEmail = null;
	private static String registeredPassword = null;

	@BeforeSuite
	public void performSignupOnce() throws Exception {
		System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

		SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
		String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

		registeredEmail = creds[0];
		registeredPassword = creds[1];

		System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
	}

	@Test
	public void campaignProfileNegativeValidation() throws Exception {

		Page page = initializePlaywright();
		page.navigate(prop.getProperty("url"));
		page.setViewportSize(1080, 720);

		MayBeLaterPopUp landingPop = new MayBeLaterPopUp(page);
		try { landingPop.getPopup().click(); } catch (Exception e) {}

		landingPop.clickLoginButton();

		LoginPage loginPage = new LoginPage(page);
		loginPage.loginMailPhone().fill(registeredEmail);
		loginPage.passwordField().fill(registeredPassword);
		loginPage.loginButton().click();

		// Navigate
		Hamburger myStuff = new Hamburger(page);
		myStuff.Mystuff().click();

		BecomeMentor becomeMentor = new BecomeMentor(page);
		becomeMentor.becomeMentorButton().click();
		becomeMentor.createMentorshipSession().click();

		CreateProfile profile = new CreateProfile(page);

		// 0) save without any input
		profile.saveButton().click();
		page.waitForTimeout(1200);
		

		
		
		List<String> allErrors1 = page.locator("[class='text-danger']").allTextContents();

		for(String msg : allErrors1) {

		    System.out.println("❌ Error: " + msg);

		    // assertion
		    if(msg.contains("First Name is required") ||
		       msg.contains("Last Name is required") ||
		       msg.contains("Mobile number is required") ||
		       msg.contains("Location is required") ||
		       msg.contains("Gender is required") ||
		       msg.contains("Date of Birth is required") ||
		       msg.contains("Experience is required") ||
		       msg.contains("Type of mentor is required") ||
		       msg.contains("Domain is required") ||
		       msg.contains("Highlight is required") ||
		       msg.contains("Image is required")) {

		        org.testng.Assert.assertTrue(true);
		    } else {
		        org.testng.Assert.fail("Unexpected error text found: " + msg);
		    }
		}

		// 1a) less than minimum length
		profile.firstName().fill("aa");
		profile.firstName().blur();
		String err1 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for 'aa': " + err1);
		org.testng.Assert.assertTrue(err1.contains("at least 3 characters"), "❌ Expected min length validation not shown");

		// 1b) clear the input for the next input value
		profile.firstName().fill("");
		profile.firstName().blur();

		// 1c) invalid numeric mix
		profile.firstName().fill("aa123");
		profile.firstName().blur();
		String err3 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for 'aa123': " + err3);
		org.testng.Assert.assertTrue(err3.contains("Only alphabets"), "❌ Expected alphabets only validation not shown");

		// finally set correct valid data for further steps
		profile.firstName().fill("karthik");
		profile.firstName().blur();


		
		

		

		// 2) lastname

		// invalid numeric only
		profile.lastName().fill("123");
		profile.lastName().blur();
		String err4 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for '123': " + err4);
		org.testng.Assert.assertTrue(err4.contains("Only alphabets are allowed"), "❌ Expected alphabets only validation not shown for '123'");

		// invalid space at end
		profile.lastName().fill("aaaa ");
		profile.lastName().blur();
		String err5 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for 'aaaa ': " + err5);
		org.testng.Assert.assertTrue(err5.contains("should not start or end with a space"), "❌ Expected trimming validation not shown for last name");

		// finally valid lastname for continuation
		profile.lastName().fill("U");
		profile.lastName().blur();

		// 3) Phone validation

		String dynamicPhone = "90000" + String.format("%05d", (int)(Math.random() * 100000));

		// invalid - alphabets inside
		profile.mobileTextfield().fill("abc90000");
		profile.mobileTextfield().blur();
		String phErr1 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for 'abc90000': " + phErr1);
		org.testng.Assert.assertEquals(phErr1.trim(), "Mobile number must be exactly 10 digits",
		        "❌ Validation message mismatch for 'abc90000'");

		// invalid - less digits
		profile.mobileTextfield().fill("90000");
		profile.mobileTextfield().blur();
		String phErr2 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for '90000': " + phErr2);
		org.testng.Assert.assertEquals(phErr2.trim(), "Mobile number must be exactly 10 digits",
		        "❌ Validation message mismatch for '90000'");

		// valid dynamic phone
		profile.mobileTextfield().fill(dynamicPhone);
		profile.mobileTextfield().blur();   // trigger validation



		
		// OTP NEGATIVE validation

		// click send OTP first
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();

		Locator otpField = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("mobile_otp"));

		// alphabets
		otpField.fill("abcd");
		otpField.blur();
		String otpErr1 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for 'abcd': " + otpErr1);
		org.testng.Assert.assertEquals(otpErr1.trim(), "OTP must contain only digits",
		        "❌ OTP validation mismatch for alpha input");

		// invalid alpha numeric
		otpField.fill("12ab");
		otpField.blur();
		String otpErr2 = page.locator("[class='text-danger']").first().innerText();
		System.out.println("Error for '12ab': " + otpErr2);
		org.testng.Assert.assertEquals(otpErr2.trim(), "OTP must contain only digits",
		        "❌ OTP validation mismatch for alphanumeric input");

		// invalid  digits
		otpField.fill("1234");  
		otpField.blur();
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
		
        MentorshipErrorMessagesAndToasters err = new MentorshipErrorMessagesAndToasters(page);
        String actualToaster = err.invalidOtp().textContent().trim();
        Assert.assertTrue(actualToaster.contains("Invalid OTP"), "❌ Invalid OTP toaster not displayed");

		// valid otp final
		otpField.fill("9999");
		otpField.blur();


		page.waitForTimeout(1500);
		page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
		page.waitForTimeout(1200);

		page.waitForTimeout(1200);

		// 4) location
		profile.locationDropdown().first().click();
		page.keyboard().type("Ananta");
		page.keyboard().press("Enter");

		// 5) gender
		profile.genderDropdown().first().click();
		page.getByText("Male").first().click();
		page.waitForTimeout(1200);

		// 6) experience
		profile.experianceDropdwon().first().click();
		page.keyboard().type("2");
		page.keyboard().press("Enter");
		page.waitForTimeout(1200);

		// 7) mentor type
		profile.typeofMentor().click();
		page.getByText("Academic Guidance").click();
		page.waitForTimeout(1200);

		// 8) domain
		profile.domain().click();
		page.getByText("Course Selection").click();
		page.waitForTimeout(1200);

		// 9) category
		profile.categoryDropdown().click();
		page.getByText("Engineering").first().click();
		page.waitForTimeout(1200);

		// 10) course
		profile.coursesDropdown().click();
		page.getByText("BE/B.Tech").first().click();
		page.waitForTimeout(1200);

		// 11) DOB
		profile.dateOfBirth().first().fill("2002-08-24");
		page.waitForTimeout(1200);

		// 12) specialization
		profile.specialization().click();
		page.locator("li > .select-item > .item-renderer > span").first().click();
		page.waitForTimeout(1200);

		// 13) upload image
		profile.uploadCampaignImage().setInputFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));
		profile.cropButton().click();
		page.waitForTimeout(1200);

		// 14) highlight
		profile.highlightTextfield().fill("dxgfchvjbng vbnm");
		page.waitForTimeout(2000);

		System.out.println("✅ Negative sequential validation flow executed successfully.");
	}
}
