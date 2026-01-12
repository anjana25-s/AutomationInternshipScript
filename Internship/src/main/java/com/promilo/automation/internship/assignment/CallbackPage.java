package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
/**
 * Page Object Model for Callback / HR Call process during internship enrollment
 */
public class CallbackPage {
	private static final String String = null;

	private final Page page;
	
	// Locators for Internship and Callback Flow
private final Locator internshipsTab;
private final Locator automationTesterCard;
private final Locator getHRCall;
private final Locator userName;
private final Locator studentName;
private final Locator mobileNumber;
private final Locator enterNumber;
private final Locator enterEmail;
private final Locator emailTextField;
private final Locator selectIndustry;
private final Locator clickOnIndustry;
private final Locator selectValue;
private final Locator selectoneValue;
private final Locator selectIndustryClose;
private final Locator closeIndustryDropDown;
private final Locator button;
//OTP Locators
private final Locator enterOtp1;
private final Locator enterOtp2;
private final Locator enterOtp3;
private final Locator enterOtp4;
// Additional callback steps
private final Locator clickButton;
private final Locator selectLanguage;
private final Locator clickNext;
private final Locator clickLanguage;
private final Locator clickCheckBox;
private final Locator clickSubmit;
private final Locator clickMyPreference;
private final Locator clickBuildResume;
private final Locator buildReumeText;



/**
 * Constructor to initialize locators
 */
public CallbackPage(Page page) {
	this.page=page;
	this.internshipsTab = page.locator("//a[text()='Internships']");
	this.automationTesterCard = page.locator("//h3[text()='Artificial Intelligence']");
	this.getHRCall=page.locator("//button[text()='Get HR Call']");
	this.userName=page.locator("//input[@name='userName']");
	this.studentName=page.locator("//input[@placeholder='Name*']");
	this.mobileNumber=page.locator("//input[@placeholder='Mobile*']");
	this.enterNumber=page.locator("(//input[@name='userMobile'])[2]");
	this.enterEmail=page.locator("//input[@placeholder='Email*']");
	this.emailTextField=page.locator("(//input[@name='userEmail'])[2]");
	this.selectIndustry=page.locator("[id='industry-dropdown']");
	this.clickOnIndustry=page.locator("[id='industry-dropdown']");
	this.selectValue=page.locator("[id='checkbox-5']");
	this.selectoneValue=page.locator("[id='checkbox-3']");
	this.closeIndustryDropDown=page.locator("[id='industry-dropdown']");
	this.selectIndustryClose=page.locator("[id='industry-dropdown']");
	this.button=page.locator("//button[text()='Get an HR Call']");
	this.enterOtp1=page.locator("//input[@aria-label='Please enter OTP character 1']");
	this.enterOtp2=page.locator("//input[@aria-label='Please enter OTP character 2']");
	this.enterOtp3=page.locator("//input[@aria-label='Please enter OTP character 3']");
	this.enterOtp4=page.locator("//input[@aria-label='Please enter OTP character 4']");
	this.clickButton=page.locator("//button[text()='Verify & Proceed']");
	this.selectLanguage=page.locator("(//span[@class='font-14 text-dark-grey label-text'])[1]");
	this.clickNext=page.locator("//button[@class='fw-bold w-100 font-16 fw-bold calendar-modal-custom-btn mt-2 btn btn-primary']");
	this.clickLanguage=page.locator("(//span[@class='font-14 text-dark-grey label-text'])[1]");
	this.clickCheckBox=page.locator("(//input[@class='form-check-input'])[1]");
	this.clickSubmit=page.locator("(//button[text()='Submit'])[2]");
	this.clickMyPreference=page.locator("//a[text()='My Preference']");
	this.clickBuildResume=page.locator("(//div[@class='build-upload-resume'])[1]");
	this.buildReumeText=page.locator("//div[text()='Build Resume']");
}



/** Click Internships tab */
public void clickInternshipsTab() {
	internshipsTab.click();
	

}
/** Select internship card */
public void clickAutomationTesterCard() {
	automationTesterCard.click();
	page.waitForTimeout(2000);
	
}

/** Click Get HR Call button */
public void clickGetHRCall() {
	page.waitForTimeout(2000);
   getHRCall.first().click();
   }

/** Enter name in callback form 
 * @throws InterruptedException */
public void enterUserName(String name) throws InterruptedException {
	
	userName.scrollIntoViewIfNeeded();
	userName.first().fill(name);
	
}
public void enterStudentName(String name) {
	
	studentName.nth(1).fill(name);
}

/** Enter mobile number */
public void enterMobileNumber(String number) {
	mobileNumber.fill(number);
	
}

public void studentMobileNumber(String number) {
	enterNumber.fill(number);
	enterNumber.nth(2);
	page.waitForTimeout(2000);
}

/** Enter email ID */
public void emailIdField(String email) {
	enterEmail.fill(email);
	}

public void enterMailId(String email) {
	emailTextField.fill(email);
	emailTextField.nth(2);
	page.waitForTimeout(2000);
}

/** Open industry dropdown */
 public void clickDropDown() {
	selectIndustry.click();
	page.waitForTimeout(2000);
	}
 
 public void industryDropdown() {
	 clickOnIndustry.nth(1).click();
	 page.waitForTimeout(2000);
 }

 /** Select industry value */
 public void clickOnValue() {
	 page.waitForTimeout(2000);
	 selectValue.scrollIntoViewIfNeeded();
	 selectValue.click();
	 }
 
 public void selectValue() {
	 selectoneValue.first().click();
	 page.waitForTimeout(2000);
 }
 
 /**click on dropdown*/
 public void clickondropdown() {
	 selectIndustryClose.click();	 
 }
 
 public void closeDropdown() {
	 closeIndustryDropDown.nth(1).click();
 }
 
 /** Submit HR request */
public void clickButton() {
		button.click();
		page.waitForTimeout(3000);
		}

/**
 * Fill all 4 OTP fields
 * @param otp Four digit OTP
 */
 public void enterNumber(String number) {
	
	 enterOtp1.fill(number);
	 page.waitForTimeout(2000);
 }
 public void enterNum(String number) {
	
	 enterOtp2.fill(number);
	 page.waitForTimeout(2000);
	 
 }
 public void enterNum1(String number) {
	
	 enterOtp3.fill(number);
	 page.waitForTimeout(2000);
	
 }
 public void enterNum2(String number) {
	
	 enterOtp4.fill(number);
	 page.waitForTimeout(2000);
 }
 /** Click Verify & Proceed */
public void clickSubmitButton() {
	
	clickButton.click();
	 page.waitForTimeout(2000);
}
/** Select communication language */
 public void clickLanguage() {

	 selectLanguage.click();
	 page.waitForTimeout(2000);
 }
 /** Click Next button */
public void clickSubmit() {
	
	clickNext.click();
	page.waitForTimeout(2000);
}
/** Select checkbox for screening question*/
public void clickOncheckBox() {
	clickCheckBox.click();
	page.waitForTimeout(2000);
}
/** Final submit */
public void clickOnSubmit() {
	clickSubmit.click();
	page.waitForTimeout(2000);
	
}

public void myPreference() {
	clickMyPreference.click();
	page.waitForTimeout(2000);
	}

/** Click Build Resume options */
public void buildResume()
{
	clickBuildResume.click();
	page.waitForTimeout(2000);
}
public Locator buildReumeText() {
	return buildReumeText;
}


}