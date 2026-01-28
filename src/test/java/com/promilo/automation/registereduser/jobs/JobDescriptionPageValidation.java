package com.promilo.automation.registereduser.jobs;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.job.pageobjects.JobDescriptionPage;
import com.promilo.automation.job.pageobjects.JobListingPage;
import com.promilo.automation.mentorship.mentee.intrests.DescriptionPageValidation;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;

public class JobDescriptionPageValidation extends BaseClass {

    private static final Logger log = LogManager.getLogger(DescriptionPageValidation.class);

    @Test
    public void DescriptionPageValidationTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship Description Page Validation Test =====");

        // ===========================================================
        // 1) Load Excel file
        // ===========================================================
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Mentorship");
            System.out.println("✅ Excel loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Failed to load Excel: " + e.getMessage());
            Assert.fail("Excel loading failed");
            return;
        }

        // ===========================================================
        // 2) Map header names with column indices
        // ===========================================================
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ===========================================================
        // 3) Count rows
        // ===========================================================
        int rowCount = 0;

        for (int i = 1; i <= 1000; i++) {
            String tcId = excel.getCellData(i, colMap.get("testCaseId"));
            if (tcId == null || tcId.trim().isEmpty()) break;
            rowCount++;
        }
        System.out.println("✅ Loaded " + rowCount + " data rows from Excel.");

        if (rowCount == 0) {
            Assert.fail("No data rows in Excel");
            return;
        }

        // ===========================================================
        // 4) Run only matching keyword test cases
        // ===========================================================
        Set<String> targetKeywords = Collections.singleton("DescriptionPageValidation");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing Row: " + i + " | Keyword: " + keyword);

            // Browser & navigation
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            // Close popup
            MayBeLaterPopUp later = new MayBeLaterPopUp(page);
            later.getPopup().click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(2000);

            // Navigate to job listing
            JobListingPage jobListing = new JobListingPage(page);
            jobListing.homepageJobs().click();
            page.waitForTimeout(15000);
            page.navigate("https://stage.promilo.com/jobs-description/hospitality/december-campaign-automation-10/software-tester");

            // Page object
            JobDescriptionPage descriptionValidation = new JobDescriptionPage(page);

            // ============ VALIDATIONS WITH COMMENTS ============

            //  Validate Job Title
            String actualJobTitle = descriptionValidation.JobTitle().textContent().trim();
            assertEquals(actualJobTitle, "Software Tester", "Job title did not match");

            //  Validate Brand Name / Company Name
            String actualBrandName = descriptionValidation.brandValidation().textContent().trim();
            assertEquals(actualBrandName, "December Campaign Automation", "Brand name mismatch");

            //  Validate Salary Range
            String actualSalary = descriptionValidation.salaryValidation().textContent().trim();
            assertEquals(actualSalary, "₹ 5.6L - 9.9L", "Salary range mismatch");

            // 🔍 Validate Experience
            String actualExperiance = descriptionValidation.experiance().textContent().trim();
            assertEquals(actualExperiance, "0-1 Years", "Experience mismatch");

            //  Validate Location
            String actualLocation = descriptionValidation.location().textContent().trim();
            assertEquals(actualLocation, "Bengaluru/Bangalore", "Location mismatch");

            //  Validate Work Mode
            String workMode = descriptionValidation.workMode().textContent().trim();
            assertEquals(workMode, "Part-time / Short term", "Work mode mismatch");

            //  Validate Job Description content
            descriptionValidation.jobDescription().click();
            String jobDescriptionValidation = descriptionValidation.jobDescriptionValidation().textContent().trim();
            assertEquals(jobDescriptionValidation, "Located in the heart of the city…",
                    "Job Description text mismatch");

            //  Validate Benefits & Perks section
            descriptionValidation.benifitsAndPerks().click();
            String benifitsAndPerksValidation = descriptionValidation.perksAndBenifitsValidation().textContent().trim();
            assertEquals(benifitsAndPerksValidation, "Located in the heart of the city…",
                    "Benefits & Perks text mismatch");

            //  Validate About the Company section
            page.waitForTimeout(3000);
            String AboutMeText = descriptionValidation.aboutTheCompany().textContent().trim();
            String actualAboutMeText =
                    "openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...openings, SDLC engineer, deployment engineer, modern tech stack jobs...  ";
            assertEquals(AboutMeText, actualAboutMeText, "About company text mismatch");

            //  Validate 'Similar Jobs Across City' visibility
            assertTrue(descriptionValidation.similarJobsAcrossCity().isVisible(),
                    "'Similar Jobs Across City' is not visible");

            //  Validate 'Similar Jobs Across India' visibility
            assertTrue(descriptionValidation.similarJobsAcrossIndia().isVisible(),
                    "'Similar Jobs Across India' is not visible");

            //  Validate Feedback Section (contains brand name)
            String feedbackValidation = descriptionValidation.feedbackAboutValidation().textContent().trim();
            assertTrue(feedbackValidation.contains("December Campaign Automation"),
                    "Feedback section does not contain expected company name");

            System.out.println("Feedback validated");

            //  Validate 'Get Connected' section full text
            String getConnectedText = descriptionValidation.getConnectedSection().textContent().trim();
            assertEquals(getConnectedText,
                    "Looking for Your Dream Job? Connect with Promilo Experts!Who offers exclusive opportunities across top industries and personalized job matches tailored to your skills and aspirations.Get Connected",
                    "Get Connected section text mismatch");
        }
    }
}
