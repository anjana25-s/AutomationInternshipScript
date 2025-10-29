package com.promilo.automation.courses.intrestspages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FreeVideoCounsellingPage {

    private final Locator coursesMenu;
    private final Locator seeMoreLink;
    private final Locator lpuOption;
    private final Locator freeVideoCounsellingBtn;
    private final Locator userNameField;
    private final Locator userMobileField;
    private final Locator userEmailField;
    private final Locator preferredLocationDropdown;
    private final Locator locationOptions;
    private final Locator textContent;
    private final Locator enableNotificationsCheckbox;
    private final Locator finalCounsellingBtn;
    private final Locator verifyProceedBtn;
    private final Locator datePickerDay;
    private final Locator timeSlot;
    private final Locator submitBtn;
    private final Locator thankYouPopup;
    private final Locator thankYouCloseIcon;
    private final Locator myInterestTab;
    private final Locator myPreferenceCardBody;
    private final Locator sendReminderBtn;
    private final Locator reminderModal;
    private final Locator reminderConfirmationText;

    private final Page page;

    public FreeVideoCounsellingPage(Page page) {
        this.page = page;

        this.coursesMenu = page.locator("//a[text()='Courses']");
        this.seeMoreLink = page.locator("//a[text()='See More']").nth(1);
        this.lpuOption = page.locator("//div[text()='LPU']").first();
        this.freeVideoCounsellingBtn = page.locator("//span[text()='Free Video Counselling']");
        this.userNameField = page.locator("//input[@name='userName']").nth(1);
        this.userMobileField = page.locator("//input[@name='userMobile']").nth(1);
        this.userEmailField = page.locator("//input[@id='userEmail']").nth(1);
        this.preferredLocationDropdown = page.locator("[id='preferredLocation']").nth(1);
        this.locationOptions = page.locator("//div[@class='option w-100']");
        this.textContent = page.locator("//div[@class='text-content']");
        this.enableNotificationsCheckbox = page.locator("//label[@for='enableNotifications']");
        this.finalCounsellingBtn = page.locator("//button[text()='Free Video Counselling']").nth(1);
        this.verifyProceedBtn = page.locator("//button[text()='Verify & Proceed']");
        this.datePickerDay = page.locator("//span[@class='flatpickr-day']").first();
        this.timeSlot = page.locator("//li[@class='time-slot-box list-group-item']").first();
        this.submitBtn = page.locator("//button[text()='Submit']").nth(1);
        this.thankYouPopup = page.locator(
            "//div[translate(normalize-space(text()), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = 'thank you!']"
        );
        this.thankYouCloseIcon = page.locator("//img[@alt='closeIcon Ask us']");
        this.myInterestTab = page.locator("//span[text()='My Interest']");
        this.myPreferenceCardBody = page.locator("//div[@class='my-preferance-card-body card-body']");
        this.sendReminderBtn = page.locator("//button[text()='Send Reminder']");
        this.reminderModal = page.locator("//div[@class='pt-0 px-1 modal-body']");
        this.reminderConfirmationText = page.locator("//p[@class='text-capitalize text-gray-500 font-16 fw-bold text-center pt-50']");
    }

    public Locator coursesMenu() { return coursesMenu; }
    public Locator seeMoreLink() { return seeMoreLink; }
    public Locator lpuOption() { return lpuOption; }
    public Locator freeVideoCounsellingBtn() { return freeVideoCounsellingBtn; }
    public Locator userNameField() { return userNameField; }
    public Locator userMobileField() { return userMobileField; }
    public Locator userEmailField() { return userEmailField; }
    public Locator preferredLocationDropdown() { return preferredLocationDropdown; }
    public Locator locationOptions() { return locationOptions; }
    public Locator textContent() { return textContent; }
    public Locator enableNotificationsCheckbox() { return enableNotificationsCheckbox; }
    public Locator finalCounsellingBtn() { return finalCounsellingBtn; }
    public Locator verifyProceedBtn() { return verifyProceedBtn; }
    public Locator datePickerDay() { return datePickerDay; }
    public Locator timeSlot() { return timeSlot; }
    public Locator submitBtn() { return submitBtn; }
    public Locator thankYouPopup() { return thankYouPopup; }
    public Locator thankYouCloseIcon() { return thankYouCloseIcon; }
    public Locator myInterestTab() { return myInterestTab; }
    public Locator myPreferenceCardBody() { return myPreferenceCardBody; }
    public Locator sendReminderBtn() { return sendReminderBtn; }
    public Locator reminderModal() { return reminderModal; }
    public Locator reminderConfirmationText() { return reminderConfirmationText; }
}
