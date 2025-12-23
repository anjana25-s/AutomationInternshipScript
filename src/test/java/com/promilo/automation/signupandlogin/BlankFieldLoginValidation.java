package com.promilo.automation.signupandlogin;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExtentManager;


public class BlankFieldLoginValidation extends BaseClass {

    @Test
    public void blankFieldSignupValidation() throws Exception { 
    	
    	Page page = initializePlaywright();
    	page.setViewportSize(1366, 768);


        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("Promilo Staging - Blank Field Signup Validation");

        test.info("🚀 Test started: Blank Field Signup Validation");

        page.navigate(prop.getProperty("url"));
        test.info("🌐 Navigated to URL: " + prop.getProperty("url"));

LandingPage landingPage = new LandingPage(page);
        try {
            Locator popup = landingPage.getPopup();
            if (popup.isVisible()) {
                popup.click();
                test.info("✅ Popup closed.");
            } else {
                test.info("ℹ️ No popup detected, continuing...");
            }
        } catch (Exception e) {
            test.info("ℹ️ No popup detected, continuing...");
        }

        landingPage.clickLoginButton();
        test.info("✅ Signup button clicked.");

LoginPage loginpage = new LoginPage(page);
Locator loginButton = loginpage.loginButton();

        if (loginButton.isDisabled()) {
            test.pass("login  button is disabled when fields are empty, as expected.");
        } else {
            test.fail("❌ login button should be disabled when fields are empty.");
        }

        page.close();
        test.info("🧹 Browser closed.");
        extent.flush();
    }
}
