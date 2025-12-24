package com.promilo.automation.mentorship.utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.promilo.automation.mentorship.mentor.CampaignDetails;

public class CampaignDetailsSection {

    private Page page;
    private CampaignDetails campaign;

    public CampaignDetailsSection(Page page) {
        this.page = page;
        this.campaign = new CampaignDetails(page);
    }

    public void fillCampaignDetails(String campaignName, String startDate, String endDate, String skill) {
        if (campaignName != null) campaign.campaignName().fill(campaignName);
        if (startDate != null) campaign.startDate().fill(startDate);
        if (endDate != null) {
            Locator endDateField = campaign.endDate();
            endDateField.scrollIntoViewIfNeeded();
            endDateField.fill(endDate);
        }
        if (skill != null && !skill.isEmpty()) {
            campaign.clickheretoAddskills().click();
            campaign.skills().fill(skill);
            page.keyboard().press("Enter");
        }
        page.waitForTimeout(4000);
        Locator saveButton=campaign.saveButton().nth(1);
        saveButton.scrollIntoViewIfNeeded();
        saveButton.click(new Locator.ClickOptions().setForce(true));
    }
}
