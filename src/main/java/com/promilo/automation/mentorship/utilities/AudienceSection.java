package com.promilo.automation.mentorship.utilities;

import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AudienceSection {

    private Page page;
    private com.promilo.automation.mentorship.mentor.myAudiencePage audience;

    public AudienceSection(Page page) {
        this.page = page;
        this.audience = new com.promilo.automation.mentorship.mentor.myAudiencePage(page);
    }

    public void fillAudience(String industry, String functionalArea, String relevantTitle, String audienceLocation,
                             String keywordText, String ageFrom, String ageTo) {

        audience.audienceIndustry().click();
        audience.industryOption().click();

        audience.functionalArea().click();
        page.getByText(functionalArea).click();

        audience.relevantTitle().click();
        audience.relevantTitleOption().nth(1).click();

        audience.audienceLocation().click();
        audience.audiencelocationOption().first().click();

        // Keywords - random word if not provided
        String keyword = keywordText != null ? keywordText : generateRandomWord(8);
        audience.Keywords().fill(keyword);
        page.keyboard().press("Enter");

        if (ageFrom != null && !ageFrom.isEmpty()) {
            Locator minAge = page.locator(".form-floating.override-mb-0 > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container").first();
            minAge.scrollIntoViewIfNeeded();
            minAge.click();
            page.keyboard().type(ageFrom);
            page.keyboard().press("Enter");
        }

        if (ageTo != null && !ageTo.isEmpty()) {
            Locator maxAge = page.locator("div:nth-child(7) > .form-floating > .react-select-container > .react-select__control > .react-select__value-container > .react-select__input-container");
            maxAge.scrollIntoViewIfNeeded();
            maxAge.click();
            page.keyboard().type(ageTo);
            page.keyboard().press("Enter");
        }

        audience.Savebutton().click(new Locator.ClickOptions().setForce(true));
    }

    private String generateRandomWord(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return sb.toString();
    }
}
