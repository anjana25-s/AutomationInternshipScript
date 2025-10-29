package com.promilo.automation.advertiser;

import java.io.IOException;
import org.testng.annotations.Test;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentee.MeetupsListingPage;
import com.promilo.automation.pageobjects.signuplogin.DashboardPage;
import com.promilo.automation.pageobjects.signuplogin.LandingPage;
import com.promilo.automation.resources.Baseclass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MentorFilterTest extends Baseclass {

    private static final Logger log = LogManager.getLogger(MentorFilterTest.class);

    @Test
    public void MentorFilterTestlogic() throws IOException {

        log.info("===== Starting Mentor Filter Test =====");

        // -------------------- Initialize Page --------------------
        Page page = initializePlaywright();
        page.navigate(prop.getProperty("url"));
        log.info("Navigated to URL: " + prop.getProperty("url"));
        page.waitForTimeout(2000);

        // -------------------- Landing Page Popup --------------------
        LandingPage landingPage = new LandingPage(page);
        landingPage.getPopup().click(new Locator.ClickOptions().setForce(true));
        log.info("Closed landing page popup");
        page.waitForTimeout(2000);

        // -------------------- Dashboard Mentorship Module --------------------
        DashboardPage mentorshipClick = new DashboardPage(page);
        mentorshipClick.mentorships().click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked on Mentorship module");
        page.waitForTimeout(2000);

        MeetupsListingPage filterLogic = new MeetupsListingPage(page);

        // -------------------- Location Filter --------------------
        Locator location = filterLogic.location();
        location.scrollIntoViewIfNeeded();
        location.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Location filter dropdown");
        page.waitForTimeout(2000);

        filterLogic.locationName().scrollIntoViewIfNeeded();
        filterLogic.locationName().click(new Locator.ClickOptions().setForce(true));
        log.info("Selected a location");
        page.waitForTimeout(2000);

        // -------------------- Domain Filter --------------------
        page.waitForTimeout(5000);
        Locator domain = filterLogic.domain();
        domain.scrollIntoViewIfNeeded();
        domain.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Domain filter dropdown");
        page.waitForTimeout(2000);

        filterLogic.coursedomnainName().scrollIntoViewIfNeeded();
        filterLogic.coursedomnainName().click(new Locator.ClickOptions().setForce(true));
        log.info("Selected a domain");
        page.waitForTimeout(2000);

        // -------------------- Skill Filter --------------------
        page.waitForTimeout(3000);
        Locator skillButton = filterLogic.skill();

        // Wait until skill button is visible and enabled
        int retry = 0;
        while ((!skillButton.isVisible() || !skillButton.isEnabled()) && retry < 10) {
            page.waitForTimeout(500);
            retry++;
        }

        skillButton.scrollIntoViewIfNeeded();
        skillButton.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Skill filter dropdown");

        // Wait for skill search box
        Locator skillSearch = filterLogic.searchskill();
        retry = 0;
        while (!skillSearch.isVisible() && retry < 10) {
            page.waitForTimeout(500);
            retry++;
        }

        skillSearch.fill("se");
        page.keyboard().press("Enter");
        log.info("Entered skill search text and pressed Enter");
        page.waitForTimeout(2000);

        // -------------------- Experience Filter --------------------
        page.waitForTimeout(3000);
        Locator experienceButton = filterLogic.Experience();

        retry = 0;
        while ((!experienceButton.isVisible() || !experienceButton.isEnabled()) && retry < 10) {
            page.waitForTimeout(500);
            retry++;
        }

        experienceButton.scrollIntoViewIfNeeded();
        experienceButton.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Experience filter dropdown");
        page.waitForTimeout(2000);

        // Click all experience options
        Locator expOptions = filterLogic.experianceOptions();
        int totalExp = expOptions.count();
        for (int i = 0; i < totalExp; i++) {
            Locator opt = expOptions.nth(i);
            retry = 0;
            while ((!opt.isVisible() || !opt.isEnabled()) && retry < 10) {
                page.waitForTimeout(500);
                retry++;
            }
            opt.scrollIntoViewIfNeeded();
            opt.click(new Locator.ClickOptions().setForce(true));
            log.info("Clicked experience option: " + (i + 1));
            page.waitForTimeout(2000); // wait for page reload
        }

        // -------------------- Associated Company Filter --------------------
        page.waitForTimeout(3000);
        Locator companyButton = filterLogic.associatedCompany();

        retry = 0;
        while ((!companyButton.isVisible() || !companyButton.isEnabled()) && retry < 10) {
            page.waitForTimeout(500);
            retry++;
        }

        companyButton.scrollIntoViewIfNeeded();
        companyButton.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Associated Company filter dropdown");
        page.waitForTimeout(2000);

        Locator companyInput = filterLogic.associatedCompanyoptions();
        retry = 0;
        while (!companyInput.isVisible() && retry < 10) {
            page.waitForTimeout(500);
            retry++;
        }

        companyInput.fill("walmart");
        page.keyboard().press("Enter");
        log.info("Entered Associated Company name");
        page.waitForTimeout(2000);

        // -------------------- Cost Filter --------------------
        Locator costButton = filterLogic.cost();
        costButton.scrollIntoViewIfNeeded();
        costButton.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Cost filter dropdown");
        page.waitForTimeout(2000);

        String[] priceFilters = {
            "Free", "Less than ₹100", "₹100 - ₹250", "₹250 - ₹500",
            "₹500 - ₹1000", "₹1000 - ₹3000", "Above ₹3000"
        };

        for (String filter : priceFilters) {
            Locator priceOption = page.locator("span:has-text('" + filter + "')").first();
            priceOption.scrollIntoViewIfNeeded();
            priceOption.click(new Locator.ClickOptions().setForce(true));
            log.info("Applied cost filter: " + filter);
            page.waitForTimeout(2000); // wait for page reload
        }

        // -------------------- Language Filter --------------------
        Locator language = filterLogic.language();
        language.scrollIntoViewIfNeeded();
        language.click(new Locator.ClickOptions().setForce(true));
        log.info("Clicked Language filter dropdown");
        page.waitForTimeout(2000);

        // Click all language options
        Locator langOptions = filterLogic.languageOptions();
        int total = langOptions.count();
        for (int i = 0; i < total; i++) {
            Locator opt = langOptions.nth(i);
            retry = 0;
            while ((!opt.isVisible() || !opt.isEnabled()) && retry < 10) {
                page.waitForTimeout(500);
                retry++;
            }
            opt.scrollIntoViewIfNeeded();
            opt.click(new Locator.ClickOptions().setForce(true));
            log.info("Clicked language option: " + (i + 1));
            page.waitForTimeout(2000); // wait after page reload
        }

        log.info("===== Mentor Filter Test Completed Successfully =====");
    }
}
