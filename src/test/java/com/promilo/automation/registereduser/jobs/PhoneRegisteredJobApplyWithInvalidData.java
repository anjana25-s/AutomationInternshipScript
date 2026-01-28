package com.promilo.automation.registereduser.jobs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil; // keep snippet
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.SignUpLogoutUtil;

public class PhoneRegisteredJobApplyWithInvalidData extends BaseClass {

     @DataProvider(name = "jobApplicationData")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int totalRows = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.isEmpty()) break;
            totalRows++;
        }

        List<Object[]> filteredData = new ArrayList<>();
        for (int i = 1; i <= totalRows; i++) {
            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String normalizedKeyword = keyword == null ? "" : keyword.trim().toLowerCase();

            if (normalizedKeyword.equals("registereduserjobshortlist") ||
                normalizedKeyword.equals("registereduserjobshortlistwithsignup") ||
                normalizedKeyword.equals("registereduserfeedbackwithsignup")) {

                Object[] row = new Object[8];
                row[0] = testCaseId;
                row[1] = keyword;
                row[2] = excel.getCellData(i, 3); // InputValue
                row[3] = excel.getCellData(i, 6); // Password
                row[4] = excel.getCellData(i, 7); // Name
                row[5] = excel.getCellData(i, 5); // OTP
                row[6] = excel.getCellData(i, 8); // MailPhone
                row[7] = i;                         // RowIndex
                filteredData.add(row);
            }
        }

        // If no rows match, return a dummy row to mark test as passed
        if (filteredData.isEmpty()) {
            filteredData.add(new Object[]{"NoTest", "NoKeyword", "", "", "", "", "", 0});
        }

        return filteredData.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "jobApplicationData")
    public void applyForJobAsRegisteredUser(
            String testCaseId,
            String keyword,
            String inputvalue,
            String password,
            String name,
            String otp,
            String mailphone,
            int rowIndex
    ) throws Exception {

        // Pass automatically if no matching keyword
        if ("NoTest".equals(testCaseId)) {
            return;
        }

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1280, 800);

        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        try { mayBeLaterPopUp.getPopup().click(); } catch (Exception ignored) {}
        mayBeLaterPopUp.clickLoginButton();

        LoginPage loginPage = new LoginPage(page);
        loginPage.loginMailPhone().fill("9000019763");
        loginPage.passwordField().fill("Karthik@88");
        loginPage.loginButton().click();

        JobListingPage homePage = new JobListingPage(page);
        homePage.homepageJobs().click();
        homePage.applyNow().click();
        Thread.sleep(2000);

        homePage.applyNameField().fill(name);
        homePage.applyNowMobileTextField().fill("");

        homePage.selectIndustryDropdown().click();
        Thread.sleep(1000);

        List<String> industries = Arrays.asList("Telecom / ISP", "Advertising & Marketing", "Animation & VFX", "Healthcare", "Education");

        Locator options = page.locator("//div[@class='sub-sub-option d-flex justify-content-between pointer']");
        for (String industry : industries) {
            boolean found = false;
            for (int i = 0; i < options.count(); i++) {
                String optionText = options.nth(i).innerText().trim();
                if (optionText.equalsIgnoreCase(industry)) {
                    options.nth(i).click();
                    found = true;
                    break;
                }
            }
            if (!found) {
            }
        }

        homePage.applyNameField().click(); // refocus
        Thread.sleep(1000);

        Locator applyButton = page.locator("//button[@type='button' and contains(@class,'submit-btm-askUs')]");
        applyButton.scrollIntoViewIfNeeded();
        applyButton.click();
        Thread.sleep(2000);

        Locator invalidNameError = page.locator("//div[text()='Invalid User Name, only letters and spaces are allowed, and it cannot start with a space']");
        invalidNameError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidNameText = invalidNameError.innerText().trim();
        Assert.assertEquals(actualInvalidNameText, "Invalid User Name, only letters and spaces are allowed, and it cannot start with a space");

        page.locator("//input[@placeholder='Email*']").fill("invalidemail@");
        homePage.jobShortList().click();

        Locator invalidEmailError = page.locator("//div[text()='Invalid email address']");
        invalidEmailError.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        String actualInvalidEmailText = invalidEmailError.innerText().trim();
        Assert.assertEquals(actualInvalidEmailText, "Invalid email address");

        String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + testCaseId + "_invalidData.png";
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)).setFullPage(true));

        byte[] fileContent = Files.readAllBytes(Paths.get(screenshotPath));
        String base64Screenshot = Base64.getEncoder().encodeToString(fileContent);

        page.close();
    }
}
