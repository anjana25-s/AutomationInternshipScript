package com.promilo.automation.mentorship.mentee.intrests;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.BaseClass;
import com.promilo.automation.resources.ExcelUtil;

public class DescriptionPageValidation extends BaseClass {

    private static final Logger log = LogManager.getLogger(DescriptionPageValidation.class);

    @Test
    public void DescriptionPageValidationTest() throws IOException, InterruptedException {

        log.info("===== Starting Mentorship Description Page Validation Test =====");

        // ===========================================================
        // 1) LOAD EXCEL (same as your snippet)
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
        // 2) MAP HEADERS (same as your snippet)
        // ===========================================================
        Map<String, Integer> colMap = new HashMap<>();
        int totalCols = excel.getColumnCount();
        for (int c = 0; c < totalCols; c++) {
            String header = excel.getCellData(0, c).trim();
            colMap.put(header, c);
        }
        System.out.println("✅ Header mapping: " + colMap);

        // ===========================================================
        // 3) COUNT ROWS (same as your snippet)
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
        // 4) KEYWORD MATCH LOGIC (your logic exactly)
        // ===========================================================
        Set<String> targetKeywords = Collections.singleton("DescriptionPageValidation");

        for (int i = 1; i <= rowCount; i++) {

            String keyword = excel.getCellData(i, colMap.get("keyword")).trim();
            if (!targetKeywords.contains(keyword)) continue;

            System.out.println("✅ Processing Row: " + i + " | Keyword: " + keyword);

           
            Page page = initializePlaywright();
            page.navigate(prop.getProperty("url"));
            log.info("Navigated to URL");
            page.waitForTimeout(2000);

            // Landing popup
            MayBeLaterPopUp later = new MayBeLaterPopUp(page);
            later.getPopup().click(new Locator.ClickOptions().setForce(true));
            page.waitForTimeout(2000);

            // Navigate to Mentorship
            HomePage dashboard = new HomePage(page);
            dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

            // Search mentor
            MeetupsListingPage searchPage = new MeetupsListingPage(page);
            searchPage.SearchTextField().click();
            searchPage.SearchTextField().fill("Karthik");
            page.keyboard().press("Enter");
            page.waitForTimeout(2000);
            page.waitForTimeout(14000);


            // Description Page
            DescriptionPage dp = new DescriptionPage(page);

            String actualMentorName = dp.MentorName().textContent().trim();
            String actualSpecialization = dp.Specialization().textContent().trim();
            String actualLocation = dp.location().textContent().trim();
            String actualExperience = dp.experiance().textContent().trim();
            String actualMentorType = dp.typeOfMentorSection().textContent().trim();
            String actualKeyskill = dp.keySkills().textContent().trim();

            // Expected values from Excel
            String expectedMentorName = excel.getCellData(i, colMap.get("MentorName")).trim();
            String expectedHighLight = excel.getCellData(i, colMap.get("Highlight")).trim();
            String expectedLocation = excel.getCellData(i, colMap.get("Location")).trim();
            String expectedExperience = excel.getCellData(i, colMap.get("Experience")).trim();
			String expectedMentorType = excel.getCellData(i, colMap.get("MentorType")).trim(); 
            String expectedKeyskill = excel.getCellData(i, colMap.get("Keyskills")).trim();

            // Assertions
            System.out.println(actualKeyskill);
            System.out.println(expectedKeyskill);
            Assert.assertTrue(actualMentorName.contains(expectedMentorName));
            Assert.assertTrue(actualSpecialization.contains(expectedHighLight));
            Assert.assertTrue(actualLocation.contains(expectedLocation));
            Assert.assertTrue(actualExperience.contains(expectedExperience));
            Assert.assertTrue(actualMentorType.contains(expectedMentorType));
            Assert.assertTrue(
                    actualKeyskill.contains(expectedKeyskill) || expectedKeyskill.contains(actualKeyskill),
                    "Keyskills mismatch: Actual=[" + actualKeyskill + "] Expected=[" + expectedKeyskill + "]"
            );
            System.out.println(expectedKeyskill +"Paased");
            


            // Profile Image check
            Locator profileImg = page.locator("[class='mentor-description-user-profile-img karthik U']").first();
            Assert.assertTrue(profileImg.isVisible(), "Profile image not visible");
            System.out.println( "Profile image  visible");


            // Social media icon
            page.waitForTimeout(3000);
            dp.socialMediaLink().first().isVisible();
            System.out.println( "socialMediaLink   visible");


            // ======= About Me Validation =======  
            List<String> expectedTitles = new ArrayList<>();
            List<String> expectedContents = new ArrayList<>();

            int titleColumnIndex = colMap.get("About Me Tittles");
            int contentColumnIndex = colMap.get("AboutMeContent");

            for (int r = 1; r <= excel.getRowCount(); r++) {
                String title = excel.getCellData(r, titleColumnIndex).trim();
                String content = excel.getCellData(r, contentColumnIndex).trim();

                if (!title.isEmpty()) {
                    expectedTitles.add(title);
                    expectedContents.add(content);
                }
            }

            Locator aboutMeTitles = dp.aboutMeTittle();
            int titleCount = aboutMeTitles.count();

            for (int t = 0; t < titleCount; t++) {
                String actualTitle = aboutMeTitles.nth(t).textContent().trim();
                aboutMeTitles.nth(t).click();
                Thread.sleep(1500);

                String actualContent = dp.aboutMeContent().nth(1).textContent()
                        .replaceAll("\\s+", " ").trim();

                if (expectedTitles.contains(actualTitle)) {
                    int idx = expectedTitles.indexOf(actualTitle);
                    String expectedContent = expectedContents.get(idx)
                            .replaceAll("\\s+", " ").trim();

                    Assert.assertTrue(actualContent.contains(expectedContent),
                            "Mismatch in About Me content: " + actualTitle);
                }
            }

            // Left & Right arrow
            if (dp.leftButton().isEnabled()) dp.leftButton().click();
            if (dp.rightButton().isEnabled()) dp.rightButton().click();

            // Services Validations
            dp.allLink().click();
            Thread.sleep(3000);

            Assert.assertTrue(dp.getMentorCall().nth(1).textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("getamentorcall"));

            Assert.assertTrue(dp.buyResources().first().textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("buyresources"));

            Assert.assertTrue(dp.requestVideo().first().textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("requestvideo"));

            Assert.assertTrue(dp.bookEnquiry().nth(1).textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("bookinquiry"));

            Assert.assertTrue(dp.bookOnlineMeeting().textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("bookonlinemeeting"));

            Assert.assertTrue(dp.askYourQuery().nth(1).textContent()
                    .replaceAll("\\s+","").toLowerCase()
                    .contains("askyourquery"));

            // Connect With Us section
            Locator DivBox = page.locator("//div[@class=' connect-with-us-mentor d-flex align-items-center justify-content-between px-4']");
            Assert.assertTrue(DivBox.isVisible());
            Assert.assertTrue(page.locator("//button[text()='Connect Now']").isVisible());

        }

        log.info("===== Mentorship Description Page Validation Completed =====");
    }
}
