package com.promilo.automation.guestuser.courses.interest;

import static org.testng.Assert.assertEquals;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipBookMeetingPageObjects;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipErrorMessagesAndToasters;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.registereduser.jobs.MaiLRegisteredUserInvalidJobApply;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CampusVisitCancelFunctionality   extends BaseClass{
	
	
	
	
	private static final Logger logger = LogManager.getLogger(MaiLRegisteredUserInvalidJobApply.class);

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    

    @Test
    public void GuestUserCampusVisitTest() throws Exception {

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

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}

        
        
        
        // Step 1: Click Courses
        page.locator("//a[text()='Courses']").click();
        test.info("📚 Clicked on 'Courses'.");

        // Step 2: Search course
        page.locator("//input[@placeholder='Search Colleges and Courses']").fill("BTWIN");
        test.info("🔍 Entered course search text: Course auto");

        page.keyboard().press("Enter");
        test.info("↩️ Pressed Enter to search courses.");
        
        
           page.locator("//span[text()='Campus Visit']").click();
           

        
        
        
        
        MentorshipErrorMessagesAndToasters ErrorMessageValidation = new MentorshipErrorMessagesAndToasters(page);

        String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
        String serverId = "qtvjnqv9";
        int randomNum = 10000 + new Random().nextInt(90000);
        String randomEmail = "testAutomation-" + randomNum + "@" + serverId + ".mailosaur.net";
        
     // ✅ Invalid inputs validation
        ErrorMessageValidation.nameTextField().first().fill("Karthik");
        System.out.println("Entered mail");
        ErrorMessageValidation.emailTextField().nth(1).fill(randomEmail);
        ErrorMessageValidation.mobileTextField().nth(1).fill(randomPhone);

        BaseClass.generatedEmail = randomEmail;
        BaseClass.generatedPhone = randomPhone;
        
        
        
        
        
        
        page.waitForTimeout(3000);
        page.locator("[id='preferredLocation']").nth(1).click();
        test.info("📍 Clicked Preferred Location dropdown");

        Thread.sleep(1000);
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

        page.locator("//div[@class='text-content']").textContent();
        test.info("📋 Captured confirmation text content after industry selection");
        
        ErrorMessageValidation.nameTextField().first().click();


        test.info("📍 Re-clicked Preferred Location dropdown to close it");

        Thread.sleep(2000);
        
        

        page.locator("//button[text()='Book Visit']").click();
        
        String validOtp = "9999";
        for (int i1 = 0; i1 < 4; i1++) {
            String digit = Character.toString(validOtp.charAt(i1));
            Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

            for (int retry = 0; retry < 3; retry++) {
                otpField.click();
                otpField.fill("");
                otpField.fill(digit);
                if (otpField.evaluate("el => el.value").toString().trim().equals(digit))
                    break;
                page.waitForTimeout(500);
            }
            test.info("🔢 Entered OTP digit " + digit + " at position " + (i1 + 1));
        }

        page.locator("//button[text()='Verify & Proceed']").click();
        test.info("✅ Clicked 'Verify & Proceed' after entering OTP");
        
        
        
        MentorshipBookMeetingPageObjects po = new MentorshipBookMeetingPageObjects(page);

        

        String currentMonth = po.currentMonth().textContent().trim();
        String expectedMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM"));
        Assert.assertTrue(currentMonth.contains(expectedMonth));

        Locator dateElement = po.availableDate();
        dateElement.click();
        BaseClass.selectedDate = dateElement.innerText().split("\\s+")[0].trim();

        Locator timeElement = po.firstTimeSlot();
        timeElement.click();
        BaseClass.selectedTime = timeElement.innerText();

        page.locator("//button[text()='Next']").click();
        test.info("➡️ Clicked Next button");

        page.locator("//button[text()='Submit']").nth(1).click();
        test.info("📨 Clicked Submit button");            
        

        
        
        
        
        
        Locator thankYouPopup = page.locator("//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
        thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));

        String popupText = thankYouPopup.innerText().trim();
        Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                "Expected 'Thank You!' popup, found: " + popupText);
        test.pass("🎉 Thank You popup validated: " + popupText);
        
        
        page.locator("//span[text()='My Visits']").click();
        page.locator("[class='btn-danger-outlined max-w-[60px] min-w-[60px] rounded  h-[25px] ']").click();
        page.locator("[class='font-14 label-text text-dark-gray ms-1 ']").first().click();
        page.locator("//button[text()='Cancel']").click();
        page.locator("//span[text()='Yes']").click();
        
        
        String cancelpopUp=page.locator("[class='sub-heading pt-1']").textContent().trim();
        assertEquals(cancelpopUp, "Your visit has been successfully cancelled.");
        
        
        page.locator("//img[@alt='Close']").click();
        page.locator("//div[text()='Cancelled']").first().click();
        
        page.waitForTimeout(2000);
        Locator totalResults = page.locator("[class='fw-bold text-grey-600']");

        // Assert visibility
        Assert.assertTrue(totalResults.isVisible(), "Total Results label is not visible!");

        // Extract text
        String totalResultsText = totalResults.textContent().trim();
        System.out.println("Total Results Text: " + totalResultsText);

        // Assert expected value
        Assert.assertEquals(totalResultsText, "Total Results of 1", "Total Results text mismatch!");
        
        
        
        
        
        
        

        
        
        BaseClass.keepSessionAlive = true;

        

        
        
        
        
        
        
        
        }}
	
	
	
	
	
}