package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorCampaignListSearchAndFilter extends BaseClass {

    @Test
    public void campaignListSearchAndFilterTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignListSearchAndFilter");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);
        test.info("🌐 Navigated to application URL.");

        // LOAD EXCEL
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");

        int totalRows = excel.getRowCount();
        int totalCols = excel.getColumnCount();

        // CREATE HEADER MAP
        Map<String, Integer> headerMap = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                headerMap.put(header.trim().replace("\u00A0", "").toLowerCase(), c);
            }
        }


        // FIND ROW WITH TARGET KEYWORD
        int matchRow = -1;
        for (int i = 1; i < totalRows; i++) {
            String keyword = excel.getCellData(i, headerMap.get("keyword"));
            if ("MentorCampaignListSearchAndFilter".equalsIgnoreCase(keyword)) {
                matchRow = i;
                break;
            }
        }

        if (matchRow == -1) {
            test.fail("❌ No row found with keyword MentorCampaignListSearchAndFilter");
            throw new RuntimeException("No matching keyword in Excel");
        }
        test.info("📘 Executing Excel Row: " + matchRow);

        // FETCH DATA DYNAMICALLY
        String inputValue = excel.getCellData(matchRow, headerMap.get("inputvalue"));
        String password   = excel.getCellData(matchRow, headerMap.get("password"));

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
            test.info("✅ Logged in with Excel credentials.");

            // NAVIGATE TO CAMPAIGN LIST
            Hamburger hamburger = new Hamburger(page);
            hamburger.Mystuff().click();

            BecomeMentor mentor = new BecomeMentor(page);
            mentor.becomeMentorButton().click();
            mentor.campaignList().click();

            CampaignlistPage searchAndFilter = new CampaignlistPage(page);

            // SEARCH
            searchAndFilter.searchField().fill("campaign Issue");
            searchAndFilter.searchIcon().click();
            System.out.println(searchAndFilter.campaignName().first().textContent());

            // FILTER & VALIDATE
            searchAndFilter.calenderButton().click();
            searchAndFilter.fromTextfield().click();
            page.locator("//span[text()='1']").first().click();
            searchAndFilter.toTextfield().click();
            page.locator("//span[text()='1']").first().click();

            int totalRanges = page.locator("//span[@class='rdrStaticRangeLabel']").count();
            for (int r = 0; r < totalRanges; r++) {
                String rangeLabel = page.locator("//span[@class='rdrStaticRangeLabel']").nth(r).textContent().trim();
                page.locator("//span[@class='rdrStaticRangeLabel']").nth(r).click();
                page.waitForTimeout(500);

                int totalRowsInTable = page.locator("//tr").count() - 1;
                System.out.println("After selecting '" + rangeLabel + "': Rows = " + totalRowsInTable);
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
