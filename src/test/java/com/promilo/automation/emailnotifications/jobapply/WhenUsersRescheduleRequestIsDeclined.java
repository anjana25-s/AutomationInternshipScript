package com.promilo.automation.emailnotifications.jobapply;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdverstiserMyaccount;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.AdvertiserProspects;
import com.promilo.automation.pageobjects.signuplogin.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class WhenUsersRescheduleRequestIsDeclined extends Baseclass {

	 @DataProvider(name = "jobApplicationData")
	    public Object[][] jobApplicationData() throws Exception {
	        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
	                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

	        int totalRows = 0;
	        for (int i = 1; i <= 1000; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            if (testCaseId == null || testCaseId.isEmpty()) break;
	            totalRows++;
	        }

	        List<Object[]> filteredData = new ArrayList<>();
	        for (int i = 1; i <= totalRows; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            String keyword = excel.getCellData(i, 1);
	            String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

	            if (normalizedKeyword.equals("registereduserjobshortlist") ||
	                normalizedKeyword.equals("registereduserjobshortlistwithsignup") ||
	                normalizedKeyword.equals("registereduserfeedbackwithsignup")) {

	                Object[] row = new Object[8];
	                row[0] = testCaseId;
	                row[1] = keyword;
	                row[2] = excel.getCellData(i, 3); // InputValue
	                row[3] = excel.getCellData(i, 6); // Password
	                row[4] = excel.getCellData(i, 7); // Name
	                row[5] = excel.getCellData(i, 5); // OTP
	                row[6] = excel.getCellData(i, 8); // MailPhone
	                row[7] = i;                         // RowIndex
	                filteredData.add(row);
	            }
	        }

	        // If no rows match, return a dummy row to mark test as passed
	        if (filteredData.isEmpty()) {
	            filteredData.add(new Object[]{"NoTest", "NoKeyword", "", "", "", "", "", 0});
	        }

	        return filteredData.toArray(new Object[0][0]);
	    }

	    @Test(dataProvider = "jobApplicationData")
	    public void applyForJobAsRegisteredUser(
	            String testCaseId,
	            String keyword,
	            String inputvalue,
	            String password,
	            String name,
	            String otp,
	            String mailphone,
	            int rowIndex
	    ) throws Exception {

	        // Pass automatically if no matching keyword
	        if ("NoTest".equals(testCaseId)) {
	            return;
	        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1000, 768);

        applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

      
    }

    public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp,
            String mailphone) throws Exception {


        LandingPage landingPage = new LandingPage(page);
        try {
            landingPage.getPopup().click();
        } catch (Exception ignored) {
        }

        landingPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(inputvalue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        applyJobDetailsFlow(page, name, otp, mailphone);
    }

    private void applyJobDetailsFlow(Page page, String name, String otp, String mailphone) throws Exception {
        JobListingPage jobPage = new JobListingPage(page);

        Thread.sleep(5000);
        jobPage.homepageJobs().click();

        page.locator("//input[@placeholder='Search Jobs']").fill("Hiring for Software Developer Duplicate");
        page.keyboard().press("Enter");

        Locator developerJob = page.locator("//p[text()='Developer']").nth(1);
        developerJob.click();

        
        Locator developerJob1 = page.locator("//p[text()='Developer']").nth(1);
        developerJob.click();
        page.locator("//button[text()='Apply Now']").first().click();

        jobPage.applyNameField().fill("karthik");

        // Generate random number if mailphone is null/empty
        Random random = new Random();
        String mobileToUse = (mailphone != null && !mailphone.isEmpty())
                ? mailphone
                : ("90000" + String.format("%05d", random.nextInt(100000)));
        jobPage.applyNowMobileTextField().fill(mobileToUse);

        jobPage.selectIndustryDropdown().click();

        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX",
                "Healthcare", "Education");

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                if (options.nth(i).innerText().trim().equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    found = true;
                    break;
                }
            }
            if (!found)
        
        jobPage.applyNameField().click();

        Locator applyBtn = page.locator(
                "//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyBtn.scrollIntoViewIfNeeded();
        applyBtn.click();

        // OTP input logic
        if (otp == null || otp.length() < 4)
            throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

        for (int i = 0; i < 4; i++) {
            String digit = Character.toString(otp.charAt(i));
            Locator otpField = page
                    .locator("//input[@aria-label='Please enter OTP character " + (i + 1) + "']");
            otpField.click();
            otpField.fill("");
            otpField.fill(digit);
        }

        page.locator("//button[text()='Verify & Proceed']").click();

        Locator firstEnabledDate = page.locator("span.flatpickr-day:not(.flatpickr-disabled)").first();
        firstEnabledDate.click();
        
        
        
        Thread.sleep(2000);
        
        page.locator("//li[@class='time-slot-box list-group-item']").first().click();


        page.locator("//button[text()='Submit']").nth(1).click();

        Locator thankYouPopup = page.locator(
                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor();
        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, found: " + popupText);
        
        page.locator("img[alt='closeIcon Ask us']").first().click();


        page.locator("//span[text()='My Interest']").click();
        page.locator("img[alt='Reschedule']").first().click();

        page.locator("span.flatpickr-day[aria-current='date']").click();
        page.locator("li.time-slot-box.list-group-item").first().click();
        page.locator("//button[text()='Continue']").click();

        // Advertiser Side
        Page page3 = page.context().newPage();
        page3.navigate("https://stagebusiness.promilo.com/");
        page3.setViewportSize(1000, 768);

        AdvertiserLoginPage login = new AdvertiserLoginPage(page3);
        login.loginMailField().fill("agree-laugh@ofuk8kzb.mailosaur.net");
        login.loginPasswordField().fill("Karthik@88");
        login.signInButton().click();

        AdvertiserHomepage myaccount = new AdvertiserHomepage(page3);
        myaccount.hamburger().click();
        myaccount.myAccount().click();

        AdverstiserMyaccount prospect = new AdverstiserMyaccount(page3);
        prospect.myProspect().click();

        AdvertiserProspects approveFunctionality = new AdvertiserProspects(page3);
        approveFunctionality.Jobs().click();

        page3.locator("//span[text()='Reschedule Request']").first().click();

        
        page3.locator("//button[text()='Cancel Request']").click();
        page3.locator("//button[contains(text(),'Reject')]").click();
        
      System.out.println(page3.locator("//div[contains(text(),\"You have Successfully rejected user's\")]").textContent());  
        System.out.println("Reject functionality executed");
    }
}}
