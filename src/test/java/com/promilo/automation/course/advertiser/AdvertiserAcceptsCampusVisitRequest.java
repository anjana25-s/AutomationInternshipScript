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
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class AdvertiserAcceptsCampusVisitRequest extends Baseclass {

    // ✅ CLASS-LEVEL VARIABLE (USED CORRECTLY)
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

                Assert.assertTrue(login.signInContent().isVisible());
                Assert.assertTrue(login.talkToAnExpert().isVisible());

                login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
                login.loginPasswordField().fill("Karthik@88");
                login.signInButton().click();

                test.info("Advertiser logged in successfully");

                AdvertiserHomepage home = new AdvertiserHomepage(page);
                home.hamburger().click();
                home.myAccount().click();
                page.locator("//span[text()='Visits Planned']").click();
                page.locator("//a[text()='All']").click();
                page.locator("[class='approve-btn content-nowrap maxbtnwidth btn btn-']").first().click();
                page.locator("//button[text()='Proceed']").click();

                String successPopUp = page.locator("[class='font-14 text-center']")
                        .textContent().trim();
                assertEquals(successPopUp,
                        "Now user's contact is visible to you. Call or mail as per your choice");

                String acceptedStatus = page
                        .locator("[class='approve-btn content-nowrap maxbtnwidth btn btn- disabled']")
                        .first()
                        .textContent()
                        .replace('\u00A0', ' ')
                        .trim();

                assertEquals(acceptedStatus, "Accepted");

                
                
                page.locator("[class='btn done-btn w-100']").click();

                home.hamburger().click();
                home.myBilling().click();

                // -------------------- Date validation --------------------
                String dateText = page.locator(
                        "(//table//tr[td//div[contains(normalize-space(.), 'course automation december')]]//div[@class='d-flex align-items-center'])[2]")
                        .textContent().trim();

                String today = LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

                assertEquals(dateText, today);

                // -------------------- Timestamp capture --------------------
                String generatedAt = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));

                System.out.println("Row generated timestamp: " + generatedAt);

                // -------------------- GST VALIDATION (FIXED) --------------------
                String gstText = page
                        .locator("[class='d-flex align-items-center text-center']").first()
                        .textContent()
                        .trim();

                Pattern pattern = Pattern.compile("GSTS/(\\d+)/12-2025");
                Matcher matcher = pattern.matcher(gstText);

                Assert.assertTrue(matcher.find(), "GST number format is incorrect");

                int currentGstNumber = Integer.parseInt(matcher.group(1));

                if (previousGstNumber != null) {
                    Assert.assertEquals(
                            currentGstNumber,
                            previousGstNumber + 1,
                            "GSTS number is not incremented by 1"
                    );
                }

                previousGstNumber = currentGstNumber;
                
                
                
                
                
                
                
                

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
