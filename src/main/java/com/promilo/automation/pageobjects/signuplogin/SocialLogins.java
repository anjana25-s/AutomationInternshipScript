package com.promilo.automation.pageobjects.signuplogin;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SocialLogins {

    Page page;

    public SocialLogins(Page page) {
        this.page = page;
    }

    private Locator googleSignin() {
        return page.locator("//div[@role='button']");
    }

    private Locator linkedinSignin() {
        return page.locator("//img[@class='img-fluid pointer' and @alt='Linkedin']");
    }

    private Locator linkedMail() {
        return page.locator("//input[@id='username']");
    }

    private Locator linkedinPassword() {
        return page.locator("#password");
    }

    private Locator linkedinSigninButton() {
        return page.locator("//button[@class='btn__primary--large from__button--floating']");
    }

    private Locator facebookSignin() {
        return page.locator("//img[@class='img-fluid pointer' and @alt='Facebook']");
    }

    private Locator facebookMail() {
        return page.locator("//input[@name='email']");
    }

    private Locator facebookPassword() {
        return page.locator("//input[@name='pass']");
    }

    private Locator facebookLogin() {
        return page.locator("//button[@id='loginbutton']");
    }

    private Locator allowButton() {
        return page.locator("//button[@type='submit' and @name='action']");
    }

    // Exposed methods for use in tests

    public void clickGoogleSignin() {
        googleSignin().click();
    }

    public void clickLinkedinSignin() {
        linkedinSignin().click();
    }

    public void enterLinkedinMail(String email) {
        linkedMail().fill(email);
    }

    public void enterLinkedinPassword(String password) {
        linkedinPassword().fill(password);
    }

    public void clickLinkedinSigninButton() {
        linkedinSigninButton().click();
    }

    public void clickFacebookSignin() {
        facebookSignin().click();
    }

    public void enterFacebookMail(String email) {
        facebookMail().fill(email);
    }

    public void enterFacebookPassword(String password) {
        facebookPassword().fill(password);
    }

    public void clickFacebookLogin() {
        facebookLogin().click();
    }

    public void clickAllowButton() {
        allowButton().click();
    }
}
