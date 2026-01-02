package com.promilo.automation.guestuser.courses.interest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class TalkToExperts extends BaseClass {

    @Test
    public void TalkToExpertsTest() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("🧪 TalkToExperts Functionality | Data-Driven");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty())
                break;
            rowCount++;
        }

        test.info("📘 Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 7);
            String password = excel.getCellData(i, 6);
            String comment = excel.getCellData(i, 10);

            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("🔐 Executing TestCase: " + testCaseId + " | Email: " + email);

            Page Talktopage = initializePlaywright();
            Talktopage.navigate(prop.getProperty("url"));
            test.info("🌐 Navigated to application URL.");

            Talktopage.setViewportSize(1000, 768);
            test.info("🖥️ Viewport size set to 1000x768.");

            Thread.sleep(3000);

            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(Talktopage);
            try {
                mayBeLaterPopUp.getPopup().click();
                test.info("❌ Closed popup successfully.");
            } catch (Exception ignored) {
                test.info("⚠️ No popup found.");
            }

            Thread.sleep(3000);
            Talktopage.locator("//a[text()='Courses']").click();
            test.info("📚 Clicked on 'Courses'.");

            Talktopage.locator("//input[@placeholder='Search Colleges and Courses']").fill("BTWIN");
            test.info("🔍 Entered course search text: Course auto");

            Talktopage.keyboard().press("Enter");
            test.info("↩️ Pressed Enter to search courses.");
            
            Talktopage.waitForTimeout(12000);
            Talktopage.locator("//span[normalize-space()='Talk to Experts']").first().click();
            test.info("👨‍🏫 Clicked 'Talk to Experts' button.");

            Talktopage.locator("//input[@name='userName']").nth(1).fill("karthik");
            test.info("📝 Entered name: karthik");

            String randomPhone = "90000" + String.format("%05d", new Random().nextInt(100000));
            int randomNum = 10000 + new Random().nextInt(90000);
            String randomEmail = "testAutomation-" + randomNum + "@qtvjnqv9.mailosaur.net";

            test.info("📱 Generated Phone: " + randomPhone);
            test.info("📧 Generated Email: " + randomEmail);
            
            BaseClass.generatedEmail = randomEmail;
            BaseClass.generatedPhone = randomPhone;

            Talktopage.locator("//input[@name='userMobile']").nth(1).fill(randomPhone);
            Talktopage.locator("//input[@id='userEmail']").nth(1).fill(randomEmail);
            test.info("✅ Filled Email and Phone.");

            Talktopage.locator("[id='preferredLocation']").nth(1).click();
            test.info("📍 Clicked Preferred Location dropdown");

            Thread.sleep(1000);
            List<String> industries = Arrays.asList("Ahmedabad", "Bengaluru/Bangalore", "Chennai", "Mumbai (All Areas)");
            Locator options = Talktopage.locator("//div[@class='option w-100']");

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
            Talktopage.locator("//input[@name='userName']").nth(1).click();


            test.info("📍 Closed Preferred Location dropdown.");

            Talktopage.locator("//label[@for='enableNotifications']").click();
            test.info("🔔 Enabled notifications.");

            Talktopage.locator("//button[@type='submit']").nth(1).click();
            test.info("🚀 Clicked Submit button on form.");

            String otp = "9999";
            test.info("🔢 Entering OTP: " + otp);

            for (int k = 0; k < 4; k++) {
                String digit = Character.toString(otp.charAt(k));
                Locator otpField = Talktopage
                        .locator("//input[@aria-label='Please enter OTP character " + (k + 1) + "']");
                otpField.waitFor(new Locator.WaitForOptions().setTimeout(10000).setState(WaitForSelectorState.VISIBLE));
                otpField.fill(digit);
                Talktopage.waitForTimeout(200);
            }

            test.info("✅ OTP entered successfully.");

            Talktopage.locator("//button[text()='Verify & Proceed']").click();
            test.info("🔓 Clicked 'Verify & Proceed'.");
            
            
            
            Talktopage.locator("//button[text()='Next']").click();


            Talktopage.getByRole(AriaRole.DIALOG)
                    .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Submit")).click();
            test.info("📨 Clicked Final Submit button.");
            
            

            // Validate Thank You popup
            Locator thankYouPopup = Talktopage.locator(
                    "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']");
            thankYouPopup.waitFor(new Locator.WaitForOptions().setTimeout(8000));

            String popupText = thankYouPopup.innerText().trim();
            Assert.assertTrue(popupText.equalsIgnoreCase("Thank You!"),
                    "Expected 'Thank You!' popup, found: " + popupText);
            test.pass("🎉 Thank You popup validated successfully: " + popupText);
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            

            Talktopage.close();
        }

        extent.flush();
    }
}
