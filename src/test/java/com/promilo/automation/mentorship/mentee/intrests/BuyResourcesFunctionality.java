// ⚠️ NOTE: Only locator replacements added using BuyResourcesPageObjects
// ⚠️ Nothing else modified (logic, flow, sleeps, assertions, index all preserved)

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
import com.promilo.automation.mentorship.datavalidation.objects.BuyResourcesPageObjects;
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

public class BuyResourcesFunctionality extends BaseClass {

    private static final Logger log = LogManager.getLogger(BuyResourcesFunctionality.class);

    @Test()
    public void mentorshipBrandEndorsement() throws IOException, InterruptedException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

        // Load Excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Mentee Interest");
        } catch (Exception e) {
            test.fail("Excel loading failed");
            Assert.fail("Excel loading failed");
            return;
        }

        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();

        for (int c = 0; c < totalCols; c++) {
            colMap.put(excel.getCellData(0, c).trim(), c);
        }

        int rowCount = 0;
        for (int i = 1; i <= excel.getRowCount(); i++) {
            String id = excel.getCellData(i, colMap.get("Tc_Id"));
            if (id == null || id.trim().isEmpty())
                break;
            rowCount++;
        }

        if (rowCount == 0) {
            Assert.fail("No data rows in Excel");
        }

        Set<String> targetKeywords = Collections.singleton("BuyResourcesFunctionality");

        for (int i = 1; i <= rowCount; i++) {

            if (!targetKeywords.contains(excel.getCellData(i, colMap.get("keyword")).trim()))
                continue;

            // Excel data extraction
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
            String password = excel.getCellData(i, colMap.get("password"));

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            BuyResourcesPageObjects obj = new BuyResourcesPageObjects(page);

            // Landing popup
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            if (mayBeLaterPopUp.getPopup().isVisible()) {
                mayBeLaterPopUp.getPopup().click();
            }

            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click();

            MeetupsListingPage searchPage = new MeetupsListingPage(page);
            searchPage.SearchTextField().fill(mentorName);
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);

            DescriptionPage descriptionValidation = new DescriptionPage(page);
            Locator buyResources = descriptionValidation.buyResources().first();
            descriptionValidation.allLink().click();
            buyResources.click();

            // ================= Assertions =================

            assertEquals(obj.descriptionBannerText.textContent().trim(),
                    "Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.");

            assertTrue(obj.featureHeader.textContent().trim().contains("December Automation "));

            assertEquals(obj.whatsappToggleText.textContent().trim(),
                    "Enable updates & important information on Whatsapp");

            assertEquals(obj.proceedAgreementText.textContent().trim(),
                    "By proceeding ahead you expressly agree to the Promilo");

            assertTrue(obj.alreadyHaveAccountText.textContent().trim().contains("Already have an account?"));

            // Fill form
            MentorshipFormComponents form = new MentorshipFormComponents(page);
            form.name().fill(name);

            String mobileNumber = "90000" + String.format("%05d", new Random().nextInt(100000));
            form.MobileTextField().fill(mobileNumber);

            String randomEmail = "DownloadResource" + (10000 + new Random().nextInt(90000)) + "@mailosaur.net";
            obj.userEmailField.fill(randomEmail);

            form.downloadResource().click();
            page.waitForTimeout(3000);

            assertEquals(obj.otpBanner1.textContent().trim(),
                    "Accelerate Your Career GrowthTransform your path with elite professionals guiding every step. Join now to turn your career aspirations into reality.");

            assertEquals(obj.otpBanner2.textContent().trim(),
                    "Personalized Expert GuidanceAccess tailored consulting from industry leaders. Register for insights that match your unique potential.");

            assertEquals(obj.otpBanner3.textContent().trim(),
                    "Knowledge Hub AccessUnlock premium resources on careers, skills, and opportunities. Start your success journey with expert insights.");

            // OTP logic
            for (int j = 0; j < otp.length(); j++) {

                Locator otpField = page.locator("//input[@aria-label='Please enter OTP character " + (j + 1) + "']");

                otpField.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

                otpField.click();
                otpField.fill(String.valueOf(otp.charAt(j)));
            }

            assertEquals(obj.otpThanksText.textContent().trim(),
                    "Thanks for giving your Information!");

            assertEquals(obj.otpVerificationHeader.textContent().trim(), "OTP Verification");

            assertTrue(obj.otpInstructionText.textContent().trim()
                    .contains("Enter the 4-digit verification code we just sent you to"));

            assertTrue(obj.otpStillCantFindText.textContent().trim().contains("Still can’t find the OTP"));

            obj.verifyProceedButton.click();

            assertEquals(obj.descriptionAfterOtp.textContent().trim(),
                    "Access valuable resources with ease! Download expert-curated materials and learn from top mentors. Connect with a growing community of mentors at Promilo.com.");

            assertTrue(obj.noteText.textContent().trim()
                    .contains("Note:To get resource we request you to add the amount"));

            form.nextButton().click();

            // Invoice
            form.InvoiceNameField().fill(invoiceName);
            form.StreetAdress1().fill(street1);
            form.StreetAdress2().fill(street2);
            form.pinCode().fill(pincode);
            form.yesRadrioBox().click();
            form.gstNumber().fill(gst);
            form.panNumber().fill(pan);

            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

            // Payment
            MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
            paymentFunctionality.payOnline().click();
            paymentFunctionality.payButton().click();

            FrameLocator frame = page.frameLocator("iframe");
            frame.getByTestId("contactNumber").fill(contactNumber);

            Page popup2 = page.waitForPopup(() -> {
                frame.getByTestId("screen-container")
                        .locator("div")
                        .filter(new Locator.FilterOptions().setHasText("PhonePe"))
                        .nth(2)
                        .click();
            });

            popup2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();

            obj.thankYouPopup.waitFor();

            assertTrue(obj.thankYouPopup.isVisible());

            assertEquals(obj.congratulationsText.textContent().trim(),
                    "Congratulations! You did it. Your Resource is downloaded automatically.  Download Manually");

            assertEquals(obj.footerInfoText.textContent().trim(),
                    "Easy Access to Your Selections—Your dashboard shows you the details of your chosen mentor under My Interests > My Preference> My Downloads.");

            ThankYouPopup popupValidation = new ThankYouPopup(page);
            popupValidation.downloadManually().click();
            popupValidation.myDownloadsButton().click();

            // FINAL VALIDATION
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);

            assertEquals(myintrest.mentorName().innerText().trim(), "December Automation");
            assertEquals(myintrest.mentorData().innerText().trim(), "dxgfchvjbng vbnm");
            assertTrue(myintrest.experianceString().isVisible());
            assertEquals(myintrest.experianceValue().innerText().trim(), "2 Years");
            assertEquals(myintrest.locationValue().innerText().trim(), "Anantapur");
            assertEquals(myintrest.serviceName().innerText().trim(), "Resources");
        }
    }
}
