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
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.advertiser.mybilling.Billing;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.PDFOCRUtility;

public class CampusVisitDownloadInvoice extends BaseClass {

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

                
                
                
                
                
             // Click download icon in billing table
                Billing download = new Billing(page);
                page.locator("[class='pointer text-black']").first().click();

                
                // Wait for PDF tab to open
                Page pdfPage = page.waitForPopup(() -> {
                    page.locator("//button[text()='Download PDF']").click();
                    test.info(" Clicked 'Download PDF' button");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

                pdfPage.waitForLoadState();
                String pdfUrl = pdfPage.url();
                test.info(" PDF opened in new tab: " + pdfUrl);

                // Download PDF bytes
                byte[] pdfBytes = pdfPage.request().get(pdfUrl).body();

                // Save PDF locally
                java.nio.file.Path pdfPath = java.nio.file.Paths.get("downloaded.pdf");
                java.nio.file.Files.write(pdfPath, pdfBytes);
                test.info(" PDF saved locally as: downloaded.pdf");

                // Extract text via OCR
                String pdfText = PDFOCRUtility.extractTextFromPDF(pdfPath.toString());
                String normalizedPdfText = pdfText.replaceAll("\\s+", " ").trim();
                
                System.out.println(normalizedPdfText);
                
                

             // -------- ADDRESS VALIDATION ----------
                String[] keywords = {
                        "Sawara Solutions Private Limited",
                        "118 Kacharakanachali",
                        "St Thomas Town",
                        "Hennur Main Road",
                        "Bengaluru, Karnataka 560084"
                };

                boolean allFound = true;
                for (String kw : keywords) {
                    if (!normalizedPdfText.contains(kw)) {
                        allFound = false;
                        test.fail("❌ Address part not found in PDF: " + kw);
                    }
                }

                if (allFound) {
                    test.pass("✅ Full address (all key parts) found in PDF.");
                }

             // -------- GSTIN ----------
                String expectedGSTIN = "29ABFCS3845N1ZN";

                if (normalizedPdfText.contains(expectedGSTIN)) {
                    test.pass("✅ GSTIN Found: " + expectedGSTIN);
                } else {
                    test.fail("❌ GSTIN Not Found: " + expectedGSTIN);
                    Assert.fail("GSTIN missing!");
                }

                
             // -------- Email ----------
                String expectedEmail = "fewer-produce@qtvjnqv9.mailosaur.net";

                if (normalizedPdfText.contains(expectedEmail))
                    test.pass("✅ Email Found: " + expectedEmail);
                else
                    test.fail("❌ Email Missing: " + expectedEmail);

                
             // -------- PAN ----------
                String expectedPAN = "AMKPU8022P";

                if (normalizedPdfText.contains(expectedPAN))
                    test.pass("✅ PAN Found: " + expectedPAN);
                else
                    test.fail("❌ PAN Missing: " + expectedPAN);

                
             // -------- Payment ----------
                String expectedPayment = "3245";
                String expectedPaymentInWords = "Three Thousand Two Hundred Forty Five Rupees Only";

                if (normalizedPdfText.contains(expectedPayment))
                    test.pass("✅ Numeric Payment Found: " + expectedPayment);
                else
                    test.fail("❌ Numeric Payment Missing!");

                if (normalizedPdfText.contains(expectedPaymentInWords))
                    test.pass("✅ Payment in Words Found");
                else
                    test.fail("❌ Payment in Words Missing!");

                
             // -------- GST CALCULATIONS ----------
                String pdfText1 = normalizedPdfText;

                String expectedCgstLabel = "CGST (9%)";
                String expectedSgstLabel = "SGST (9%)";
                String expectedGstValue  = "247.5";

                // CGST Label
                if (pdfText1.contains(expectedCgstLabel))
                    test.pass("✅ CGST label found");
                else {
                    test.fail("❌ CGST label missing");
                    Assert.fail();
                }

                // SGST Label
                if (pdfText1.contains(expectedSgstLabel))
                    test.pass("✅ SGST label found");
                else {
                    test.fail("❌ SGST label missing");
                    Assert.fail();
                }

                // CGST Value
                if (pdfText1.contains(expectedGstValue))
                    test.pass("✅ CGST value found: " + expectedGstValue);
                else {
                    test.fail("❌ CGST value mismatch");
                    Assert.fail();
                }

                // SGST Value
                if (pdfText1.contains(expectedGstValue))
                    test.pass("✅ SGST value found: " + expectedGstValue);
                else {
                    test.fail("❌ SGST value mismatch");
                    Assert.fail();
                }

                
             // -------- BANK & PAYMENT TRANSFER ----------
                String bankAccountName = "Account Name: Sawara Solutions Private Limited";
                String bankName        = "Bank Name: RBL Bank";
                String accountNumber   = "409001937429";
                String ifscCode        = "RATN0000156";
                String paymentMode     = "Payment received mode - Prepaid";

                // Account Name
                if (pdfText1.contains(bankAccountName))
                    test.pass("✅ Account Name found");
                else {
                    test.fail("❌ Account Name missing");
                    Assert.fail();
                }

                // Bank Name
                if (pdfText1.contains(bankName))
                    test.pass("✅ Bank Name found");
                else {
                    test.fail("❌ Bank Name missing");
                    Assert.fail();
                }

                // Account Number
                if (pdfText1.contains(accountNumber))
                    test.pass("✅ Account Number found");
                else {
                    test.fail("❌ Account Number missing");
                    Assert.fail();
                }

                // IFSC Code
                if (pdfText1.contains(ifscCode))
                    test.pass("✅ IFSC Code found");
                else {
                    test.fail("❌ IFSC Code missing");
                    Assert.fail();
                }

                // Payment Mode
                if (pdfText1.contains(paymentMode))
                    test.pass("✅ Payment Mode found");
                else {
                    test.fail("❌ Payment Mode missing");
                    Assert.fail();
                }


                

            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
