package com.promilo.automation.mentorship.mentor.campaign;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.promilo.automation.mentorship.mentor.BecomeMentor;
import com.promilo.automation.mentorship.mentor.CampaignlistPage;
import com.promilo.automation.pageobjects.myresume.Hamburger;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.pageobjects.signuplogin.LoginPage;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class MentorshipCampaignDuplication extends BaseClass {

    @Test
    public void duplication() throws Exception {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorshipCampaignDuplication");

        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.setViewportSize(1080, 720);
        test.info("🌐 Navigated to application URL.");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "CampaignCreation");

        int totalRows = excel.getRowCount();
        int totalCols = excel.getColumnCount();

        // 1️⃣ Create normalized header map
        Map<String, Integer> headerMap = new HashMap<>();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                header = header.trim().toLowerCase().replaceAll("[^a-z]", ""); // remove spaces/symbols
                headerMap.put(header, c);
            }
        }

        // 2️⃣ Map required columns dynamically
        Map<String, String[]> columnVariants = new HashMap<>();
        columnVariants.put("keyword", new String[]{"keyword"});
        columnVariants.put("inputvalue", new String[]{"inputvalue"});
        columnVariants.put("password", new String[]{"password"});

        Map<String, Integer> finalHeaderMap = new HashMap<>();
        for (String col : columnVariants.keySet()) {
            Integer colIndex = null;
            for (String variant : columnVariants.get(col)) {
                if (headerMap.containsKey(variant)) {
                    colIndex = headerMap.get(variant);
                    break;
                }
            }
            if (colIndex == null) {
                throw new RuntimeException("Required column missing in Excel: " + col);
            }
            finalHeaderMap.put(col, colIndex);
        }

        // 3️⃣ Loop through rows and execute only target keyword
        for (int i = 1; i < totalRows; i++) {
            String keyword = excel.getCellData(i, finalHeaderMap.get("keyword"));
            if (!"MentorshipCampaignDuplication".equalsIgnoreCase(keyword)) continue;

            String inputValue = excel.getCellData(i, finalHeaderMap.get("inputvalue"));
            String password   = excel.getCellData(i, finalHeaderMap.get("password"));

            try {
                test.info("➡️ Executing Excel row " + i + " with input: " + inputValue);

                // Close popup if present
                MayBeLaterPopUp popup = new MayBeLaterPopUp(page);
                try { 
                    PlaywrightAssertions.assertThat(popup.getPopup()).isVisible();
                    popup.getPopup().click(); 
                    test.info("✅ Popup closed.");
                } catch (Exception ignored) {
                    test.info("ℹ️ No popup found.");
                }

                // Click login button
                popup.clickLoginButton();
                test.info("🔑 Clicked login button.");

                // LOGIN
                LoginPage loginPage = new LoginPage(page);
                PlaywrightAssertions.assertThat(loginPage.loginMailPhone()).isVisible();
                loginPage.loginMailPhone().fill(inputValue);

                PlaywrightAssertions.assertThat(loginPage.passwordField()).isVisible();
                loginPage.passwordField().fill(password);

                PlaywrightAssertions.assertThat(loginPage.loginButton()).isEnabled();
                loginPage.loginButton().click();
                test.info("✅ Logged in with Excel credentials.");

                // Navigate to campaign list
                Hamburger hamburger = new Hamburger(page);
                PlaywrightAssertions.assertThat(hamburger.Mystuff()).isVisible();
                hamburger.Mystuff().click();

                BecomeMentor mentor = new BecomeMentor(page);
                PlaywrightAssertions.assertThat(mentor.becomeMentorButton()).isVisible();
                mentor.becomeMentorButton().click();

                PlaywrightAssertions.assertThat(mentor.campaignList()).isVisible();
                mentor.campaignList().click();

                CampaignlistPage duplicationPage = new CampaignlistPage(page);

                // Duplicate campaign
                PlaywrightAssertions.assertThat(duplicationPage.checkBox()).isVisible();
                duplicationPage.checkBox().click();

                PlaywrightAssertions.assertThat(duplicationPage.copyButton()).isVisible();
                duplicationPage.copyButton().click();

                PlaywrightAssertions.assertThat(duplicationPage.yesButton()).isVisible();
                duplicationPage.yesButton().click();

                // Validations
                PlaywrightAssertions.assertThat(duplicationPage.draftText().first()).isVisible();
                Assert.assertEquals(duplicationPage.draftText().first().textContent(), "Draft", "Draft text mismatch!");
                System.out.println("Draft Text: " + duplicationPage.draftText().first().textContent());

                PlaywrightAssertions.assertThat(duplicationPage.createdDate().first()).isVisible();
                System.out.println("Created Date: " + duplicationPage.createdDate().first().textContent());

                PlaywrightAssertions.assertThat(duplicationPage.servicesLink().first()).isVisible();
                duplicationPage.servicesLink().first().click();
                test.info("✅ Clicked Services link.");

            } catch (Exception e) {
                test.fail("❌ Error in Excel row " + i + ": " + e.getMessage());
                throw e;
            }
        }

        extent.flush();
        closePlaywright();
    }
}
