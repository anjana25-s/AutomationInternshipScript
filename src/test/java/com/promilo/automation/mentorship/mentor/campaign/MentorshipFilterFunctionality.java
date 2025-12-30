package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.util.*;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipFilterFunctionality extends BaseClass {

    @Test
    public void mentorshipFilterTest() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorshipFilterFunctionalityTest");

        // 1️⃣ Load Excel
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");

        int totalRows = excel.getRowCount();
        if (totalRows < 2) {
            test.fail("❌ No data found in Excel.");
            Assert.fail("No data in Excel");
        }

        int totalCols = excel.getColumnCount();

        // 2️⃣ Build normalized header map
        Map<String, Integer> headerMap = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                String key = header.replace("\u00A0","").trim().toLowerCase(); // remove non-breaking space
                headerMap.put(key, c);
            }
        }

        // 🔹 Debug: print headers
        System.out.println("Excel headers:");
        for (String key : headerMap.keySet()) {
            System.out.println("Header: '" + key + "' -> Column: " + headerMap.get(key));
        }

        // 3️⃣ Find row with keyword = MentorshipFilterFunctionality
        int matchRow = -1;
        int keywordCol = headerMap.get("keyword"); // ALWAYS lowercase key from header map

        for (int i = 1; i < totalRows; i++) {
            String keyword = excel.getCellData(i, keywordCol);
            if (keyword != null && keyword.trim().equalsIgnoreCase("MentorshipFilterFunctionality")) {
                matchRow = i;
                break;
            }
        }

        if (matchRow == -1) {
            test.fail("❌ No row found with keyword MentorshipFilterFunctionality");
            Assert.fail("No keyword match in Excel");
        }

        test.info("📘 Executing Excel row: " + matchRow);

        // 4️⃣ Fetch credentials from Excel
        String inputValue = excel.getCellData(matchRow, headerMap.get("inputvalue"));
        String password   = excel.getCellData(matchRow, headerMap.get("password"));

        // 5️⃣ Initialize Playwright
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);

        try {
            // 6️⃣ Close popup if present
            MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
            try { popup.getPopup().click(); test.info("✅ Popup closed."); } catch (Exception ignored) {}
            popup.clickLoginButton();

            // 7️⃣ Login
            LoginPage loginPage = new LoginPage(page);
            loginPage.loginMailPhone().fill(inputValue);
            loginPage.passwordField().fill(password);
            loginPage.loginButton().click();
            test.info("✅ Logged in successfully with Excel credentials.");

            // 8️⃣ Navigate to Campaign List
            Hamburger hb = new Hamburger(page);
            hb.Mystuff().click();

            BecomeMentor mentor = new BecomeMentor(page);
            mentor.becomeMentorButton().click();
            mentor.campaignList().click();

            CampaignlistPage listPage = new CampaignlistPage(page);

            // 9️⃣ Apply filters (hardcoded, not from Excel)
            Locator statusButton = page.locator("//div[contains(@class,'campaign-list-select__value-container')]");
            String[] statusFilters = {"Draft","In review","Active","Stopped","Rejected","All"};

            for (String status : statusFilters) {
                statusButton.click();
                Locator option = page.locator("//div[text()='" + status + "']");
                option.scrollIntoViewIfNeeded();
                option.click();
                page.waitForTimeout(500);
                test.info("✅ Status filter applied: " + status);
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
