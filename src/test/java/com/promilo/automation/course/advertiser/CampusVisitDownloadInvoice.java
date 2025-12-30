package com.promilo.automation.course.advertiser;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Paths;
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
import com.promilo.automation.advertiser.mybilling.Billing;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;
import com.promilo.automation.resources.PDFOCRUtility;

public class CampusVisitDownloadInvoice extends BaseClass {

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

                assertEquals(login.signInContent().isVisible(), true, "Sign in content not visible");
                assertEquals(login.talkToAnExpert().isVisible(), true, "Talk to an expert not visible");

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

                assertEquals(
                        successPopUp,
                        "Now user's contact is visible to you. Call or mail as per your choice",
                        "Success popup text mismatch"
                );

                String acceptedStatus = page
                        .locator("[class='approve-btn content-nowrap maxbtnwidth btn btn- disabled']")
                        .first()
                        .textContent()
                        .replace('\u00A0', ' ')
                        .trim();

                assertEquals(acceptedStatus, "Accepted", "Status is not Accepted");

                page.locator("[class='btn done-btn w-100']").click();

                home.hamburger().click();
                home.myBilling().click();

                Billing billing = new Billing(page);
                page.locator("[class='pointer text-black']").first().click();

                Page pdfPage = page.waitForPopup(() -> {
                    page.locator("//button[text()='Download PDF']").click();
                    try { Thread.sleep(5000); } catch (InterruptedException e) {}
                });

                pdfPage.waitForLoadState();
                String pdfUrl = pdfPage.url();

                byte[] pdfBytes = pdfPage.request().get(pdfUrl).body();
                java.nio.file.Path pdfPath = java.nio.file.Paths.get("downloaded.pdf");
                java.nio.file.Files.write(pdfPath, pdfBytes);

                String pdfText = PDFOCRUtility.extractTextFromPDF(pdfPath.toString());
                String normalizedPdfText = pdfText.replaceAll("\\s+", " ").trim();

                System.out.println(normalizedPdfText);

                // ---------- ADDRESS ----------
                String[] addressParts = {
                        "Sawara Solutions Private Limited",
                        "118 Kacharakanachali",
                        "St Thomas Town",
                        "Hennur Main Road",
                        "Bengaluru, Karnataka 560084"
                };

                for (String part : addressParts) {
                    assertEquals(
                            normalizedPdfText.contains(part),
                            true,
                            "Address part missing: " + part
                    );
                }

                // ---------- GSTIN ----------
                assertEquals(
                        normalizedPdfText.contains("29ABFCS3845N1ZN"),
                        true,
                        "GSTIN missing"
                );

                // ---------- EMAIL ----------
                assertEquals(
                        normalizedPdfText.contains("fewer-produce@qtvjnqv9.mailosaur.net"),
                        true,
                        "Email missing"
                );

                // ---------- PAN ----------
                assertEquals(
                        normalizedPdfText.contains("AMKPU8022P"),
                        true,
                        "PAN missing"
                );

                // ---------- PAYMENT ----------
                assertEquals(
                        normalizedPdfText.contains("3245"),
                        true,
                        "Payment amount missing"
                );

                assertEquals(
                        normalizedPdfText.contains("Three Thousand Two Hundred Forty Five Rupees Only"),
                        true,
                        "Payment in words missing"
                );

                // ---------- GST ----------
                assertEquals(
                        normalizedPdfText.contains("CGST (9%)"),
                        true,
                        "CGST label missing"
                );

                assertEquals(
                        normalizedPdfText.contains("SGST (9%)"),
                        true,
                        "SGST label missing"
                );

                Pattern gstPattern = Pattern.compile("(CGST|SGST) \\(9%\\).*?(\\d+\\.?\\d*)");
                Matcher matcher = gstPattern.matcher(normalizedPdfText);

                boolean cgstFound = false;
                boolean sgstFound = false;

                while (matcher.find()) {
                    if ("CGST".equals(matcher.group(1))) cgstFound = true;
                    if ("SGST".equals(matcher.group(1))) sgstFound = true;
                }

                assertEquals(cgstFound, true, "CGST value missing");
                assertEquals(sgstFound, true, "SGST value missing");

                // ---------- BANK ----------
                assertEquals(
                        normalizedPdfText.contains("Account Name: Sawara Solutions Private Limited"),
                        true,
                        "Account name missing"
                );

                String bankName = null;

                Pattern bankPattern = Pattern.compile(
                        "Bank\\s*Name\\s*:?\\s*([A-Za-z ]+)",
                        Pattern.CASE_INSENSITIVE
                );

                Matcher bankMatcher = bankPattern.matcher(normalizedPdfText);

                if (bankMatcher.find()) {
                    bankName = bankMatcher.group(1).trim();
                }

                System.out.println("Fetched Bank Name: " + bankName);

                
                
                
                
                assertEquals(
                        normalizedPdfText.contains("409001937429"),
                        true,
                        "Account number missing"
                );

                String fetchedIfscCode = null;

                Pattern ifscPattern = Pattern.compile(
                        "IFSC\\s*Code\\s*:?\\s*([A-Z0-9]{6,15})",
                        Pattern.CASE_INSENSITIVE
                );

                Matcher ifscMatcher = ifscPattern.matcher(normalizedPdfText);

                if (ifscMatcher.find()) {
                    fetchedIfscCode = ifscMatcher.group(1).trim();
                }

                System.out.println("Fetched IFSC Code: " + fetchedIfscCode);

                String fetchedPaymentMode = null;

                Pattern paymentModePattern = Pattern.compile(
                        "Payment\\s*received\\s*mode\\s*-?\\s*([A-Za-z]+)",
                        Pattern.CASE_INSENSITIVE
                );

                Matcher paymentModeMatcher = paymentModePattern.matcher(normalizedPdfText);

                if (paymentModeMatcher.find()) {
                    fetchedPaymentMode = paymentModeMatcher.group(1).trim();
                }

                System.out.println("Fetched Payment Mode: " + fetchedPaymentMode);


            } catch (Exception e) {
                test.fail("Test failed for TestCaseID : " + testCaseId);
                test.fail(e);
                Assert.fail(e.getMessage());
            }
        }
    }
}
