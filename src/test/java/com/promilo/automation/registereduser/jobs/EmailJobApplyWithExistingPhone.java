package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.emailnotifications.jobapply.UserInitiatesApplyingForJob;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class EmailJobApplyWithExistingPhone extends BaseClass {

	ExtentReports extent = ExtentManager.getInstance();
    //Test Description
    ExtentTest test = extent.createTest("🚀 promilo shortlist functionality with email notification validation");


   private static final Logger logger = LogManager.getLogger(UserInitiatesApplyingForJob.class);

   private static String registeredEmail = null;
   private static String registeredPassword = null;

   @BeforeSuite
   public void performSignupOnce() throws Exception {
       System.out.println("⚙️ Performing signup ONCE before entire suite using Mailosaur UI signup...");

       SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
       String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

       registeredEmail = creds[0];
       registeredPassword = creds[1];

       System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
   }

   @DataProvider(name = "jobApplicationData")
   public Object[][] jobApplicationData() throws Exception {
       String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
       ExcelUtil excel = new ExcelUtil(excelPath, "PromiloJob");

       int rowCount = 0;
       for (int i = 1; i <= 1; i++) {  
           String testCaseId = excel.getCellData(i, 0);
           if (testCaseId == null || testCaseId.isEmpty()) break;
           rowCount++;
       }

       Object[][] data = new Object[rowCount][8];
       for (int i = 1; i <= rowCount; i++) {
           data[i - 1][0] = excel.getCellData(i, 0); // TestCaseID
           data[i - 1][1] = excel.getCellData(i, 1); // Keyword
           data[i - 1][2] = excel.getCellData(i, 4); // InputValue (ignored)
           data[i - 1][3] = excel.getCellData(i, 6); // Password (ignored)
           data[i - 1][4] = excel.getCellData(i, 7); // Name
           data[i - 1][5] = excel.getCellData(i, 5); // OTP
           data[i - 1][6] = excel.getCellData(i, 8); // MailPhone
           data[i - 1][7] = i;                       // RowIndex
       }
       return data;
   }

   @Test(dataProvider = "jobApplicationData")
   public void applyForJobTestFromExcel(
           String testCaseId,
           String keyword,
           String inputvalue,
           String password,
           String name,
           String otp,
           String mailphone,
           int rowIndex
   ) throws Exception {

       ExtentReports extent = ExtentManager.getInstance();
       ExtentTest test = extent.createTest("Apply for Job as Registered User | " + testCaseId);

       if (registeredEmail == null || registeredPassword == null) {
           test.fail("❌ Signup credentials not found.");
           Assert.fail("Signup not completed.");
           return;
       }

       // Override input credentials with signed up ones
       inputvalue = registeredEmail;
       password = registeredPassword;

       Page page = initializePlaywright();
       page.navigate(prop.getProperty("url"));
       page.setViewportSize(1000, 768);

       applyForJobAsRegisteredUser(page, inputvalue, password, name, otp, mailphone);

       test.pass("✅ Job application test passed for TestCase: " + testCaseId);
       extent.flush();
   }

   public void applyForJobAsRegisteredUser(Page page, String inputvalue, String password, String name, String otp, String mailphone) throws Exception {
       MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
       try {
           mayBeLaterPopUp.getPopup().click();
       } catch (Exception ignored) {}

       mayBeLaterPopUp.clickLoginButton();

       LoginPage loginPage = new LoginPage(page);
       loginPage.loginMailPhone().fill(inputvalue);
       loginPage.passwordField().fill(password);
       loginPage.loginButton().click();
       
       
       JobListingPage jobPage = new JobListingPage(page);
       jobPage.homepageJobs().click();
      
       
       Locator developerJob = page.locator("//h3[text()='Developer']").first();
       developerJob.click();
       test.info("Clicked on Developer job listing");
       
       Thread.sleep(4000);
       
       

       page.locator("//button[text()='Apply Now']").first().click();

       jobPage.applyNameField().fill("karthik");
       

       // Use your POM method instead of hardcoded locator
       jobPage.applyNowMobileTextField().fill("7026268342");   
       jobPage.selectIndustryDropdown().click();
       Thread.sleep(1000);

       List<String> industries = Arrays.asList(
               "Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education"
       );

       Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
       for (String industry : industries) {
           boolean found = false;
           for (int i = 0; i < options.count(); i++) {
               if (options.nth(i).innerText().trim().equalsIgnoreCase(industry)) {
                   options.nth(i).click();
                   test.info("✅ Selected industry: " + industry);
                   found = true;
                   break;
               }
           }
           if (!found) test.warning("⚠️ Industry not found: " + industry);
       }

       jobPage.applyNameField().click(); // blur to trigger validation
       Thread.sleep(1000);

       Locator applyBtn = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
       applyBtn.scrollIntoViewIfNeeded();
       applyBtn.click();
       Thread.sleep(2000);


        // Screenshot capture
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/"  + "_shortlist_pass.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));

        test.addScreenCaptureFromPath(screenshotPath, "Screenshot after job shortlist");

        byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
        String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);
        test.addScreenCaptureFromBase64String(base64Screenshot, "Base64 Screenshot after job shortlist");

        page.close();
        test.info("Browser closed for TestCaseID: " );

        extent.flush();
    }
}
