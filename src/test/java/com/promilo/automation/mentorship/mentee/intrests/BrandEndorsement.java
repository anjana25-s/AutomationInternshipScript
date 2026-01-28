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
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.datavalidation.objects.DataValidationObjects;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.mentorship.mentee.MentorshipFormComponents;
import com.promilo.automation.mentorship.mentee.MentorshipMyintrest;
import com.promilo.automation.mentorship.mentee.ThankYouPopup;
import com.promilo.automation.mentorship.menteenotifications.BrandEndorsementEmailAndInAppNotifications;
import com.promilo.automation.mentorship.mentornotifications.MentorBrandEndorsementsNotifications;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.MailsaurCredentials;

public class BrandEndorsement extends BaseClass {

    private static final Logger log = LogManager.getLogger(BrandEndorsement.class);

    @Test
    public void mentorshipBrandEndorsement() throws IOException, InterruptedException {

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
        Set<String> targetKeywords = Collections.singleton("BrandEndorsement");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data
            String otp = excel.getCellData(i, colMap.get("otp"));
            String name = excel.getCellData(i, colMap.get("MentorName"));
            if (name == null || name.trim().isEmpty()) name = "User";
            if (otp == null || otp.trim().isEmpty()) otp = "9999";

            // -------------------- INITIALIZE PLAYWRIGHT --------------------
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            // -------------------- CLOSE LANDING POPUP --------------------
            try {
                new MayBeLaterPopUp(page).getPopup().click(new Locator.ClickOptions().setForce(true));
            } catch (Exception ignored) {}

            // -------------------- GO TO MENTORSHIP --------------------
            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

            
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
            // -------------------- MENTOR PROFILE --------------------
            DescriptionPage GetMentorCall = new DescriptionPage(page);
            GetMentorCall.allLink().click();
            GetMentorCall.brandEndorsement().click();
            GetMentorCall.bookEnquiry().nth(1).click();

            // -------------------- DATA VALIDATION OBJECT --------------------
            DataValidationObjects dataValidation = new DataValidationObjects(page);

            // Validate header & hero section
            assertEquals(dataValidation.growYourBrandWithPopularPersonalities().textContent().trim(),
                    "Grow Your Brand with Popular PersonalitiesPartner with influencers who truly match your brand's valuesBoost your brand's reach through trusted personality partnershipsBuild customer trust through authentic product promotionPreviousNext");

            assertEquals(dataValidation.secureABrandEndorsement().textContent().trim(),
                    "Secure a brand endorsement from karthik U and elevate your brand's visibility!");

            assertEquals(dataValidation.enableUpdatesImportantInformationWhatsapp().textContent().trim(),
                    "Enable updates & important information on Whatsapp");

            assertEquals(dataValidation.byProceedingAheadYouExpresslyAgreeToPromilo().textContent().trim(),
                    "By proceeding ahead you expressly agree to the Promilo");

            assertEquals(dataValidation.alreadyHaveAnAccountLogin().textContent().trim(),
                    "Already have an account?Login");
            
            
            
            

            
            // -------------------- FILL FORM --------------------
            MentorshipFormComponents fillForm = new MentorshipFormComponents(page);
            fillForm.name().fill(name);

            // Generate random phone + email
            int random = 10000 + new Random().nextInt(90000);
            String randomPhone = "90000" + random;
            String serverId = "qtvjnqv9";
            String randomEmail = "brand-" + random + "@" + serverId + ".mailosaur.net";

            fillForm.MobileTextField().fill(randomPhone);
            fillForm.emailTextfield().fill(randomEmail);

            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            GetMentorCall.bookAnEnquiry().click();
            
            
            
            page.waitForTimeout(3000);
         // Validate otp content
            assertEquals(dataValidation.thanksForGivingYourInformation().textContent().trim(),
                    "Thanks for giving your Information!");

            assertEquals(dataValidation.otpVerification().textContent().trim(),
                    "OTP Verification");

            
            String enterOtpValidation= dataValidation.enterThe4DigitVerificationCode().textContent().trim();
            assertTrue(enterOtpValidation.contains("Enter the 4-digit verification code we just sent you to")); 
                  

            assertEquals(dataValidation.stillCantFindTheOtp().textContent().trim(),
                    "Still can’t find the OTP?");
            
            
            assertEquals(dataValidation.descriptionValidation1().textContent().trim(),
            		"Leverage the influence of celebrities to enhance your brand’s trust and appeal among target audiences.");
            
            assertEquals(dataValidation.descriptionValidation2().textContent().trim(),
            		"Connect with a broader audience by using celebrity endorsements to increase brand visibility and engagement across various platforms.");
            
            // -------------------- ENTER OTP --------------------
            if (otp.length() != 4)
                throw new RuntimeException("Invalid OTP in Excel!");

            for (int j = 0; j < 4; j++) {
                String digit = otp.substring(j, j + 1);
                Locator otpField = page.locator(
                        "//input[@aria-label='Please enter OTP character " + (j + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(8000)
                        .setState(WaitForSelectorState.VISIBLE));
                otpField.fill(digit);
            }
            fillForm.verifyAndProceed().click();

            // -------------------- BRAND DETAILS --------------------
            fillForm.typeofBrand().click();
            page.waitForTimeout(2000);
            fillForm.brandOptions().click();
            fillForm.typeYourMessage().fill("Hello");
            fillForm.brandEndorsementSubmit().click();
            page.waitForTimeout(4000);

            // -------------------- THANK YOU POPUP --------------------
            assertTrue(fillForm.thankYouPopup().isVisible());
            page.waitForTimeout(3000);
            String actualMsg = dataValidation.thankYouForRegisteringRequestingBrandEndorsement()
                    .textContent()
                    .trim();

            Assert.assertTrue(
            		actualMsg.contains("karthik U."),
            		"❌ Expected text to contain 'December Automation' but got: " + actualMsg
            		);

            assertEquals(dataValidation.easyAccessToYourSelections().textContent().trim(),
                    "Easy Access to Your Selections—Your dashboard shows you the details of your chosen mentor under My Interests > My Preference");

            new ThankYouPopup(page).myPreference().click();

            // -------------------- MENTOR INTEREST CARD --------------------
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
            assertEquals(myintrest.mentorName().innerText().trim(), "karthik U");
            assertEquals(myintrest.mentorData().innerText().trim(), "dxgfchvjbng vbnm");
            assertTrue(myintrest.experianceString().isVisible());
            assertEquals(myintrest.experianceValue().innerText().trim(), "2 Years");
            assertEquals(myintrest.locationValue().innerText().trim(), "Anantapur");
            assertEquals(myintrest.serviceName().innerText().trim(), "Brand Endorsement");

            // -------------------- MAILOSAUR + NOTIFICATIONS --------------------
            page.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            MailsaurCredentials mailsaurCredenatinals = new MailsaurCredentials(page);
            mailsaurCredenatinals.MialsaurMail();
            mailsaurCredenatinals.MailsaurContinue();
            mailsaurCredenatinals.MailsaurPassword();
            mailsaurCredenatinals.MailsaurLogin();

            MentorBrandEndorsementsNotifications mentorNotification = new MentorBrandEndorsementsNotifications(page);
            mentorNotification.intrestShownMailNotification().first().click();
            mentorNotification.dearText().isVisible();
            mentorNotification.goodnewsText().first().isVisible();
            mentorNotification.pendingCard().isVisible();
            mentorNotification.acceptButton().click();	
            page.bringToFront();
            mentorNotification.backButton().click();
            page.waitForTimeout(4000);

            BrandEndorsementEmailAndInAppNotifications menteeNotification = new BrandEndorsementEmailAndInAppNotifications(page);
            menteeNotification.emailNotification().first().click();
            menteeNotification.hiText();
            menteeNotification.thankYoufortakingText();
            menteeNotification.viewCard().click();
        }
    }
}
