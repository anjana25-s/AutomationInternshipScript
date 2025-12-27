package com.promilo.automation.guestuser.courses.interest;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class FilterLogic extends BaseClass {

    @Test
    public void downloadBrochure() throws InterruptedException, IOException {
        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("FilterLogic Functionality | POM Converted");

        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "PromiloAutomationTestData_Updated_With_OTP (2).xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "PromiloTestData");

        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String testCaseId = excel.getCellData(i, 0);
            if (testCaseId == null || testCaseId.trim().isEmpty()) break;
            rowCount++;
        }

        test.info("Loaded " + rowCount + " rows from Excel.");

        for (int i = 1; i < rowCount; i++) {
            String keyword = excel.getCellData(i, 1);
            if (!"CommentFunctionality".equalsIgnoreCase(keyword)) continue;

            String testCaseId = excel.getCellData(i, 0);
            test.info("Executing TestCase: " + testCaseId);

            Page page = initializePlaywright();
            test.info("Browser launched successfully");

            page.navigate(prop.getProperty("url"));
            test.info("Navigated to: " + prop.getProperty("url"));

            page.setViewportSize(1000, 768);
            Thread.sleep(3000);

            try {
                new MayBeLaterPopUp(page).getPopup().click();
                test.info("Closed popup");
            } catch (Exception ignored) {
                test.info("No popup found");
            }

            Thread.sleep(3000);

            com.promilo.automation.courses.intrestspages.CoursesFilterPage filterPage =
                    new com.promilo.automation.courses.intrestspages.CoursesFilterPage(page);

            // Open Courses section
            filterPage.coursesTab().click();
            test.info("Opened Courses section");
            Thread.sleep(5000);

            // -----------------------------------------
            // LOCATION FILTER
            // -----------------------------------------
            page.waitForTimeout(2000);
            filterPage.locationFilterButton().scrollIntoViewIfNeeded();
            filterPage.locationFilterButton().click(new Locator.ClickOptions().setForce(true));
            test.info("Opened Location filter");

            filterPage.ahmedabadLocationOption().click();
            test.info("Selected Location: Ahmedabad");

            page.waitForTimeout(3000);

            List<String> allLocations = filterPage.locationAssertion()
                    .allTextContents().stream()
                    .map(String::trim)
                    .collect(Collectors.toList());

            boolean containsAhmedabad = allLocations.stream()
                    .anyMatch(loc -> loc.toLowerCase().contains("ahmedabad"));
            String actualContains = containsAhmedabad ? "Ahmedabad" : "Not Found";
            Assert.assertEquals(actualContains, "Ahmedabad",
                    "Expected at least one location containing 'Ahmedabad'");

            boolean allContainAhmedabad = allLocations.stream()
                    .allMatch(loc -> loc.toLowerCase().contains("ahmedabad"));
            String actualAllContains = allContainAhmedabad ? "Ahmedabad" : "Not Found";
            Assert.assertEquals(actualAllContains, "Ahmedabad",
                    "Expected all locations to contain 'Ahmedabad'");

            page.waitForLoadState(LoadState.NETWORKIDLE);
            Thread.sleep(2000);

            // -----------------------------------------
            // STREAM FILTER
            // -----------------------------------------
            filterPage.streamFilterButton().click();
            test.info("Opened Stream filter");

            filterPage.engineeringStreamOption().click();
            test.info("Selected Stream: Engineering");

            page.waitForLoadState(LoadState.NETWORKIDLE);
            Thread.sleep(2000);

            String actualStream = filterPage.streamAssertion().first().textContent().trim();
            String expectedStream = "Engineering (B.E. / B.Tech)";
            Assert.assertEquals(actualStream, expectedStream,
                    "Stream mismatch! Expected: " + expectedStream + " but found: " + actualStream);

            // -----------------------------------------
            // COURSE LEVEL FILTER
            // -----------------------------------------
            filterPage.courseLevelFilterButton().click();
            test.info("Opened Course Level filter");

            filterPage.beBtechOption().click();
            test.info("Selected Course Level: B.E./B.Tech");

            page.waitForLoadState(LoadState.NETWORKIDLE);
            Thread.sleep(2000);

            String actualCourseLevel = filterPage.streamAssertion().first().textContent().trim();
            Assert.assertEquals(actualCourseLevel, expectedStream,
                    "Course level mismatch! Expected: " + expectedStream + " but found: " + actualCourseLevel);

            // -----------------------------------------
            // MODE OF STUDY FILTER
            // -----------------------------------------
            filterPage.modeOfStudy().click();
            Locator studyOptions = filterPage.modeOfStudyOptions();
            int studyCount = studyOptions.count();

            for (int d = 0; d < studyCount; d++) {
                String studyOptionText = studyOptions.nth(d).textContent().trim();
                studyOptions.nth(d).click();
                page.waitForTimeout(800);

                List<String> cardTexts = filterPage.modeOfStudyAssertion()
                        .allTextContents().stream().map(String::trim).collect(Collectors.toList());
                Locator noResult = filterPage.noResultsFound();

                if (!cardTexts.isEmpty()) {
                    Assert.assertEquals("Displayed", "Displayed",
                            "Cards not displayed for mode of study: " + studyOptionText);
                } else if (noResult.isVisible()) {
                    continue;
                } else {
                    Assert.fail("Neither cards nor 'No Results Found' visible for: " + studyOptionText);
                }

                page.waitForTimeout(500);
            }

            // -----------------------------------------
            // DURATION FILTER
            // -----------------------------------------
            filterPage.durationButton().click();
            Locator durationOptions = filterPage.durationOption();
            int durationCount = durationOptions.count();

            for (int d = 0; d < durationCount; d++) {
                String durationText = durationOptions.nth(d).textContent().trim();
                durationOptions.nth(d).click();
                page.waitForTimeout(800);

                List<String> cardTexts = filterPage.durationAssertion()
                        .allTextContents().stream().map(String::trim).collect(Collectors.toList());
                Locator noResult = filterPage.noResultsFound();

                if (!cardTexts.isEmpty()) {
                    Assert.assertEquals("Displayed", "Displayed",
                            "Cards not displayed for duration: " + durationText);
                } else if (noResult.isVisible()) {
                    continue;
                } else {
                    Assert.fail("Neither cards nor 'No Results Found' visible for: " + durationText);
                }

                page.waitForTimeout(500);
            }

            // -----------------------------------------
            // FEE FILTER
            // -----------------------------------------
            filterPage.feeRange().click();
            Locator feeOptions = filterPage.feeOptions();
            int feeCount = feeOptions.count();

            for (int f = 0; f < feeCount; f++) {
                String feeText = feeOptions.nth(f).textContent().trim();
                feeOptions.nth(f).click();
                page.waitForTimeout(800);

                List<String> cardTexts = filterPage.feeAssertion()
                        .allTextContents().stream().map(String::trim).collect(Collectors.toList());
                Locator noResult = filterPage.noResultsFound();

                if (!cardTexts.isEmpty()) {
                    Assert.assertEquals("Displayed", "Displayed",
                            "Cards not displayed for fee: " + feeText);
                } else if (noResult.isVisible()) {
                    continue;
                } else {
                    Assert.fail("Neither cards nor 'No Results Found' visible for: " + feeText);
                }

                page.waitForTimeout(500);
            }

            test.pass("Filter Logic Completed for: " + testCaseId);
            page.close();
        }

        extent.flush();
    }
}
