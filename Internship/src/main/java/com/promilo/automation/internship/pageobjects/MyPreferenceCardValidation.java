package com.promilo.automation.internship.pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MyPreferenceCardValidation {

    private final Page page;

    private final Locator internshipRole;
    private final Locator brandName;
    private final Locator location;
    private final Locator duration;
    private final Locator stipend;
    private final Locator serviceName;
    private final Locator completedStatusTag;
    private final Locator rejectedStatusTag;

    public MyPreferenceCardValidation(Page page) {
        this.page = page;

     
        this.internshipRole = page.locator(".preferance-header-text");
        this.brandName =page.locator("//div[contains(@class,'preferance-card')]//span").first();
        this.location = page.locator("//div[contains(@class,'preferance')]//span").last();
        this.duration = page.locator("//div[normalize-space()='Duration']/following-sibling::div");
        this.stipend = page.locator("//div[contains(@class,'preferance')]//span").nth(2);
        this.serviceName =page.locator(".service-name");
        this.completedStatusTag =page.locator("//span[text()='Completed']");
        this.rejectedStatusTag =page.locator("//span[text()='Rejected']");
    }

    public Locator internshipRole() {
        return internshipRole;
    }

    public Locator brandName() {
        return brandName;
    }

    public Locator location() {
        return location;
    }

    public Locator duration() {
        return duration;
    }

    public Locator stipend() {
        return stipend;
    }

    public Locator serviceName() {
        return serviceName;
    }

    public Locator completedStatusTag() {
        return completedStatusTag;
    }

    public Locator rejectedStatusTag() {
        return rejectedStatusTag;
}}