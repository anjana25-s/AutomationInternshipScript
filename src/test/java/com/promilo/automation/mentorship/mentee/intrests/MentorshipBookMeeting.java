package com.promilo.automation.mentorship.mentee.intrests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
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
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.mentorship.datavalidation.objects.MentorshipBookMeetingPageObjects;
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

public class MentorshipBookMeeting extends BaseClass {

    private static final Logger log = LogManager.getLogger(MentorshipBookMeeting.class);

    @Test
    public void mentorshipbook() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

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

        // ======== Count data rows (skip header) ========
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, colMap.get("Tc_Id"));
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        if (rowCount == 0) {
            test.fail("No data rows in Excel");
            Assert.fail("No data rows in Excel");
            return;
        }

        Set<String> targetKeywords = Collections.singleton("MentorshipBookMeeting");

        for (int i = 1; i <= rowCount; i++) {
            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing row " + i + " | Keyword: " + keyword);

            // Fetch data
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

            // -------------------- Initialize Playwright --------------------
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            // -------------------- Landing Page --------------------
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
            mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(2000);

            // -------------------- Mentorship Module --------------------
            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

            // Search for mentor
            page.navigate("https://stage.promilo.com/meetups-description/academic-guidance/course-selection/engineering/-dxgfchvjbng-vbnm--127");
            page.waitForTimeout(2000);
            page.waitForTimeout(14000);

            DescriptionPage GetMentorCall = new DescriptionPage(page);
            GetMentorCall.allLink().click();
            GetMentorCall.videoCallLink().click();
            page.waitForTimeout(3000);

            Locator bookMeeting = page.locator("//button[text()='Book Online Meeting']").nth(1);
            bookMeeting.scrollIntoViewIfNeeded();
            bookMeeting.click();

            // -------------------- Use Page Object --------------------
            MentorshipBookMeetingPageObjects po = new MentorshipBookMeetingPageObjects(page);

            assertEquals(po.header().textContent().trim(),
                    "Book a Video Call from karthik U!");
            assertEquals(po.registerWithUsText().textContent().trim(), "Why register with us?Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.Transform your journey with guidance from industry-leading mentors and experts.Access real-time updates from our elite Academic, Career, and Skill mentors network.Receive instant alerts when mentors matching your specific criteria join our platform.Access authentic reviews and testimonials from peers about potential mentors.Benefit from tailored consulting that aligns with your unique career aspirations.Unlock premium content and tools designed to enhance your professional journey.Your data security is our priority - guaranteed protection from unauthorized communications.PreviousNext");
            assertEquals(po.termsAndConditionsText().textContent().trim(),
                    "By proceeding ahead you expressly agree to the PromiloTerms & Conditions");
            assertEquals(po.loginLinkText().textContent().trim(), "Already have an account?Login");

            // -------------------- Fill Form --------------------
            MentorshipFormComponents fillForm = new MentorshipFormComponents(page);
            fillForm.name().fill(name);

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            String serverId = "qtvjnqv9";
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "VideoCall" + randomNum + "@" + serverId + ".mailosaur.net";

            fillForm.MobileTextField().fill(randomPhone);
            fillForm.emailTextfield().fill(randomEmail);

            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;
            fillForm.getMentorCall().click();

            if (otp == null || otp.length() < 4) {
                throw new IllegalArgumentException("OTP must be 4 characters: " + otp);
            }

            // ✅ Enter OTP (replaced otp fields)
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
                    } else {
                        page.waitForTimeout(500);
                    }
                }

                if (!filled) {
                    throw new RuntimeException("Failed to enter OTP digit " + (i1 + 1));
                }
                test.info("Entered OTP digit: " + otpChar);
            }            assertEquals(po.otpBanner(0).textContent().trim(),
                    "Accelerate Your Career GrowthTransform your path with elite professionals guiding every step. Join now to turn your career aspirations into reality.");
            assertEquals(po.otpBanner(1).textContent().trim(),
                    "Personalized Expert GuidanceAccess tailored consulting from industry leaders. Register for insights that match your unique potential.");
            assertEquals(po.otpBanner(2).textContent().trim(),
                    "Knowledge Hub AccessUnlock premium resources on careers, skills, and opportunities. Start your success journey with expert insights.");

            assertEquals(po.otpThankYouText().textContent().trim(),
                    "Thanks for giving your Information!");
            assertEquals(po.otpHeader().textContent().trim(), "OTP Verification");
            assertTrue(po.otpDescription().textContent().trim().contains(
                    "Enter the 4-digit verification code we just sent you to"));
            assertTrue(po.otpStillCannotFind().textContent().trim().contains("Still can’t find the OTP"));

            po.verifyAndProceedButton().click();

            assertEquals(po.mentorshipLabel().textContent().trim(), "Mentorship");
            assertEquals(po.calendarPopupDescription().textContent().trim(),
                    "Enjoy a video call with your chosen mentors to guide your career path. Select your preferred language and schedule for a video call with a mentor. This will ensure a smooth and personalized connection, making it easy for both you and your mentor to communicate effectively.");
            assertEquals(po.chooseSlotText().textContent().trim(),
                    "Please choose your preferred language and time for the video call with karthik U");
            page.locator("[class='custom-next-month']").nth(1).click();

            page.waitForTimeout(2000);
            String currentMonth = po.currentMonth().textContent().trim();
            String expectedMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM"));

            Locator dateElement = po.availableDate();
            dateElement.click();
            BaseClass.selectedDate = dateElement.innerText().split("\\s+")[0].trim();

            Locator timeElement = po.firstTimeSlot();
            timeElement.click();
            BaseClass.selectedTime = timeElement.innerText();

            fillForm.nextButton().click();

            // -------------------- Invoice --------------------
            fillForm.InvoiceNameField().fill(invoiceName);
            fillForm.StreetAdress1().fill(street1);
            fillForm.StreetAdress2().fill(street2);
            fillForm.pinCode().fill(pincode);
            fillForm.yesRadrioBox().click();
            fillForm.gstNumber().fill(gst);
            fillForm.panNumber().fill(pan);
            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("By checking this box, I")).check();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save")).click();

            // -------------------- Payment --------------------
            MentorshipMyintrest paymentFunctionality = new MentorshipMyintrest(page);
            paymentFunctionality.payOnline().click();
            paymentFunctionality.payButton().click();
            page.waitForTimeout(5000);

            FrameLocator frame = page.frameLocator("iframe");
            frame.getByTestId("contactNumber").fill(contactNumber);
            frame.getByTestId("nav-sidebar").locator("div")
                    .filter(new Locator.FilterOptions().setHasText("Wallet")).nth(2).click();
            page.waitForTimeout(3000);

            Page page8 = page.waitForPopup(() -> {
                page.locator("iframe").contentFrame().getByTestId("screen-container")
                        .locator("div").filter(new Locator.FilterOptions().setHasText("PhonePe")).nth(2).click();
            });
            page8.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Success")).click();
            page.waitForTimeout(8000);

            // -------------------- Thank You Popup --------------------
            Locator thankYouPopup = page.locator(
                    "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            Assert.assertTrue(thankYouPopup.isVisible());
            assertEquals(page.locator("[class='ThankYou-message text-center text-blue-600 pt-2 ']")
                    .textContent().trim(),
                    "Thank you for registering and requesting a Video Call from karthik U. Check your email, notifications, and WhatsApp for details on exclusive access.");

            // -------------------- Validate My Interest Card --------------------
            MentorshipMyintrest myintrest = new MentorshipMyintrest(page);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("My Interest")).nth(1).click();
            page.waitForTimeout(5000);

            String meetingDate = myintrest.meetingDate().first().textContent().trim();
            Pattern p = Pattern.compile("(\\d{1,2})");
            Matcher m = p.matcher(meetingDate);
            String displayedDayStr = "";
            if (m.find()) displayedDayStr = m.group(1);
            else Assert.fail("❌ Could not extract day from Meeting Date: " + meetingDate);
            int displayedDay = Integer.parseInt(displayedDayStr);
            int storedDay = Integer.parseInt(BaseClass.selectedDate.trim());
            Assert.assertEquals(displayedDay, storedDay);

            String meetingTime = myintrest.meetingTime().first().textContent().trim();
            Pattern timePattern = Pattern.compile("(\\d{1,2}:\\d{2}\\s?[AP]M)");
            Matcher timeMatcher = timePattern.matcher(meetingTime);
            String displayedTime = "";
            if (timeMatcher.find()) displayedTime = timeMatcher.group(1).replaceAll("\\s+", " ").trim();
            String storedTime = BaseClass.selectedTime.replaceAll("\\s+", " ").trim();
            storedTime = storedTime.replaceFirst("^0", "");
            displayedTime = displayedTime.replaceFirst("^0", "");
            Assert.assertEquals(displayedTime, storedTime);

            Assert.assertEquals(myintrest.bookMeetingMentorName().innerText().trim(), "karthik U");
            Assert.assertEquals(myintrest.bookMeetingMentorData().innerText().trim(), "dxgfchvjbng vbnm");
            Assert.assertEquals(myintrest.bookMeetingServiceName().innerText().trim(), "Video Call");

        }
    }
}
