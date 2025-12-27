package com.promilo.automation.guestuser.courses.interest;

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
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.courses.intrestspages.FillApplication;
import com.promilo.automation.courses.intrestspages.FillApplicationFormComponents;
import com.promilo.automation.courses.pageobjects.CourseSearchPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FillApplicationPaymentFailedScenario extends BaseClass {
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    

    	
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
        fillPopUpForm.nameTextField().nth(1).fill("karthik");
        fillPopUpForm.mobileTextField().nth(1).fill(randomPhone);
        fillPopUpForm.emailTextField().nth(1).fill(randomEmail);

        // Step 4: Select preferred locations
        fillPopUpForm.preferredLocationDropdown().nth(1).click();
        List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
        fillPopUpForm.selectLocations(industries);

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
                fillApplication.reactSelectInput().click();
                fillApplication.maleOption().click();
                fillApplication.sendOtpButton().click();

                fillApplication.emailOtp().fill("9999");
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

                fillApplication.reactSelectInput().click();
                fillApplication.bandScoreOption().click();

                fillApplication.companyName().click();
                fillApplication.companyName().fill("promilo");

                fillApplication.jobTitle().click();
                fillApplication.jobTitle().fill("qa engineer");

                fillApplication.saveAndNextButton().click();

                fillApplication.reactSelectInput().first().click();
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

                // ========================
                // PAYMENT
                // ========================

                fillApplication.payButton().click();
                fillApplication.payOnlineLabel().click();
                System.err.println("Amount fetched");

                MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
                paymentFunctionality.payOnline().click();
                System.out.println("Pay online clicked");

                paymentFunctionality.payButton().nth(1).click();
                System.out.println("Pay button clicked");

                // Fill iframe details
                FrameLocator frame = fillApplication.paymentIframe();
                fillApplication.contactNumberInput().fill("9000087183");

                Page popup2 = page.waitForPopup(() -> {
                    fillApplication.phonePeOption().click();
                });

                popup2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Failure")).click();

                
                page.locator("iframe").contentFrame().getByText("Payment could not be completed").click();
                page.locator("iframe").contentFrame().getByText("Your payment didn't go").click();
                page.locator("iframe").contentFrame().getByTestId("payment-status-modal").getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText(Pattern.compile("^$"))).click();
                page.locator("iframe").contentFrame().getByTestId("checkout-close").click();
                page.locator("iframe").contentFrame().getByTestId("confirm-positive").click();
                
                
	                
                page.locator("//span[text()='My Applications']").click();
                String statusValidation=fillApplication.statusTag().textContent().trim();
                assertEquals(statusValidation, "Pending");
                
                
                
                Hamburger billingNavigation= new Hamburger(page);
                billingNavigation.Mystuff().click();
                billingNavigation.MyAccount().click();
                billingNavigation.myBilling().click();
                
                

               
                String billingRow = page.locator("(//tr)[2]").textContent().trim();

                // Assert status
                assertTrue(billingRow.contains("Failed"),
                        "Billing status is not Failed");
                
             // Validate today's date (UI format: 24 Dec 2025)
                String currentDate = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                assertEquals(billingRow.contains(currentDate), true,
                        "Billing row does not contain today's date");
                
                
                
        
        
        
        
        
        
        
        
        
        

        
        
        
        
        



        }
    }
    
}
