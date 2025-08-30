package com.promilo.automation.pageobjects.myresume;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ResumeHeadLinepage {

    private final Page page;

    private final Locator Headlinearea;
    private final Locator Headlinesavebutton;

    public ResumeHeadLinepage(Page page) {
        this.page = page;

        this.Headlinearea = page.locator("//textarea[@placeholder='Resume headline ...']");
        this.Headlinesavebutton = page.locator("//button[text()='Save']");
    }

    public Locator getResumeHeadlineTextArea() {
        return Headlinearea;
    }

    public Locator getResumeHeadlineSaveButton() {
        return Headlinesavebutton;
    }
}
