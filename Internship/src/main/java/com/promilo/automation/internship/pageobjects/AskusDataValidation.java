package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AskusDataValidation {

private final Page page;

public AskusDataValidation(Page page) {
this.page = page;
}

// ===================== ASK US PAGE TEXT =====================
public Locator askUsDescription() {
	return page.locator("[class='text-content']");
}


public Locator askUsHeaderText() {
	return page.locator("[class='feature-content-header']");
}


public Locator askUsFooterText() {
return page.locator("[class='text-center pt-2']");
}

// ===================== OTP PAGE =====================
public Locator otpPageDescription() {
return page.locator("[class='col-6 askUs-content-container free-counselling']");
}

public Locator otpInputField(int index) {
return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
}

public Locator otpSuccessText() {
return page.locator("[class='text-primary mb-2 text-center']");
}

public Locator otpHeader() {
	
    return page.locator("//h5[text()='OTP Verification']");
}


public Locator otpDescription() {
return page.locator("//p[@class=' text-center']");
}

public Locator otpStillCantFind() {
return page.locator("//p[text()='Still can’t find the OTP?']");
}

// ===================== THANK YOU POPUP =====================
public Locator thankYouHeader() {
    return page.locator(".ThankYouPopup-Modal .headerText");
}


}

