package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
/**
 * Represents the Login Page of the application.
 * Contains actions to perform login-related operations.
 */
public class LoginPage {
    private final Page page;

    // Locators for login workflow elements
   private final Locator maybeLater;
    private final Locator login;
    private final Locator emailId;
    private final Locator password;
    private final Locator loginButton;
    /**
     * Constructor initializes Playwright Page instance and related locators.
     *
     * @param page Playwright Page used for interaction
     */
    public LoginPage(Page page) {
        this.page = page;
       this.maybeLater = page.locator("//button[text()='May be later!']");
        this.login = page.locator("//div[text()='Login']"); 
        this.emailId = page.locator("input[placeholder='Enter Email Or Mobile No.']");
        this.password = page.locator("input[placeholder='Enter Password']");
        this.loginButton = page.locator("//button[text()='Login']");
    }
    /**
     * Clicks on the "May be later!" popup to close any initial popups.
     */
    public void clickMaybeLater() {
      
        maybeLater.click();
        page.waitForTimeout(2000); 
    }
    /**
     * Clicks the Login button on the homepage to open the login popup.
     */
    public void clickLoginButtonOnHome() {
     
        login.click();
        page.waitForTimeout(2000); 
    }
   

    /**
     * Enters user email in the login field.
     *
     * @param email User email
     */
    public void enterEmail(String email) {
      
        emailId.fill(email);
        page.waitForTimeout(2000); 
    }
    /**
     * Enters user account password.
     *
     * @param pass User password
     */
    public void enterPassword(String pass) {
     
        password.fill(pass);
        page.waitForTimeout(2000); 
    }
    /**
     * Clicks on the Login button to submit login credentials.
     */
    public void clickLoginSubmit() {
        
        loginButton.click();
        page.waitForTimeout(2000); 
    }
}