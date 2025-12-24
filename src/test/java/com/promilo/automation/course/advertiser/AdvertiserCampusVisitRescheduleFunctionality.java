package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AdvertiserCampusVisitRescheduleFunctionality extends Baseclass {

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
            "com.promilo.automation.guestuser.courses.interest.GuestUserCampusVisit.GuestUserCampusVisitTest"
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

                // UI Assertions
                Assert.assertTrue(login.signInContent().isVisible(),
                        "Sign-in content is not visible");
                Assert.assertTrue(login.talkToAnExpert().isVisible(),
                        "Talk To Expert content should be visible");

                // Login
                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();

                test.info("Advertiser logged in successfully");

                // Hamburger → My Account
                AdvertiserHomepage home = new AdvertiserHomepage(page);
                home.hamburger().click();
                home.myAccount().click();
                page.locator("//span[text()='Visits Planned']").click();
                page.locator("//a[text()='All']").click();
                
                
                
                
                
                
                
                page.locator("//div[@class='pointer mx-1 d-flex my-auto cards-features-btn-alignment']//following::span[text()='Reschedule']").first().click();
                page.locator("[class='font-14 label-text text-dark-gray ms-1 ']").first().click();
                page.locator("//button[text()='Reschedule']").click();
                
                
                page.waitForTimeout(3000);
                page.locator("//span[@class='flatpickr-day']").first().click();
                test.info("📅 Selected date for reschedule");
                
               

                // Select two slots for reschedule
                page.locator("li.time-slot-box.list-group-item").first().click();
                page.locator("li.time-slot-box.list-group-item").nth(1).click();


                page.locator("//button[text()='Submit']").click();
                
                
                String popUpText=page.locator("[class='modal-content']").textContent().trim();
                System.out.println(popUpText);
                
                
                
                
                
                
                String successPopUp=page.locator("[class='font-14 text-center']").textContent().trim();
                assertEquals(successPopUp, "Once the user confirms the proposed timings, campus visit will be confirmed.");
                                
                String rescheduleStatus=page.locator("//span[text()='Reschedule Requested']").first().textContent().trim();
                assertEquals(rescheduleStatus, "Reschedule Requested");
                
                
                String text2= page.locator("//div[text()='Awaiting Acceptance']").first().textContent().trim();
                assertEquals(text2, "Awaiting Acceptance");

                
                
                

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
                
                
                
                
                
            }
        }
    }
}
