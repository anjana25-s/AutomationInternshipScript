package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class GetHRcallDataValidation {

private final Page page;

public GetHRcallDataValidation(Page page) {
this.page = page;
}

// ============================ Popup & Header ===============================
public Locator getHrCallPopupDescription() {
return page.locator("[class='text-content']");
}

public Locator getHrCallHeaderText() {
return page.locator("[class='feature-content-header']");
}

public String getCampaignName() {
    String header =
            getHrCallHeaderText().textContent().trim();

    return header
            .replace("Get an HR Call from", "")
            .replace("!", "")
            .trim();
}

public Locator enableWhatsappNotification() {
return page.locator("[class='form-check-label']");
}

public Locator getHrCallFooterText() {
return page.locator("[class='text-center pt-2']");
}

// ============================ Industry Options =============================
public Locator industryOptions() {
return page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
}

// ============================ OTP Page ====================================
public Locator otpPageDescription() {
return page.locator("[class='col-6 askUs-content-container free-counselling']");
}

public Locator otpDigitField(int index) {
return page.locator("//input[@aria-label='Please enter OTP character " + index + "']");
}

public Locator otpThanksForInformation() {
return page.locator("[class='text-primary mb-2 text-center']");
}

public Locator otpVerificationHeader() {
return page.locator("//h5[text()='OTP Verification']");
}

public Locator otpInstructionText() {
return page.locator("//p[@class=' text-center']");
}

public Locator otpStillCantFind() {
return page.locator("//p[text()='Still can’t find the OTP?']");
}

// ============================ Language Selection ===========================
public Locator nextPageInfoText() {
return page.locator("[class='otp-banner-info mb-2 calendar-infobox']");
}

public Locator chooseLanguageText() {
return page.locator("[class='fw-500 font-18 text-primary mb-50 text-default-color-language']");
}

public String getCampaignNameFromChooseLanguage() {
    String text = chooseLanguageText().textContent().trim();

    return text
            .replace("Please Select your preferred language with", "")
            .replace("This will make it easier for you and HR", "")
            .replace(".", "")
            .trim();
}

public Locator takeMomentSideText() {
return page.locator("[class='col-6 askUs-content-container ']");
}


public Locator takeMomentText() {
return page.locator("//span[text()='Please take a moment to answer the below questions.']");
}

public Locator thankYouText() {
return page.locator("[class='ThankYou-content justify-content-center']");
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
}
    
    public Locator latestInAppNotification() {
        return page.locator(
            "(//div[contains(@class,'notification-item')]//h6)[1]"
        );
    }

}
 



