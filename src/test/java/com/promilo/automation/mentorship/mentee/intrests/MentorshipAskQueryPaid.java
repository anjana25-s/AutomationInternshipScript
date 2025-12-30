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
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipAskQueryObjects;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.intrests.MentorshipFormComponents;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipAskQueryPaid extends BaseClass {

    private static final Logger log = LogManager.getLogger(MentorshipAskQueryPaid.class);

    @Test
    public void mentorshipShortListFunctionalityTest() throws IOException, InterruptedException {
        log.info("===== Starting Mentorship ShortList Functionality Test =====");
        System.out.println("===== Starting Mentorship ShortList Functionality Test =====");

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

        // ======== Count data rows ========
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

            // Fetch data
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

            // -------------------- Initialize Playwright --------------------
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            // Initialize object class for locators
            MentorshipAskQueryObjects obj = new MentorshipAskQueryObjects(page);

            // -------------------- Landing Page --------------------
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            if (mayBeLaterPopUp.getPopup().isVisible()) {
                mayBeLaterPopUp.getPopup().click();
            }
            page.waitForTimeout(2000);

            // -------------------- Mentorship Module --------------------
            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click();
            page.waitForTimeout(2000);

            // Search for mentor
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
            page.waitForTimeout(2000);
            page.waitForTimeout(14000);

            // -------------------- Mentor Description --------------------
            DescriptionPage descriptionValidation = new DescriptionPage(page);
            page.waitForTimeout(3000);
            descriptionValidation.askQuery().click();
            descriptionValidation.askYourQuery().nth(1).click();
            page.waitForTimeout(2000);

            // -------------------- Assertions --------------------
            assertEquals(obj.registerWithUsText().textContent().trim(), "Why register with us?Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.PreviousNext");
            assertEquals(obj.headerText().textContent().trim(), "Ask your query from karthik U directly!");
            assertEquals(obj.text2().textContent().trim(), "Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.");
            assertEquals(obj.text3().textContent().trim(), "By proceeding ahead you expressly agree to the PromiloTerms & Conditions");
            assertEquals(obj.text4().textContent().trim(), "Already have an account?Login");

            // -------------------- Ask Query Form --------------------
            MentorshipFormComponents askQuery = new MentorshipFormComponents(page);
            askQuery.name().nth(1).fill(name);
            String randomPhone = "90000" + String.format("%05d", (int) (Math.random() * 100000));
            askQuery.MobileTextField().fill(randomPhone);

            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "GetMentorCall" + randomNum + "@" + serverId + ".mailosaur.net";
            askQuery.emailTextfield().fill(randomEmail);   
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            obj.askYourQueryButton().click();
            page.waitForTimeout(2000);

            // -------------------- OTP Handling --------------------
            for (int j = 0; j < 4; j++) {
                String otpChar = String.valueOf(otp.charAt(j));
                Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
                otpField.fill(otpChar);
            }

            assertEquals(obj.otpBannerFirst().textContent().trim(), "Accelerate Your Career GrowthTransform your path with elite professionals guiding every step. Join now to turn your career aspirations into reality.");
            assertEquals(obj.otpBannerSecond().textContent().trim(), "Personalized Expert GuidanceAccess tailored consulting from industry leaders. Register for insights that match your unique potential.");
            assertEquals(obj.otpBannerThird().textContent().trim(), "Knowledge Hub AccessUnlock premium resources on careers, skills, and opportunities. Start your success journey with expert insights.");
            assertEquals(obj.otpThankYouText().textContent().trim(), "Thanks for giving your Information!");
            assertEquals(obj.otpVerificationHeader().textContent().trim(), "OTP Verification");
            assertTrue(obj.otpVerificationText().textContent().trim().contains("Enter the 4-digit verification code we just sent you to"));
            assertTrue(obj.otpVerificationStillNotFound().textContent().trim().contains("Still can’t find the OTP"));

            obj.verifyAndProceedButton().click();
            page.waitForTimeout(2000);

            assertEquals(obj.askQueryDescription().textContent().trim(), "Ask your question and receive a personalized response from experienced mentors.Learn from their expertise and connect with a growing community of mentors at Promilo.com.");
            assertEquals(obj.shareYourQueryText().textContent().trim(), "Share your query to receive expert guidance!");
            assertEquals(obj.noteText().textContent().trim(), "To get your query answered process we request you to pay the amount");

            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Type your questions here..."))
                .fill(BaseClass.askYourQuestionText);

            askQuery.nextButton().click();

            // -------------------- Invoice Section --------------------
            askQuery.InvoiceNameField().fill(invoiceName);
            askQuery.StreetAdress1().fill(street1);
            askQuery.StreetAdress2().fill(street2);
            askQuery.pinCode().fill(pincode);
            askQuery.yesRadrioBox().click();
            askQuery.gstNumber().fill(gst);
            askQuery.panNumber().fill(pan);        
            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

            // -------------------- Payment --------------------
            MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
            paymentFunctionality.payOnline().click();
            paymentFunctionality.payButton().click();
            page.waitForTimeout(5000);

            FrameLocator frame = obj.paymentFrame();
            frame.getByTestId("contactNumber").fill(contactNumber);
            obj.phonePeWalletOption(frame).click();

            Page phonePePage = page.waitForPopup(() -> obj.phonePeScreenContainer(frame).click());
            phonePePage.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
            page.waitForTimeout(8000);

            // -------------------- Validation --------------------
            obj.thankYouPopup().waitFor();
            Assert.assertTrue(obj.thankYouPopup().isVisible());
            assertEquals(obj.thankYouMessage().textContent().trim(), "Thank you for registering and asking the query from karthik U. Check your email, notifications, and WhatsApp for details on exclusive access.");

            page.getByRole(AriaRole.DIALOG)
                .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("My Interest"))
                .click();

            // -------------------- Mentor Card Validation --------------------
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
            page.waitForTimeout(5000);
            assertEquals(myintrest.askQueryMentorName().innerText().trim(), "Karthik U");
            assertEquals(myintrest.askQueryMentorData().innerText().trim(), "dxgfchvjbng vbnm");
            assertEquals(myintrest.askQueryDuration().innerText().trim(), "22 Days");
            assertEquals(myintrest.askQueryServiceName().innerText().trim(), "Ask Query");
        }
    }
}
