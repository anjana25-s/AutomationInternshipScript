package com.promilo.automation.advertiser.jobcampaign;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SaveAndPreviewPage {

    private final Page page;

    // ====== LOCATORS ======
    private final Locator title;
    private final Locator subTitle;
    private final Locator salary;
    private final Locator experience;
    private final Locator location;
    private final Locator workMode;

    private final Locator jobDescriptionTab;
    private final Locator benefitsTab;
    private final Locator ckContent;

    private final Locator companyName;
    private final Locator companyType;
    private final Locator brandName;
    private final Locator companySize;

    private final Locator aboutCompanyTitle;
    private final Locator paragraph9;

    private final Locator closeButton;
    private final Locator publishButton;

    // ====== CONSTRUCTOR ======
    public SaveAndPreviewPage(Page page) {
        this.page = page;

        this.title = page.locator("//p[@class='font-28 text-black fw-500']");
        this.subTitle = page.locator("//p[@class='font-16 text-dark-grey  fw-500']");
        this.salary = page.locator("//div[@class='d-flex logo-text col-6']").first();

        this.experience = page.locator("//div[@class='font-16 ']").nth(1);
        this.location = page.locator("//div[@class='font-16 ']").nth(2);
        this.workMode = page.locator("//div[@class='font-16 ']").nth(3);

        this.jobDescriptionTab = page.locator("//a[text()='Job Description']");
        this.benefitsTab = page.locator("//a[text()='Benefits & Perks']");
        this.ckContent = page.locator("//div[@class='ck-content']").first();

        this.companyName = page.locator("//div[@class='font-14 ']").first();
        this.companyType = page.locator("//div[@class='font-14 ']").nth(1);
        this.brandName = page.locator("//div[@class='font-14 ']").nth(2);
        this.companySize = page.locator("//div[@class='font-14 ']").nth(3);

        this.aboutCompanyTitle = page.locator("//p[@class='font-20 text-black fw-700']");
        this.paragraph9 = page.locator("(//p)[9]");

        this.closeButton = page.locator("//img[@alt='close']");
        this.publishButton = page.locator("//span[text()='Publish']");
    }

    // ====== GETTERS ======
    public Locator title() { return title; }
    public Locator subTitle() { return subTitle; }
    public Locator salary() { return salary; }

    public Locator experience() { return experience; }
    public Locator location() { return location; }
    public Locator workMode() { return workMode; }

    public Locator jobDescriptionTab() { return jobDescriptionTab; }
    public Locator benefitsTab() { return benefitsTab; }
    public Locator ckContent() { return ckContent; }

    public Locator companyName() { return companyName; }
    public Locator companyType() { return companyType; }
    public Locator brandName() { return brandName; }
    public Locator companySize() { return companySize; }

    public Locator aboutCompanyTitle() { return aboutCompanyTitle; }
    public Locator paragraph9() { return paragraph9; }

    public Locator closeButton() { return closeButton; }
    public Locator publishButton() { return publishButton; }
}
