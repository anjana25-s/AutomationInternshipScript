package com.automation.tests;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.options.AriaRole;

public class ApplyNowEmailTest {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeClass
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false)); // GUI
        page = browser.newPage();
    }

    @Test
    public void applyNowTest() throws InterruptedException {
        page.navigate("https://stage.promilo.com/");
        Thread.sleep(5000);
        page.locator("//img[@alt='Close']").click();
        page.locator("//div[@class='Login-button']").click();
        page.locator("//input[@placeholder='Enter Email Or Mobile No.']").fill("testwork1@yopmail.com");
        page.locator("//p[@class='font-12 fw-bold text-primary pt-50 text-end mb-0 pointer']").click();
        Thread.sleep(2000);
        page.locator("//input[@id='otp']").fill("9999");
        Thread.sleep(2000);
        page.locator("//button[text()='Login']").click();
        Thread.sleep(1000);

        page.locator("//a[text()='Internships']").click();
        Thread.sleep(1000);

        page.locator("(//p[text()='Artificial Intelligence'])[1]").click();
        Thread.sleep(2000);
        page.locator("(//button[text()='Apply Now'])[1]").click();
        page.locator("//input[@placeholder='Name*']").fill("manju");
        page.locator("//input[@placeholder='Mobile*']").fill("9000010654");
        page.locator("//div[text()='Select your Industry*']").click();
        page.locator("//label[text()='Advertising & Marketing']").click();
        page.locator("//div[@id='industry-dropdown']").click();
        page.locator("(//button[text()='Apply Now'])[4]").click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please enter OTP character 1")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please enter OTP character 1")).fill("9");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please enter OTP character 2")).fill("9");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please enter OTP character 3")).fill("9");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Please enter OTP character 4")).fill("9");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify & Proceed")).click();

        page.locator("//span[@aria-label='September 26, 2025']").click();
        page.locator("//li[text()='06:00 AM']").click();
        page.locator("//button[text()='Next']").click();
        page.locator("//input[@id='2c9fa8f5973526e5019743bac04e12da']").click();
        page.locator("(//button[text()='Submit'])[2]").click();

        page.locator("//a[text()='My Interest']").click();
        page.waitForTimeout(2000);
    }

    @AfterClass
    public void teardown() {
        browser.close();
        playwright.close();
    }
}
