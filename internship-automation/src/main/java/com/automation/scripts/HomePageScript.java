package com.automation.scripts;

import com.microsoft.playwright.Page;
import com.automation.pages.HomePageActions;

public class HomePageScript {

    private final HomePageActions actions;

    public HomePageScript(Page page) {
        this.actions = new HomePageActions(page);
    }

    public void clickMaybeLater() {
        actions.clickMaybeLater();
    }

    public void clickInternships() {
        actions.clickInternshipsTab();
    }

    public void selectInternship(String title) {
        actions.selectInternshipCard(title);
    }

    // Complete flow for selecting internship quickly
    public void completeInternshipSelectionFlow(String title) {
        clickMaybeLater();
        clickInternships();
        selectInternship(title);
    }
}
