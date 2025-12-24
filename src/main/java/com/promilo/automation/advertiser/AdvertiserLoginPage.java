package com.promilo.automation.advertiser;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AdvertiserLoginPage {
    
    private final Page page;

    private final Locator loginMailField;
    private final Locator loginPasswordField;
    private final Locator signInButton;
    private final Locator forgotPassword;
    private final Locator notACustomerSignInForFree;
    private final Locator talkToAnExpert;
    private final Locator signInContent;
    private final Locator startYourFreeTrial;

    public AdvertiserLoginPage(Page page) {
        this.page = page;

        this.loginMailField = page.locator("label[for='login-email']");
        this.loginPasswordField = page.locator("input[placeholder='Enter Password']");
        this.signInButton = page.locator("//button[text()='Sign In']");
        this.forgotPassword = page.locator("span[class='remember-me']");
        this.notACustomerSignInForFree = page.locator("//button[text()='Try for Free']");
        this.talkToAnExpert = page.locator("p[class='expert ']");
        this.signInContent = page.locator("div[class='sign-in-content']");
        this.startYourFreeTrial = page.locator("//button[text()='Start Your Free Trial']");
    }

    public Locator loginMailField() { return loginMailField; }
    public Locator loginPasswordField() { return loginPasswordField; }
    public Locator signInButton() { return signInButton; }
    public Locator forgotPassword() { return forgotPassword; }
    public Locator notACustomerSignInForFree() { return notACustomerSignInForFree; }
    public Locator talkToAnExpert() { return talkToAnExpert; }
    public Locator signInContent() { return signInContent; }
    public Locator startYourFreeTrial() { return startYourFreeTrial; }
}
