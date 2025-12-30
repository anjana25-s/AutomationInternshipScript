package com.promilo.automation.advertiser.mybilling;

import static org.testng.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.PDFOCRUtility;

public class Sendmail extends BaseClass {
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser Sendmail Test | Data-Driven");

    @Test
    public void SendmailTest() {
        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");
            test.info("Loaded test data from Excel: " + excelPath);

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            test.info("Navigated to: " + prop.getProperty("stageUrl"));

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("fewer-produce@qtvjnqv9.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            login.signInButton().click();
            test.pass("Logged in successfully");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.pass("Navigated to My Billing");

            // Billing page interactions
            Billing billing = new Billing(page);
            Thread.sleep(5000);
            billing.sendMail().first().click();
            test.pass("Clicked Send Mail button");

            page.locator("//button[@style='background-color: #006699 !important']").click();
            test.pass("Clicked confirm button");

            Page page1 = page.context().newPage();
            test.info("Opened new page for Mailosaur");

            page1.navigate("https://mailosaur.com/app/servers/qtvjnqv9/messages/inbox");
            test.info("Navigated to Mailosaur inbox");

            page1.locator("//input[@placeholder='Enter your email address']").fill("karthikmailsaur@gmail.com");
            page1.locator("//button[text()='Continue']").click();
            test.info("Entered email and clicked Continue");

            page1.locator("//input[@placeholder='Enter your password']").fill("Karthik@88");
            page1.locator("//button[text()='Log in']").click();
            test.info("Entered password and logged in");

            Locator firstEmail = page1
                    .locator("//p[text()='Invoice Attached: Review your Billing Details']").first();
            firstEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
            test.pass("Inbox email list is visible");

            firstEmail.click();
            test.pass("Clicked the first email in inbox");

            Thread.sleep(5000); // Ideally use explicit wait for email body or attachment

            page1.getByText("As requested,").click();
            test.pass("Clicked email content text");

            Locator scrollIntoview = page1.getByTestId("sidebar-attachments");
            scrollIntoview.scrollIntoViewIfNeeded();
            scrollIntoview.click();
            test.pass("Clicked attachments sidebar");

            Download download = page1.waitForDownload(() -> {
                Locator downloadButton = page1.locator("//div[@class='w-full']").nth(1);
                downloadButton.click();
            });
            test.pass("Triggered download and waiting for file");

            Path downloadedFilePath = download.path();
            test.pass("File downloaded to: " + downloadedFilePath);

            Page page2 = page.context().newPage();
            page2.navigate(downloadedFilePath.toUri().toString());
            test.pass("Opened downloaded file in new tab: " + downloadedFilePath.toUri());

            // Extract text via OCR
            String pdfText = PDFOCRUtility.extractTextFromPDF(downloadedFilePath.toString());
            String normalizedPdfText = pdfText.replaceAll("\\s+", " ").trim();

            // -------- ADDRESS VALIDATION ----------
            String[] addressKeywords = { "Sawara Solutions Private Limited", "118 Kacharakanachali",
                    "Hennur Main Road", "Bengaluru, Karnataka 560084" };
            boolean allAddressFound = true;
            for (String kw : addressKeywords) {
                if (!normalizedPdfText.contains(kw)) {
                    allAddressFound = false;
                    test.fail("Address part not found in PDF: " + kw);
                }
            }
            if (allAddressFound) {
                test.pass("Full address (all key parts) found in PDF.");
            }

            // -------- GSTIN ----------
            String[] gstin = { "29ABFCS3845N1ZN" };
            for (String val : gstin) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("GSTIN Found: " + val);
                } else {
                    test.fail("GSTIN Missing: " + val);
                }
            }

            // -------- EMAIL ----------
            String[] emails = { "dhriti190999@yopmail.com" };
            for (String val : emails) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Email Found: " + val);
                } else {
                    test.fail("Email Missing: " + val);
                }
            }

            // -------- PAN ----------
            String[] panNumbers = { "ABCDE1234F" };
            for (String val : panNumbers) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("PAN Found: " + val);
                } else {
                    test.fail("PAN Missing: " + val);
                }
            }

            // -------- PAYMENT ----------
            String[] numericPayment = { "29500" };
            for (String val : numericPayment) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Numeric Payment Found: " + val);
                } else {
                    test.fail("Numeric Payment Missing: " + val);
                }
            }

            String[] paymentWords = { "Twenty Nine Thousand Five Hundred Rupees Only" };
            for (String val : paymentWords) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Payment in Words Found");
                } else {
                    test.fail("Payment in Words Missing!");
                }
            }

            // -------- GST CALCULATIONS ----------
            String[] cgstLabel = { "CGST (9%):" };
            for (String val : cgstLabel) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("CGST label found: " + val);
                } else {
                    test.fail("CGST label NOT found: " + val);
                }
            }

            String[] sgstLabel = { "SGST (9%):" };
            for (String val : sgstLabel) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("SGST label found: " + val);
                } else {
                    test.fail("SGST label NOT found: " + val);
                }
            }

            String[] gstValues = { "2250" };
            for (String val : gstValues) {
                if (normalizedPdfText.contains("CGST (9%):") && normalizedPdfText.contains(val)) {
                    test.pass("CGST value found: " + val);
                } else {
                    test.fail("CGST value NOT found: " + val);
                }
                if (normalizedPdfText.contains("SGST (9%):") && normalizedPdfText.contains(val)) {
                    test.pass("SGST value found: " + val);
                } else {
                    test.fail("SGST value NOT found: " + val);
                }
            }

            // -------- BANK & PAYMENT DETAILS ----------
            String[] bankAccountName = { "Account Name: Sawara Solutions Private Limited" };
            for (String val : bankAccountName) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Account Name found: " + val);
                } else {
                    test.fail("Account Name NOT found: " + val);
                }
            }

            String[] bankName = { "Bank Name: RBL BANK" };
            for (String val : bankName) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Bank Name found: " + val);
                } else {
                    test.fail("Bank Name NOT found: " + val);
                }
            }

            String[] accountNumber = { "Account Number: 409001937429" };
            for (String val : accountNumber) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Account Number found: " + val);
                } else {
                    test.fail("Account Number NOT found: " + val);
                }
            }

            String[] ifscCode = { "IFSC Code: RATNO000156" };
            for (String val : ifscCode) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("IFSC Code found: " + val);
                } else {
                    test.fail("IFSC Code NOT found: " + val);
                }
            }

            String[] paymentMode = { "Payment received mode - Prepaid" };
            for (String val : paymentMode) {
                if (normalizedPdfText.contains(val)) {
                    test.pass("Payment Mode found: " + val);
                } else {
                    test.fail("Payment Mode NOT found: " + val);
                }
            }

        } catch (Exception e) {
            test.fail("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
