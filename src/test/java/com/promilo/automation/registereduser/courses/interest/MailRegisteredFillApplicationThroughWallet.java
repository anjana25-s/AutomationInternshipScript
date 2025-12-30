package com.promilo.automation.registereduser.courses.interest;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.courses.intrestspages.FillApplication;
import com.promilo.automation.courses.intrestspages.FillApplicationFormComponents;
import com.promilo.automation.courses.pageobjects.CourseSearchPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.AddFundsUtility;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class MailRegisteredFillApplicationThroughWallet extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);
	private static String registeredEmail;
    private static String registeredPassword;

    
    @BeforeSuite
    public void performSignupOnce() throws Exception {
        SignupWithMailosaurUI signup = new SignupWithMailosaurUI();
        String[] creds = signup.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];
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
        test.info("Logged in as registered user: " + registeredEmail);

     // ======== Add Funds in the SAME SESSION ========
        AddFundsUtility.addFundsInSameSession(page, invoiceName, street1, street2, pincode, gst, pan, contactNumber);

        Thread.sleep(3000);
        test.info("⏱ Waited 3 seconds for page load.");

        CourseSearchPage coursePage = new CourseSearchPage(page);
        coursePage.searchAndSelectCourse("Golden Institute of Technology");


        
        
        
        
        page.waitForTimeout(10000);
                
        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));

        
        FillApplicationFormComponents fillPopUpForm= new FillApplicationFormComponents(page);
        String alphabets = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder name1 = new StringBuilder();

        name1.append(Character.toUpperCase(alphabets.charAt(random.nextInt(alphabets.length()))));

        for (int i1 = 1; i1 < 8; i1++) {   // length = 8 (change if needed)
            name1.append(alphabets.charAt(random.nextInt(alphabets.length())));
        }

        BaseClass.name = name.toString();

        
        
        
        
        fillPopUpForm.nameTextField().fill(name1.toString());
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
                page.locator("[id='middleName']").fill("Promilo");
                fillApplication.dateOfBirth().fill("1997-08-28");
                fillApplication.genderDropdown().click();
                fillApplication.maleOption().click();
                fillApplication.sendOtpButton().click();
                
                
                
             // Open new tab for Mailosaur UI
                BrowserContext context = page.context();
                Page mailosaurPage = context.newPage();
                mailosaurPage.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");

                // Login to Mailosaur
                mailosaurPage.locator("//input[@placeholder='Enter your email address']").fill("karthikmailsaur@gmail.com");
                mailosaurPage.locator("//button[text()='Continue']").click();

                mailosaurPage.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
                mailosaurPage.locator("//button[text()='Log in']").click();

                
                
                
                mailosaurPage.waitForTimeout(3000);
                mailosaurPage.reload();
                Locator firstEmail = mailosaurPage.locator("//p[text()='Verify Your Email Address to Complete Promilo Registration 🌐']").first();
                firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
                firstEmail.click();

                Thread.sleep(5000);

                String otpText = mailosaurPage.locator("//p[contains(text(),'Verification Code')]").innerText().trim();
                String otp1 = otpText.replaceAll("\\D+", "");
                System.out.println("✅ OTP fetched from Mailosaur UI: ");

                mailosaurPage.close();

                

                fillApplication.emailOtp().fill(otp1);
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
                        .setFiles(Paths.get("\"C:\\Users\\Admin\\Downloads\\preview_fullpage.png\""));

                fillApplication.declarationText().click();
                fillApplication.incompleteWarningText().click();

                page.waitForFileChooser(() -> fillApplication.uploadFileSecond().click())
                        .setFiles(Paths.get("C:/Users/Admin/Downloads/pexels-moh-adbelghaffar-771742.jpg"));

                fillApplication.declarationCheckbox().check();

                fillApplication.saveAndNextButton().click();

                // ========================
                // PAYMENT
                // ========================

                fillApplication.payButton().click();
                
        Locator paymentLabel = page.getByText(Pattern.compile("Use Wallet \\(₹\\d+\\)"));
        String fullText = paymentLabel.textContent().trim(); 
        System.out.println(fullText);
        String amount = fullText.replaceAll("[^0-9.]", "");  
        
        System.out.println(fullText);
        System.out.println(amount);
        
        
        // Store in BaseClass
        BaseClass.courseFee = amount;
        page.locator("//span[text()='Use Wallet (₹']").click();       
        page.locator("//button[text()='Pay']").click();
        
        
     
        
        
        page.waitForTimeout(4000);
        String expectedMessage = "Success! You are all set We’ve received your application and paymentView and track all your application details > My Application.";
        String successText = page.locator("[class='payment-success-main-cont']").textContent().replace("\n", " ").trim();

        Assert.assertEquals(successText, expectedMessage);
        
        
        
        page.locator("//a[text()='My Application.']").click();
        
        
        
        
        
        FillApplication fillFormValidation= new FillApplication(page);
        
        String campaignName=fillFormValidation.campaignName().textContent().trim();
        assertEquals(campaignName, "Golden Institute of Technology");
        
        
        String location= fillFormValidation.location().textContent().trim();
        assertEquals(location, "Nandyal");
        
        
        String courseName= fillFormValidation.courseName().textContent().trim();
        assertEquals(courseName, "MBA");
        
        String serviceName= fillFormValidation.serviceName().textContent().trim();
        assertEquals(serviceName, "Application");
        
        fillFormValidation.serviceIcon().isVisible();
        
        
        String statusTag= fillFormValidation.statusTag().textContent().trim();
        assertEquals(statusTag, "Submitted");
        System.out.println(statusTag+ "is displayed");
        
        

         
        
        
        
        String submittedDate = fillFormValidation.submittedDate().first().textContent().trim();
        System.out.println("Extracted Submitted Date: [" + submittedDate + "]");

        // Validate not empty
        if (submittedDate.isEmpty()) {
            Assert.fail("❌ Submitted date text is EMPTY. Locator might be wrong.");
        }

        Pattern p = Pattern.compile("(\\d{2}/\\d{2}/\\d{4})");
        Matcher m = p.matcher(submittedDate);

        String extractedDate = "";
        if (m.find()) {
            extractedDate = m.group(1);   
        } else {
            Assert.fail("❌ Could not extract a valid date from submitted text: " + submittedDate);
        }

        System.out.println("Extracted Date: " + extractedDate);

        // Build today's date for comparison
        String expectedToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim();

        // Validate date
        Assert.assertEquals(
                extractedDate,
                expectedToday,
                "❌ Meeting date does not match today's date"
        );

        System.out.println("✅ Current date validated: " + expectedToday);
        
        
        	
        
        
        
        
        
        
        
        
        


        
        
        
        
        
        
        
        
        
        
        

        
        
        
        
        



        }
    }
    
}
