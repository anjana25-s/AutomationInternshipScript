package com.promilo.automation.guestuser.courses.interest;

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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.courses.intrestspages.DescriptionPageValidation;
import com.promilo.automation.courses.pageobjects.CourseSearchPage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;
import com.promilo.automation.resources.ExtentManager;

public class CourseDescriptonPageValidation extends BaseClass {

    private static final Logger log = LogManager.getLogger(CourseDescriptonPageValidation.class);

    @Test
    public void CourseDescriptonPageValidationTest() throws IOException, InterruptedException {

        ExtentReports extent = ExtentManager.getInstance();
        ExtentTest test = extent.createTest("MentorCampaignDownload");

        // ================= LOAD EXCEL =================
        String excelPath = Paths.get(System.getProperty("user.dir"),
                "Testdata", "Mentorship Test Data.xlsx").toString();

        ExcelUtil excel;
        try {
            excel = new ExcelUtil(excelPath, "Courses");
            System.out.println("Excel loaded successfully");
        } catch (Exception e) {
            Assert.fail("Failed to load Excel: " + e.getMessage());
            return;
        }

        // ================= HEADER → COLUMN MAP (case-insensitive) =================
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();

        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c);
            if (header != null && !header.trim().isEmpty()) {
                colMap.put(header.trim().toLowerCase(), c);
            }
        }

        System.out.println("Header mapping: " + colMap);

        // ================= REQUIRED HEADER VALIDATION =================
        String[] requiredHeaders = {
                "tc_id",
                "keyword",
                "brandname",
                "producttitle",
                "course",
                "location",
                "totalcost",
                "durationyears",
                "description"
        };

        for (String header : requiredHeaders) {
            if (!colMap.containsKey(header)) {
                Assert.fail("Missing required Excel header: " + header
                        + " | Available headers: " + colMap.keySet());
            }
        }

        // ================= COUNT DATA ROWS =================
        int rowCount = 0;
        for (int i = 1; i <= 1000; i++) {
            String tcId = excel.getCellData(i, colMap.get("tc_id"));
            if (tcId == null || tcId.trim().isEmpty()) break;
            rowCount++;
        }

        System.out.println("Loaded " + rowCount + " data rows from Excel.");

        if (rowCount == 0) {
            Assert.fail("No data rows found in Excel");
        }

        // ================= PROCESS ONLY TARGET KEYWORD =================
        Set<String> targetKeywords = Collections.singleton("Course Campaign Creation");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("Processing row " + i + " | Keyword: " + keyword);

            // ================= FETCH DATA =================
            String brandName      = excel.getCellData(i, colMap.get("brandname"));
            String productTitle   = excel.getCellData(i, colMap.get("producttitle"));
            String selectedCourse = excel.getCellData(i, colMap.get("course"));
            String location       = excel.getCellData(i, colMap.get("location"));
            String courseFee      = excel.getCellData(i, colMap.get("totalcost"));
            String duration       = excel.getCellData(i, colMap.get("durationyears"));
            String tabContent     = excel.getCellData(i, colMap.get("description"));

            // ================= INITIALIZE PLAYWRIGHT =================
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            page.waitForTimeout(2000);

            // ================= CLOSE LANDING POPUP =================
            try {
                new MayBeLaterPopUp(page)
                        .getPopup()
                        .click(new Locator.ClickOptions().setForce(true));
            } catch (Exception ignored) {}

            CourseSearchPage coursePage = new CourseSearchPage(page);
            coursePage.searchAndSelectCourse("Golden Institute of Technology");


            page.navigate(
                "https://stage.promilo.com/courses-description/engineering/b-e-b-tech/course-promilo-automation/btwin-10"
            );

            DescriptionPageValidation descriptionPage = new DescriptionPageValidation(page);

            // ================= VALIDATIONS =================
            assertEquals(descriptionPage.brandName().textContent().trim(), brandName);
            assertEquals(descriptionPage.productTitle().textContent().trim(), productTitle);
            assertEquals(descriptionPage.selectedCourse().textContent().trim(), selectedCourse);
            assertEquals(descriptionPage.location().textContent().trim(), location);
            assertEquals(descriptionPage.duration().textContent().trim(), duration+" Years");
            
            
            double fee = Double.parseDouble(courseFee);
            String expectedFeeInLakhs = String.format("₹ %.2f Lakhs", fee / 100000);

            String actualFee = descriptionPage.averageFee().textContent()
                    .replaceAll("\\s+", " ")
                    .trim();

            // Assertion
            assertEquals(actualFee, expectedFeeInLakhs, "❌ Course fee mismatch");

            

            // ================= TABS VALIDATION =================
            Locator tabs = descriptionPage.coursesTabs();
            Locator tabContents = descriptionPage.coursesTabsContent();

            int tabCount = tabs.count();
            Assert.assertTrue(tabCount > 0, "No course tabs found");

            for (int t = 0; t < tabCount; t++) {
                tabs.nth(t).click();
                page.waitForTimeout(500);

                String actualContent = tabContents.first().textContent().trim();
                System.out.println("Tab " + t + " Content: " + actualContent);

                Assert.assertEquals(
                        actualContent,
                        tabContent,
                        "Content mismatch for tab index: " + t
                );
            }

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            // ================= OTHER UI CHECKS =================
            assertTrue(descriptionPage.freeGuidanceExpert().isVisible());
            assertTrue(descriptionPage.shortListedBy().isVisible());

            String feedbackText = descriptionPage.feedbackAboutText().textContent().trim();
            assertTrue(feedbackText.contains(brandName));

            page.close();
        }
    }
}
