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



public Locator signUpSideContentHeader() {
	return page.locator("//p[@class='header-text header-text-margin']");
	
}

public Locator SignUpSideContentFirstDescription() {
	return page.locator("(//li[@class='pb-1'])[1]");
	
}
public Locator SignUpSideContentSecondDescription() {
	return page.locator("(//li[@class='pb-1'])[2]");
	}

public Locator SignUpSideContentThirdDescription() {
	return page.locator("(//li[@class='pb-1'])[3]");

}

public Locator SignUpSideContentFourthDescription() {
	return page.locator("(//li[@class='pb-1'])[4]");

}
public Locator inAppNotification() {
    return page.locator("(//div[@class='notification-item notification-item-unread'])[1]");
  
    
}}














