package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FillApplicationCompletedFormSubmissionValidation extends BaseClass {

    private static Integer previousGstNumber = null;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setUpExtent() {
        extent = ExtentManager.getInstance();
    }

    @AfterClass
    public void tearDownExtent() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Test(
        dependsOnMethods = {
            "com.promilo.automation.registereduser.courses.interest.MailRegisteredFillApplicationThroughWallet.MailRegisteredFillApplicationThroughWallet"
        }
    )
    public void CallbackOrTalktoExpertApprovefunctionalityTest()
            throws InterruptedException, IOException {

        test = extent.createTest("📊 Callback / Talk To Expert Approve Functionality");

        String excelPath = Paths.get(
                System.getProperty("user.dir"),
                "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx"
        ).toString();

        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) {
                break;
            }
            rowCount++;
        }

        for (int i = 1; i <= rowCount; i++) {

            String testCaseId = excel.getCellData(i, 0);
            String keyword = excel.getCellData(i, 1);
            String email = excel.getCellData(i, 2);
            String password = excel.getCellData(i, 3);

            if (!"CallbackOrTalkToExpertApprove".equalsIgnoreCase(keyword)) {
                continue;
            }

            test.info("Executing Row : " + i + " | TestCaseID : " + testCaseId);

            try {
                Page page = initializePlaywright();
                test.info("Browser launched successfully");

                page.navigate(prop.getProperty("stageUrl"));
                page.setViewportSize(1000, 768);
                Thread.sleep(5000);

                AdvertiserLoginPage login = new AdvertiserLoginPage(page);

                Assert.assertTrue(login.signInContent().isVisible());
                Assert.assertTrue(login.talkToAnExpert().isVisible());

                login.loginMailField().fill("testlucky000@yopmail.com");
                login.loginPasswordField().fill("Abcd12345");
                login.signInButton().click();

                test.info("Advertiser logged in successfully");

                AdvertiserHomepage home = new AdvertiserHomepage(page);
                home.hamburger().click();
                home.myAccount().click();
                
                page.locator("//span[text()='User Applications']").click();
                page.locator("//input[@placeholder='Search by keyword']").fill(BaseClass.name);
                page.keyboard().press("Enter");
                
                page.waitForTimeout(3000);
                String statusValidation=page.locator("//p[@class='text-nowrap']").first().textContent().trim();
                assertEquals(statusValidation, "Application Status : Completed");
                
             // Click copy icon
                page.locator("//img[@class='position-absolute copy-image pointer']").first().click();

                // Read copied value from clipboard
                String actualNumber = (String) page.evaluate("() => navigator.clipboard.readText()");

                // Assertion
                Assert.assertEquals(actualNumber, BaseClass.randomNumber);
                
                
             // Click copy icon
                page.locator("//img[@class='position-absolute copy-image pointer']").nth(1).click();

                // Read copied value from clipboard
                String actualMail = (String) page.evaluate("() => navigator.clipboard.readText()");

                // Assertion
                Assert.assertEquals(actualMail, BaseClass.registeredEmail);       
                
                
                
                
                page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").first().click();
                
                String campaignName=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").first().textContent().trim();
                assertEquals(campaignName, "Golden college of engineering");
                
                
                
                String preferredStream=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(1).textContent().trim();
                assertEquals(preferredStream, "Engineering");
                
                String preferredCourse=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(2).textContent().trim();
                assertEquals(preferredCourse, "B.E. / B.Tech");
                
                page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(3).click();
                String preferredLocation=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(3).textContent().trim();
                assertEquals(preferredLocation, "Ahmedabad, Bengaluru/Bangalore, Chennai, Mumbai (All Areas)");
                
                
                String interestShownDate = page
                        .locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']")
                        .nth(4)
                        .textContent()
                        .trim();

                String currentDate = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                Assert.assertEquals(interestShownDate, currentDate,
                        "Interest shown date is not matching current date");
                
                
                

                String status=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(5).textContent().trim();
                assertEquals(status, "Accepted");
                
                
                String applicationCost=page.locator("[class='font-10 d-flex justify-content-start align-items-center pointer text-primary']").nth(6).textContent().trim();
                assertEquals(applicationCost, "₹130");
                
                                
                
                String source=page.locator("[class='mb-25 text-nowrap']").nth(0).textContent().trim();
                assertEquals(source, "Source : Regular");
                
                
                String activeOn = page.locator("[class='mb-25 text-nowrap']")
                        .nth(1)
                        .textContent()
                        .trim();

                String update = page.locator("[class='mb-25 text-nowrap']")
                        .nth(2)
                        .textContent()
                        .trim();

                String expectedDate = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                Assert.assertEquals(activeOn, "Active on : "+expectedDate, "Active On date mismatch");
                Assert.assertEquals(update, "Update : "+expectedDate, "Updated date mismatch");                
                
                
                              

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
