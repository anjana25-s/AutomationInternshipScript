package com.promilo.automation.resumemodule;

import com.aventstack.extentreports.*;
import com.microsoft.playwright.*;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.myresume.PersonalDetailsPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignupWithMailosaurUI;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class PersonalDetailsTest extends BaseClass {

    private static String registeredEmail = null;
    private static String registeredPassword = null;

    @BeforeSuite
    public void performSignupOnce() throws Exception {
        System.out.println("⚙️ Performing signup ONCE before suite using Mailosaur...");

        SignupWithMailosaurUI signupWithMailosaur = new SignupWithMailosaurUI();
        String[] creds = signupWithMailosaur.performSignupAndReturnCredentials();

        registeredEmail = creds[0];
        registeredPassword = creds[1];

        System.out.println("✅ Signup completed. Registered user: " + registeredEmail);
    }

    @Test
    public void fillPersonalDetailsFormDataDriven() throws Exception {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("📝 Personal Details | Data-Driven Test");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", 
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }
        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);

            String dob = excel.getCellData(i, 11);
            String address = excel.getCellData(i, 7);
            String pincode = excel.getCellData(i, 8);
            String language = excel.getCellData(i, 9);
            String proficiency = excel.getCellData(i, 15);
            String breakYear = excel.getCellData(i, 16);
            String breakMonth = excel.getCellData(i, 17);
            String workPermitUSA = excel.getCellData(i, 18);
            String workPermitOther = excel.getCellData(i, 19);

            if (!"PersonalDetailsTest".equalsIgnoreCase(keyword)) continue;

            Page page = initializePlaywright();
            try {
                test.info("🌐 Navigating to application...");
                page.navigate(prop.getProperty("url"));
                page.setViewportSize(1000, 760);

                MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
                try {
                    mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
                    test.info("✅ Closed popup.");
                } catch (Exception e) {
                    test.info("ℹ️ No popup to close.");
                }
                mayBeLaterPopUp.clickLoginButton();
                test.info("➡️ Clicked Login button.");

                // 🔑 Use Mailosaur signup credentials
                if (registeredEmail == null || registeredPassword == null) {
                    test.fail("❌ Signup credentials not available. Aborting test.");
                    return;
                }

                LoginPage loginPage = new LoginPage(page);
                loginPage.loginMailPhone().fill(registeredEmail);
                test.info("📧 Entered email: " + registeredEmail);
                loginPage.passwordField().fill(registeredPassword);
                test.info("🔑 Entered password.");
                loginPage.loginButton().click();
                test.info("🔐 Logged in successfully.");

                Hamburger resumePage = new Hamburger(page);
                resumePage.Mystuff().click();
                test.info("📂 Clicked on 'MyStuff'.");
                Assert.assertTrue(resumePage.Mystuff().isVisible(), "'MyStuff' should be visible.");
                resumePage.MyAccount().click();
                test.info("👤 Clicked on 'MyAccount'.");
                resumePage.MyResume().click();
                test.info("📄 Navigated to 'MyResume'.");
                resumePage.AddPersonalDetails().click();
                test.info("➕ Opened 'Personal Details' form.");

                PersonalDetailsPage personalDetails = new PersonalDetailsPage(page);
                personalDetails.personalDetailsGenderMaleButton().click();
                test.info("👤 Selected Gender: Male.");
                personalDetails.personalDetailsSingleParentButton().click();
                test.info("👨‍👦 Selected Single Parent: Yes.");
                personalDetails.personalDetailsMaritalStatusSingleButton().click();
                test.info("💍 Selected Marital Status: Single.");
                personalDetails.personalDetailsDobField().fill(dob != null ? dob : "1997-01-05");
                test.info("📅 Entered DOB: " + dob);

                personalDetails.personalDetailsCategoryGeneralButton().click();
                test.info("🧑 Category: General.");
                personalDetails.personalDetailsDifferentlyAbledNo().check();
                test.info("♿ Differently Abled: No.");
                personalDetails.personalDetailsCareerBreakYes().check();
                test.info("📌 Career Break: Yes.");
                personalDetails.personalDetailsCareerBreakEducationButton().click();
                test.info("🎓 Career Break Reason: Education.");
                personalDetails.personalDetailsCurrentlyOnBreakCheckbox().check();
                test.info("📖 Currently on Break: Checked.");

                Locator selectYear = personalDetails.personalDetailsBreakStartedYearDropdown();
                selectYear.click();
                page.keyboard().type(breakYear != null ? breakYear : "2020");
                page.keyboard().press("Enter");
                test.info("📅 Break Year selected: " + breakYear);

                Locator selectMonth = personalDetails.personalDetailsBreakStartedMonthDropdown();
                selectMonth.click();
                page.keyboard().type(breakMonth != null ? breakMonth : "Jan");
                page.keyboard().press("Enter");
                test.info("🗓️ Break Month selected: " + breakMonth);

                Locator workPermit = personalDetails.personalDetailsWorkPermitUSA();
                workPermit.click();
                workPermit.fill(workPermitUSA != null ? workPermitUSA : "citizen");
                page.keyboard().press("Enter");
                test.info("🇺🇸 Work Permit USA: " + workPermitUSA);

                Locator otherCountries = personalDetails.personalDetailsWorkPermitOtherCountries();
                otherCountries.click();
                otherCountries.fill(workPermitOther != null ? workPermitOther : "afg");
                page.keyboard().press("Enter");
                test.info("🌍 Work Permit Other Countries: " + workPermitOther);

                personalDetails.personalDetailsAddressField().fill(address != null ? address : "123 Test Street, Bangalore");
                test.info("🏠 Entered Address: " + address);
                personalDetails.personalDetailsPincodeField().fill(pincode != null ? pincode : "560001");
                test.info("📮 Entered Pincode: " + pincode);

                personalDetails.personalDetailsEnterLanguageField().fill(language != null ? language : "English");
                test.info("🗣️ Entered Language: " + language);
                Locator proficiencyDropdown = personalDetails.personalDetailsLanguageProficiencyDropdown();
                proficiencyDropdown.click();
                proficiencyDropdown.fill(proficiency != null ? proficiency : "Expert");
                page.keyboard().press("Enter");
                test.info("📊 Selected Language Proficiency: " + proficiency);

                personalDetails.personalDetailsLanguageReadCheckbox().check();
                personalDetails.personalDetailsLanguageWriteCheckbox().check();
                personalDetails.personalDetailsLanguageSpeakCheckbox().check();
                test.info("✅ Language Skills: Read, Write, Speak selected.");

                personalDetails.personalDetailsSaveButton().click();
                test.info("💾 Clicked Save button.");

                test.pass("✅ Personal details saved successfully for " + testCaseId);

                String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_personaldetails_pass.png";
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));
                test.addScreenCaptureFromPath(screenshotPath, "📸 Screenshot captured.");

                break;

            } catch (Exception e) {
                test.fail("❌ " + testCaseId + " failed: " + e.getMessage());
                throw e;
            } finally {
                closePlaywright();
                test.info("🧹 Closed browser for " + testCaseId);
            }
        }

        extent.flush();
    }
}
