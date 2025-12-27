package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.*;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.job.pageobjects.FormComponents;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.job.pageobjects.AskUsJobPageObjects;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.SignupWithMailosaurUI;

public class AskUsJobTest extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    // ---------------------------------------------
    // SIGNUP ONCE BEFORE SUITE
    // ---------------------------------------------
    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered Email: " + registeredEmail);
    }

    // -------------------------------------------------
    // DataProvider (kept exactly as your script)
    // -------------------------------------------------
    @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int totalRows = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty())
                break;
            totalRows++;
        }

        List<Object[]> filteredData = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

            if (normalizedKeyword.equals("registereduserjobshortlist")
                    || normalizedKeyword.equals("registereduserjobshortlistwithsignup")
                    || normalizedKeyword.equals("registereduserfeedbackwithsignup")) {

                Object[] row = new Object[8];
                row[0] = testCaseId;
                row[1] = keyword;
                row[2] = excel.getCellData(i, 3); 
                row[3] = excel.getCellData(i, 6); 
                row[4] = excel.getCellData(i, 7); 
                row[5] = excel.getCellData(i, 5); 
                row[6] = excel.getCellData(i, 8); 
                row[7] = i; 
                filteredData.add(row);
            }
        }

        if (filteredData.isEmpty()) {
            filteredData.add(new Object[] { "NoTest", "NoKeyword", "", "", "", "", "", 0 });
        }

        return filteredData.toArray(new Object[0][0]);
    }

    // ---------------------------------------------
    // Test Case (fully updated to use AskUsJobPageObjects)
    // ---------------------------------------------
    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String inputValue,
            String password,
            String name,
            String otp,
            String mailPhone,
            int rowIndex) throws Exception {

        if (registeredEmail == null || registeredPassword == null) {
            Assert.fail("❌ Signup NOT completed! No registered credentials found.");
            return;
        }

        inputValue = registeredEmail;
        password = registeredPassword;

        System.out.println("➡ Using Registered Email for Test: " + inputValue);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}

        mayBeLaterPopUp.clickLoginButton();
        LoginPage loginPage = new LoginPage(page);

        loginPage.loginMailPhone().fill(inputValue);
        loginPage.passwordField().fill(password);
        loginPage.loginButton().click();

        Thread.sleep(3000);

        FormComponents askUs = new FormComponents(page);
        JobListingPage listingPage = new JobListingPage(page);

        AskUsJobPageObjects askUsObj = new AskUsJobPageObjects(page);

        listingPage.homepageJobs().click();
        Thread.sleep(5000);

        listingPage.searchJob().fill("December Campaign Automation");
        page.keyboard().press("Enter");
        askUs.askUsButton().first().click();

        // -------------------------
        // Assertions (POM based)
        // -------------------------
        assertEquals(
                askUsObj.askUsDescription().textContent().trim(),
                "Ask Us Anything for FreeGet personalized responses tailored to your career needs.Learn & ConnectGain insights from industry experts and engage with a dynamic community of professionals and peers at Promilo.com."
        );

        assertEquals(
                askUsObj.askUsHeaderText().textContent().trim(),
                "Share your query to get help!"
        );

        assertEquals(
                askUsObj.askUsFooterText().textContent().trim(),
                "By proceeding ahead you expressly agree to the PromiloTerms & Conditions"
        );

        askUs.userNameField().fill("karthik");

        if (mailPhone == null || mailPhone.isEmpty()) {
            int randomSuffix = (int) (Math.random() * 100000);
            mailPhone = "90000" + String.format("%05d", randomSuffix);
        }
        askUs.mobileField().fill(mailPhone);

        askUs.feedbackMessageField().fill("Automation test message");

        listingPage.askUsSubmitButton().click();

        assertEquals(
                askUsObj.otpPageDescription().textContent().trim(),
                "Accelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams.PreviousNextAccelerate Your Career JourneyTake your career to the next level with access to exclusive job opportunities and personalized support.Tailored Job MatchesReceive customized job recommendations that align with your skills, goals, and aspirations.Unlock Your PotentialStep into a world of opportunities designed to help you achieve your professional dreams."
        );

        // ------------------------------
        // OTP ENTRY (unchanged logic)
        // ------------------------------
        if (otp == null || otp.length() < 4) otp = "9999";

        for (int i = 0; i < 4; i++) {
            String otpChar = "" + otp.charAt(i);
            Locator otpField = askUsObj.otpInputField(i + 1);
            otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000)
                    .setState(WaitForSelectorState.VISIBLE));
            otpField.fill(otpChar);
        }

        assertEquals(
                askUsObj.otpSuccessText().textContent().trim(),
                "Thanks for giving your Information!"
        );

        assertEquals(
                askUsObj.otpHeader().textContent().trim(),
                "OTP Verification"
        );

        assertTrue(
                askUsObj.otpDescription().textContent().trim()
                        .contains("Enter the 4-digit verification code we just sent you to")
        );

        assertTrue(
                askUsObj.otpStillCantFind().textContent().trim()
                        .contains("Still can’t find the OTP")
        );

        askUs.verifyProceedButton().click();

        page.waitForTimeout(3000);
        assertEquals(
                askUsObj.thankYouPopup().textContent().trim(),
                "Congratulations! You did it. Our expert will answer shortly!"
        );
    }
}
