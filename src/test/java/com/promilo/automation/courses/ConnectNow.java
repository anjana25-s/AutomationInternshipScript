package com.promilo.automation.courses;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class ConnectNow extends Baseclass {
	
	
	
	 @Test
	    public void TaltoExpert() throws InterruptedException, IOException {
	        ExtentReports extent = ExtentManager.getInstance();
	        ExtentTest test = extent.createTest("🧪 Comment Functionality | Data-Driven");

	        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
	                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

	        int rowCount = 0;
	        for (int i = 1; i <= 1000; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            if (testCaseId == null || testCaseId.trim().isEmpty())
	                break;
	            rowCount++;
	        }

	        System.out.println("📘 Loaded " + rowCount + " rows from Excel.");
	        test.info("📘 Loaded " + rowCount + " rows from Excel.");

	        for (int i = 1; i < rowCount; i++) {
	            String testCaseId = excel.getCellData(i, 0);
	            String keyword = excel.getCellData(i, 1);
	            String email = excel.getCellData(i, 7); // MailPhone
	            String password = excel.getCellData(i, 6); // Password
	            String comment = excel.getCellData(i, 10); // Comment text

	            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
	                continue;
	            }

	            System.out.println("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);
	            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

	            Page page = initializePlaywright();
	            page.navigate(prop.getProperty("url"));
	            page.setViewportSize(1000, 768);
	            Thread.sleep(3000);

	            LandingPage landingPage = new LandingPage(page);
	            try {
	                landingPage.getPopup().click();
	            } catch (Exception ignored) {
	            }

	            Thread.sleep(3000);
	            page.locator("//a[text()='Courses']").click();
	            Thread.sleep(3000);
	            
	            page.locator("//img[@alt='College Bangalore']").first().click();

	            

	       page.locator("//button[text()='Connect Now']").click();   

	            page.locator("//input[@name='userName']").nth(1).fill("karthik");

	            // Generate random 10-digit phone number starting with 90000
	            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));

	            // Generate random email with timestamp
	            String randomEmail = "testuserautomation" + System.currentTimeMillis() + "@mail.com";

	            // Fill the fields
	            page.locator("//input[@name='userMobile']").nth(1).fill(randomPhone);
	            page.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);
	            
	            
	            page.locator("//input[@placeholder='Preferred Stream*']").click();
	            
	            page.locator("//label[text()='Engineering']").click();
	         // Get all checkboxes
	            Locator checkboxes = page.locator("//input[@type='checkbox']").first();
	            checkboxes.click();

	           

	            
	            

	            page.locator("//input[@id='preferredLocation']").nth(1).click();
	            
	            
	            List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
	            Locator options = page.locator("//div[@class='option w-100']");
	            for (String industry : industries) {
	                boolean found = false;
	                for (int i1 = 0; i1 < options.count(); i1++) {
	                    String optionText = options.nth(i1).innerText().trim();
	                    if (optionText.equalsIgnoreCase(industry)) {
	                        options.nth(i1).click();
	                        test.info("✅ Selected industry: " + industry);
	                        found = true;
	                        break;
	                    }
	                }
	                if (!found) {
	                    test.warning("⚠️ Industry not found: " + industry);
	                }
	            }

	            page.locator("//input[@id='preferredLocation']").nth(1).click();
	            
	            
	            page.locator("//input[@placeholder='Create Password*']").nth(1).fill("123456@ab");
	            
	            
	            page.locator("//button[text()='Register']").click();
	            
	            String otp = "9999";
	            // OTP input logic
	            if (otp == null || otp.length() < 4)
	                throw new IllegalArgumentException("❌ OTP must be at least 4 digits. Found: " + otp);

	            for (int i1 = 0; i1 < 4; i1++) {
	                String digit = Character.toString(otp.charAt(i1));
	                Locator otpField = page
	                        .locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
	                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

	                for (int retry = 0; retry < 3; retry++) {
	                    otpField.click();
	                    otpField.fill("");
	                    otpField.fill(digit);

	                    if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
	                        break;
	                    page.waitForTimeout(500);
	                }
	            }

	            page.locator("//button[text()='Verify & Proceed']").click();
	            
	            
	            
	            
	         // ✅ Validate Thank You popup
	            Locator thankYouPopup = page.locator(
	                "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
	            );
	            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(10000)); // wait max 10s

	            String popupText = thankYouPopup.innerText().trim();
	            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
	                    "Expected 'Thank You!' popup, found: " + popupText);
	            
	            page.locator("img[alt='closeIcon Ask us']").click();
	            
	            Thread.sleep(2000);
	         Locator notification=  page.locator("//img[@alt='In-App-Notification-Icon']").nth(1);
	         notification.click();
	       
           test.pass("🎉 Thank You popup validated: " + popupText);

	            
	        }}
}
