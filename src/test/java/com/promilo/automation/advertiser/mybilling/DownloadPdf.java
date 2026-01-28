package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.PDFOCRUtility;

public class DownloadPdf extends BaseClass {

    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser  DownloadPdf Test | Data-Driven");

    @Test
    public void DownloadPdfTest() {

        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            test.info(" Navigated to: " + prop.getProperty("stageUrl"));

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("vikas78@yopmail.com");
            login.loginPasswordField().fill("Abcd12345");
            login.signInButton().click();
            test.info(" Logged in successfully as vikas78@yopmail.com");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.info(" Navigated to My Billing section");

            // Click download icon in billing table
            Billing download = new Billing(page);
            page.getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName("GSTS/00128/09-2025 Fund added"))
                    .getByRole(AriaRole.IMG).nth(2).click();
            test.info(" Clicked on Download button for invoice");

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

            // -------- ADDRESS VALIDATION ----------
            String[] keywords = {
                    "Sawara Solutions Private Limited",
                    "118 Kacharakanachali",
                    "Hennur Main Road",
                    "Bengaluru, Karnataka 560084"
            };

            boolean allFound = true;
            for (String kw : keywords) {
                if (!normalizedPdfText.contains(kw)) {
                    allFound = false;
                    test.fail(" Address part not found in PDF: " + kw);
                }
            }

            if (allFound) {
                test.pass(" Full address (all key parts) found in PDF.");
            }

            // -------- GSTIN ----------
            String expectedGSTIN = "29ABCDE1234F2Z5";
            if (normalizedPdfText.contains(expectedGSTIN))
                test.pass(" GSTIN Found: " + expectedGSTIN);
            else
                test.fail("❌ GSTIN Missing: " + expectedGSTIN);

            // -------- Email ----------
            String expectedEmail = "dhriti190999@yopmail.com";
            if (normalizedPdfText.contains(expectedEmail))
                test.pass(" Email Found: " + expectedEmail);
            else
                test.fail(" Email Missing: " + expectedEmail);

            // -------- PAN ----------
            String expectedPAN = "ABCDE1234F";
            if (normalizedPdfText.contains(expectedPAN))
                test.pass(" PAN Found: " + expectedPAN);
            else
                test.fail("❌ PAN Missing: " + expectedPAN);

            // -------- Payment ----------
            String expectedPayment = "29500";
            String expectedPaymentInWords = "Twenty Nine Thousand Five Hundred Rupees Only";

            if (normalizedPdfText.contains(expectedPayment))
                test.pass(" Numeric Payment Found: " + expectedPayment);
            else
                test.fail(" Numeric Payment Missing!");

            if (normalizedPdfText.contains(expectedPaymentInWords))
                test.pass(" Payment in Words Found");
            else
                test.fail(" Payment in Words Missing!");

         // -------- GST CALCULATIONS ----------
            String pdfText1 = normalizedPdfText;

            // Expected values
            String expectedCgstLabel = "CGST (9%):";
            String expectedSgstLabel = "SGST (9%):";
            String expectedValue = "2250";
            
            

            // --- Validate CGST label ---
            if (pdfText1.contains(expectedCgstLabel)) {
                test.pass("✅ CGST label found: " + expectedCgstLabel);
            } else {
                test.fail("❌ CGST label NOT found: " + expectedCgstLabel);
                Assert.fail("CGST label missing!");
            }
            
            

            // --- Validate SGST label ---
            if (pdfText1.contains(expectedSgstLabel)) {
                test.pass("✅ SGST label found: " + expectedSgstLabel);
            } else {
                test.fail("❌ SGST label NOT found: " + expectedSgstLabel);
                Assert.fail("SGST label missing!");
            }
            
            

            // --- Validate CGST value ---
            if (pdfText1.contains(expectedCgstLabel) && pdfText1.contains(expectedValue)) {
                test.pass("✅ CGST value found: " + expectedValue);
            } else {
                test.fail("❌ CGST value NOT found: " + expectedValue);
                Assert.fail("CGST value mismatch!");
            }

            
            
            // --- Validate SGST value ---
            if (pdfText1.contains(expectedSgstLabel) && pdfText1.contains(expectedValue)) {
                test.pass("✅ SGST value found: " + expectedValue);
            } else {
                test.fail("❌ SGST value NOT found: " + expectedValue);
                Assert.fail("SGST value mismatch!");
            }
            
            
            
            
            
            
            
            
            
         // -------- BANK & PAYMENT Transfer VALIDATIONS ----------

            String bankAccountName     = "Account Name: Sawara Solutions Private Limited";
            String bankName            = "Bank Name: RBL BANK";
            String accountNumber       = "Account Number: 409001937429";
            String ifscCode            = "IFSC Code: RATNO000156";
            String paymentMode         = "Payment received mode - Prepaid";

            
            // ---- Validate Account Name ----
            if (pdfText1.contains(bankAccountName)) {
                test.pass("✅ Account Name found: " + bankAccountName);
            } else {
                test.fail("❌ Account Name NOT found: " + bankAccountName);
                Assert.fail("Account Name missing!");
            }

            
            // ---- Validate Bank Name ----
            if (pdfText1.contains(bankName)) {
                test.pass("✅ Bank Name found: " + bankName);
            } else {
                test.fail("❌ Bank Name NOT found: " + bankName);
                Assert.fail("Bank Name missing!");
            }

            
            // ---- Validate Account Number ----
            if (pdfText1.contains(accountNumber)) {
                test.pass("✅ Account Number found: " + accountNumber);
            } else {
                test.fail("❌ Account Number NOT found: " + accountNumber);
                Assert.fail("Account Number missing!");
            }

            
            pdfPage.waitForTimeout(3000);
            pdfPage.evaluate("window.scrollTo(0, document.body.scrollHeight)");


            page.waitForTimeout(4000);
            
            
            System.out.println("🔍 DEBUG OCR TEXT:\n" + pdfText1);

            
            // ---- Validate IFSC Code ----
            if (pdfText1.contains(ifscCode)) {
                test.pass("✅ IFSC Code found: " + ifscCode);
            } else {
                test.fail("❌ IFSC Code NOT found: " + ifscCode);
                Assert.fail("IFSC Code missing!");
            }

            // ---- Validate Payment Mode ----
            if (pdfText1.contains(paymentMode)) {
                test.pass("✅ Payment Mode found: " + paymentMode);
            } else {
                test.fail("❌ Payment Mode NOT found: " + paymentMode);
                Assert.fail("Payment Mode missing!");
            }



        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
