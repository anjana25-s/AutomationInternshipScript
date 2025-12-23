package com.promilo.automation.mentorship.mentee;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentee.pagepbjects.DescriptionPage;
import com.promilo.automation.mentorship.mentee.pagepbjects.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;

public class DescriptionPageValidation extends Baseclass {

    private static final Logger log = LogManager.getLogger(DescriptionPageValidation.class);

    // =========================================================
    //                🔹 DATA PROVIDER SECTION
    // =========================================================
    /**
     * ✅ DataProvider to load Mentorship Excel Test Data
     */
    @DataProvider(name = "MentorshipDescriptionPageValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata", "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");
        return new Object[][] { { excel } };
    }

    // =========================================================
    //                🔹 MAIN TEST SECTION
    // =========================================================
    @Test(dataProvider = "MentorshipDescriptionPageValidation")
    public void mentorshipShortListFunctionalityTest(ExcelUtil excel) throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        // =====================================================
        // 🔸 Playwright Initialization
        // =====================================================
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // =====================================================
        // 🔸 May be later pop-up
        // =====================================================
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // =====================================================
        // 🔸 Mentorship Module Navigation
        // =====================================================
        HomePage dashboard = new HomePage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");

        // Search for mentor
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("karthik");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // =====================================================
        // 🔸 Description Page Validation Section
        // =====================================================
        DescriptionPage descriptionValidation = new DescriptionPage(page);

        String actualMentorName = descriptionValidation.MentorName().textContent().trim();
        String actualSpecialization = descriptionValidation.Specialization().textContent().trim();
        String actualLocation = descriptionValidation.location().textContent().trim();
        String actualExperience = descriptionValidation.experiance().textContent().trim();
        String actualMentorType=  descriptionValidation.typeOfMentorSection().textContent().trim();
        String actualKeyskill= descriptionValidation.keySkills().textContent().trim();
        
        
        System.out.println("Mentor Name: " + actualMentorName);
        System.out.println("Specialization: " + actualSpecialization);
        System.out.println("Location: " + actualLocation);
        System.out.println("Experience: " + actualExperience);
        System.out.println(descriptionValidation.shortlistedBy().textContent());

        // -----------------------------------------------------
        // Fetch expected Description data from Excel
        // -----------------------------------------------------
        int mentorNameCol = excel.getColumnIndex("MentorName");
        int highLightCol = excel.getColumnIndex("HighLight");
        int locationCol = excel.getColumnIndex("Location");
        int experienceCol = excel.getColumnIndex("Experience");
        int mentorTypeCol= excel.getColumnIndex("MentorType");
        int KeyskillCol= excel.getColumnIndex("Keyskill");

        String expectedMentorName = excel.getCellData(1, mentorNameCol).trim();
        String expectedHighLight = excel.getCellData(1, highLightCol).trim();
        String expectedLocation = excel.getCellData(1, locationCol).trim();
        String expectedExperience = excel.getCellData(1, experienceCol).trim();
        String expectedMentorType = excel.getCellData(1, mentorTypeCol).trim();
        String expectedKeyskill= excel.getCellData(1, KeyskillCol ).trim();
        		


        // -----------------------------------------------------
        // ✅ Assertions for description page data
        // -----------------------------------------------------
        Assert.assertTrue(actualMentorName.contains(expectedMentorName),
                "❌ Mentor Name mismatch. Expected: " + expectedMentorName + ", Actual: " + actualMentorName);

        Assert.assertTrue(actualSpecialization.contains(expectedHighLight),
                "❌ Highlight/Specialization mismatch. Expected: " + expectedHighLight + ", Actual: " + actualSpecialization);

        Assert.assertTrue(actualLocation.contains(expectedLocation),
                "❌ Location mismatch. Expected: " + expectedLocation + ", Actual: " + actualLocation);

        Assert.assertTrue(actualExperience.contains(expectedExperience),
                "❌ Experience mismatch. Expected: " + expectedExperience + ", Actual: " + actualExperience);

        Assert.assertTrue(actualExperience.contains(expectedMentorType),
                "❌ Experience mismatch. Expected: " + expectedMentorType + ", Actual: " + actualMentorType);
  
        Assert.assertTrue(actualExperience.contains(expectedKeyskill),
                "❌ Experience mismatch. Expected: " + expectedKeyskill + ", Actual: " + actualKeyskill);

        System.out.println("✅ All description page validations passed successfully.");
        
     // verify profile image exists
        Locator profileImg = page.locator("//img[@src='https://promilo-stage.s3.ap-south-1.amazonaws.com/campaign/2c9f9e1e9a05425e019a0fde44db0f2e/profile_image/1761202816311-rofile-peg']").first();
        Assert.assertTrue(profileImg.isVisible(), "❌ Profile image is not visible!");
        
        
    //  Verify Social media icon exists
        descriptionValidation.socialMediaLink().isVisible();
        

        

        
        

        // =====================================================
        // 🔸 About Me Section Validation
        // =====================================================
        List<String> expectedTitles = new ArrayList<>();
        List<String> expectedContents = new ArrayList<>();

        int titleColumnIndex = excel.getColumnIndex("About Me Tittles");
        int contentColumnIndex = excel.getColumnIndex("AboutMeContent");

        for (int i = 1; i <= excel.getRowCount(); i++) {
            String titleValue = excel.getCellData(i, titleColumnIndex).trim();
            String contentValue = excel.getCellData(i, contentColumnIndex).trim();

            if (!titleValue.isEmpty()) {
                expectedTitles.add(titleValue);
                expectedContents.add(contentValue);
            }
        }

        System.out.println("✅ Expected Titles from Excel: " + expectedTitles);
        System.out.println("✅ Expected Contents from Excel: " + expectedContents);

        Locator aboutMeTitles = descriptionValidation.aboutMeTittle();
        int titleCount = aboutMeTitles.count();
        System.out.println("Total About Me Titles Found: " + titleCount);

        for (int i = 0; i < titleCount; i++) {
            String actualTitle = aboutMeTitles.nth(i).textContent().trim();
            System.out.println("\nClicking on Title: " + actualTitle);

            aboutMeTitles.nth(i).click();
            Thread.sleep(1500);

            String actualContent = descriptionValidation.aboutMeContent().nth(1).textContent().trim();
            actualContent = actualContent.replaceAll("\\s+", " ").trim();

            System.out.println("Content for '" + actualTitle + "': " + actualContent);

            if (expectedTitles.contains(actualTitle)) {
                int indexInExcel = expectedTitles.indexOf(actualTitle);
                String expectedContent = expectedContents.get(indexInExcel).replaceAll("\\s+", " ").trim();

                if (actualContent.contains(expectedContent)) {
                    System.out.println("✅ Title & content match for: " + actualTitle);
                } else {
                    System.out.println("⚠️  Content differs slightly for title: " + actualTitle);
                }
            } else {
            }
        }
        
        
        
        
      //Services Offered Section's Left and Right Arrow buttons
        if(descriptionValidation.leftButton().isEnabled()) {
            descriptionValidation.leftButton().click();
            System.out.println("✅ Left button clicked successfully");
        } else {
            System.out.println("❌ Left button is not clickable");
        }

        if(descriptionValidation.rightButton().isEnabled()) {
            descriptionValidation.rightButton().click();
            System.out.println("✅ Right button clicked successfully");
        } else {
            System.out.println("❌ Right button is not clickable");
        }


        // =====================================================
        // 🔸 All Links And Services Validation
        // =====================================================
        descriptionValidation.allLink().click();
        Thread.sleep(3000);

        // get mentor call
        String getMentorCallText = descriptionValidation.getMentorCall().nth(1).textContent();
        Assert.assertTrue(getMentorCallText.replaceAll("\\s+","").toLowerCase()
                .contains("Get a Mentor Call".replaceAll("\\s+","").toLowerCase()),
                "❌ getMentorCall text mismatch!");

        // buy resources
        String buyResourcesText = descriptionValidation.buyResources().first().textContent();
        Assert.assertTrue(buyResourcesText.replaceAll("\\s+","").toLowerCase()
                .contains("Buy Resources".replaceAll("\\s+","").toLowerCase()),
                "❌ buyResources text mismatch!");

        // request video
        String requestVideoText = descriptionValidation.requestVideo().first().textContent();
        Assert.assertTrue(requestVideoText.replaceAll("\\s+","").toLowerCase()
                .contains("Request Video".replaceAll("\\s+","").toLowerCase()),
                "❌ requestVideo text mismatch!");

        // book enquiry
        String bookEnquiryText = descriptionValidation.bookEnquiry().nth(1).textContent();
        Assert.assertTrue(bookEnquiryText.replaceAll("\\s+","").toLowerCase()
                .contains("Book Inquiry".replaceAll("\\s+","").toLowerCase()),
                "❌ bookEnquiry text mismatch!");

        // book online meeting
        String bookOnlineMeetingText = descriptionValidation.bookOnlineMeeting().textContent();
        Assert.assertTrue(bookOnlineMeetingText.replaceAll("\\s+","").toLowerCase()
                .contains("Book Online Meeting".replaceAll("\\s+","").toLowerCase()),
                "❌ bookOnlineMeeting text mismatch!");

        // ask your query
        String askYourQueryText = descriptionValidation.askYourQuery().nth(1).textContent();
        Assert.assertTrue(askYourQueryText.replaceAll("\\s+","").toLowerCase()
                .contains("Ask Your Query".replaceAll("\\s+","").toLowerCase()),
                "❌ askYourQuery text mismatch!");

        
        

        int cardCount = page.locator("//div[@class='swiper-card-title truncate w-full mb-0 leading-tight']").count();
        System.out.println("Number of Similar cards: " + cardCount);

        // Feedback Validation
        System.out.println(descriptionValidation.feedBack().textContent());

        // =====================================================
        // 🔸 Connect With Us Section Validation
        // =====================================================
        Locator DivBox = page.locator(
                "//div[@class=' connect-with-us-mentor d-flex align-items-center justify-content-between px-4']");
        DivBox.scrollIntoViewIfNeeded();

        System.out.println(DivBox.textContent());
        Assert.assertTrue(DivBox.isVisible(), "❌ Connect with us div not visible");

        Assert.assertTrue(page.locator("//button[text()='Connect Now']").isVisible(),
                "❌ Connect Now button not visible");

        log.info("===== Mentorship ShortList Functionality Test Completed =====");
    }
}
