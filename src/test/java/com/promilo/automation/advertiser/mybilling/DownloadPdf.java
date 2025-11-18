package com.promilo.automation.advertiser.mybilling;

import java.nio.file.Paths;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.advertiser.AdvertiserHomepage;
import com.promilo.automation.advertiser.AdvertiserLoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class DownloadPdf extends BaseClass {
    ExtentReports extent = ExtentManager.getInstance();
    ExtentTest test = extent.createTest("🚀 Advertiser Add Funds Test | Data-Driven");

    @Test
    public void runAddFundsTest() {
        try {
            String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                    "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
            ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

            Page page = initializePlaywright();
            page.navigate(prop.getProperty("stageUrl"));
            page.setViewportSize(1000, 768);
            test.info("✅ Navigated to: " + prop.getProperty("stageUrl"));

            // Login
            AdvertiserLoginPage login = new AdvertiserLoginPage(page);
            login.loginMailField().fill("warm-apart@ofuk8kzb.mailosaur.net");
            login.loginPasswordField().fill("Karthik@88");
            login.signInButton().click();
            test.info("✅ Logged in successfully as vikas78@yopmail.com");

            // Navigate to Billing
            AdvertiserHomepage homepage = new AdvertiserHomepage(page);
            homepage.hamburger().click();
            homepage.myAccount().click();
            homepage.myBilling().click();
            test.info("✅ Navigated to My Billing section");

            // Click download icon
            Billing download = new Billing(page);
            Thread.sleep(4000);
            download.download().first().click();
            test.info("✅ Clicked on Download button for invoice");

            // Wait for the PDF tab to open
            Page pdfPage = page.waitForPopup(() -> {
                page.locator("//button[text()='Download PDF']").click();
                test.info("✅ Clicked 'Download PDF' button");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // Wait for PDF to load
            pdfPage.waitForLoadState();
            String pdfUrl = pdfPage.url();
            test.info("📄 PDF opened in new tab: " + pdfUrl);

            // Download PDF bytes
            byte[] pdfBytes = pdfPage.request().get(pdfUrl).body();

            // Save PDF locally
            java.nio.file.Path pdfPath = java.nio.file.Paths.get("downloaded.pdf");
            java.nio.file.Files.write(pdfPath, pdfBytes);
            test.info("💾 PDF saved locally as: downloaded.pdf");

        } catch (Exception e) {
            test.fail("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            extent.flush();
        }
    }
}
