package com.automation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyInterestPage {

    private final Page page;

    public MyInterestPage(Page page) {
        this.page = page;
    }

    public Locator getInterestCard() {
        return page.locator("//div[contains(@class,'my-interest-card-contianer')]");
    }

    public Locator getCardTitle() {
        return page.locator("//div[contains(@class,'my-interest-card-contianer')]//a[contains(@class,'card-content-header-text')]");
    }

    public Locator getCardCompany() {
        return page.locator("//div[contains(@class,'my-interest-card-contianer')]//div[contains(@class,'category-text-interest-card')][2]");
    }

    public Locator getCardStatus() {
        return page.locator("//div[contains(@class,'my-interest-status-tag')]");
    }

    public Locator getMeetingDate() {
        return page.locator(
            "//div[contains(@class,'card_detail-label') and normalize-space()='Meeting Date']" +
            "/following-sibling::div[contains(@class,'card_detail-value')]"
        );
    }

    public Locator getMeetingTime() {
        return page.locator(
            "//div[contains(@class,'card_detail-label') and normalize-space()='Meeting Time']" +
            "/following-sibling::div[contains(@class,'card_detail-value')]"
        );
    }

}
