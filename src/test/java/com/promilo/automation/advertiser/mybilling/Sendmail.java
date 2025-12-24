package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class Sendmail extends  BaseClass {
	 ExtentReports extent = ExtentManager.getInstance();
	    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

	    @Test
	    public void runAddFundsTest() {
	        try {
	            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
	            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
	            test.info("✅ Loaded test data from Excel: " + excelPath);

	            Page page = initializePlaywright();
	            page.navigate(prop.getProperty("stageUrl"));
	            page.setViewportSize(1000, 768);
	            test.info("✅ Navigated to: " + prop.getProperty("stageUrl"));

	            // Login
	            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
	            login.loginMailField().fill("warm-apart@ofuk8kzb.mailosaur.net");
	            login.loginPasswordField().fill("Karthik@88");
	            login.signInButton().click();
	            test.pass("✅ Logged in successfully");

	            // Navigate to Billing
	            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
	            homepage.hamburger().click();
	            homepage.myAccount().click();
	            homepage.myBilling().click();
	            test.pass("✅ Navigated to My Billing");

	            // Billing page interactions
	            Billing billing = new Billing(page);
	            Thread.sleep(5000);
	            billing.sendMail().first().click();
	            test.pass("✅ Clicked Send Mail button");

	            page.locator("//button[@style='background-color: #006699 !important']").click();
	            test.pass("✅ Clicked confirm button");

	            Page page1 = page.context().newPage();
	            test.info("✅ Opened new page for Mailosaur");

	            page1.navigate("https://mailosaur.com/app/servers/ofuk8kzb/messages/inbox");
	            test.info("✅ Navigated to Mailosaur inbox");

	            page1.locator("//input[@placeholder='Enter your email address']").fill("karthiku7026@gmail.com");
	            page1.locator("//button[text()='Continue']").click();
	            test.info("✅ Entered email and clicked Continue");

	            page1.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
	            page1.locator("//button[text()='Log in']").click();
	            test.info("✅ Entered password and logged in");

	            Locator firstEmail = page1.locator("//p[text()='Invoice Attached: Review your Billing Details']").first();
	            firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	            test.pass("✅ Inbox email list is visible");

	            firstEmail.click();
	            test.pass("✅ Clicked the first email in inbox");

	            Thread.sleep(5000); // Ideally use explicit wait for email body or attachment

	            page1.getByText("Hi test user, As requested,").click();
	            test.pass("✅ Clicked email content text");

	            page1.getByTestId("sidebar-attachments").click();
	            test.pass("✅ Clicked attachments sidebar");

	            Download download = page1.waitForDownload(() -> {
	                Locator downloadButton = page1.locator("//div[@class='w-full']").nth(1);
	                downloadButton.click();
	            });
	            test.pass("✅ Triggered download and waiting for file");

	            Path downloadedFilePath = download.path();
	            test.pass("✅ File downloaded to: " + downloadedFilePath);

	            Page page2 = page.context().newPage();
	            page2.navigate(downloadedFilePath.toUri().toString());
	            test.pass("✅ Opened downloaded file in new tab: " + downloadedFilePath.toUri());

	        } catch (Exception e) {
	            test.fail("❌ Test failed with exception: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            extent.flush();
	        }
	    }
}
