package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.FillApplication;
import com.promilo.automation.courses.intrestspages.FillApplicationFormComponents;
import com.promilo.automation.courses.pageobjects.CourseSearchPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class LoggedUserFailPaymentWantedly extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

	private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();
        registeredEmail = creds[0];
        registeredPassword = creds[1];
        logger.info("✅ Signup completed for suite. Email: " + registeredEmail);
    }


    	
    @Test
    public void applyForJobWithInvalidData() throws Exception {

        
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

     // ======== Load Excel ========
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Mentee Interest");
            System.out.println("✅ Excel loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Failed to load Excel: " + e.getMessage());
            test.fail("Failed to load Excel: " + e.getMessage());
            Assert.fail("Excel loading failed");
            return;
        }

        // ======== Map headers to column index ========
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ======== Count data rows (skip header) ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("✅ Loaded " + rowCount + " data rows from Excel.");

        if (rowCount == 0) {
            System.out.println("❌ No data rows found");
            test.fail("No data rows in Excel");
            Assert.fail("No data rows in Excel");
            return;
        }

        // ======== Process rows ========
        Set<String> targetKeywords = Collections.singleton("GetMentorCall");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();

            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data using header mapping
            String mentorName     = excel.getCellData(i, colMap.get("MentorName"));
            String otp            = excel.getCellData(i, colMap.get("otp"));
            String invoiceName    = excel.getCellData(i, colMap.get("invoiceName"));
            String name           = excel.getCellData(i, colMap.get("MentorName"));
            String street1        = excel.getCellData(i, colMap.get("street1"));
            String street2        = excel.getCellData(i, colMap.get("street2"));
            String pincode        = excel.getCellData(i, colMap.get("pincode"));
            String gst            = excel.getCellData(i, colMap.get("gst"));
            String pan            = excel.getCellData(i, colMap.get("pan"));
            String contactNumber  = excel.getCellData(i, colMap.get("contactNumber"));
            String password       = excel.getCellData(i, colMap.get("password"));
        
        
        
        
        
        
        
        
        
        
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        
        
        
        page.waitForTimeout(3000);
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();
        
        
        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill(registeredEmail);
        loginPage.passwordField().fill(registeredPassword);
        loginPage.loginButton().click();
        test.info("Logged in as registered user: " );

        

        Thread.sleep(3000);
        test.info("⏱ Waited 3 seconds for page load.");

        CourseSearchPage coursePage = new CourseSearchPage(page);
        coursePage.searchAndSelectCourse("Golden Institute of Technology");


        
        
        
        
        page.waitForTimeout(10000);
        //  Click Fill  Application
        page.locator("//span[text()='Fill Application']").click();
        test.info("👨‍🏫 Clicked 'Talk to Experts' button.");
        
        
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        int randomNum = 10000 + new Random().nextInt(90000);
        String serverId = "qtvjnqv9";
        String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";

        
        //Fill pop-up form
        FillApplicationFormComponents fillPopUpForm= new FillApplicationFormComponents(page);
        fillPopUpForm.nameTextField().fill("karthik");
        fillPopUpForm.mobileTextField().fill(randomPhone);

        // Step 4: Select preferred locations
        fillPopUpForm.preferredLocationDropdown().click();
        List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
        fillPopUpForm.selectLocations(industries);

        fillPopUpForm.nameTextField().click();

        // Step 5: Click Fill Application
        fillPopUpForm.fillApplicationButton().click();

        // Step 6: Enter OTP
        String validOtp = "9999";
        fillPopUpForm.enterOtp(validOtp);

        // Click Verify & Proceed
        fillPopUpForm.verifyAndProceedButton().click();

        page.waitForTimeout(2000);         
        
        
        
        
                FillApplication fillApplication = new FillApplication(page);

                // ========================
                // FIRST PAGE – PERSONAL DETAILS
                // ========================

                fillApplication.lastName().fill("U");
                fillApplication.dateOfBirth().fill("1997-08-28");
                fillApplication.genderDropdown().click();
                fillApplication.maleOption().click();
                fillApplication.sendOtpButton().click();
                
                
                
                Page page1 = page.context().newPage();
                page1.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

                
                page1.waitForTimeout(3000);
                MailsaurCredentials formOtp= new MailsaurCredentials(page1);       
                if (page1.locator("//input[@id='email']").isVisible()) {
                    // Need to login
                    formOtp.MialsaurMail().fill("karthikmailsaur@gmail.com");
                    formOtp.MailsaurContinue().click();
                    formOtp.MailsaurPassword().fill("Karthik@88");
                    formOtp.MailsaurLogin();
                } else {
                    System.out.println("Already logged in. Skipping login.");
                }
                
                
                Locator firstEmail = page1.locator("//p[text()='Verify Your Email Address to Complete Promilo Registration 🌐']").first();
                firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                firstEmail.click();
                page1.waitForTimeout(3000);
                String otpText = page1.locator("//p[contains(text(),'Verification Code')]").innerText().trim();
                String formOtp1 = otpText.replaceAll("\\D+", "");
                System.out.println("✅ OTP fetched from Mailosaur UI: " + otp);
                
                
                
                page1.close();        

                
                fillApplication.emailOtp().fill(formOtp1);
                fillApplication.verifyOtpButton().click();
                System.out.println("Mail OTP verified");

                fillApplication.aadhar().fill("121113231322");
                fillApplication.sendOtpButton().click();
                fillApplication.mobileOtp().fill("9999");

                page.waitForTimeout(4000);
                fillApplication.verifyOtpButton().click();
                System.out.println("Phone verified");

                fillApplication.nextButton().click();

                // ========================
                // SECOND PAGE – EDUCATION 
                // ========================

                fillApplication.boardOfExamination().click();
                fillApplication.centralBoardOption().click();
                fillApplication.selectBoardText().click();
                fillApplication.subOption().first().click();

                fillApplication.genderDropdown().click();
                fillApplication.bandScoreOption().click();

                fillApplication.companyName().click();
                fillApplication.companyName().fill("promilo");

                fillApplication.jobTitle().click();
                fillApplication.jobTitle().fill("qa engineer");

                fillApplication.saveAndNextButton().click();

                fillApplication.genderDropdown().first().click();
                fillApplication.jeeMainOption().click();

                fillApplication.reactSelectControlInput().click();
                fillApplication.jeeMainOption().click();

                page.waitForTimeout(2000);
                fillApplication.saveAndNextButton().click();

                // ========================
                // THIRD PAGE – ACHIEVEMENTS / UPLOAD
                // ========================

                fillApplication.selectedValuesDisplay().click();

                // Print all sub-options
                List<Locator> subOptions = fillApplication.allSubOptions().all();
                for (int i1 = 0; i1 < subOptions.size(); i1++) {
                    System.out.println("Option " + (i1 + 1) + ": " + subOptions.get(i1).textContent().trim());
                }

                fillApplication.subOption().first().click();

                // Achievement dropdown
                page.waitForTimeout(3000);
                Locator level = fillApplication.achievementLevelDropdown();
                level.scrollIntoViewIfNeeded();
                level.click();
                fillApplication.districtLevelOption().click();

                // Upload files
                page.waitForTimeout(4000);
                page.waitForFileChooser(() -> fillApplication.uploadFileFirst().click())
                        .setFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));

                fillApplication.declarationText().click();
                fillApplication.incompleteWarningText().click();

                page.waitForFileChooser(() -> fillApplication.uploadFileSecond().click())
                        .setFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));

                fillApplication.declarationCheckbox().check();

                fillApplication.saveAndNextButton().click();
                page.waitForTimeout(100);

                



        }
    }
    
}
