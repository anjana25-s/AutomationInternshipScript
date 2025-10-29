package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DownloadsIntrestPage {

    private final Locator coursesMenu;
    private final Locator downloadBrochureBtn;
    private final Locator userNameField;
    private final Locator userMobileField;
    private final Locator userEmailField;
    private final Locator finalDownloadBtn;
    private final Locator verifyProceedBtn;
    private final Locator thankYouPopup;
    private final Locator thankYouCloseIcon;
    private final Locator myInterestTab;
    private final Locator myPreferenceTab;
    private final Locator downloadsTab;
    private final Locator totalResults;

    private final Page page;

    public DownloadsIntrestPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.downloadBrochureBtn = page.locator("//span[text()='Download Brochure']").first();
        this.userNameField = page.locator("//input[@name='userName']").nth(1);
        this.userMobileField = page.locator("//input[@name='userMobile']").nth(1);
        this.userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        this.finalDownloadBtn = page.locator("//button[text()='Download Brochure']");
        this.verifyProceedBtn = page.locator("//button[text()='Verify & Proceed']");
        this.thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );
        this.thankYouCloseIcon = page.locator("img[alt='closeIcon Ask us']");
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceTab = page.locator("//div[text()='My Preference']");
        this.downloadsTab = page.locator("//div[text()='Downloads']");
        this.totalResults = page.locator("//span[text()='Total Results of ']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator downloadBrochureBtn() { return downloadBrochureBtn; }
    public Locator userNameField() { return userNameField; }
    public Locator userMobileField() { return userMobileField; }
    public Locator userEmailField() { return userEmailField; }
    public Locator finalDownloadBtn() { return finalDownloadBtn; }
    public Locator verifyProceedBtn() { return verifyProceedBtn; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator thankYouCloseIcon() { return thankYouCloseIcon; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceTab() { return myPreferenceTab; }
    public Locator downloadsTab() { return downloadsTab; }
    public Locator totalResults() { return totalResults; }
}
