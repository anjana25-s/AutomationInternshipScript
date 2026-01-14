package com.promilo.automation.internship.assignment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators for login workflow elements
   private final Locator maybeLater;
    private final Locator login;
    private final Locator emailId;
    private final Locator password;
    private final Locator loginButton;
   
    public LoginPage(Page page) {
        this.page = page;
       this.maybeLater = page.locator("//button[text()='May be later!']");
        this.login = page.locator("//div[text()='Login']"); 
        this.emailId = page.locator("input[placeholder='Enter Email Or Mobile No.']");
        this.password = page.locator("input[placeholder='Enter Password']");
        this.loginButton = page.locator("//button[text()='Login']");
    }
   
    public void clickMaybeLater() {
      
        maybeLater.click();
        page.waitForTimeout(2000); 
    }
    
    public void clickLoginButtonOnHome() {
     
        login.click();
        page.waitForTimeout(2000); 
    }
   

    public void enterEmail(String email) {
      
        emailId.fill(email);
        page.waitForTimeout(2000); 
    }
   
    public void enterPassword(String pass) {
     
        password.fill(pass);
        page.waitForTimeout(2000); 
    }
    
    public void clickLoginSubmit() {
        
        loginButton.click();
        page.waitForTimeout(2000); 
    }
    
   }