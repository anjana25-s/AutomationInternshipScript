package com.promilo.automation.resources;

import com.microsoft.playwright.*;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SignUpLogoutUtil extends Baseclass {

    public String[] createAccountAndLoginFromExcel(ExcelUtil excel, int rowIndex) throws Exception {

        String randomEmail;
        String testCaseId = excel.getCellData(rowIndex, 0);
        String keyword = excel.getCellData(rowIndex, 1);

        Set<String> keywords = new HashSet<>(Arrays.asList(
            "AskUsWithInvalidData",
            "NotifySimilarJobsWithInvalidOTP",
            "RegisteredUserShortList",
            "GetHrCallWithInvalidData",
            "MailRegisteredUserJobApplicationWithINvalidotp",
            "MailRegisteredUserJobApplicationTest",
            "AskUsJobTestWithInvalidOtp",
            "GetHrCallTest",
            "PhoneRegisteredApplyJob",
            "RegisteredUserShortListWithInvalidOtp",
            "PhoneRegisteredJobApplyWithInvalidData",
            "RegisteredUserFeedBack",
            "NotifySimilarJobsWithInvalidData",
            "MaiLRegisteredUserInvalidJobApply",
            "GetHrCallTestWithInvalidOtp",
            "PhoneRegisteredJobApplyWithInvalidOtp",
            "EmailJobApplyWithExistingPhone",
            "NotifySimilarJobs",
            "GuestUserRegisteredWithUS",
            "RegisteredUserShortListWithInvalidData",
            "AskUsJobTest",
            "RegisteredUserJobShortListWithSignup", // ✅ signup-related
            "UserSignup"
        ));

        int inputValueColumnIndex = 3; // email/phone
        int otpColumnIndex = 5;
        int passwordColumnIndex = 6;

        if (!keywords.contains(keyword)) {
            throw new RuntimeException("❌ Test data row is not marked for a signup-required keyword.");
        }

        String otp = excel.getCellData(rowIndex, otpColumnIndex);
        String password = excel.getCellData(rowIndex, passwordColumnIndex);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 678);

        try {
            LandingPage landingPage = new LandingPage(page);

            try {
                landingPage.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
            } catch (PlaywrightException ignored) {
                System.out.println("⚠️ Popup not found. Skipping...");
            }

            landingPage.getSignup().click();
            CreateAccountpage create = new CreateAccountpage(page);

            randomEmail = "testpromilo" + System.currentTimeMillis() + (int) (Math.random() * 1000) + "@mailinator.com";
            create.getPhoneMailTextField().fill(randomEmail);

            // ✅ Write email to Excel
            excel.setCellData(rowIndex, inputValueColumnIndex, randomEmail);
            System.out.println("✅ Generated email written to Excel at row " + rowIndex + ": " + randomEmail);

            create.getSendCodeButton().click();
            create.getOtpField().fill("9999");
            create.getPasswordField().fill(password);
            create.getSubmitButton().click();

            // ✅ Dashboard verification
            DashboardPage dashboardPage = new DashboardPage(page);
            dashboardPage.mystuff().click();
            page.waitForTimeout(2000);

            // ✅ Logout
            page.locator("//button[normalize-space()='My Account']").click();
            page.waitForTimeout(1000);
            Locator logoutButton = page.locator("//a[text()='Logout']");
            logoutButton.scrollIntoViewIfNeeded();
            logoutButton.click();
            page.locator("//button[text()='Logout']").click();

            page.context().close();
        } catch (Exception e) {
            page.context().close();
            throw e;
        }

        return new String[]{randomEmail, password};
    }
}
