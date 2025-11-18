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
import com.promilo.automation.mentorship.mentee.DescriptionPage;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.HomePage;
import com.promilo.automation.pageobjects.signuplogin.MayBeLaterPopUp;
import com.promilo.automation.resources.Baseclass;
import com.promilo.automation.resources.ExcelUtil;

public class DescriptionPageValidation extends Baseclass {

    private static final Logger log = LogManager.getLogger(DescriptionPageValidation.class);

    // =========================================================
    //                🔹 DATA PROVIDER SECTION
    // =========================================================
    @DataProvider(name = "MentorshipDescriptionPageValidation")
    public Object[][] jobApplicationData() throws Exception {
        String excelPath = Paths.get(System.getProperty("user.dir"), "Testdata",
                "Mentorship Test Data.xlsx").toString();
        ExcelUtil excel = new ExcelUtil(excelPath, "Mentorship");
        return new Object[][] { { excel } };
    }

    // =========================================================
    //                🔹 MAIN TEST SECTION
    // =========================================================
    @Test(dataProvider = "MentorshipDescriptionPageValidation")
    public void mentorshipShortListFunctionalityTest(ExcelUtil excel)
            throws IOException, InterruptedException {

        log.info("===== Starting Mentorship ShortList Functionality Test =====");

        // Playwright setup
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        page.waitForTimeout(2000);

        // landing popup
        MayBeLaterPopUp mayBeLaterPopUp = new MayBeLaterPopUp(page);
        mayBeLaterPopUp.getPopup().click(new Locator.ClickOptions().setForce(true));
        page.waitForTimeout(2000);

        // mentorship module
        HomePage dashboard = new HomePage(page);
        dashboard.mentorships().click(new Locator.ClickOptions().setForce(true));

        // search flow
        MeetupsListingPage searchPage = new MeetupsListingPage(page);
        searchPage.SearchTextField().click();
        searchPage.SearchTextField().fill("karthik");
        page.keyboard().press("Enter");
        page.waitForTimeout(2000);

        // description page
        DescriptionPage descriptionValidation = new DescriptionPage(page);

        // actual values
        String actualMentorName = descriptionValidation.MentorName().textContent().trim();
        String actualSpecialization = descriptionValidation.Specialization().textContent().trim();
        String actualLocation = descriptionValidation.location().textContent().trim();
        String actualExperience = descriptionValidation.experiance().textContent().trim();
        String actualMentorType = descriptionValidation.typeOfMentorSection().textContent().trim();
        String actualKeyskill = descriptionValidation.keySkills().textContent().trim();

        // Print
        System.out.println("Mentor Name: " + actualMentorName);
        System.out.println("Specialization: " + actualSpecialization);
        System.out.println("Location: " + actualLocation);
        System.out.println("Experience: " + actualExperience);
        System.out.println(descriptionValidation.shortlistedBy().textContent());

        // expected values from Excel
        int mentorNameCol = excel.getColumnIndex("MentorName");
        int highLightCol = excel.getColumnIndex("HighLight");
        int locationCol = excel.getColumnIndex("Location");
        int experienceCol = excel.getColumnIndex("Experience");
        int mentorTypeCol = excel.getColumnIndex("MentorType");
        int keyskillCol = excel.getColumnIndex("Keyskill");

        String expectedMentorName = excel.getCellData(1, mentorNameCol).trim();
        String expectedHighLight = excel.getCellData(1, highLightCol).trim();
        String expectedLocation = excel.getCellData(1, locationCol).trim();
        String expectedExperience = excel.getCellData(1, experienceCol).trim();
        String expectedMentorType = excel.getCellData(1, mentorTypeCol).trim();
        String expectedKeyskill = excel.getCellData(1, keyskillCol).trim();

        // assertions
        Assert.assertTrue(actualMentorName.contains(expectedMentorName));
        Assert.assertTrue(actualSpecialization.contains(expectedHighLight));
        Assert.assertTrue(actualLocation.contains(expectedLocation));
        Assert.assertTrue(actualExperience.contains(expectedExperience));
        Assert.assertTrue(actualMentorType.contains(expectedMentorType));
        Assert.assertTrue(actualKeyskill.contains(expectedKeyskill));

        System.out.println("✔ Description validations passed");

        // profile image check
        Locator profileImg = page.locator(
                "//img[@src='https://promilo-stage.s3.ap-south-1.amazonaws.com/campaign/2c9f9e1e9a05425e019a0fde44db0f2e/profile_image/1761202816311-rofile-peg']")
                .first();
        Assert.assertTrue(profileImg.isVisible());

        // social media icon
        descriptionValidation.socialMediaLink().isVisible();

        // About me section
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

        Locator aboutMeTitles = descriptionValidation.aboutMeTittle();
        int titleCount = aboutMeTitles.count();

        for (int i = 0; i < titleCount; i++) {
            aboutMeTitles.nth(i).click();
            Thread.sleep(1500);

            String actualContent = descriptionValidation.aboutMeContent().nth(1).textContent().trim();
            actualContent = actualContent.replaceAll("\\s+", " ");

            if (expectedTitles.contains(aboutMeTitles.nth(i).textContent().trim())) {
                int idx = expectedTitles.indexOf(aboutMeTitles.nth(i).textContent().trim());
                Assert.assertTrue(actualContent.contains(expectedContents.get(idx).trim()));
            }
        }

        // services navigation
        if (descriptionValidation.leftButton().isEnabled())
            descriptionValidation.leftButton().click();

        if (descriptionValidation.rightButton().isEnabled())
            descriptionValidation.rightButton().click();

        // All link
        descriptionValidation.allLink().click();
        Thread.sleep(3000);

        // get mentor call
        Assert.assertTrue(descriptionValidation.getMentorCall().nth(1).textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("getamentorcall".toLowerCase()));

        // buy resources
        Assert.assertTrue(descriptionValidation.buyResources().first().textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("buyresources".toLowerCase()));

        // request video
        Assert.assertTrue(descriptionValidation.requestVideo().first().textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("requestvideo".toLowerCase()));

        // book enquiry
        Assert.assertTrue(descriptionValidation.bookEnquiry().nth(1).textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("bookinquiry".toLowerCase()));

        // online meeting
        Assert.assertTrue(descriptionValidation.bookOnlineMeeting().textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("bookonlinemeeting".toLowerCase()));

        // ask query
        Assert.assertTrue(descriptionValidation.askYourQuery().nth(1).textContent()
                .replaceAll("\\s+", "").toLowerCase()
                .contains("askyourquery".toLowerCase()));

        // count similar cards
        int cardCount = page.locator(
                "//div[@class='swiper-card-title truncate w-full mb-0 leading-tight']").count();
        System.out.println("Similar cards: " + cardCount);

        System.out.println(descriptionValidation.feedBack().textContent());

        // connect with us section
        Locator DivBox = page.locator(
                "//div[@class=' connect-with-us-mentor d-flex align-items-center justify-content-between px-4']");
        DivBox.scrollIntoViewIfNeeded();
        Assert.assertTrue(DivBox.isVisible());
        Assert.assertTrue(page.locator("//button[text()='Connect Now']").isVisible());

        log.info("===== Mentorship ShortList Functionality Test Completed =====");
    }
}
