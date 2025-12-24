package com.promilo.automation.mentorship.utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.AboutMePage;

public class AboutMeSection {

    private Page page;
    private AboutMePage aboutMe;

    public AboutMeSection(Page page) {
        this.page = page;
        this.aboutMe = new AboutMePage(page);
    }

    public void fillAboutMe(String aboutMeText) {

        if (aboutMeText != null && !aboutMeText.isEmpty()) {
            aboutMe.textArea().fill(aboutMeText);
        } else {
            aboutMe.textArea().fill("About me data from excel not provided.");
        }

        aboutMe.addTittle().click();
        aboutMe.focus().click();
        aboutMe.fluentIn().click();
        aboutMe.addButton().click();

        aboutMe.aboutmeOptions().nth(1).click();
        aboutMe.textArea().fill(aboutMeText == null ? "" : aboutMeText);

        aboutMe.aboutmeOptions().nth(2).click();
        aboutMe.textArea().fill(aboutMeText == null ? "" : aboutMeText);

        // ---- FORCE CLICK ADDED ----
        aboutMe.saveButton()
                .nth(2)
                .click(new Locator.ClickOptions().setForce(true));

        /*page.waitForTimeout(5000);
        // ---- Added Toaster Wait (Action Logic Only) ----
        Locator successToast = page.locator("//div[text()='Successfully Saved']");
        successToast.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        
        */
    }
}
