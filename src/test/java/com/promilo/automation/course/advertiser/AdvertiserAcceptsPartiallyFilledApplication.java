package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

public class AdvertiserAcceptsPartiallyFilledApplication extends BaseClass {

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
            "com.promilo.automation.registereduser.courses.interest.FillApplicationDraftFunctionality.applyForJobWithInvalidData"
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
                System.out.println(page.locator("[class='text-nowrap']").first().textContent());
                page.locator("//span[text()='Accept']").first().click();
                page.locator("//div[@class='position-relative email-input-modal text-center font-16']").textContent().trim();
                page.locator("//button[@class='font-16 submit-btn w-50 btn-primary me-2 d-flex align-items-center justify-content-center']").click();
                page.locator("[class='font-14 text-center']").textContent().trim();
                page.locator("[class='btn done-btn w-100']").click();
                
                page.locator("//span[text()='View Application']").first().click();


                home.myBilling().click();
                                                
             // -------------------- Date validation --------------------
                String dateText = page.locator(
                        "(//table//tr[td//div[contains(normalize-space(.), 'golden college of engineering')]]//div[@class='d-flex align-items-center'])[2]")
                        .textContent().trim();

                String today = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                assertEquals(dateText, today);

                // -------------------- Status validation --------------------
                

                String statusValidation = page.locator(
                        "//td[text()='Completed']").first()
                		.textContent().trim();

                assertEquals(statusValidation, "Completed");
                
                String paymentMethodValidation= page.locator("//td[text()='Prepaid Payment']").first()
                		.textContent().trim();
                
                assertEquals(paymentMethodValidation, "Prepaid Payment");
                
                // -------------------- Client Name validation --------------------
                
                
                String clientNameValidation = page
                        .locator("[class='fw-bold text-primary']")
                        .first()
                        .innerText()
                        .trim();

                assertEquals(clientNameValidation, BaseClass.name);
                

             

                              

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
