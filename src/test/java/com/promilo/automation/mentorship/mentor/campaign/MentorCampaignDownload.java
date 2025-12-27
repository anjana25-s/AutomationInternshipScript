package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.util.*;
import org.testng.annotations.Test;
import java.nio.file.Path;
import java.awt.Desktop;
import java.io.File;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignDownload extends BaseClass {

    @Test
    public void mentorCampaignDownloadTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

        // LOAD EXCEL
        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");

        int totalRows = excel.getRowCount();
        if (totalRows < 2) {
            test.fail("❌ No data found in Excel.");
            Assert.fail("No data in Excel");
        }

        // BUILD HEADER MAP (normalize header names to avoid NPE)
        int totalCols = excel.getColumnCount();
        Map<String, Integer> headerMap = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                String key = header.trim().replace("\u00A0","").toLowerCase(); // remove non-breaking space
                headerMap.put(key, c);
            }
        }

        // Debug: print headers

        // FIND ROW WHERE KEYWORD = MentorCampaignDownload
        int matchRow = -1;
        for (int i = 1; i < totalRows; i++) {
            String keyword = excel.getCellData(i, headerMap.get("keyword"));
            if ("MentorCampaignDownload".equalsIgnoreCase(keyword)) {
                matchRow = i;
                break;
            }
        }

        if (matchRow == -1) {
            test.fail("❌ No row found with keyword MentorCampaignDownload");
            Assert.fail("No keyword match");
        }

        test.info("📘 Executing with Excel Row: " + matchRow);

        // FETCH REQUIRED DATA FROM EXCEL
        String inputValue = excel.getCellData(matchRow, headerMap.get("inputvalue"));
        String password   = excel.getCellData(matchRow, headerMap.get("password"));

        // INITIALIZE PLAYWRIGHT
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {

            // CLOSE POPUP IF PRESENT
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try { popup.getPopup().click(); } catch (Exception ignored) {}

            popup.clickLoginButton();

            // LOGIN
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(inputValue);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();

            // NAVIGATE TO CAMPAIGN LIST
            Hamburger hb = new Hamburger(page);
            hb.Mystuff().click();

            BecomeMentor mentor = new BecomeMentor(page);
            mentor.becomeMentorButton().click();
            mentor.campaignList().click();

            CampaignlistPage listPage = new CampaignlistPage(page);

            // DOWNLOAD EXCEL
            listPage.checkBox().first().click();
            Download download = page.waitForDownload(() -> listPage.excelDownload().click());

            new File("downloads").mkdirs();
            String downloadPath = "downloads/" + download.suggestedFilename();
            download.saveAs(Paths.get(downloadPath));

            System.out.println("✅ Excel downloaded at: " + downloadPath);

            // OPEN DOWNLOADED FILE
            try {
                File f = new File(downloadPath);
                if (f.exists() && Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(f);
                }
            } catch (Exception ignored) {}

            // READ DOWNLOADED EXCEL
            ExcelUtil downloaded = new ExcelUtil(downloadPath, "Mentor-Campaigns");
            int rowCount = downloaded.getRowCount();
            int colCount = downloaded.getColumnCount();

            // PRINT ALL DATA
            for (int r = 0; r < rowCount; r++) {
                for (int c = 0; c < colCount; c++) {
                    String value = downloaded.getCellData(r, c);
                    System.out.print(value + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            test.fail("❌ Error: " + e.getMessage());
            throw e;
        } finally {
            closePlaywright();
        }

        extent.flush();
    }
}
