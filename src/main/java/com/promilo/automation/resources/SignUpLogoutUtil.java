package com.promilo.automation.resources;

import com.microsoft.playwright.*;
import com.promilo.automation.pageobjects.signuplogin.CreateAccountpage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

public class SignUpLogoutUtil extends BaseClass {
	
	
@Test
    public String[] createAccountAndLoginFromExcel(ExcelUtil excel, int rowIndex) throws Exception {

        String randomEmail;
        String testCaseId = excel.getCellData(rowIndex, 0);
        String keyword = excel.getCellData(rowIndex, 1);

        
        int inputValueColumnIndex = 3; // email/phone
        int otpColumnIndex = 5;
        int passwordColumnIndex = 6;

      

        String otp = excel.getCellData(rowIndex, otpColumnIndex);
        String password = excel.getCellData(rowIndex, passwordColumnIndex);

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1366, 678);

        try {
            MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);

            try {
                mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setTimeout(5000));
            } catch (PlaywrightException ignored) {
                System.out.println("⚠️ Popup not found. Skipping...");
            }

            mayBeLaterPopUp.getSignup().click();
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
            HomePage homePage = new HomePage(page);
            homePage.mystuff().click();
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
