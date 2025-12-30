package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class RequestVideoTest extends BaseClass {

    private static final Logger log = LogManager.getLogger(RequestVideoTest.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

        // ========================= SECTION: Excel Initialization =========================
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

        // ========================= SECTION: Excel Header Mapping =========================
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();

        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ========================= SECTION: Count Excel Data Rows =========================
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

        // ========================= SECTION: Row Processing =========================
        Set<String> targetKeywords = Collections.singleton("GetMentorCall");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch all values from Excel
            String mentorName = excel.getCellData(i, colMap.get("MentorName"));
            String otp = excel.getCellData(i, colMap.get("otp"));
            String invoiceName = excel.getCellData(i, colMap.get("invoiceName"));
            String name = excel.getCellData(i, colMap.get("MentorName"));
            String street1 = excel.getCellData(i, colMap.get("street1"));
            String street2 = excel.getCellData(i, colMap.get("street2"));
            String pincode = excel.getCellData(i, colMap.get("pincode"));
            String gst = excel.getCellData(i, colMap.get("gst"));
            String pan = excel.getCellData(i, colMap.get("pan"));
            String contactNumber = excel.getCellData(i, colMap.get("contactNumber"));

            
            // ========================= SECTION: Playwright Initialization =========================
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            log.info("Navigated to URL: " + prop.getProperty("url"));
            page.waitForTimeout(2000);

            // ========================= SECTION: Landing Popup =========================
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
            log.info("Closed landing page popup");
            page.waitForTimeout(2000);

            // ========================= SECTION: Navigate to Mentorship =========================
            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
            log.info("Clicked on Mentorship module");

            // ========================= SECTION: Search Mentor =========================
            MeetupsListingPage searchPage = new MeetupsListingPage(page);

            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
            page.waitForTimeout(2000);
            page.waitForTimeout(14000);


            // ========================= SECTION: Request Video =========================
            DescriptionPage descriptionValidation = new DescriptionPage(page);
            descriptionValidation.allLink().click();

            Locator personalizedVideoMessage = descriptionValidation.personalizedVideoMessage();
            personalizedVideoMessage.scrollIntoViewIfNeeded();
            personalizedVideoMessage.click();

            page.waitForTimeout(3000);
            descriptionValidation.requestVideo().nth(1).click();

            // ========================= SECTION: Fill Request Video Form =========================
            MentorshipFormComponents form = new MentorshipFormComponents(page);

            form.name().fill(mentorName);

            // Generate phone
            String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
            form.MobileTextField().fill(randomPhone);

            // Generate random email
            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "Automation" + randomNum + "@" + serverId + ".mailosaur.net";

            form.emailTextfield().fill(randomEmail);
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            form.RequestVideoMessageButton().click();
            page.waitForTimeout(3000);

            // ========================= SECTION: OTP Handling =========================
            if (otp.length() < 4) {
                throw new IllegalArgumentException("OTP must be 4 digits: " + otp);
            }

            for (int j = 0; j < 4; j++) {
                String otpChar = String.valueOf(otp.charAt(j));

                Locator otpField = page.locator(
                        "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

                otpField.waitFor(new Locator.WaitForOptions()
                        .setTimeout(10000)
                        .setState(WaitForSelectorState.VISIBLE));

                boolean filled = false;
                for (int attempt = 1; attempt <= 3 && !filled; attempt++) {
                    otpField.click();
                    otpField.fill("");
                    otpField.fill(otpChar);

                    String currentValue =
                            otpField.evaluate("el => el.value").toString().trim();

                    if (currentValue.equals(otpChar)) filled = true;
                    else page.waitForTimeout(500);
                }

                if (!filled) {
                    throw new RuntimeException("❌ Failed to enter OTP digit: " + (j + 1));
                }
            }

            page.locator("//button[text()='Verify & Proceed']").click();

            // ========================= SECTION: Select Occasion =========================
            form.occationDropdown().click();

            Locator options = form.occationOptions();
            int optionCount = options.count();

            for (int i1 = 0; i1 < optionCount; i1++) {
                Locator option = options.nth(i1);
                String radioText = option.innerText().replace("\n", " ").trim();
                System.out.println("Option " + (i1 + 1) + ": " + radioText);
            }

            form.occationOptions().first().click();
            form.contentDescriptionBox().fill("Something");
            form.toggleButton().click();
            form.nextButton().click();

            page.waitForTimeout(3000);

            // ========================= SECTION: Invoice Details =========================
            form.InvoiceNameField().fill(invoiceName);
            form.StreetAdress1().fill(street1);
            form.StreetAdress2().fill(street2);
            form.pinCode().fill(pincode);
            form.yesRadrioBox().click();
            form.gstNumber().fill(gst);
            form.panNumber().fill(pan);

            page.getByRole(AriaRole.CHECKBOX,
                    new Page.GetByRoleOptions().setName("By checking this box, I")).check();

            page.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Save")).click();

            // ========================= SECTION: Payment Process =========================
            MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
            paymentFunctionality.payOnline().click();
            paymentFunctionality.payButton().click();

            page.waitForTimeout(5000);

            FrameLocator frame = page.frameLocator("iframe");

            frame.getByTestId("contactNumber").fill(contactNumber);

            frame.getByTestId("nav-sidebar").locator("div")
                    .filter(new Locator.FilterOptions().setHasText("Wallet"))
                    .nth(2).click();

            page.waitForTimeout(3000);

            Page page8 = page.waitForPopup(() -> {
                page.locator("iframe").contentFrame()
                        .getByTestId("screen-container")
                        .locator("div")
                        .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                        .nth(2).click();
            });

            page8.getByRole(AriaRole.BUTTON,
                    new Page.GetByRoleOptions().setName("Success")).click();

            // ========================= SECTION: Thank You Popup =========================
            ThankYouPopup popupValidation = new ThankYouPopup(page);
            System.out.println(popupValidation.registeringText().textContent());


            // ========================= SECTION: Validate Mentor Interest Card =========================
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.DIALOG)
            .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
            .click();
            
            

         // ------------------ Expected Values (You can load from Excel later) ------------------
	        String expectedMentorName = "karthik U";
	        String expectedMentorData = "dxgfchvjbng vbnm";
	        String expectedServiceName = "Personalized Video";


	        
	       
	        // 2. Mentor name
	        String actualMentorName = myintrest.bookMeetingMentorName().innerText().trim();
	        Assert.assertEquals(actualMentorName, expectedMentorName,
	                "❌ Mentor Name does not match!");
	        System.out.println("✔ Mentor name validated: " + actualMentorName);

	        // 3. Mentor data (High)
	        Assert.assertTrue(myintrest.bookMeetingMentorData().isVisible(), "Mentor data is NOT visible");
	        String actualMentorData = myintrest.bookMeetingMentorData().innerText().trim();
	        Assert.assertEquals(actualMentorData, expectedMentorData,
	                "❌ Mentor Data does not match!");

	        		        
	        // 3. Service Name
	        String actualServiceName = myintrest.bookMeetingServiceName().innerText().trim();
	        Assert.assertEquals(actualServiceName, expectedServiceName,
	                "❌ Service name mismatch!");
	        
	        
	        // 3. Duration 

	        String actulaDuration=myintrest.askQueryDuration().textContent().trim();
	        assertEquals(actulaDuration, "3 Days");
	        
        } 

    } }
