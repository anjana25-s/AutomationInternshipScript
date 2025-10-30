package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class GetConnectedActions {


	    private final Page page;
	    private final GetConnectedLocators locators;

	    public GetConnectedActions(Page page) {
	        this.page = page;
	        this.locators = new GetConnectedLocators(page);
	    }

	    public void registerUserInPopupWithoutWaitingBackdrop(
	        String name,
	        String mobile,
	        String email,
	        String industry,
	        String password
	    ) {
	        Locator getConnectedBtn = page.locator(locators.getConnectedBtn);
	        getConnectedBtn.waitFor(new Locator.WaitForOptions()
	            .setState(WaitForSelectorState.VISIBLE).setTimeout(15000));
	        getConnectedBtn.click();
	        System.out.println("[GetConnectedActions] Clicked 'Get Connected' button.");

	        Locator nameInput = page.locator(locators.userName);
	        nameInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(60000));
	        nameInput.scrollIntoViewIfNeeded();

	        nameInput.fill(name);
	        page.locator(locators.userMobile).fill(mobile);
	        page.locator(locators.userEmail).fill(email);

	        page.locator(locators.industryDropdown).click();
	        Locator industryOption = page.locator(locators.industryOption(industry));
	        industryOption.waitFor();
	        industryOption.click();

	        page.locator("body").click(); // close dropdown

	        page.locator(locators.password).fill(password);

	        page.locator(locators.registerBtn).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	        page.locator(locators.registerBtn).click();

	        System.out.println("[GetConnectedActions] Filled popup form and clicked Register.");
	        
	    }
	

private void waitForVisible(Locator locator, int timeoutMs) {
    locator.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout((double) timeoutMs));
}

    // Click Get Connected button to open popup
    public void clickGetConnected() {
        Locator btn = page.locator(locators.getConnectedBtn);
        
        btn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(10000));
        btn.click();
        System.out.println("[GetConnectedActions] Clicked 'Get Connected' button.");

        // Wait for backdrop to disappear so the form can be interacted with
        page.waitForSelector("div.modal-backdrop", new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.DETACHED)
            .setTimeout(30000));
    }

    public void fillUserName(String name) {
        Locator input = page.locator(locators.userName);
        waitForVisible(input, 10000);
        input.scrollIntoViewIfNeeded();
        input.fill(name);
    }


    public void fillUserMobile(String mobile) {
        Locator input = page.locator(locators.userMobile);
        waitForVisible(input, 5000);
        input.scrollIntoViewIfNeeded();
        input.fill(mobile);
    }

    public void fillUserEmail(String email) {
        Locator input = page.locator(locators.userEmail);
        waitForVisible(input, 5000);
        input.scrollIntoViewIfNeeded();
        input.fill(email);
    }

    public void fillPassword(String password) {
        Locator input = page.locator(locators.password);
        waitForVisible(input, 5000);
        input.scrollIntoViewIfNeeded();
        input.fill(password);
    }

    public void selectIndustry(String industry) {
        Locator dropdown = page.locator(locators.industryDropdown);
        waitForVisible(dropdown, 5000);
        dropdown.click();

        Locator option = page.locator(locators.industryOption(industry));
        waitForVisible(option, 5000);
        option.click();

        // Click outside dropdown to close it
        page.locator("body").click();
        System.out.println("[GetConnectedActions] Selected industry: " + industry);
    }

    public void clickRegister() {
        Locator btn = page.locator(locators.registerBtn);
        waitForVisible(btn, 5000);
        btn.click();
        System.out.println("[GetConnectedActions] Clicked 'Register' button.");
    }

    // OTP entry: 4-digit OTP expected
    public void enterOtp(String otp) {
        if (otp == null || otp.length() != 4) {
            throw new IllegalArgumentException("OTP must be exactly 4 characters");
        }
        for (int i = 0; i < otp.length(); i++) {
            Locator input = page.locator(locators.otpInput(i + 1));
            waitForVisible(input, 5000);
            input.fill(String.valueOf(otp.charAt(i)));
        }
        System.out.println("[GetConnectedActions] Entered OTP: " + otp);
    }

    public void clickVerify() {
        Locator btn = page.locator(locators.verifyBtn);
        waitForVisible(btn, 10000);
        btn.click();
        System.out.println("[GetConnectedActions] Clicked 'Verify & Proceed' button.");
    }

    public boolean isThankYouDisplayed() {
        Locator popup = page.locator(locators.thankYouPopup);
        return popup.isVisible();
    }
}
