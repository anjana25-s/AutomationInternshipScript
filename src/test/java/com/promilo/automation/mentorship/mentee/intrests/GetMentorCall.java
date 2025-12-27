package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.datavalidation.objects.GetMentorCallPageObjects;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class GetMentorCall extends BaseClass {

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {

        System.out.println("===== TEST STARTED: GetMentorCall =====");

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

            // ===== Random email & phone =====
            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String randomEmail = "Automation" + new Random().nextInt(99999) + "@qtvjnqv9.mailosaur.net";
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            System.out.println("Generated Email: " + randomEmail);
            System.out.println("Generated Phone: " + randomPhone);

            // ===== Playwright execution =====
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            System.out.println("Navigated to URL");

            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            popup.getPopup().click(new Locator.ClickOptions().setForce(true));

            page.waitForTimeout(3000);
            new HomePage(page).mentorships().click(new Locator.ClickOptions().setForce(true));

            // Search mentor
            MeetupsListingPage searchPage = new MeetupsListingPage(page);
            searchPage.SearchTextField().fill(mentorName);
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);

            DescriptionPage desc = new DescriptionPage(page);
            desc.allLink().click();
            desc.getMentorCall().first().click();

            // ===== Page Objects =====
            GetMentorCallPageObjects obj = new GetMentorCallPageObjects(page);

            // Assertions using Page Objects
            assertEquals(obj.featureContentHeader().textContent().trim(),
                    "Get a 1:1 Call from December Automation!");
            assertEquals(obj.mentorDescription().textContent().trim(),
                    "Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.");
            assertEquals(obj.termsAndConditions().textContent().trim(),
                    "By proceeding ahead you expressly agree to the PromiloTerms & Conditions");
            assertEquals(obj.loginLink().textContent().trim(),
                    "Already have an account?Login");

            
            
            // Form fill remains unchanged
            MentorshipFormComponents form = new MentorshipFormComponents(page);
            form.name().fill(name);
            form.MobileTextField().fill(randomPhone);
            page.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);
            form.getMentorCall().click();

            // OTP entry logic (unchanged)
            if (otp == null || otp.length() < 4) {
                throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
            }
            for (int i1 = 0; i1 < 4; i1++) {
                String otpChar = String.valueOf(otp.charAt(i1));
                Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (i1 + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));

                int attempts = 0;
                boolean filled = false;
                while (!filled && attempts < 3) {
                    attempts++;
                    otpField.click();
                    otpField.fill("");
                    otpField.fill(otpChar);

                    String currentValue = otpField.evaluate("el => el.value").toString().trim();
                    if (currentValue.equals(otpChar)) {
                        filled = true;
                    }
                }

                if (!filled) {
                    throw new RuntimeException("Failed to enter OTP digit " + (i1 + 1));
                }
                test.info("Entered OTP digit: " + otpChar);
            }
            
         // OTP Banner Assertions
            assertEquals(obj.otpBanner(0).textContent().trim(),
                    "Accelerate Your Career GrowthTransform your path with elite professionals guiding every step. Join now to turn your career aspirations into reality.");
            assertEquals(obj.otpBanner(1).textContent().trim(),
                    "Personalized Expert GuidanceAccess tailored consulting from industry leaders. Register for insights that match your unique potential.");
            assertEquals(obj.otpBanner(2).textContent().trim(),
                    "Knowledge Hub AccessUnlock premium resources on careers, skills, and opportunities. Start your success journey with expert insights.");

            // OTP Section
            assertEquals(obj.otpSuccessMessage().textContent().trim(), "Thanks for giving your Information!");
            assertEquals(obj.otpVerificationHeader().textContent().trim(), "OTP Verification");
            assertTrue(obj.otpInstructionText().textContent().trim()
                    .contains("Enter the 4-digit verification code we just sent you to"));
            assertTrue(obj.otpTroubleshootText().textContent().trim().contains("Still can’t find the OTP"));


            
            
            form.verifyAndProceed().click();
            
         // Language selection
            assertEquals(obj.languageHeader().textContent().trim(),
                    "Please choose your preferred language with Campaign VALIDATION undefined. This will make it easier for you and them to connect as you choose.");
            assertEquals(obj.languageDescription().textContent().trim(),
                    "Enjoy a direct 1:1 Call with your chosen mentors to guide your career path in your preferred language.");
            assertEquals(obj.languageNote().textContent().trim(),
                    "Note:To confirm the expert call we request you to pay the amount");


            // Language selection page assertions already covered by Page Objects
            form.nextButton().click();
            
            
         

            // Invoice & payment logic remains unchanged
            form.InvoiceNameField().fill(invoiceName);
            form.StreetAdress1().fill(street1);
            form.StreetAdress2().fill(street2);
            form.pinCode().fill(pincode);
            form.yesRadrioBox().click();
            form.gstNumber().fill(gst);
            form.panNumber().fill(pan);
            page.getByRole(AriaRole.CHECKBOX).check();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

            MentorshipMyintrest pay = new MentorshipMyintrest(page);
            pay.payOnline().click();
            pay.payButton().click();

            FrameLocator frame = page.frameLocator("iframe");
            frame.getByTestId("contactNumber").fill(contactNumber);

            frame.getByTestId("nav-sidebar")
                    .locator("div")
                    .filter(new Locator.FilterOptions().setHasText("Wallet"))
                    .nth(2)
                    .click();

            Page popup2 = page.waitForPopup(() -> {
                frame.getByTestId("screen-container")
                        .locator("div")
                        .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                        .nth(2)
                        .click();
            });

            popup2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
            page.waitForTimeout(5000);
            
            

            // Thank You validations using Page Objects
            assertEquals(obj.thankYouMessage().textContent().trim(),
                    "Thank you for registering and requesting a 1:1 Call from December Automation. Check your email, notifications, and WhatsApp for details on exclusive access.");
            new ThankYouPopup(page).myPreference().click();
            
            

            // Mentor Interest Card validations (unchanged)
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
            String expectedMentorName = "December Automation";
            String expectedMentorData = "dxgfchvjbng vbnm";
            String expectedExperience = "2 Years";
            String expectedLocation = "Anantapur";
            String expectedServiceName = "1:1 call";

            assertEquals(myintrest.mentorName().innerText().trim(), expectedMentorName);
            assertEquals(myintrest.mentorData().innerText().trim(), expectedMentorData);
            Assert.assertTrue(myintrest.experianceString().isVisible());
            assertEquals(myintrest.experianceValue().innerText().trim(), expectedExperience);
            assertEquals(myintrest.locationValue().innerText().trim(), expectedLocation);
            assertEquals(myintrest.serviceName().innerText().trim(), expectedServiceName);

            System.out.println("✅ Row " + i + " executed successfully: Thank You displayed");
        }
    }
}
